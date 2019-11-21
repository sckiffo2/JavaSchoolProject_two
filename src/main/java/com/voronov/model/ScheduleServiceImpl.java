package com.voronov.model;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Named("scheduleService")
public class ScheduleServiceImpl {

	private List<ScheduleRow> schedule;

	@Produces
	@Named
	public List<ScheduleRow> getSchedule() {
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

		Client client = Client.create(clientConfig);
		WebResource webResource = client.resource("http://localhost:8081/AnyWayTicket/getSchedule?name=%D0%9C%D0%BE%D1%81%D0%BA%D0%B2%D0%B0&stringDate=2019-11-23");
		ClientResponse response = webResource
				.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON)
				.get(ClientResponse.class);

		ScheduleRow[] res = response.getEntity(ScheduleRow[].class);
		schedule = new ArrayList<>(Arrays.asList(res));
		return schedule;
	}
}
