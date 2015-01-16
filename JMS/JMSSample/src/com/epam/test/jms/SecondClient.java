package com.epam.test.jms;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Destination;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.MessageConsumer;
import javax.jms.TextMessage;

public class SecondClient {
    Context context = null;
    ConnectionFactory factory = null;
    Connection connection = null;
    Destination destination = null;
    Session session = null;
    MessageConsumer consumer = null;

    public SecondClient() {

    }

    public void receiveMessage() {

        Properties initialProperties = new Properties();
        
        initialProperties.put(InitialContext.INITIAL_CONTEXT_FACTORY, "org.exolab.jms.jndi.InitialContextFactory");
        initialProperties.put(InitialContext.PROVIDER_URL, "tcp://localhost:3035");
        
        try {
            context = new InitialContext(initialProperties);
            factory = (ConnectionFactory) context.lookup("ConnectionFactory");
            destination = (Destination) context.lookup("queue1");
            connection = factory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            consumer = session.createConsumer(destination);
            
            consumer.setMessageListener(new MessageListener() {
				
				@Override
				public void onMessage(Message message) {
					if (message instanceof TextMessage) {
		                TextMessage text = (TextMessage) message;
		                try {
							System.out.println("Message is : " + text.getText());
						} catch (JMSException e) {
							e.printStackTrace();
						}
		            } else if (message instanceof ObjectMessage) {
		            	ObjectMessage objectMessage = (ObjectMessage) message;
		            	try {
							System.out.println("Message is : " + objectMessage.getObject().toString());
						} catch (JMSException e) {
							e.printStackTrace();
						}
		            }
				}
			});
            connection.start();
//            Message message = consumer.receive();	
            

        } catch (JMSException ex) {
            ex.printStackTrace();
        } catch (NamingException ex) {
            ex.printStackTrace();
        }

        if (context != null) {
            try {
                context.close();
            } catch (NamingException ex) {
                ex.printStackTrace();
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (JMSException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SecondClient secondClient = new SecondClient();
        secondClient.receiveMessage();
    }
}
