package com.verizon.trainingdemo.order.config;

import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CacheEvictConfiguration {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	RedisTemplate redisTemplate;

	@Scheduled(cron = "${redis.api.update.interval}")
	@CacheEvict(value = "${redis.key.order,redis.key.allOrder}", allEntries = true)
	protected void cacheEvict() {
		
		Set keys2 = redisTemplate.keys("*");
		//Set<byte[]> keys = redisTemplate.getConnectionFactory().getConnection().keys("*".getBytes());
		//Iterator<byte[]> it = keys.iterator();
		Iterator it = keys2.iterator();
		log.info("deleting cache " + keys2.size());
		while(it.hasNext()) {
			Boolean delete = redisTemplate.delete(it.next());
			log.info("cache deleted" + delete);
		}
	}
}
