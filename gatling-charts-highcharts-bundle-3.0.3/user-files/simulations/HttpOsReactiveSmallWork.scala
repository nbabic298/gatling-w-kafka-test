import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class HttpOsReactiveSmallWork extends Simulation {

  val httpConf = http.baseUrl("http://reactive-service.om-test-sigfox-components-throughput.svc:8080/test/")
                    .shareConnections

  val scnSmallWork = scenario("Small Work Reactive Scenario")
  .exec(
    http("Small Work Reactive Request")
      .get("small-work")
  )

  setUp(scnSmallWork.inject(rampUsersPerSec(1) to 1000 during (100 seconds)).protocols(httpConf))
}
