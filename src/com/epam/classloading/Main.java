package com.epam.classloading;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.Scanner;

import org.apache.log4j.Logger;


public class Main {

	static final Logger logger = Logger.getLogger(Main.class);
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, MalformedURLException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		//com.epam.classloading.TestModel
		//logger.addAppender(new FileAppender());
		
		Scanner scanner = new Scanner(System.in);
		
		String option = "";
		do {
			
			if(option.equals("1")){
				System.out.println("=====load v1=====\n");
				
				ClassLoader loader = new TestClassLoader("D:/eclipse/workspace2/JMP/lib/test_v1.jar");
	            process(loader);
				
	            
			} else if(option.equals("2")){
				System.out.println("=====load v2=====\n");
				
				ClassLoader loader = new TestClassLoader();
	            process(loader);
	            
	            
			} else if(option.equals("q")){
				System.out.println("========quit=========");
				break;
			}
			
			System.out.println("Choose option:");
			System.out.println("[1] - load v1");
			System.out.println("[2] - load v2");
			System.out.println("[q] - quit");
			System.out.print("Your option: ");
			
		} while ((option=scanner.next())!="q");
		
		scanner.close();

	}

	private static void process(ClassLoader loader) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		Class clazz = Class.forName("com.epam.classloading.TestModel", true, loader);
		Object object = (Object) clazz.newInstance();
		logger.info(object);
		logger.info(clazz.getMethod("getVersion").invoke(object));
		
		System.out.println("\n=====================\n");
	}

}
