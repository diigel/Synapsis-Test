package digel.synapsis.test.data.remote.repository

import digel.synapsis.test.data.remote.entity.GetLocation
import digel.synapsis.test.data.network.ApiService
import digel.synapsis.test.utils.extension.fetch
import digel.synapsis.test.utils.state.ResultState
import kotlinx.coroutines.flow.Flow

class MapsRepositoryImp(val apiService: ApiService) : MapsRepository {

    override suspend fun getLocation(
        latLng: String,
        mapKey: String
    ): Flow<ResultState<GetLocation>> {
        return fetch {
            apiService.getLocation(latLng,mapKey)
        }
    }
}