package mq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActiveMQTopicSubscriber implements MessageListener {

	private Logger log = LoggerFactory.getLogger(ActiveMQTopicPublisher.class);
	
	public static void main(String[] args) {
		ActiveMQTopicSubscriber subscriber = new ActiveMQTopicSubscriber();
		subscriber.startListening();
	}

	public void startListening() {
		
		Connection connection = null;
		
		log.info("Subscriber.startListening()");

	    try {
	        ConnectionFactory factory = ActiveMQHelper.getJmsConnectionFactory();
	        connection = factory.createConnection();
	        Session session = connection.createSession(false,
	                Session.AUTO_ACKNOWLEDGE);
	        Topic topic = session
	                .createTopic(ActiveMQHelper.TOPIC_TEST);
	        MessageConsumer consumer = session.createConsumer(topic);
	        consumer.setMessageListener(this);

	        connection.start();
	    } catch (JMSException e) {
	        log.info("Exception occurred: " + e.toString());
	    }
	}

	/**
	 * Just log a note when a message is received.
	 */
	public void onMessage(Message message) {
	    if (message instanceof TextMessage) {
	        TextMessage txtMsg = (TextMessage) message;
	        try {
	        	log.info("Subscriber.onMessage(): " + txtMsg.getText());
	        } catch (JMSException e) {
	            e.printStackTrace();
	        }
	    }
	}
}
