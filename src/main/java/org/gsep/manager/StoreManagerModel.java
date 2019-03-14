package org.gsep.manager;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/*
 * Client
 *
 * @author Humzah Malik
 * @version 1.0
 *
 */
public class StoreManagerModel {

	static ArrayList<File> filesSong = new ArrayList<File>();

	/**
	 * Method that calls the methods readFile() and zipFile(). The purpose of run() is to invoke the client side methods all at once.
	 * @throws Exception
	 */


	StoreManagerModel(ArrayList<File> filesSong){
		this.filesSong = filesSong;

	}
//	public void run() throws Exception {
//
//		Socket soc = new Socket("192.168.56.1", 3332);
//		boolean valid = true;
//		DataInputStream dis = new DataInputStream(soc.getInputStream());
//		String msg = dis.readUTF();
//		System.out.println(msg);
//		String file = readFile(filesSong.get(0).toString(), StandardCharsets.UTF_8);
//		System.out.println(file);
//		zipFile(filesSong, name);
//		sendFile(name, soc);
//
//	}

	/**
	 * Method that reads a file and returns its contents in a string.
	 * @return
	 * @throws IOException
	 */
	public static String readFile()
			throws IOException
	{
		String path = filesSong.get(0).toString();
		Charset encoding = StandardCharsets.UTF_8;
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		//hold contents of file in name
		return new String(encoded, encoding);
	}

	/**
	 * Method that zips an array of files, with the zip file assigned a given name.
	 * @param name: String to name the Zip file.
	 * @throws IOException
	 */
	public static void zipFile(String name) throws IOException {

		try {

			FileOutputStream   fos = new FileOutputStream(name+".zip");
			ZipOutputStream zos = new ZipOutputStream(fos);
			byte[] buffer = new byte[128];

			for (File currentFile : filesSong) {
				if (!currentFile.isDirectory()) {
					ZipEntry entry = new ZipEntry(currentFile.getName());
					FileInputStream fis = new FileInputStream(currentFile);
					zos.putNextEntry(entry);
					int read;
					while ((read = fis.read(buffer)) != -1) {
						zos.write(buffer, 0, read);
					}
					zos.closeEntry();

				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found : " + e);
		}
	}

	public static void sendFile(String file) throws IOException {
		Socket soc = new Socket("192.168.56.1", 3332);
		File f = new File(file+".zip");
		DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
		System.out.println("Receving file..");
		DataInputStream dis = new DataInputStream(new FileInputStream(file + ".zip"));
		dos.writeUTF("Send-" + file + ".zip");
		dos.writeLong(f.length());
		byte[] b = new byte[(int) f.length()];
		dis.readFully(b, 0, b.length);
		dos.write(b, 0, b.length);
		System.out.println("Comleted");

		dis.close();
		dos.close();


	}


}