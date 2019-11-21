package com.voronov.model;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.*;
import javax.websocket.OnMessage;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Named("ajaxBean")
public class AjaxBean implements Serializable, javax.jms.MessageListener{

	@Inject
	@Push
	PushContext ajaxChannel;

	@Inject
	@Push
	PushContext ajaxListenerChannel;

	private Connection connection;

	@Override
	public void onMessage(Message message) {
		System.out.println("JMS message received by");
		pushToAjaxChannel();
	}

	@PostConstruct
	public void onInit() {

		System.out.println("ScheduleMessageListener postConstruct start");
		try {
			ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616"); //  "vm://localhost"
			this.connection = factory.createConnection();
			connection.start();

			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Queue queue = session.createQueue("JCG_QUEUE");
			MessageConsumer consumer = session.createConsumer(queue);
			consumer.setMessageListener(this);
		} catch (JMSException e) {
			System.out.println("ActiveMQ connection have error");
			e.printStackTrace();
		}
		System.out.println("ScheduleMessageListener postConstruct end");
	}

	public void pushToAjaxChannel() {
		ajaxChannel.send("ajaxEvent");
		System.out.println("sending ajaxEvent");
	}

	public void pushToAjaxListenerChannel(){
		ajaxListenerChannel.send("ajaxListenerEvent");
		System.out.println("sending ajaxListenerEvent");
	}
}