package digel.synapsis.test.data.network

import digel.synapsis.test.data.remote.entity.GetLocation
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/v1/revgeocode")
    fun getLocation(
        @Query("at") at: String,
        @Query("apiKey") apiKey: String
    ): GetLocation
}