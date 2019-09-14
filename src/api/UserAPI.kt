package hos.houns.api

import hos.houns.model.*
import hos.houns.repository.UserRepo
import hos.houns.repository.UserRepoInterface
import hos.houns.utils.errorAware
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.request.receiveParameters
import io.ktor.response.respond
import io.ktor.response.respondRedirect
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.repackaged.net.bytebuddy.utility.RandomString
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.IllegalArgumentException

fun Route.user(userRepo: UserRepoInterface) {
    route("/user-api/") {
        post("signup") {
            errorAware {
                val signupPayload = call.receive<SignUpPayload>()

                userRepo.read(signupPayload.username)?.let {
                    call.respond(HttpStatusCode.Created,
                        message = SignupResponse(token = it.token))
                } ?: run {
                    val user = userRepo.create(
                        User(userName = signupPayload.username,
                            firstName = signupPayload.firstname,
                            lastName = signupPayload.lastname,
                            token = RandomString.make(50))
                    )
                    call.respond(HttpStatusCode.Created,
                        message = SignupResponse(token = user?.token ?: RandomString.make(50))
                    )
                }
            }

        }

        authenticate("adminAuth") {
            get("customers") {
              errorAware {
                      call.respond(FreeMarkerContent("users.ftl", mapOf("customers" to userRepo.getAll())))
              }
            }

            post("customers") {
              errorAware {
                  val parameters = call.receiveParameters()
                  val userId = parameters["userId"] ?: throw  IllegalArgumentException("missing parameter: id")
                  val action = parameters["action"] ?: throw  IllegalArgumentException("missing parameter: action")
                  when (action) {
                      "delete" -> userRepo.delete(userId.toInt())
                      "disable" -> {
                          //TODO
                      }
                  }
                  call.respondRedirect("/user-api/customers")
              }

            }
        }

    }
}