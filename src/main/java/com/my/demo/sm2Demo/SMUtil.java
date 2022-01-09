package com.my.demo.sm2Demo;

import java.math.BigInteger;

public class SMUtil {
	public static byte[] intToBytes(int num) {
		byte[] bytes = new byte[4];
		bytes[0] = (byte) (0xff & (num >> 0));
		bytes[1] = (byte) (0xff & (num >> 8));
		bytes[2] = (byte) (0xff & (num >> 16));
		bytes[3] = (byte) (0xff & (num >> 24));
		return bytes;
	}

	public static int byteToInt(byte[] bytes) {
		int num = 0;
		int temp;
		temp = (0x000000ff & (bytes[0])) << 0;
		num = num | temp;
		temp = (0x000000ff & (bytes[1])) << 8;
		num = num | temp;
		temp = (0x000000ff & (bytes[2])) << 16;
		num = num | temp;
		temp = (0x000000ff & (bytes[3])) << 24;
		num = num | temp;
		return num;
	}

	public static byte[] longToBytes(long num) {
		byte[] bytes = new byte[8];
		for (int i = 0; i < 8; i++) {
			bytes[i] = (byte) (0xff & (num >> (i * 8)));
		}
		return bytes;
	}

	public static byte[] byteConvert32Bytes(BigInteger n) {
		byte tmpd[] = (byte[]) null;
		if (n == null) {
			return null;
		}
		if (n.toByteArray().length == 33) {
			tmpd = new byte[32];
			System.arraycopy(n.toByteArray(), 1, tmpd, 0, 32);
		} else if (n.toByteArray().length == 32) {
			tmpd = n.toByteArray();
		} else {
			tmpd = new byte[32];
			for (int i = 0; i < 32 - n.toByteArray().length; i++) {
				tmpd[i] = 0;
			}
			System.arraycopy(n.toByteArray(), 0, tmpd, 32 - n.toByteArray().length, n.toByteArray().length);
		}
		return tmpd;
	}

	public static byte[] hexToByte(String hex) throws IllegalArgumentException {
		if (hex.length() % 2 != 0) {
			throw new IllegalArgumentException();
		}
		char[] arr = hex.toCharArray();
		byte[] b = new byte[hex.length() / 2];
		for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
			String swap = "" + arr[i++] + arr[i];
			int byteint = Integer.parseInt(swap, 16) & 0xFF;
			b[j] = new Integer(byteint).byteValue();
		}
		return b;
	}

	public static String byteToHex(byte b[]) {
		if (b == null) {
			throw new IllegalArgumentException("Argument b ( byte array ) is null! ");
		}
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0xff);
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

}