package services;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PersonServiceFactory {

	public static IPersonService newInstance() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		String filePath = "src/cfg/service.cfg";
		
		FileInputStream fileInputStream = new FileInputStream(filePath);
		Properties properties = new Properties();
		properties.load(fileInputStream);
		
		String classPath = properties.getProperty("class");
		
		System.out.println("classPath " + classPath);
		
		Class<?> metaClass = Class.forName(classPath);
		
		return (IPersonService) metaClass.newInstance();
	}
}
