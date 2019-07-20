package szu.vander.rpc.demo;

import java.awt.Point;

/**
 * @author : caiwj
 * @date :   2019/7/15
 * @description : DemoService
 */
public interface DemoService {
	/**
	 * 测试方法1
	 * @param name
	 * @return
	 */
	String sayHello(String name);

	/**
	 * 测试方法2
	 * @param p
	 * @param multi
	 * @return
	 */
	Point multiPoint(Point p, int multi);
}
