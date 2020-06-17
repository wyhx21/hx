package org.layz.hx.kakfa.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.MessageListener;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HxSpringKafkaMessageListener<K, V> implements MessageListener<K, V>{
	private static final String ERROR_TOPIC_SUFFIX = ".DLT";
	private static final Logger LOGGER = LoggerFactory.getLogger(HxSpringKafkaMessageListener.class);
	@Autowired
	private List<HxKafkaListener<K,V>> listeners;
	@Autowired
	private List<HxKafkaErrorHandler<K,V>> handlers;
	
	@Override
	public void onMessage(ConsumerRecord<K, V> data) {
		String topic = data.topic();
		int partition = data.partition();
		long offset = data.offset();
		long begin = System.currentTimeMillis();
		LOGGER.info("onMessage begin, topic: {}, offset: {}, partition: {}", topic, offset, partition);
		try {
			if(topic.endsWith(ERROR_TOPIC_SUFFIX)) {
				for (HxKafkaErrorHandler<K, V> handler : handlers) {
					String handlerTopic = handler.topic() + ERROR_TOPIC_SUFFIX;
					if(handlerTopic.equals(topic)) {
						handler.errorHandler(data);
					}
				}
			} else {
				for (HxKafkaListener<K, V> listener : listeners) {
					if(listener.topic().equals(topic)) {
						listener.onMessage(data);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("onMessage end, topic: {}, offset: {}, partition: {}, handleTime: {} ms",
					topic, offset, partition,(System.currentTimeMillis() - begin), e);
			throw e;
		}
		LOGGER.info("onMessage end, topic: {}, offset: {}, partition: {}, handleTime: {} ms",
				topic, offset, partition,(System.currentTimeMillis() - begin));
	}

	public String[] topics(){
		Set<String> topicSet = new HashSet<>();
		for (HxKafkaListener<K, V> listener : listeners) {
			topicSet.add(listener.topic());
		}
		for (HxKafkaErrorHandler<K, V> handler : handlers) {
			topicSet.add(handler.topic() + ERROR_TOPIC_SUFFIX);
		}
		return topicSet.toArray(new String[0]);
	}
}
