import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.hobarb.molio.models.dtos.SaveTitleModel
import com.hobarb.molio.models.schemas.SearchTitle
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MolioBackendService {
    @POST("/v1/save")
    fun saveTitle(@Body body: SaveTitleModel): Call<Unit>
}

interface OmdbApiService {
    @GET("/")
    fun searchTitles(@Query("apikey") apikey: String, @Query("s") s: String): Call<JsonObject>
}