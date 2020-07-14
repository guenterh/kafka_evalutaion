package producer

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}

import scala.beans.BeanProperty

@BeanProperty
class Customer (val customerId:Int, val customerName: String)


object Main1 extends App {

  val props = new Properties()
  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092")

  props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")

  props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "CustomSerializerScala")
  props.put(ProducerConfig.LINGER_MS_CONFIG,100)

  val producer = new KafkaProducer[String,Customer](props)

  val prodRecord = new ProducerRecord[String,Customer]("cc-eperiodica","poster-key-neu",
          new Customer(1234, "dies ist ein neuer Name"))

  val md = producer.send(prodRecord).get()

  //println(md)

}
