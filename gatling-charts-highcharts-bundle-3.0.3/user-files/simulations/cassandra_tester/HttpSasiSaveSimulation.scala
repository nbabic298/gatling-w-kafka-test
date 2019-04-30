package cassandra_tester

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class HttpSasiSaveSimulation extends Simulation {

  val httpConf = http.baseUrl("http://cassandra-tester:8080/telemetry/sasi/")
                    .shareConnections

  val scn = scenario("Save Sasi telemetry")
  .exec {
        session => session.set("applicationId", 1).set("assetUnitId", 2)
                    .set("deviceId", 3).set("key", "co2")
                    .set("doubleValue", System.currentTimeMillis % 100000000)
    }
  .exec(
    http("Sasi telemetry req")
      .post("")
      .body(StringBody("{\"applicationId\":\"${applicationId}\", \"assetUnitId\":\"${assetUnitId}\", \"deviceId\":\"${deviceId}\", \"deviceId\":\"${deviceId}\", \"key\":\"${key}\", \"key\":\"${key}\"}"))
  )

  setUp(scn.inject(constantUsersPerSec(1) during(10 seconds)).protocols(httpConf))
}