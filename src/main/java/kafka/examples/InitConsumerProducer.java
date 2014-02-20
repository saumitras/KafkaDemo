package kafka.examples;

public class InitConsumerProducer {
  public static void main(String[] args) {
    System.out.println("Starting producer");
    Producer producerThread = new Producer("topic1");
    producerThread.start();

    System.out.println("Starting consumer...");
    Consumer consumerThread = new Consumer("topic1");
    consumerThread.start();
    
  }
}
