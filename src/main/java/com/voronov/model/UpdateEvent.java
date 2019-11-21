package com.voronov.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEvent {

	private final String eventMessage;

	public UpdateEvent(String eventMessage) {
		this.eventMessage = eventMessage;
	}
}
