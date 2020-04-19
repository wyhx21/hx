package org.layz.hx.kakfa.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HxKafkaDefaultListener<K, V> implements HxKafkaListener<K, V>{
	private static final Logger LOGGER = LoggerFactory.getLogger(HxKafkaDefaultListener.class);
	@Override
	public String topic() {
		return "default";
	}

	@Override
	public void onMessage(ConsumerRecord<K, V> data) {
		V value = data.value();
		LOGGER.debug("value: {}", value);
	}

}
