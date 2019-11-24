package com.voronov.model;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;

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
		System.out.println("JMS message received by");
		pushToChannel();
	}

	@PostConstruct
	public void onInit() {
		System.out.println("UpdateBean postConstruct start");
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
	}

	public void pushToChannel() {
		updateChannel.send("updateEvent");
		System.out.println("sending updateEvent");
	}
}