package digel.synapsis.test.utils.mapper

import com.google.android.gms.maps.model.LatLng
import digel.synapsis.test.data.remote.entity.GetLocation
import digel.synapsis.test.data.remote.request.MapRequest
import digel.synapsis.test.domain.uimodel.MapUiModel
import kotlin.math.ln

object MapMapper {

    fun GetLocation.mapToMapUiModel() : MapUiModel {
        return MapUiModel(lat = items.first().position.lat, lng = items.first().position.lng)
    }

    fun mapToMapsRequest(latLng: LatLng, keyMaps : String) : MapRequest {
        return MapRequest("${latLng.latitude},${latLng.longitude}",keyMaps)
    }
}