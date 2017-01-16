package main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.HashMap;

import encode.Encode;
import net.Downloader;
import net.sf.json.JSONArray;


public class Tester {
	static final String URL_PATH = "//home/jack/桌面/url2";
	static final String ICONS_DIR = ".icons";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		
//		String url = "http://www.baidu.com/";
//		
//		FavSite site = new FavSite(url);
//		System.out.println(site.getDomain());
////		site.fetchIco();
////		site.fetchTitle();
//		String icon = site.fetchIco();
//		System.out.println("icon: " + icon);
////		Connection con = HttpConnection.connect(icon);
		
//		try {
//			Downloader.download(new URL("http://wenda.golaravel.com/static/css/default/ico/favicon.ico?v=20140930"), "icos/test");
//		} catch (MalformedURLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		System.out.println(new Downloader().fetchable("http://support.open.alipay.com/favicon.ico"));
		
//		System.out.println("title: " + site.fetchTitle());
		
		
		HashMap<String, FavSite> map = new HashMap<String, FavSite>();

		System.out.println("mkdir...");
		File icons_dir = new File(ICONS_DIR);
		icons_dir.mkdirs();
		
		try {
			FileInputStream fis = new FileInputStream(URL_PATH);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader reader = new BufferedReader(isr);
			String line = "";
			int index = 0;
			while(null != (line=reader.readLine())) {
				index++;
				System.out.println(index + ": " + line);

//				Create instance
				FavSite site = new FavSite(line);
				System.out.println("\tDomain: " + site.getDomain());

//				Fetch resource
				String title = site.fetchTitle();
//				if(title == null) {
//					System.out.println("\tBlocked!");
//					continue;
//				}
				String ico = "";
				if(Downloader.fetchable(new URL(site.getDomain() + "/favicon.ico"))) {		// www.example.com/favicon.ico
					ico = site.getDomain() + "/favicon.ico";
				} else {

					ico = site.fetchIco();
					if("".equals(ico)) {		// sub.example.com/favicon.ico
						ico = site.fetchIco(line);			// sub.example.com/article/2017/favicon.ico
					}
					
//					try {
//						ico = site.fetchIco();
//						if(ico.equals("")) {		// sub.example.com/favicon.ico
//							ico = site.fetchIco(line);			// sub.example.com/article/2017/favicon.ico
//						}
//						
//					} catch(Exception e) {
//						
//					}
				}
//				System.out.println("---> " + site.fetchIco());
//				System.out.println("---> " + site.fetchIco(line));
//				Downloader.download(new URL("http://wenda.golaravel.com/static/css/default/ico/favicon.ico?v=20140930"), "icos/test");
				
//				Download icon
				if(ico != null)
					Downloader.download(new URL(ico), ICONS_DIR + "/ICO_" + Encode.useMD5(site.getUrl()));
				
//				Print
				System.out.println("\tIco: " + ico);
				System.out.println("\tTitle: " + title);
				
//				Set attribute
				site.setTitle(title);
				site.setIco(ico);
//				System.out.println("");
				map.put(Encode.useMD5(line), site);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		JSONArray jsonArray = JSONArray.fromObject(map);
//		System.out.println(jsonArray.toString());
//		System.out.println(map);
		
		
//		Save Json
		
		
		JSONArray json = JSONArray.fromObject(map);
		System.out.println(json.toString());
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		PrintWriter writer = null;
//		System.out.println(json);
		try {
			fos = new FileOutputStream("url.json");
			osw = new OutputStreamWriter(fos);
			writer = new PrintWriter(osw);
			writer.write(json.toString());
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				osw.close();
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("File saved in url.json");
		
		
		
		
	}

} 
