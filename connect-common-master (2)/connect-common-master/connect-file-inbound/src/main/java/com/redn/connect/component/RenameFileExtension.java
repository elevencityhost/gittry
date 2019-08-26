package com.redn.connect.component;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.redn.connect.vo.OutputProps;
/**
 * * @author Sai Prasad Jonnala 
 *  The Java class is used to Rename the File
 * *
 */
public class RenameFileExtension {
	DateFormat utcDateFormat =  new SimpleDateFormat("yyyyMMdd.HHmmssSSS");
	Date date = new Date();
	public void renameFileWithExtension(String oldFileName, String extension) {


		try {
			File file = new File(oldFileName);
			File file2 = new File(oldFileName + extension+utcDateFormat.format(date));
			file.renameTo(file2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void moveFile(String oldFileName, String newFileName) {

		try {
			File file = new File(oldFileName);
			File file2 = new File(newFileName);
			if (file2.exists())
				file2.delete();
			file.renameTo(file2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void renameInputFilesForTrigger(String inputFiles, String inputExtension, String outputExtension,
			OutputProps outputProps) {

		try {
			if (inputFiles != null && inputFiles.length() > 0 && outputProps != null) {
				boolean inputExtensionFlag = (outputProps.isCustomInterface())
						? outputProps.getCustomProperties().isUseInProcessExtension() : true;
				String[] inputFilesArray = inputFiles.split(",");
				File file;
				File file2;
				if (inputExtensionFlag) {
					for (String oldFileName : inputFilesArray) {

						file = new File(oldFileName + inputExtension);
						file2 = new File(oldFileName + inputExtension + outputExtension);
						file.renameTo(file2);
					}
				} else {
					for (String oldFileName : inputFilesArray) {

						file = new File(oldFileName);
						file2 = new File(oldFileName + outputExtension);
						file.renameTo(file2);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
