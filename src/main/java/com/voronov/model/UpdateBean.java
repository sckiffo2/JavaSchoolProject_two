package com.voronov.model;

import lombok.extern.log4j.Log4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;
import org.omnifaces.cdi.Startup;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.*;
import java.io.Serializable;

@ApplicationScoped
@Named("updateBean")
public class UpdateBean implements Serializable, javax.jms.MessageListener{

	@Inject
	@Push
	PushContext updateChannel;

	private Connection connection;

	@Override
	public void onMessage(Message message) {
		pushToChannel();
	}

	@PostConstruct
	public void onInit() {
		try {

			ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
			this.connection = factory.createConnection();
			connection.start();

			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Queue queue = session.createQueue("JCG_QUEUE");
			MessageConsumer consumer = session.createConsumer(queue);
			consumer.setMessageListener(this);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void pushToChannel() {
		updateChannel.send("updateEvent");
	}
}