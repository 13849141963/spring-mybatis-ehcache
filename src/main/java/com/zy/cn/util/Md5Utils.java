package com.zy.cn.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;

public class Md5Utils {
	public static String MD5Encode(String src) {
		String resultString = null;
		try {
			resultString = getMD5(src);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resultString;
	}

	public static final String byte2hexString(byte[] bytes) {
		StringBuffer buf = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; ++i) {
			if ((bytes[i] & 0xFF) < 16) {
				buf.append("0");
			}
			buf.append(Long.toString(bytes[i] & 0xFF, 16));
		}
		return buf.toString();
	}

	public static String getMD5(File file)/*    */ throws Exception {
		FileInputStream fis = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			fis = new FileInputStream(file);
			byte[] buffer = new byte[2048];
			int length = -1;
			while ((length = fis.read(buffer)) != -1) {
				md.update(buffer, 0, length);
			}
			byte[] b = md.digest();
			return byte2hexString(b);
		} finally {
			try {
				fis.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static String getMD5(String message)/*    */ throws Exception {
		if (message == null)
			return null;
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] b = md.digest(message.getBytes("utf-8"));
		return byte2hexString(b);
	}
}
