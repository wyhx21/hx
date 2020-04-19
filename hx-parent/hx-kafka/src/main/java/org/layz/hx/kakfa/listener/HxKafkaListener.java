package org.layz.hx.kakfa.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface HxKafkaListener<K,V> {
	/**
	 * 监听的 topic
	 * @return
	 */
	String topic();
	/**
	 * 消息处理
	 * @param data
	 */
	void onMessage(ConsumerRecord<K, V> data);
}
