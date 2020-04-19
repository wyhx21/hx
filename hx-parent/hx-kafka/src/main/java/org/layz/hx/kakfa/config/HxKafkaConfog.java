package org.layz.hx.kakfa.config;

import java.util.Map;

import org.layz.hx.kakfa.listener.HxMessageListener;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;

public class HxKafkaConfog<K, V>  {
	private Map<String, Object> cunsumerProperTies;
	
	public void init() {
		HxMessageListener<K, V>  hxMessageListener = new HxMessageListener<K, V> ();
		String[] topics = hxMessageListener.init();
		
		ConsumerFactory<K, V>  consumerFactory = new DefaultKafkaConsumerFactory<K, V> (cunsumerProperTies);
		
		ContainerProperties containerProperties = new ContainerProperties(topics);
		containerProperties.setMessageListener(hxMessageListener);
		
		KafkaMessageListenerContainer<K, V>  container = new KafkaMessageListenerContainer<K, V> (
				consumerFactory,containerProperties);
		container.start();
	}
	
	public void setCunsumerProperTies(Map<String, Object> cunsumerProperTies) {
		this.cunsumerProperTies = cunsumerProperTies;
	}

}
