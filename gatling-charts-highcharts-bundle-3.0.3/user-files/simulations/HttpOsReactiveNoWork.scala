import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class HttpOsReactiveNoWork extends Simulation {

  val httpConf = http.baseUrl("http://reactive-service.om-test-sigfox-components-throughput.svc:8080/test/")
                    .shareConnections

  val scnNoWork = scenario("No Work Reactive Scenario")
  .exec(
    http("No Work Reactive Request")
      .get("no-work")
  )

  setUp(scnNoWork.inject(rampUsersPerSec(1) to 1000 during (100 seconds)).protocols(httpConf))
}
