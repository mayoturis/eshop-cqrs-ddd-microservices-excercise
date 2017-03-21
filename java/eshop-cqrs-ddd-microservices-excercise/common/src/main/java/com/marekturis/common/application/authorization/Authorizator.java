package com.marekturis.common.application.authorization;

/**
 * @author Marek Turis
 */
public interface Authorizator {
	boolean canBeAuthorized(String authenticationToken, String roleName);
}
