package hos.houns
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import freemarker.cache.ClassTemplateLoader
import hos.houns.api.user
import hos.houns.model.UsersEntity
import hos.houns.repository.UserRepo
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.features.*
import io.ktor.freemarker.FreeMarker
import io.ktor.gson.gson
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.event.Level
import java.util.*

fun initDB(block: () ->HikariDataSource) {
    Database.connect(block())
    transaction {
        SchemaUtils.create(UsersEntity)
    }
}

fun Application.module() {
   // install(DefaultHeaders)
    install(StatusPages) {
    }

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this@module.javaClass.classLoader, "templates")
    }

    install(Authentication) {
        basic(name = "adminAuth") {
            realm = "Ktor Server"
            validate { credentials ->
                if (credentials.password == "passer123") UserIdPrincipal(credentials.name) else null
            }
        }
    }


    initDB {
        val config = HikariConfig()
        config.driverClassName = "org.h2.Driver"
        config.jdbcUrl = "jdbc:h2:mem:test"
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
         HikariDataSource(config)
    }

    install(Routing) {
        user(UserRepo())
    }

}

fun main(args: Array<String>) {
    embeddedServer(Netty, 8081, module = Application::module).start(wait = true)
}
