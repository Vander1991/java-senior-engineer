package szu.vander.rpc.demo.provider;

import szu.vander.rpc.demo.DemoService;

import java.awt.Point;


public class DemoServiceImpl implements DemoService {

	@Override
	public String sayHello(String name) {
		return "Hello " + name;
	}

	@Override
	public Point multiPoint(Point p, int multi) {
		p.x = p.x * multi;
		p.y = p.y * multi;
		return p;
	}
}
