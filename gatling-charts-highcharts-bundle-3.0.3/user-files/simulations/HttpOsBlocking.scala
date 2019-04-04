import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class HttpOsBlocking extends Simulation {

  val httpConf = http.baseUrl("http://blocking-service.om-test-sigfox-components-throughput.svc:8080/test/")
                    .shareConnections

  val scnNoWork = scenario("No Work Blocking Scenario")
  .exec(
    http("No Work Blocking Request")
      .get("no-work")
  )

  val scnSmallWork = scenario("Small Work Blocking Scenario")
  .exec(
    http("Small Work Blocking Request")
      .get("small-work")
  )

  val scnMedWork = scenario("Medium Work Blocking Scenario")
  .exec(
    http("Medium Work Blocking Request")
      .get("medium-work")
  )

  setUp(scnNoWork.inject(rampUsersPerSec(1) to 1000 during (100 seconds)).protocols(httpConf),
        scnSmallWork.inject(rampUsersPerSec(1) to 1000 during (100 seconds)).protocols(httpConf),
        scnMedWork.inject(rampUsersPerSec(1) to 1000 during (100 seconds)).protocols(httpConf))
}
