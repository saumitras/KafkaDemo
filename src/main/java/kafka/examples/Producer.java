package kafka.examples;

import java.util.List;
import java.util.Properties;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import java.util.Random;
import java.util.Arrays;

public class Producer extends Thread {
   private static final List<String> rndStr = Arrays.asList(
	"When Alok Nath was born doctor said, badhai ho, babu ji hue hain :P",
	"alok nath is so sanskari that he closes his eyes when he takes a bath",
	"alok nath is so sanskari that he was born at the age of 65",
	"Alok Nath is so sanskaari, he went to Sunburn and did Surya Namaskara.",
	"Alok Nath is so sanskari, ki uska ghar ka pressure cooker bhi seeti nahi Marta #aloknath",
	"When you type #Alok nath on Google search, I'm feeling Lucky changes to I'm feeling Sanskari",
	"#Alok nath ji has been appointed as coach to Virat Kohli to make him learn some etiquettes",
	"Alok Nath has his own AIIMS - Aloknath Institute of Insaaniyat, Maanavta & Sanskaar !",
	"According to Hindu Marriage Act, a girl can not marry if Alok Nath disapproves.",
	"Alok Nath walks slowly. Because Babuji jara dheere chalo"
   );

  private final kafka.javaapi.producer.Producer<Integer, String> producer;
  private final String topic;
  private final Properties props = new Properties();

  public Producer(String topic) {
    props.put("serializer.class", "kafka.serializer.StringEncoder");
    props.put("metadata.broker.list", "localhost:9092,localhost:9093");
    producer = new kafka.javaapi.producer.Producer<Integer, String>(new ProducerConfig(props));
    this.topic = topic;
  }
  
  public void run() {
    int messageNo = 1;

    //while(true) {
    while(messageNo < 10) {

      Random r = new Random();
      String messageStr = rndStr.get(r.nextInt(10));
      System.out.println("[Producing Message" + messageNo + "]: " + messageStr);

      producer.send(new KeyedMessage<Integer, String>(topic, messageStr));

      messageNo++;
    }
  }

}
