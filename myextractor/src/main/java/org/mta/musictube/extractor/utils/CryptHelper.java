package org.mta.musictube.extractor.utils;

import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/*mcrypt = new CryptHelper();
 Encrypt 
String encrypted = CryptHelper.bytesToHex( mcrypt.encrypt("Text to Encrypt") );
 Decrypt 
String decrypted = new String(mcrypt.decrypt(encrypted));*/

public class CryptHelper {
	private static final String TAG = LogHelper.makeLogTag(CryptHelper.class);
	static char[] HEX_CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	private String iv = "1493gfedCBA27889";// Dummy iv (CHANGE IT!)
	private IvParameterSpec ivspec;
	private SecretKeySpec keyspec;
	private Cipher cipher;

	private String secretKey = "17484ABCdefg2013";// Dummy secretKey (CHANGE IT!)

	public CryptHelper() {
		ivspec = new IvParameterSpec(iv.getBytes());
		keyspec = new SecretKeySpec(secretKey.getBytes(), "AES");
		try {
			cipher = Cipher.getInstance("AES/CBC/NoPadding");
		}
		catch (NoSuchAlgorithmException e) {
			LogHelper.i(TAG, "CryptHelper NoSuchAlgorithmException = ", e.getMessage());
		}
		catch (NoSuchPaddingException e) {
			LogHelper.i(TAG, "CryptHelper NoSuchPaddingException = ", e.getMessage());
		}
	}

	public byte[] encrypt(String text) throws Exception {
		if (Utils.isNullOrEmpty(text)) throw new Exception("Empty string");
		byte[] encrypted = null;
		try {
			cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
			encrypted = cipher.doFinal(padString(text).getBytes());
		}
		catch (Exception e) {
			throw new Exception("[encrypt] " + e.getMessage());
		}

		return encrypted;
	}

	public byte[] decrypt(String code){
		byte[] decrypted = null;
		if (Utils.isNullOrEmpty(code))  {
			//throw new Exception("Empty string");
			return decrypted;
		}
		try {
			cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
			decrypted = cipher.doFinal(hexToBytes(code));
			// Remove trailing zeroes
			if (decrypted.length > 0) {
				int trim = 0;
				for (int i = decrypted.length - 1; i >= 0; i--)
					if (decrypted[i] == 0)
						trim++;

				if (trim > 0) {
					byte[] newArray = new byte[decrypted.length - trim];
					System.arraycopy(decrypted, 0, newArray, 0, decrypted.length - trim);
					decrypted = newArray;
				}
			}
		}
		catch (Exception e) {
			LogHelper.i(TAG, "decrypt Exception = ", e.getMessage());
			return decrypted;
			//throw new Exception("[decrypt] " + e.getMessage());
		}
		return decrypted;
	}

	public static String bytesToHex(byte[] buf) {
		char[] chars = new char[2 * buf.length];
		for (int i = 0; i < buf.length; ++i) {
			chars[2 * i] = HEX_CHARS[(buf[i] & 0xF0) >>> 4];
			chars[2 * i + 1] = HEX_CHARS[buf[i] & 0x0F];
		}
		return new String(chars);
	}

	public static byte[] hexToBytes(String str) {
		if (str == null) {
			return null;
		}
		else if (str.length() < 2) {
			return null;
		}
		else {
			int len = str.length() / 2;
			byte[] buffer = new byte[len];
			for (int i = 0; i < len; i++) {
				buffer[i] = (byte) Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
			}
			return buffer;
		}
	}

	private static String padString(String source) {
		char paddingChar = 0;
		int size = 16;
		int x = source.length() % size;
		int padLength = size - x;
		for (int i = 0; i < padLength; i++) {
			source += paddingChar;
		}
		return source;
	}
}
