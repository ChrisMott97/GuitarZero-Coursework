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

	static ArrayList<File> filesSong = new ArrayList<>();
	static Socket soc;

	/**
	 * Method that calls the methods readFile() and zipFile(). The purpose of run() is to invoke the client side methods all at once.
	 * @throws Exception
	 */


	StoreManagerModel(ArrayList<File> filesSong){

		this.filesSong = filesSong;
		try {
			soc = new Socket("192.168.56.1", 3332);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
//	public void run() throws Exception {
//

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



	public static void sendFile() throws IOException {

		DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
		dos.writeUTF("Send-" +filesSong.size());
		System.out.println("Receving file..");
		for(int i =0; i <filesSong.size(); i++) {
			FileInputStream fis = new FileInputStream(filesSong.get(i));
			dos.writeUTF(filesSong.get(i).getName() + "-" + (int) filesSong.get(i).length());
			byte[] b = new byte[(int) filesSong.get(i).length()];
			fis.read(b, 0, b.length);
			OutputStream os = soc.getOutputStream();
			os.write(b,0,b.length);
			System.out.println("Comleted" + filesSong.get(i).getName());
		}


	}


}