package main;

import com.beust.jcommander.internal.Lists;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RunTestNGXML 
{
	static String tesNGFileName = "testng.xml";
	public static void main(String[] args) 
	{
		
		TestNG testng = new TestNG();
        TestListenerAdapter adapter = new TestListenerAdapter();
        List<String> suites = new ArrayList<String>();
        System.out.println("Executing TestNG Suite File = "+args[0]);
        testng.addListener(adapter);
        suites.add(args[0]);
        testng.setTestSuites(suites);
        testng.setParallel("parallel");
        testng.setSuiteThreadPoolSize(5);
        testng.setOutputDirectory(System.getProperty("user.dir")+"/Reports");
        testng.run();
		
		
/*		TestNG testng = new TestNG();
		List<String> suites = Lists.newArrayList();
		System.out.println("Executing TestNG Suite File = "+args[0]);
		suites.add(args[0]);
		testng.setTestSuites(suites);
		testng.run();*/
		
		
		
/*		TestNG testng = new TestNG();
		List<String> suites = Lists.newArrayList();
		//CreateTestNGXML("ilbq7890.eaas.amdocs.com", "28501", "Extra Param Value");
		CreateTestNGXML(args[0], args[1], args[2]);
		String testNGFile = System.getProperty("user.dir") + "\\"+tesNGFileName;		
		System.out.println("Executing TestNG Suite File = "+testNGFile);
		suites.add(testNGFile);
		testng.setTestSuites(suites);
		testng.run();*/
	}

	public static void CreateTestNGXML(String HostName,String Port, String ExtraParam)
	{
		try
		{
			Map<String, String> testClassParameters = new HashMap<>();
			testClassParameters.put("HostName", HostName);
			testClassParameters.put("Port", Port);
			testClassParameters.put("ExtraParam", ExtraParam);


			XmlSuite suite = new XmlSuite();
			suite.setName("NewTours");
			suite.setParallel("none");
			suite.setThreadCount(2);
			suite.setVerbose(2);
			suite.setParameters(testClassParameters);

			XmlTest test = new XmlTest(suite);
			test.setName("New Tours Test Execution");
			test.setPreserveOrder("true");
			//test.addParameter("testParam1", "clock");

			XmlClass testClass = null;

			ArrayList<XmlClass> classes = new ArrayList<XmlClass>();
			ArrayList<XmlInclude> methodsToRun = new ArrayList<XmlInclude>();

			testClass = new XmlClass();
			testClass.setName("com.testmadness.testcase.BookFlightTest");
			/*		methodsToRun.add(new XmlInclude("Method01"));
			methodsToRun.add(new XmlInclude("Method02"));
			methodsToRun.add(new XmlInclude("Method03"));
			methodsToRun.add(new XmlInclude("Method04"));
			methodsToRun.add(new XmlInclude("Method05"));

			testClass.setIncludedMethods(methodsToRun);*/
			classes.add(testClass);
			test.setXmlClasses(classes);

			methodsToRun.clear();

			File file = new File(tesNGFileName);
			System.out.println("file - "+file);

			FileWriter writer = new FileWriter(file);
			writer.write(suite.toXml());
			System.out.println("\n"+suite.toXml());
			writer.close();
		} catch (IOException e)
		{
			System.out.println("Error while creating "+tesNGFileName);
			e.printStackTrace();
		} 
	}
}