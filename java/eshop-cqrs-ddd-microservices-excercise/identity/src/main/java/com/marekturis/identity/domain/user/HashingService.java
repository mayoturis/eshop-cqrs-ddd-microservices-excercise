package com.marekturis.identity.domain.user;

/**
 * @author Marek Turis
 */
public interface HashingService {
	String createHash(String plainText);
	boolean compareHashWithPlainText(String hash, String plainText);
}
