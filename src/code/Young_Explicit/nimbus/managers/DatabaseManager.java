package code.Young_Explicit.nimbus.managers;

import redis.clients.jedis.Jedis;

public class DatabaseManager {

	public Jedis jedis = new Jedis("localhost");

	public DatabaseManager() {
		jedis.connect();
	}

	public Jedis getJedis() {
		if (jedis.isConnected()) {
			return jedis;
		} else {
			jedis.connect();
			return jedis;
		}
	}
}
