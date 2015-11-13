package mq;

import java.util.Date;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActiveMQQueuePublisher {

	private Logger log = LoggerFactory.getLogger(ActiveMQQueuePublisher.class);	
	
	public static void main(String[] args) {
		ActiveMQQueuePublisher publisher = new ActiveMQQueuePublisher();
		publisher.run();
	}

	public void run() {
	    Connection connection = null;
	    
        try {
            log.info("Publish.run()");

            ConnectionFactory factory = ActiveMQHelper.getJmsConnectionFactory();
            connection = factory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); // false=NotTransacted
            Queue queue = session.createQueue(ActiveMQHelper.QUEUE_TEST);

            MessageProducer producer = session.createProducer(queue);
            //producer.setTimeToLive(10000);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);

            TextMessage message = session.createTextMessage("Here is a message at: " + new Date());
            producer.send(message);

            connection.close(); // In a real world application, you may want
                                // to keep
            connection = null; // the connection open for performance.
        }

        catch (Exception e) {
            log.info("Exception occurred: " + e.toString());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                }
            }
        }
	    log.info("Publisher.run(). Stopped.");
	}	
}
