package com.redn.connect.ftp.transformers;
/**
 * 
 * 
 * @author Laxshmi Maram
 *  *
 */
import java.io.File;
public class SourceFileName {
	public static void main(String a[])
	{
	File file = new File("E:/Backup/RCI/Basicstuff/Files/input/LEN006.txt");
	String filename1 = file.getName();
	System.out.println("filename1="+filename1);
	}
}
