package hos.houns.model
import org.jetbrains.exposed.sql.Table

object UsersEntity: Table("users") {
    val userId = integer("user_id").autoIncrement().primaryKey(0)
    val userName = varchar("username", length = 50) // Column<String>
    val firstName = varchar("firstname", length = 50) // Column<String>
    val lastName = varchar("lastname", length = 50) // Column<String>
    val token = varchar("token", length = 50) // Column<String>
}
