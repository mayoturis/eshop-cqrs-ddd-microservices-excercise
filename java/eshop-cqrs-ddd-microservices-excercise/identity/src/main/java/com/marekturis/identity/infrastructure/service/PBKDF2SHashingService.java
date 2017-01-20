package com.marekturis.identity.infrastructure.service;

import com.marekturis.identity.domain.user.HashingService;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.inject.Named;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author Marek Turis
 */
@Named
public class PBKDF2SHashingService implements HashingService {

	public String createHash(String plainText) {
		final int SALT_BYTE_SIZE = 24;
		final int HASH_BYTE_SIZE = 24;
		final int PBKDF2_ITERATIONS = 1000;
		// Generate a random salt
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[SALT_BYTE_SIZE];
		random.nextBytes(salt);
		// Hash the plainText
		byte[] hash = pbkdf2(plainText.toCharArray(), salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
		// format iterations:salt:hash
		return PBKDF2_ITERATIONS + ":" + toHex(salt) + ":" + toHex(hash);
	}

	private static byte[] pbkdf2(char[] plainText, byte[] salt, int iterations, int bytes) {
		try {
			PBEKeySpec spec = new PBEKeySpec(plainText, salt, iterations, bytes * 8);
			return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean compareHashWithPlainText(String correctHash, String plainText) {
		if(plainText==null) return false;
		if(correctHash==null) throw new IllegalArgumentException("password hash is null");
		String[] params = correctHash.split(":");
		int iterations = Integer.parseInt(params[0]);
		byte[] salt = fromHex(params[1]);
		byte[] hash = fromHex(params[2]);
		byte[] testHash = pbkdf2(plainText.toCharArray(), salt, iterations, hash.length);
		return slowEquals(hash, testHash);
	}

	/**
	 * Compares two byte arrays in length-constant time. This comparison method
	 * is used so that password hashes cannot be extracted from an on-line
	 * system using a timing attack and then attacked off-line.
	 *
	 * @param a the first byte array
	 * @param b the second byte array
	 * @return true if both byte arrays are the same, false if not
	 */
	private static boolean slowEquals(byte[] a, byte[] b) {
		int diff = a.length ^ b.length;
		for (int i = 0; i < a.length && i < b.length; i++)
			diff |= a[i] ^ b[i];
		return diff == 0;
	}

	private static byte[] fromHex(String hex) {
		byte[] binary = new byte[hex.length() / 2];
		for (int i = 0; i < binary.length; i++) {
			binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return binary;
	}

	private static String toHex(byte[] array) {
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length * 2) - hex.length();
		return paddingLength > 0 ? String.format("%0" + paddingLength + "d", 0) + hex : hex;
	}
}
