package szu.vander.rpc.util;
/**
 * PropertiesUtils
 * 
 */

import java.io.IOException;
import java.util.Properties;

/**
 * @author : caiwj
 * @date :   2019/7/15
 * @description : 获取Properties配置文件的内容
 */
public class PropertiesUtils {

	private static Properties properties;

	static {
		try {
			properties = new Properties();
			properties.load(PropertiesUtils.class.getResourceAsStream("/app.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getProperties(String key) {
		return (String) properties.getProperty(key);
	}

}
