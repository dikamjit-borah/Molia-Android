data class TitleDetails(
    var Actors: String? = null,
    var Awards: String? = null,
    var BoxOffice: String? = null,
    var Country: String? = null,
    var DVD: String? = null,
    var Director: String? = null,
    var Genre: String? = null,
    var Language: String? = null,
    var Metascore: String? = null,
    var Plot: String? = null,
    var Poster: String? = null,
    var Production: String? = null,
    var Rated: String? = null,
    var Ratings: List<Rating>? = null,
    var Released: String? = null,
    var Response: String? = null,
    var Runtime: String? = null,
    var Title: String? = null,
    var Type: String? = null,
    var Website: String? = null,
    var Writer: String? = null,
    var Year: String? = null,
    var imdbID: String? = null,
    var imdbRating: String? = null,
    var imdbVotes: String? = null
)

data class Rating(
    var Source: String? = null,
    var varue: String? = null
)