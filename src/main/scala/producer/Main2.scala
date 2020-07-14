package producer

import java.util.Properties

object Main2 extends App {


  import org.apache.kafka.clients.admin.{AdminClient, AdminClientConfig}

  val props = new Properties()
  props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  val admin = AdminClient.create(props) // TODO: Do something useful with AdminClientadmin.close(Duration.ofSeconds(30));
  val cGroups = admin.listConsumerGroups().all().get



  admin.close()


}
