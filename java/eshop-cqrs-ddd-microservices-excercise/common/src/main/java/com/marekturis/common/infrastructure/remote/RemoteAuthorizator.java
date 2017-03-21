package com.marekturis.common.infrastructure.remote;

import com.marekturis.common.application.authorization.Authorizator;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.inject.Named;

/**
 * @author Marek Turis
 */
@Named
public class RemoteAuthorizator implements Authorizator {

	@Override
	public boolean canBeAuthorized(String authenticationToken, String roleName) {
		Client client = Client.create();

		WebResource webResource = client.resource(ServiceLocations.IDENTITY +
				"authentication/" + authenticationToken +
				"/inRole/" + roleName);

		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

		return response.getStatus() == 200;
	}
}
