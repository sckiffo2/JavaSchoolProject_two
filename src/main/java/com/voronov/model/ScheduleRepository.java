package com.voronov.model;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


//@Named("scheduleRepository")
@Singleton
@Startup
@Getter
@Setter
@NoArgsConstructor
@ManagedBean(name = "scheduleRepository")
public class ScheduleRepository implements Serializable {

	private Station station = new Station(1L,"Москва");

	private List<ScheduleRowDto> schedule;

	@Produces
	@Named
	public List<ScheduleRowDto> getSchedule() {
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

		Client client = Client.create(clientConfig);
		WebResource webResource = client.resource("http://localhost:8081/AnyWayTicket/getSchedule?id=" + station.getId());
		ClientResponse response = webResource
				.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON)
				.get(ClientResponse.class);

		ScheduleRowDto[] res = response.getEntity(ScheduleRowDto[].class);
		schedule = new ArrayList<>(Arrays.asList(res));
		Collections.sort(schedule);
		return schedule;
	}
}
