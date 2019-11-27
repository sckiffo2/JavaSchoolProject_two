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
import lombok.extern.log4j.Log4j;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.ws.rs.core.MediaType;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@Log4j
@Singleton
@Startup
@Getter
@Setter
@NoArgsConstructor
@ManagedBean(name = "scheduleRepository")
public class ScheduleRepository implements Serializable {

	private Station station = new Station(1L);

	private List<ScheduleRowDto> schedule;

	private String date = LocalDate.now().toString();

	@PostConstruct
	private void onInit() {
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream("app.properties");
			Properties properties = new Properties();
			properties.load(input);
			long id = Long.parseLong(properties.getProperty("stationId"));
			station = new Station(id);
			log.info("scheduleRepository created");
			log.info("getting station id from properties. id = " + id);
		} catch (IOException e) {
		}
	}

	@Produces
	@Named
	public List<ScheduleRowDto> getSchedule() {
		String requestUrl = "http://localhost:8081/AnyWayTicket/getSchedule?id=" + station.getId();
		ClientResponse response = webClient(requestUrl);
		ScheduleRowDto[] res = response.getEntity(ScheduleRowDto[].class);
		station.setName(res[0].getStationName());
		schedule = new ArrayList<>(Arrays.asList(res));
		Collections.sort(schedule);
		return schedule;
	}

	public Station getStation() {
		String requestUrl = "http://localhost:8081/AnyWayTicket/getStation?id=" + station.getId();
		ClientResponse response = webClient(requestUrl);
		station = response.getEntity(Station.class);
		log.info("getting station:" + station.getId() + " " + station.getName());
		return station;
	}

	private ClientResponse webClient(String url) {
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

		Client client = Client.create(clientConfig);
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource
				.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON)
				.get(ClientResponse.class);
		return response;
	}
}
