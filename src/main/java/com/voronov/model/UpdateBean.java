package com.voronov.model;


import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named("updateBean")
public class UpdateBean implements Serializable {

	@Inject
	@Push
	PushContext updateChannel;

	public void push() {

	}
}
