package com.pool.memory.tree;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestJUnit {
	
	   String message = "Hello World";	
	   MemoryPool messageUtil = new MemoryPool(message);

	   @Test
	   public void testPrintMessage() {	  
	      assertEquals(message,messageUtil.printMessage());
	   }
	}