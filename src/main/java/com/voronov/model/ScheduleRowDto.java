package com.voronov.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRowDto implements Comparable<ScheduleRowDto>{

	private String routeNumber;

	private String routeName;

	private boolean canceled;

	private int delay;

	private String arrival;

	private String departure;

	public String getCanceled() {
		return canceled ? "отменен" : "активен";
	}

	public String getArrival() {
		if (arrival == null) {
			return "-";
		}
		LocalDateTime arrivalTime = LocalDateTime.parse(arrival).plusMinutes(delay);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		return arrivalTime.format(formatter);
	}

	public String getDeparture() {
		if (departure == null) {
			return "-";
		}
		LocalDateTime departureTime = LocalDateTime.parse(departure).plusMinutes(delay);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		return departureTime.format(formatter);
	}

	public String getDelay() {
		if (canceled) {
			return "отменен";
		}
		if (delay == 0) {
			return "вовремя";
		}
		int delayHours = delay / 60;
		int delayMinutes = delay % 60;
		String result = "";
		switch (delayHours) {
			case 0:
				break;
			case 1:
				result = delayHours + " час";
				break;
			case 2:
			case 3:
			case 4:
				result = delayHours + " часа";
				break;
			default:
				return "более 5 часов";
		}
		if (delayMinutes > 10 && delayMinutes < 21) {
			result += " "  + delayMinutes + " минут";
			return result;
		}
		switch (delayMinutes % 10) {
			case 1:
				result += " "  + delayMinutes + " минута";
				break;
			case 2:
			case 3:
			case 4:
				result += " "  + delayMinutes + " минуты";
				break;
			default:
				result += " "  + delayMinutes + " минут";
				break;
		}
		return result;
	}

	@Override
	public int compareTo(ScheduleRowDto o) {
		if (arrival != null) {
			LocalDateTime arrivalTime = LocalDateTime.parse(arrival).plusMinutes(delay);
			if (o.arrival != null) {
				return arrivalTime.compareTo(LocalDateTime.parse(o.arrival).plusMinutes(o.delay));
			} else {
				return arrivalTime.compareTo(LocalDateTime.parse(o.departure).plusMinutes(o.delay));
			}
		} else {
			LocalDateTime departureTime = LocalDateTime.parse(departure);
			if (o.arrival != null) {
				return departureTime.compareTo(LocalDateTime.parse(o.arrival).plusMinutes(o.delay));
			} else {
				return departureTime.compareTo(LocalDateTime.parse(o.departure).plusMinutes(o.delay));
			}
		}
	}
}
