package hos.houns.utils
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import io.ktor.routing.Route
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.transactions.transaction
import com.google.gson.Gson
import com.google.gson.JsonArray
import hos.houns.model.SignUpPayload
import hos.houns.model.User
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.respondText
import io.ktor.util.pipeline.PipelineContext
import javax.jws.soap.SOAPBinding


suspend fun <T> backgroundScope(block: () -> T): T =
    withContext(Dispatchers.Default) {
        transaction { block() }
    }


inline fun <reified T>JsonElement.convertToObject(): T? {
    return Gson().fromJson(this, T::class.java)
}


suspend fun <R> PipelineContext<*, ApplicationCall>.errorAware(block: suspend () -> R): R? {
    return try {
        block()
    } catch (e: Exception) {
        call.respondText("""{"error":"$e"}"""
            , ContentType.parse("application/json")
            , HttpStatusCode.InternalServerError)
        null
    }
}

