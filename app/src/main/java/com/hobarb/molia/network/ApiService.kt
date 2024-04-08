import com.google.gson.JsonObject
import com.hobarb.molia.models.dtos.SaveTitleModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MoliaBackendService {
    @POST("/v1/save")
    fun saveTitle(@Body body: SaveTitleModel): Call<Unit>
}

interface OmdbApiService {
    @GET("/")
    fun searchTitles(@Query("apikey") apikey: String, @Query("s") s: String): Call<JsonObject>
}