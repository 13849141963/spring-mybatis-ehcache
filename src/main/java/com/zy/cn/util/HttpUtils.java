package com.zy.cn.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class HttpUtils {
	private static final int CONNECT_TIMEOUT = 3000;
	private static final int READ_TIMEOUT = 6000;
	private static final String encoding = "UTF-8";
	
	/**
	 * 发送http请求，内容为kv格式；返回json响应
	 * @param url
	 * @param jsonStr
	 * @return
	 */
	public static String formPost(String urlStr,Map<String,Object> params) {
		BufferedReader br = null;
		HttpURLConnection urlconn = null;
		OutputStream out=null;
		try {
			URL url = new URL(urlStr);
			urlconn = (HttpURLConnection) url.openConnection();
			urlconn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset="+encoding);
			urlconn.setConnectTimeout(CONNECT_TIMEOUT);
			urlconn.setReadTimeout(READ_TIMEOUT);
			urlconn.setRequestMethod("POST"); 
			urlconn.setUseCaches(false); 
			urlconn.setDoOutput(true);  
			urlconn.setDoInput(true);
			
			StringBuilder sb =new StringBuilder();
			for(String key : params.keySet()){
				sb.append(key).append("=").append(URLEncoder.encode(params.get(key).toString(),encoding)).append("&");				
			}
			if(sb.length()>0)sb.deleteCharAt(sb.length()-1);
			//发送请求
			 {
			     out = urlconn.getOutputStream();
			     if(sb.length()>0)
				 out.write(sb.toString().getBytes(encoding));
			     out.flush();
			     out.close(); 
			     out=null;
			 }
			 
			//处理响应
			 char[] charBuf = new char[1024];
			 int readChars = -1;
			 StringBuffer res = new StringBuffer();
			InputStream inStrm = urlconn.getInputStream();
			br = new BufferedReader(new InputStreamReader(inStrm,encoding));
			while ((readChars=br.read(charBuf)) != -1){
			    res.append(charBuf,0, readChars);
			}
			br.close();
			urlconn.disconnect();
			urlconn=null;
			return res.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
		    try { if(out!=null){out.close();}	} catch (IOException e) {e.printStackTrace();}
		    try { if(br!=null){br.close();}	} catch (IOException e) {e.printStackTrace();}
		     if(urlconn!=null) urlconn.disconnect(); 
		}
	}
	
 	/**
	 * 发送http请求，内容为json格式；返回json响应
	 * @param url
	 * @param jsonStr
	 * @return
	 */
	public static String jsonPost(String urlStr,String jsonStr) {
		BufferedReader br = null;
		HttpURLConnection urlconn = null;
		OutputStream out=null;
		try {
			URL url = new URL(urlStr);
			urlconn = (HttpURLConnection) url.openConnection();
			urlconn.setRequestProperty("Content-type", "application/json;charset="+encoding);
			urlconn.setConnectTimeout(CONNECT_TIMEOUT);
			urlconn.setReadTimeout(READ_TIMEOUT);
			urlconn.setRequestMethod("POST"); 
			urlconn.setUseCaches(false); 
			urlconn.setDoOutput(true);  
			urlconn.setDoInput(true);			 
			//发送请求
			 {
			     out = urlconn.getOutputStream();
			     if(jsonStr!=null)
			    	 out.write(jsonStr.getBytes("utf-8"));
			     out.flush();
			     out.close(); 
			     out=null;
			 }
			 
			//处理响应
			 char[] charBuf = new char[1024];
			 int readChars = -1;
			 StringBuffer res = new StringBuffer();
			InputStream inStrm = urlconn.getInputStream();
			br = new BufferedReader(new InputStreamReader(inStrm,encoding));
			while ((readChars=br.read(charBuf)) != -1){
			    res.append(charBuf,0, readChars);
			}
			br.close();
			urlconn.disconnect();
			urlconn=null;
			return res.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
		    try { if(out!=null){out.close();}	} catch (IOException e) {e.printStackTrace();}
		    try { if(br!=null){br.close();}	} catch (IOException e) {e.printStackTrace();}
		     if(urlconn!=null) urlconn.disconnect(); 
		}
	}
	
	/**
	 * 执行http请求，返回字符串
	 * @param urlStr
	 * @return
	 */
	public static String formGet(String urlStr,Map<String,String> params) {
		String url=urlStr;
		if(url.indexOf("?")==-1)
			 url = urlStr.trim()+"?";
		else
			url = urlStr.trim()+"&";
		StringBuilder sb =new StringBuilder(url);
		try {
			for(String key : params.keySet()){
					sb.append(key).append("=").append(URLEncoder.encode(params.get(key),encoding)).append("&");
			}
		} catch (UnsupportedEncodingException e) {				 
			throw new RuntimeException(e);
		}				
		if(sb.length()>0)sb.deleteCharAt(sb.length()-1);
		return doGet(sb.toString());
	}
	
	/**
	 * 执行http请求，返回字符串
	 * @param urlStr
	 * @return
	 */
	public static String doGet(String urlStr) {
		BufferedReader br = null;
		HttpURLConnection urlconn = null;
		OutputStream out=null;
		try {
			URL url = new URL(urlStr);
			urlconn = (HttpURLConnection) url.openConnection();
			urlconn.setConnectTimeout(CONNECT_TIMEOUT);
			urlconn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset="+encoding);
			urlconn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.94 Safari/537.36");
			urlconn.setRequestProperty("Accept-Encoding", "gzip");
			urlconn.setRequestProperty("Content-type", "text/html");
		        urlconn.setRequestProperty("Connection", "close"); 
		        urlconn.setUseCaches(false); 
		        urlconn.setDoOutput(true);
		        urlconn.setDoInput(true);
			urlconn.setInstanceFollowRedirects(true);
			urlconn.setReadTimeout(READ_TIMEOUT);
			urlconn.setRequestMethod("GET"); 
			 //处理响应
			 char[] charBuf = new char[1024];
			 int readChars = -1;
			 String contentType = urlconn.getHeaderField("Content-Type");
			 String ec=encoding;
			 if(contentType!=null && contentType.indexOf("=")>0){
			     ec= contentType.substring(contentType.indexOf('=')+1);
			 }
			 StringBuffer res = new StringBuffer();
			InputStream inStrm = urlconn.getInputStream();
			if("Accept-Encoding".equals(urlconn.getHeaderField("Vary"))){
			    inStrm = new java.util.zip.GZIPInputStream(inStrm);
			}
			br = new BufferedReader(new InputStreamReader(inStrm,ec));
			while ((readChars=br.read(charBuf)) != -1){
			    res.append(charBuf,0, readChars);
			}
			br.close();
			urlconn.disconnect();
			urlconn=null;
			return res.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
		    try { if(out!=null){out.close();}	} catch (IOException e) {e.printStackTrace();}
		    try { if(br!=null){br.close();}	} catch (IOException e) {e.printStackTrace();}
		     if(urlconn!=null) urlconn.disconnect(); 
		}
	}
	
	public static void  main(String[] args) throws Exception{
	    String ip="183.54.83.199";
	    String url="http://www.cz88.net/ip/index.aspx?ip="+ip;  
	    //url="http://wap.ip138.com/ip_search138.asp?ip="+ip;
	    //url="http://www.ip.cn/index.php?ip="+ip;
	    String res = doGet(url);
	    System.out.println(res);
	    //System.out.println(StringHelper.getSubString(res,"来自：","</p>"));	        
	}
}
