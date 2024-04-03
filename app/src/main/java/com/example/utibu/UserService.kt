
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("/api/users/login")
    fun login(@Body user: LoginRequest): Call<LoginResponse>
}