package net;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Downloader {
	
//	private URL url;
	
	public static void download(URL url, String filename) {
//		try {
//			this.url = new URL(url);
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println("download..." + url.toString());
		
//		URLConnection con = null;
		HttpURLConnection con = null;
		try {
			con = (HttpURLConnection) url.openConnection();
			con.connect();
//			System.out.println(con.getContentType());
//			System.out.println(con.getHeaderField("Server"));
//			System.out.println("leng: " + con.getContentLength());
//			System.out.println("status" + con.getResponseCode());
			InputStream is = con.getInputStream();
//			InputStreamReader reader = new InputStreamReader(is);
			
			FileOutputStream fos = new FileOutputStream(filename);
			byte[] buf = new byte[4096];
			int len;
			while((len=is.read(buf)) != -1) {
				fos.write(buf, 0, len);
			}
			fos.flush();
			fos.close();
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	public static boolean fetchable(URL url) {
		
//		try {
//			this.url = new URL(url);
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		HttpURLConnection con = null;
		try {
			con = (HttpURLConnection) url.openConnection();
			con.connect();
			if(con.getResponseCode() == 200)
				return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			return false;
		}
		
		
		return false;
	}
	
}
