package com.voronov.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleRow {

	private String routeNumber;

	private String routeName;

	private boolean canceled;

	private int delay;

	private String arrival;

	private String departure;
}
