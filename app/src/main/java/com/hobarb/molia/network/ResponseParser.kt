import retrofit2.Response
import com.hobarb.molia.utils.Constants

object ResponseParser {

    fun <T> parseResponse(response: Response<T>): ApiResponse<T & Any> {
        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                ApiResponse.Success(body)
            } else {
                ApiResponse.Error(Constants.MESSAGE.NO_RESPONSE)
            }
        } else {
            val errorBody = response.errorBody()?.string()
            val errorMessage = if (!errorBody.isNullOrBlank()) {
                errorBody
            } else {
                response.message()
            }
            ApiResponse.Error(errorMessage ?: Constants.MESSAGE.PARSE_ERROR)
        }
    }
}

sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val errorMessage: String) : ApiResponse<Nothing>()
}
