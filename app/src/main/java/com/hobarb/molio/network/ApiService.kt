import com.hobarb.molio.models.SaveTitleModel
import retrofit2.Call
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
    fun searchMovie(@Query("s") searchTerm: String): Call<SearchResponse>
}

data class SearchResponse(
    val search: List<Movie>
)

data class Movie(
    val title: String,
    val year: String,
    val imdbID: String,
    val type: String,
    val poster: String
)
