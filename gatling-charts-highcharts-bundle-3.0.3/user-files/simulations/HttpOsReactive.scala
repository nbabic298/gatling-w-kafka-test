import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class HttpOsReactive extends Simulation {

  val httpConf = http.baseUrl("http://reactive-service.om-test-sigfox-components-throughput.svc/test/")
                    .shareConnections

  val scnNoWork = scenario("No Work Reactive Scenario")
  .exec(
    http("No Work Reactive Request")
      .get("no-work")
  )

  val scnSmallWork = scenario("Small Work Reactive Scenario")
  .exec(
    http("Small Work Reactive Request")
      .get("small-work")
  )

  val scnSmallScheduledWork = scenario("Small Scheduled Work Reactive Scenario")
  .exec(
    http("Small Scheduled Work Reactive Request")
      .get("small-scheduled-work")
  )

  val scnMedWork = scenario("Medium Work Reactive Scenario")
  .exec(
    http("Medium Work Reactive Request")
      .get("medium-work")
  )

  val scnMedScheduledWork = scenario("Medium Scheduled Work Reactive Scenario")
  .exec(
    http("Medium Scheduled Work Reactive Request")
      .get("medium-scheduled-work")
  )

  setUp(scnNoWork.inject(rampUsersPerSec(1) to 1000 during (100 seconds)).protocols(httpConf),
        scnSmallWork.inject(rampUsersPerSec(1) to 1000 during (100 seconds)).protocols(httpConf),
        scnSmallScheduledWork.inject(rampUsersPerSec(1) to 1000 during (100 seconds)).protocols(httpConf),
        scnMedWork.inject(rampUsersPerSec(1) to 1000 during (100 seconds)).protocols(httpConf),
        scnMedScheduledWork.inject(rampUsersPerSec(1) to 1000 during (100 seconds)).protocols(httpConf)
      )
}
