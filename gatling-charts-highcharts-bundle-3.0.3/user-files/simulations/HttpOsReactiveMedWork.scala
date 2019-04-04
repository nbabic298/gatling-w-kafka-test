import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class HttpOsReactiveMedWork extends Simulation {

  val httpConf = http.baseUrl("http://reactive-service.om-test-sigfox-components-throughput.svc:8080/test/")
                    .shareConnections

  val scnMedWork = scenario("Medium Work Reactive Scenario")
  .exec(
    http("Medium Work Reactive Request")
      .get("medium-work")
  )

  setUp(scnMedWork.inject(rampUsersPerSec(1) to 1000 during (100 seconds)).protocols(httpConf))
}
