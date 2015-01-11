package code.Young_Explicit.nimbus.managers;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class DatabaseManager {

	JedisPool jp = new JedisPool(new JedisPoolConfig(), "192.99.46.113", 7969, 0);
	
	public JedisPool getPool() {
		return jp;
	}
}