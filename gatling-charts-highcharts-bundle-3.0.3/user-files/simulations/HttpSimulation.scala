import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class HttpSimulation extends Simulation {

  val httpConf = http.baseUrl("https://sigfox-reactive-connector.apps.heptk.hr/sigfox/uplink/")
  						      .header("AccessToken", "ddewfrg4334wfswe2342e2dd")
                    .header("Content-Type", "application/json")
                    .shareConnections

  val scn = scenario("My Scenario")
  .feed(csv("telemetry.csv").circular)
  .exec(
    http("My Request")
      .post("callback/12")
      .body(StringBody("{\"EUI\":\"${eui}\", \"data\":\"${data}\"}"))
  )

  setUp(scn.inject(constantUsersPerSec(10) during(10 seconds)).protocols(httpConf))
}
