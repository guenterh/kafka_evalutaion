package consumer

import java.time.Duration
import java.time.temporal.TemporalUnit
import java.{lang, util}
import java.util.Properties
import java.util.{Collections, Properties}

import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRebalanceListener, ConsumerRecords, KafkaConsumer}
import org.apache.kafka.common.TopicPartition

object MainConsumer extends App{

  import scala.jdk.CollectionConverters._

  val props = new Properties()
  props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
  props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
  props.put(ConsumerConfig.GROUP_ID_CONFIG,"111")
  props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest")



  val consumer = new KafkaConsumer[String, String](props)
  consumer.subscribe(Collections.singletonList("cc-idssg"), new ConsumerRebalanceListener {
    override def onPartitionsRevoked(partitions: util.Collection[TopicPartition]): Unit = {

    }

    override def onPartitionsAssigned(partitions: util.Collection[TopicPartition]): Unit = {
      //println("assigned partitions " + partitions)
      consumer.seekToBeginning(partitions)


      //for (partition <- partitions.asScala) {
      //  consumer.seek(partition,5000)
      //}


    }
  })
  //consumer.poll(Duration.ofMillis(0))
  //consumer.seekToBeginning(Collections.singletonList(new TopicPartition("cc-idssg",0)))

  while (true) {
    val records = consumer.poll(Duration.ofMillis(1000L))

    var count = 0
    records.forEach(record => {
      count += 1
      //println(record.key())
      //println(record.offset())
      //println(record.value())

    })
    println(count)

  }





  /*
  consumer.subscribe(Collections.singletonList("cc-idssg") )
  val tp: util.Map[TopicPartition, lang.Long] = consumer.endOffsets(Collections.singletonList( new TopicPartition("cc-idssg",0)))
  tp.forEach((tp,off) => println(off))
  println()
   */



}
