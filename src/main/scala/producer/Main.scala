package producer

import java.util.Properties

import org.apache.kafka.clients.producer._

import scala.util.Random

class DemoProducerCallback extends Callback {
  override def onCompletion(metadata: RecordMetadata, exception: Exception): Unit = {

    //println(metadata.topic)
    //println(metadata.offset)

  }
}


object Main extends App {

  val props = new Properties()
  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092")

  props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")

  props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
  props.put(ProducerConfig.LINGER_MS_CONFIG,100)

  val producer = new KafkaProducer[String,String](props)

  val prodRecord = new ProducerRecord[String,String]("cc-posters","poster-key-neu","poster-value-neu")

  val f = producer.send(prodRecord).get

  for (r <- 1 to 50000) {
    //println("value_" + Random.nextString(r))
    //producer.send(new ProducerRecord[String,String]("cc-posters", "key_" + Random.nextString(r), "value_" + Random.nextString(r)),
    //  new DemoProducerCallback)
    producer.send(new ProducerRecord[String,String]("cc-posters", "key_" + Random.nextString(r), "value_" + Random.nextString(r)))

  }


  //println(f)



}
