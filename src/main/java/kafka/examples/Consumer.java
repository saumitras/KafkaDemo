package kafka.examples;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.Message;


public class Consumer extends Thread {

  private final ConsumerConnector consumer;
  private final String topic;
  
  public Consumer(String topic) {
    consumer = kafka.consumer.Consumer.createJavaConsumerConnector(createConsumerConfig());
    this.topic = topic;
  }

  private static ConsumerConfig createConsumerConfig() {
    Properties props = new Properties();
    props.put("zookeeper.connect", "localhost:2181");
    props.put("group.id","group1");
    props.put("zookeeper.session.timeout.ms", "400");
    props.put("zookeeper.sync.time.ms", "200");
    props.put("auto.commit.interval.ms", "1000");

    return new ConsumerConfig(props);

  }
 
  public void run() {
    Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
    topicCountMap.put(topic, new Integer(1));
    //topicCountMap.put(topic, new Integer(2));

    //provide a list of (topic,id) for which you want a stream iterator
    Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);

    //it will return multiple streams...one stream per (topic,id) pair....multiple streams can read from same topic
    KafkaStream<byte[], byte[]> stream =  consumerMap.get(topic).get(0);
    
    ConsumerIterator<byte[], byte[]> it = stream.iterator();

    long tweetNumber = 1L; 
    while(it.hasNext()) {
      
      String msg = new String(it.next().message());
	
      SentimentClassifier sentClassifier = new SentimentClassifier();
      String sentiment = sentClassifier.classify(msg);

      System.out.println("[Sentiment = " + sentiment + " ] " + " [Consumed Message" + tweetNumber + "]: " + msg);

      tweetNumber++;
    }
  }
}
