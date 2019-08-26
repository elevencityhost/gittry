package com.redn.test.input;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;
/*************************************************************************************************************************
 * File Name 	: InputFactory.java 
 * Author 		: Nagswapna Yelluru 
 * Date 		: 13-Oct-2015 
 * Description 	: The purpose of this class is load a samples enterprise messages xml file 
 * from location i.e src/test/resources/samples
 *************************************************************************************************************************/

public class InputFactory {

	public static String getMessage(String fileName) throws IOException
	{
		final String fileLocation = "src/test/resources/testInput";
		Scanner in = new Scanner(new FileReader(fileLocation+"/"+fileName));
		FileInputStream inputStream = new FileInputStream(fileLocation+"/"+fileName);
		String everything = IOUtils.toString(inputStream);
		return everything;
	}
}
