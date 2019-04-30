package cassandra_tester

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class HttpSasiV2AssetReadSimulation extends Simulation {

  val httpConf = http.baseUrl("http://cassandra-tester:8080/telemetry/sasi/")
                    .shareConnections

  val scn = scenario("Read Sasi v2 telemetry")
  .exec {
        session => session.set("applicationId", 1).set("assetUnitId", 2)
                    .set("deviceId", 3).set("key", "co2")
                    .set("doubleValue", System.currentTimeMillis % 100000000)
    }
  .exec(
    http("Sasi v2 telemetry read req")
      .get("v2/1/asset-unit/2/from/1556625536000/to/1564487936000/limit/2147483647?keys=co2,nh3")
  )

  setUp(scn.inject(constantUsersPerSec(1) during(10 seconds)).protocols(httpConf))
}