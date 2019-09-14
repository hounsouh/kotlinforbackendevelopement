package hos.houns.model

data class User(val userId:Int?=null,
                val userName:String,
                val firstName:String,
                val lastName:String,
                val token:String)

data class SignUpPayload(val username: String,
                         val firstname: String,
                         val lastname: String)

data class SignupResponse(val token:String)

data class Customers(val list: List<User>)


