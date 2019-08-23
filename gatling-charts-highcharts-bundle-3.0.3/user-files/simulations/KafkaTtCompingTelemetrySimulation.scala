import io.gatling.core.Predef._
import org.apache.kafka.clients.producer.ProducerConfig
import scala.concurrent.duration._

import com.github.mnogu.gatling.kafka.Predef._

class KafkaTtCompingTelemetrySimulation extends Simulation {
  val kafkaConf = kafka
    .topic("telemetry")
    .properties(
      Map(
        ProducerConfig.ACKS_CONFIG -> "1",
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG -> "my-cluster-kafka-brokers.strimzi0113:9092",
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
    .feed(csv("tlm_import2017.csv").circular)
    .exec(kafka("request").send[String](
      "{" +
        "\"deviceId\": ${deviceId}," +
        "\"${property}\": ${value}" +
        "\"time\": ${time}," +
      "}"))

  setUp(
    scn
      .inject(constantUsersPerSec(500) during(1752 seconds)))// .inject(rampUsersPerSec(1) to 1 during (1 seconds)))
    .protocols(kafkaConf)
}