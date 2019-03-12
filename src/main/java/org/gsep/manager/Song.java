

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
public class Song{

	static ArrayList<File> filesSong = new ArrayList<File>();
	static String name;
	static Socket socket;

	/**
	 * Method that calls the methods readFile() and zipFile(). The purpose of run() is to invoke the client side methods all at once.
	 * @throws Exception
	 */
	public static void run() throws Exception {

		Socket soc = new Socket("192.168.56.1",3332);
		DataInputStream dis = new DataInputStream(soc.getInputStream());
		String msg = dis.readUTF();
		System.out.println(msg);
		DataOutputStream dos = new DataOutputStream((soc.getOutputStream()));
		String file = readFile(filesSong.get(0).toString(), StandardCharsets.UTF_8 );
		System.out.println(file);
		dos.writeUTF("File has been created of length: " + file.length());
		zipFile(filesSong, name);
		sendFile(name,soc);

	}

	/**
	 * Method that reads a file and returns its contents in a string.
	 * @param path: Path of the file
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static String readFile(String path, Charset encoding)
			throws IOException
	{

		byte[] encoded = Files.readAllBytes(Paths.get(path));
		//hold contents of file in name
		name= new String(encoded, encoding);
		return name;
	}

	/**
	 * Method that zips an array of files, with the zip file assigned a given name.
	 * @param files: List of files to zip
	 * @param name: String to name the Zip file.
	 * @throws IOException
	 */
	public static void zipFile(ArrayList<File> files, String name) throws IOException {

		try {

			FileOutputStream   fos = new FileOutputStream(name+".zip");
			ZipOutputStream zos = new ZipOutputStream(fos);
			byte[] buffer = new byte[128];

			for (int i = 0; i < files.size(); i++) {
				File currentFile = files.get(i);

				if (!currentFile.isDirectory()) {
					ZipEntry entry = new ZipEntry(currentFile.getName());
					FileInputStream fis = new FileInputStream(currentFile);
					zos.putNextEntry(entry);
					int read = 0;
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

	public static void sendFile(String file, Socket soc) throws IOException {

		DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
		System.out.println("Receving file..");
		FileInputStream fos =new FileInputStream(file+".zip");
		dos.writeUTF(file);
		byte[] b = new byte[1024];
		fos.read(b,0,b.length);
		dos.write(b,0,b.length);
		System.out.println("Comleted");

	}


}