package com.redn.connect.as2.test.util;

import java.io.File;
import java.io.IOException;

import org.mule.util.FileUtils;

public class FileUtil {

	public static String readFile(String fileName) throws IOException
	{
		String fileContent = FileUtils.readFileToString(new File(ConnectAS2TestConstants.TEST_FILE_LOCATION+fileName),"UTF-8");
		return fileContent;
	}
}
