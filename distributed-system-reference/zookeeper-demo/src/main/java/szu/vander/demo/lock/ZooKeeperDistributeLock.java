package szu.vander.demo.lock;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import szu.vander.demo.watch.MyZkSerializer;



/**
 * @author : caiwj
 * @date :   2019/7/5
 * @description :
 */
public class ZooKeeperDistributeLock implements Lock {

	private String lockPath;

	private ZkClient client;

	private final static String serverString = "192.168.118.168:2181";

	/**
	 * 锁重入计数，利用threadLocal的特性
	 */
	private ThreadLocal<Integer> reentrantCount = new ThreadLocal<>();

	public ZooKeeperDistributeLock(String lockPath) {
		super();
		this.lockPath = lockPath;

		client = new ZkClient(serverString);
		client.setZkSerializer(new MyZkSerializer());
	}

	@Override
	public boolean tryLock() { // 不会阻塞

	    // 若重入次数不为空，说明此线程已经获取到锁
		if (this.reentrantCount.get() != null) {
			int count = this.reentrantCount.get();
			// 若重入次数大于0，说明已经获取到了锁
			if (count > 0) {
				this.reentrantCount.set(++count);
				return true;
			}
		}
		// 创建节点
		try {
		    // 创建临时节点
			client.createEphemeral(lockPath);
			this.reentrantCount.set(1);
		} catch (ZkNodeExistsException e) {
		    // 创建不成功说明没有获取到锁
			return false;
		}
		return true;
	}

	@Override
	public void unlock() {
		// 重入的释放锁处理
		if (this.reentrantCount.get() != null) {
			int count = this.reentrantCount.get();
			if (count > 1) {
				this.reentrantCount.set(--count);
				return;
			} else {
				this.reentrantCount.set(null);
			}
		}
		client.delete(lockPath);
	}

	@Override
	public void lock() { // 如果获取不到锁，阻塞等待
		if (!tryLock()) {
			// 没获得锁，阻塞自己
			waitForLock();
			// 再次尝试
			lock();
		}

	}

	private void waitForLock() {

		final CountDownLatch cdl = new CountDownLatch(1);

		IZkDataListener listener = new IZkDataListener() {

		    @Override
			public void handleDataDeleted(String dataPath) throws Exception {
				System.out.println("----收到节点被删除了-------------");
				cdl.countDown();
			}
            @Override
			public void handleDataChange(String dataPath, Object data) throws Exception {
			}
		};

		client.subscribeDataChanges(lockPath, listener);

		// 阻塞自己
		if (this.client.exists(lockPath)) {
			try {
				cdl.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// 取消注册
		client.unsubscribeDataChanges(lockPath, listener);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		// 并发数
		int currency = 50;

		// 循环屏障，循环中会一直等待，循环结束后，所有线程才继续往下走
		CyclicBarrier cb = new CyclicBarrier(currency);

        // 创建线程池
        ExecutorService threadPoolExecutor = ThreadPoolConfig.createThreadPool();

		// 多线程模拟高并发
		for (int i = 0; i < currency; i++) {
            threadPoolExecutor.execute(()-> {
                System.out.println(Thread.currentThread().getName() + "---------我准备好---------------");
                // 等待一起出发
                try {
                    cb.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                ZooKeeperDistributeLock lock = new ZooKeeperDistributeLock("/distLock11");

                try {
                    lock.lock();
                    System.out.println(Thread.currentThread().getName() + " 获得锁！");
                } finally {
                    lock.unlock();
                }
            });
		}
        threadPoolExecutor.shutdown();
	}

}
