package org.layz.hx.kakfa.listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.MessageListener;

public class HxMessageListener<K, V> implements MessageListener<K, V>{
	private static final Logger LOGGER = LoggerFactory.getLogger(HxMessageListener.class);
	private Map<String, HxKafkaListener<K, V>> listenerMap = new HashMap<String, HxKafkaListener<K,V>>();
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String[] init() {
		ServiceLoader<HxKafkaListener> load = ServiceLoader.load(HxKafkaListener.class);
		List<String> topicList = new ArrayList<String>();
		for (HxKafkaListener hxKafkaListener : load) {
			listenerMap.put(hxKafkaListener.topic(), hxKafkaListener);
			topicList.add(hxKafkaListener.topic());
		}
		String[] array = topicList.toArray(new String[topicList.size()]);
		LOGGER.info("kafka topics: {}",Arrays.toString(array));
		return array;
	}
	
	@Override
	public void onMessage(ConsumerRecord<K, V> data) {
		String topic = data.topic();
		int partition = data.partition();
		long offset = data.offset();
		long begin = System.currentTimeMillis();
		LOGGER.info("onMessage begin, topic: {}, offset: {}, partition: {}", topic, offset, partition);
		HxKafkaListener<K, V> kafkaListener = listenerMap.get(topic);
		kafkaListener.onMessage(data);
		LOGGER.info("onMessage end, topic: {}, offset: {}, partition: {}, handleTime: {} ms",
				topic, offset, partition,(System.currentTimeMillis() - begin));
	}

}
