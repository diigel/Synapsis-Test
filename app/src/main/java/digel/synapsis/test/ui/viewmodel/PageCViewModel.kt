package digel.synapsis.test.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import digel.synapsis.test.domain.uimodel.MapUiModel
import digel.synapsis.test.domain.usecase.MapUseCase
import digel.synapsis.test.utils.extension.setOnListener
import digel.synapsis.test.utils.mapper.MapMapper
import digel.synapsis.test.utils.state.ResultState
import digel.synapsis.test.utils.state.ResultStateListener
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@OptIn(KoinApiExtension::class)
class PageCViewModel(context: Application) : AndroidViewModel(context), KoinComponent {

    private val mapUseCase : MapUseCase by inject()

    private val _mapEvent = MutableLiveData<MapUiModel>()
    val mapEvent get() = _mapEvent

    fun getLocation(latLng: LatLng,key : String) {
        viewModelScope.launch {
            mapUseCase(MapMapper.mapToMapsRequest(latLng,key)).collectLatest { resultState ->

                resultState.setOnListener(object : ResultStateListener<MapUiModel> {
                    override fun onSuccess(data: MapUiModel) {
                        _mapEvent.value = data
                    }

                    override fun onFailed(t: Throwable) {
                        t.printStackTrace()
                    }

                })

            }
        }
    }
}