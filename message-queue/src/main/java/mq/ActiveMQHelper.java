package mq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class ActiveMQHelper {

	public static String TOPIC_TEST = "topic_test";
	public static String QUEUE_TEST = "queue_test";
	
	public static ActiveMQConnectionFactory getJmsConnectionFactory()
	{
	    String user = ActiveMQConnection.DEFAULT_USER;
        String password = ActiveMQConnection.DEFAULT_PASSWORD;
        String url = ActiveMQConnection.DEFAULT_BROKER_URL;

        return new ActiveMQConnectionFactory(user, password, url);
	}
	
}
