package com.voronov.jms;

import com.voronov.model.AjaxBean;
import com.voronov.model.UpdateEvent;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.*;
import java.io.Serializable;

//@Startup
//@Singleton
public class MessageListener {//implements javax.jms.MessageListener, Serializable {

//	private Connection connection;
//
//	@Inject
//	AjaxBean ajaxBean;
//
//	@Inject
//	private BeanManager beanManager;
//
//	@Override
//	public void onMessage(Message message) {
//		System.out.println("JMS message received by");
//		beanManager.fireEvent(new UpdateEvent("update"));
//	}
//
//	@PostConstruct
//	void postConstruct() {
//		System.out.println("ScheduleMessageListener postConstruct start");
//		try {
//			ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616"); //  "vm://localhost"
//			this.connection = factory.createConnection();
//			connection.start();
//
//			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//
//			Queue queue = session.createQueue("JCG_QUEUE");
//			MessageConsumer consumer = session.createConsumer(queue);
//			consumer.setMessageListener(this);
//		} catch (JMSException e) {
//			System.out.println("ActiveMQ connection have error");
//			e.printStackTrace();
//		}
//		System.out.println("ScheduleMessageListener postConstruct end");
//	}
}