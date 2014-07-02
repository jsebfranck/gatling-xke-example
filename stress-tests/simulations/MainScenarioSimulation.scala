
import io.gatling.core.Predef._
import io.gatling.core.session.Expression
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import io.gatling.http.Headers.Names._
import io.gatling.http.Headers.Values._
import scala.concurrent.duration._
import bootstrap._
import assertions._

class MainScenarioSimulation extends Simulation {

	val httpProtocol = http
		.baseURL("http://fortuneo-alpha.elasticbeanstalk.com")
		.authorizationHeader("Basic b2s6b2s=")

	val postParams = """{"civility":"M","lastname":"qsdf","firstname":"qsdf","birthDate":"10051900","country":"FRANCE","address":"qsdmlfkjqlmsdf","postalCode":"78888","city":"ville","mobilePhone":"0610101010","email":"jfranck@xebia.test.fr","revenu":"REVENU_LESS_1500"}"""

	val scn = scenario("Scenario Name")
		.exec(http("GET homepage")
			.get("""/"""))
		.pause(1)
		.exec(http("GET documentation page")
			.get("""/demander-documentation"""))
		.pause(1)
		.exec(http("GET documentation form")
			.get("""/modules/core/views/documentation.client.view.html"""))
		.pause(1)
		.exec(http("POST documentation form")
			.post("""/api/documentationrequest""")
			.body(StringBody(postParams)).asJSON)
		.pause(1)
		.exec(http("GET validation page")
			.get("""/demander-documentation-confirmation"""))
		
	setUp(scn.inject(atOnce(1 user))).protocols(httpProtocol)
}