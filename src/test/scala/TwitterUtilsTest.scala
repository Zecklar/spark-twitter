import org.scalatest.{FlatSpec, Matchers}
import scala.io.Source

class TwitterUtilsTest extends FlatSpec with Matchers {
  import TwitterHelper._
  behavior of "TwitterUtilsTest"

  it should "readTwitterTokens" in {
    val tokens = readTwitterTokens(Source.fromResource("testTokens.keys"))
    val expectedTokens = Map(
      "consumerKey" -> "testkey",
      "consumerSecret" -> "testsecret",
      "accessToken" -> "testtoken",
      "accessTokenSecret" -> "testtokensecret"
    )

    tokens shouldBe expectedTokens
  }

}
