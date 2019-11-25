package com.voronov.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Station {

	private long id;

	private String name;

	public Station(long id) {
		this.id = id;
	}
}
