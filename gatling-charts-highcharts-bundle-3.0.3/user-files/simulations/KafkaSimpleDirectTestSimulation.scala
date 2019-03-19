import io.gatling.core.Predef._
import org.apache.kafka.clients.producer.ProducerConfig
import scala.concurrent.duration._

import com.github.mnogu.gatling.kafka.Predef._

class KafkaSimpleDirectTestSimulation extends Simulation {
  val kafkaConf = kafka
    .topic("telemetry")
    .properties(
      Map(
        ProducerConfig.ACKS_CONFIG -> "1",
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG -> "my-cluster-kafka-brokers.om-strimzi-0100:9092",
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG ->
          "org.apache.kafka.common.serialization.StringSerializer",
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG ->
          "org.apache.kafka.common.serialization.StringSerializer"))

  val scn = scenario("Kafka Test")
    // You can also use feeder
    .exec {
        session => session
                    .set("tlm_counter_totald", System.currentTimeMillis % 100000)
    }
    .feed(csv("kafkaDirectTestDevices.csv").random)
    .exec(kafka("request").send[String](
      "{" + 
        "\"tlm_counter_total\": ${tlm_counter_totald}" +
      "}"))

  setUp(
    scn
      .inject(rampUsersPerSec(5000) to 10000 during (10 seconds)))//.inject(constantUsersPerSec(10) during(10 seconds))) 
    .protocols(kafkaConf)
}