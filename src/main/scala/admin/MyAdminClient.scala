package admin

import java.util.{Collections, Properties}

import org.apache.kafka.clients.admin.{AdminClient, AdminClientConfig, DescribeConfigsResult}
import org.apache.kafka.common.config.ConfigResource

object MyAdminClient extends App{

  /*
  val props = new Properties()
  props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  val admin = AdminClient.create(props) // TODO: Do something useful with AdminClientadmin.close(Duration.ofSeconds(30));
  val cGroups = admin.listOffsets()


   */
  //https://docs.scala-lang.org/overviews/collections/conversions-between-java-and-scala-collections.html
  //https://docs.scala-lang.org/overviews/collections-2.13/conversions-between-java-and-scala-collections.html
  import scala.jdk.CollectionConverters._
  //import collection.JavaConverters._

  val config = new Properties();
  config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
  val admin = AdminClient.create(config);
  for (node <- admin.describeCluster().nodes().get().asScala) {
    System.out.println("-- node: " + node.id() + " --");
    val cr: ConfigResource = new ConfigResource(ConfigResource.Type.BROKER,"0");
    val dcr: DescribeConfigsResult = admin.describeConfigs(Collections.singleton(cr));

    dcr.all().get().forEach((k, c) => {
      c.entries()
        .forEach(configEntry => {System.out.println(configEntry.name() + "= " + configEntry.value());});
    });
  }


}
