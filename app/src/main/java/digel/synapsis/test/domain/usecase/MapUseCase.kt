package digel.synapsis.test.domain.usecase

import digel.synapsis.test.base.BaseUseCase
import digel.synapsis.test.domain.uimodel.MapUiModel
import digel.synapsis.test.data.remote.repository.MapsRepository
import digel.synapsis.test.data.remote.request.MapRequest
import digel.synapsis.test.utils.extension.fetchState
import digel.synapsis.test.utils.mapper.MapMapper.mapToMapUiModel
import digel.synapsis.test.utils.state.ResultState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MapUseCase(private val mapRepository: MapsRepository, dispatcher: CoroutineDispatcher) :
    BaseUseCase<MapRequest, MapUiModel>(dispatcher) {
    override suspend fun execute(param: MapRequest): Flow<ResultState<MapUiModel>> {
        return mapRepository.getLocation(param.latLng,param.mapKey).map { state ->
            fetchState {
                when (state) {
                    is ResultState.Loading -> {
                        ResultState.Loading()
                    }
                    is ResultState.Success -> {
                        ResultState.Success(state.data.mapToMapUiModel())
                    }

                    is ResultState.Failed -> {
                        ResultState.Failed(Throwable())
                    }
                }
            }

        }
    }
}