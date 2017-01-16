package main;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;

import encode.Encode;

public class FavSite {

	private String ico;
	private URL url;
	private String title;
	private boolean isBlockedByGfw;
	
	public FavSite(String url) {
		// TODO Auto-generated constructor stub
		this.isBlockedByGfw = false;
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setIco(String ico) {
		this.ico = ico;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getUrl() {
		return url.toString();
	}
	
	public String getIco() {
		return ico;
	}
	
	public String getLocalIco() {
		return "ICO_" + Encode.useMD5(this.url.toString());
	}
	
	public String getTitle() {
		return title;
	}
	public boolean getIsBlockedByGfw() {
		return this.isBlockedByGfw;
	}
	
	
	
	
//	CORE
	
	public String getDomain() {
//		return this.url.getHost();
//		System.out.println("Host: " + this.url.getHost());
		String[] arrUrl = this.url.toString().split("/");
		ArrayList<String> tempList = new ArrayList<String>();
		if(arrUrl.length == 3) {
			for(int i = 0; i < arrUrl.length; i++) {
				tempList.add(arrUrl[i]);
			}
		} else {
			for(int i = 0; i < arrUrl.length-1; i++) {
				tempList.add(arrUrl[i]);
			}
		}
		
//		Get url
//		String url = "";
//		for(int i = 0; i < tempList.size(); i++) {
//			url += " {" + i + ": " + tempList.get(i) + "} ";
//		}
		String url = tempList.get(2);
//		System.out.println("url: " + tempList.get(2));
		
//		System.out.println("... domain: " + domain);
//		try {
//			return "http://" + new URL(domain).getHost() + "/";
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
//		Get domain
//		System.out.println(url);
		String[] domainSplit = url.split("\\.");
//		System.out.println("length: "+domainSplit.length);
		
		ArrayList<String> domainSplitList = new ArrayList<String>();
		for(int i = 0; i < domainSplit.length; i++) {
			domainSplitList.add(domainSplit[i]);
//			System.out.println("x: " + domainSplit[i]);
		}
//		if(domainSplit.length > 2)
//			domainSplitList.remove(0);
//		System.out.println(domainSplitList);
//		ArrayList<String> newDomainSplitList = new ArrayList<String>();
//		newDomainSplitList.add(domainSplitList.get(domainSplitList.size()-2));
//		newDomainSplitList.add(domainSplitList.get(domainSplitList.size()-1));
//		System.out.println(newDomainSplitList);
//		Joint domain split
		String domain = "";
		for(int i = 0; i < domainSplitList.size(); i++) {
			if(i != domainSplitList.size()-1)
				domain += domainSplitList.get(i)+".";
			else
				domain += domainSplitList.get(i);
		}
		
//		System.out.print("domainSplite: ");
//		for(int i = 0; i < domainSplit.length; i++) {
//			System.out.print(domainSplit[i] + " ");
//		}
//		System.out.println("");
		
		if(domainSplit.length > 2)
			domain = "http://" + domain;
		else
			domain = "http://www." + domain;
		return domain;
	}
	
	public String fetchIco() {
		return fetchIco(this.getDomain());
	}
	
	public String fetchIco(String url) {
//		System.out.println("Fetching icon from " + this.getDomain());
		String html = this.fetchHTML(url);
		if(html == null)
			return null;
		Document doc = Jsoup.parse(html);
		String i = doc.select("link[rel='icon']").attr("href");
		String s = doc.select("link[rel='shortcut icon']").attr("href").toString();
		String a = doc.select("link[rel='apple-touch-icon']").attr("href").toString();
//		System.out.println("i: " + i);
//		System.out.println("s: " + s);
//		System.out.println("a: " + a);
//		String imgUrl = i.equals("") ? s : i;

		ArrayList<String> listImgUrl = new ArrayList<String>();
		if(!i.equals("") && i.substring(i.length()-3).equals("ico"))
			listImgUrl.add(i);
		if(!s.equals("") && s.substring(s.length()-3).equals("ico"))
			listImgUrl.add(s);
		if(!a.equals("") && a.substring(a.length()-3).equals("ico"))
			listImgUrl.add(a);

		if(!i.equals(""))
			listImgUrl.add(i);
		if(!s.equals(""))
			listImgUrl.add(s);
		if(!a.equals(""))
			listImgUrl.add(a);
		
		if(i.equals("") && s.equals("") && a.equals(""))
			listImgUrl.add("favicon.ico");
		
		String imgUrl = null;
//		Collections.reverse(listImgUrl);
		if(listImgUrl.size() > 0)
			imgUrl = listImgUrl.get(0);

		String ico = null;
//		imgUrl = (imgUrl.charAt(0)+"").equals("/") ? imgUrl : "/"+imgUrl;
		if(imgUrl == null)
			return "";
		if(imgUrl.substring(0, 1).equals("/") && !imgUrl.substring(1, 2).equals("/"))
			imgUrl = imgUrl.substring(1);
//		System.out.println("imgUrl: " + imgUrl);
		if(isAbsolute(imgUrl)) {
			ico = imgUrl;
		} else {
			ico = this.getDomain() + "/" + imgUrl;
		}
		if(imgUrl.substring(0, 2).equals("//"))
			ico = "http:" + imgUrl;
//		System.out.println("\tIco: " + ico);
		return ico;
	}
	
	public String fetchTitle() {
//		System.out.println("Fetching title from " + this.getDomain());
		String html = this.fetchHTML();
		if(html == null)
			return null;
		Document doc = Jsoup.parse(html);
		String title = doc.select("title").text().replaceAll("\r\n", "").replaceAll("\\s+", " ").toString();
//		System.out.println("\tTitle: " + title);
		return title;
	}
	
	public String fetchHTML() {
		return fetchHTML(this.url.toString());
	}
	
	public String fetchHTML(String url) {
//		System.out.println("Fetching HTML from " + this.getDomain());
		Connection con = HttpConnection.connect(url);
		try {
			return con.get().toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			this.isBlockedByGfw = true;
			System.out.println("\tPage not found!");
		}
		return null;
	}
	
	
	public boolean isAbsolute(String url) {
//		String url = this.url.toString();
		if(url == null)
			return false;
		if(url.contains("http://") || url.contains("https://"))
			return true;
		return false;
	}
	
}
