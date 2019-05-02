package cassandra_tester

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class HttpSidxSaveSimulation extends Simulation {

  val httpConf = http.baseUrl("http://cassandra-tester:8080/telemetry/sidx/")
                    .header("Content-Type", "application/json")
                    .shareConnections

  val scn = scenario("Save Sidx telemetry")
  .exec {
        session => session.set("applicationId", 1).set("assetUnitId", (System.currentTimeMillis % 10) + 1)
                    .set("deviceId", (System.currentTimeMillis % 10) + 1).set("key", "co2")
                    .set("doubleValue", System.currentTimeMillis % 1000000000)
    }
  .exec(
    http("Sidx telemetry req")
      .post("")
      .body(StringBody("{\"applicationId\":\"${applicationId}\", \"assetUnitId\":\"${assetUnitId}\", \"deviceId\":\"${deviceId}\", \"deviceId\":\"${deviceId}\", \"key\":\"${key}\", \"doubleValue\":\"${doubleValue}\"}"))
  )

  setUp(scn.inject(constantUsersPerSec(1800) during(150 seconds)).protocols(httpConf))
}