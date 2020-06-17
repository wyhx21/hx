package org.layz.hx.kakfa.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HxKafkaDefaultTopicListener<K, V> implements HxKafkaListener<K, V>,HxKafkaErrorHandler<K,V>{
	private static final Logger LOGGER = LoggerFactory.getLogger(HxKafkaDefaultTopicListener.class);
	@Override
	public String topic() {
		return "default";
	}

	@Override
	public void errorHandler(ConsumerRecord<K, V> data) {
		LOGGER.error("topic: {}, value: {}", data.topic(), data.value());
	}

	@Override
	public void onMessage(ConsumerRecord<K, V> data) {
		LOGGER.info("topic: {}, value: {}", data.topic(), data.value());
	}

}
