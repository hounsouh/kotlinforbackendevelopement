package hos.houns.repository

import hos.houns.model.Customers
import hos.houns.model.User
import hos.houns.model.UsersEntity
import hos.houns.utils.backgroundScope
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*

interface UserRepoInterface {
    suspend fun create(user: User): User?
    suspend fun read(userName: String): User?
    suspend fun update(userId: String, user: User): Boolean
    suspend fun delete(userId: Int): Boolean
    suspend fun getAll(): Customers
}

class UserRepo : UserRepoInterface {

    @Throws(ExposedSQLException::class)
    override suspend fun create(user: User): User? {
        backgroundScope {
            UsersEntity.insert {
                it[firstName] = user.firstName
                it[lastName] = user.lastName
                it[userName] = user.userName
                it[token] = user.token
            }
        }
        return read(userName = user.userName)
    }


    override suspend fun read(userName: String) = backgroundScope {
        UsersEntity.select { UsersEntity.userName eq userName }.map {
            User(
                userId = it[UsersEntity.userId],
                firstName = it[UsersEntity.firstName],
                lastName = it[UsersEntity.lastName],
                userName = it[UsersEntity.userName],
                token = it[UsersEntity.token]
            )
        }.firstOrNull()

    }

    override suspend fun getAll() = backgroundScope {
        Customers(UsersEntity.selectAll().map {
            User(
                userId = it[UsersEntity.userId],
                firstName = it[UsersEntity.firstName],
                lastName = it[UsersEntity.lastName],
                userName = it[UsersEntity.userName],
                token = it[UsersEntity.token]
            )
        })
    }

    override suspend fun update(userId: String, user: User): Boolean {
        backgroundScope {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    override suspend fun delete(userId: Int) = backgroundScope {
        UsersEntity.deleteWhere { UsersEntity.userId eq userId } > 0
    }
}