package code.Young_Explicit.nimbus.utils;

import redis.clients.jedis.Jedis;

public class PubSubWriter {

	Jedis publisher;
	String channel;
	
	public PubSubWriter(Jedis publishInstance, String channel) {
		this.publisher = publishInstance;
		this.channel = channel;
	}
	
	public void publish(final String message) {
		new Runnable() {
			public void run() {
			   	publisher.publish(channel, message);
			}
		}.run();
	}
}
