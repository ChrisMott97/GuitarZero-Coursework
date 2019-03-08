//package org.gsep.manager;
//
//import static org.junit.Assert.*;
//import junit.framework.*;
//import static org.junit.Assert.assertEquals;
//
//import java.io.ByteArrayOutputStream;
//import java.io.PrintStream;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//public class StoreManagerFrameTest {
//
//	StoreManagerFrame smf = new StoreManagerFrame();
//	private String f1= getClass().getResource("/test.txt").getFile();
//	private String f2= getClass().getResource("/test.jpg").getFile();
//
//
//
//
//	@Test
//	public void test1CheckF(){
//
//		  //Text file - check if file is recognised as not existing
//		  assertEquals("falseString", smf.checkF("falseFile", 1,1));
//
//		  //Text file- check if file is recognised as not having the correct suffix
//		  assertEquals("falseFormat", smf.checkF(f2, 1,2));
//
//	}
//
//	@Test
//	public void test2CheckF(){
//
//		  //Text file - check if file is recognised as not existing
//		  assertEquals("falseString", smf.checkF("falseFile", 2,1));
//
//		  //Text file- check if file is recognised as not having the correct suffix
//		  assertEquals("falseFormat", smf.checkF(f1, 2,2));
//
//	}
//
//	@Test
//	public void test3CheckF(){
//
//		  //Text file - check if file is recognised as not existing
//		  assertEquals("falseString", smf.checkF("falseFile", 3,1));
//
//		  //Text file- check if file is recognised as not having the correct suffix
//		  assertEquals("falseFormat", smf.checkF(f2, 3,2));
//
//	}
//
//}