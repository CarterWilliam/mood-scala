package mood.config.parse.circe

import io.circe.generic.extras.Configuration

object WithDefaultsConfig {
  implicit val withDefaults: Configuration = Configuration.default.withDefaults
}
