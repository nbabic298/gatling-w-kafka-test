import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class HttpOsReactiveScheduledMedWork extends Simulation {

  val httpConf = http.baseUrl("http://reactive-service.om-test-sigfox-components-throughput.svc:8080/test/")
                    .shareConnections

  val scnMedScheduledWork = scenario("Medium Scheduled Work Reactive Scenario")
  .exec(
    http("Medium Scheduled Work Reactive Request")
      .get("medium-scheduled-work")
  )

  setUp(scnMedScheduledWork.inject(rampUsersPerSec(1) to 1000 during (100 seconds)).protocols(httpConf))
}
