import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class HttpOsReactiveSmallScheduledWork extends Simulation {

  val httpConf = http.baseUrl("http://reactive-service.om-test-sigfox-components-throughput.svc:8080/test/")
                    .shareConnections

  val scnSmallScheduledWork = scenario("Small Scheduled Work Reactive Scenario")
  .exec(
    http("Small Scheduled Work Reactive Request")
      .get("small-scheduled-work")
  )

  setUp(scnSmallScheduledWork.inject(rampUsersPerSec(1) to 1000 during (100 seconds)).protocols(httpConf))
}
