package digel.synapsis.test.data.remote.repository

import digel.synapsis.test.data.remote.entity.GetLocation
import digel.synapsis.test.utils.state.ResultState
import kotlinx.coroutines.flow.Flow

interface MapsRepository {

    suspend fun getLocation (latLng: String, mapKey : String) : Flow<ResultState<GetLocation>>
}