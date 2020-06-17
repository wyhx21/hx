package org.layz.hx.kakfa.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface HxKafkaErrorHandler<K,V> {
    /**
     * 监听的 topic
     * @return
     */
    String topic();

    /**
     * 消息处理
     * @param data
     */
    void errorHandler(ConsumerRecord<K, V> data);
}
