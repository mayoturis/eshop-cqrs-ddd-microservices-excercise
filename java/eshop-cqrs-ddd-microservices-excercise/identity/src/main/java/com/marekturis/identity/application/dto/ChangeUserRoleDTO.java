package com.marekturis.identity.application.dto;

import javax.validation.constraints.NotNull;

/**
 * @author Marek Turis
 */
public class ChangeUserRoleDTO {
	@NotNull
	private Integer userId;
	@NotNull
	private String newRole;
	@NotNull
	private String executorToken;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getNewRole() {
		return newRole;
	}

	public void setNewRole(String newRole) {
		this.newRole = newRole;
	}

	public String getExecutorToken() {
		return executorToken;
	}

	public void setExecutorToken(String executorToken) {
		this.executorToken = executorToken;
	}
}
