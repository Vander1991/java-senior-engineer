package szu.vander.demo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

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

	/**
	 * 锁重入计数
	 */
	private ThreadLocal<Integer> reentrantCount = new ThreadLocal<>();

	public ZooKeeperDistributeLock(String lockPath) {
		super();
		this.lockPath = lockPath;

		client = new ZkClient("localhost:2181");
		client.setZkSerializer(new MyZkSerializer());
	}

	public boolean tryLock() { // 不会阻塞

		if (this.reentrantCount.get() != null) {
			int count = this.reentrantCount.get();
			if (count > 0) {
				this.reentrantCount.set(++count);
				return true;
			}
		}
		// 创建节点
		try {
			client.createEphemeral(lockPath);
			this.reentrantCount.set(1);
		} catch (ZkNodeExistsException e) {
			return false;
		}
		return true;
	}

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

			public void handleDataDeleted(String dataPath) throws Exception {
				System.out.println("----收到节点被删除了-------------");
				cdl.countDown();
			}

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

	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub

	}

	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		// 并发数
		int currency = 50;

		// 循环屏障
		CyclicBarrier cb = new CyclicBarrier(currency);

		// 多线程模拟高并发
		for (int i = 0; i < currency; i++) {
			new Thread(new Runnable() {
				public void run() {

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
				}
			}).start();

		}
	}
}
