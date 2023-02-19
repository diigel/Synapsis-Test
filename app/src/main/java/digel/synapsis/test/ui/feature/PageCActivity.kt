package digel.synapsis.test.ui.feature

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import digel.synapsis.test.R
import digel.synapsis.test.databinding.ActivityPageCActivityBinding
import digel.synapsis.test.domain.uimodel.MapUiModel
import digel.synapsis.test.ui.viewmodel.PageCViewModel

class PageCActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityPageCActivityBinding
    private val viewModel: PageCViewModel by viewModels()

    private val latLong by lazy {
        if (Build.VERSION.SDK_INT >= 33) {
            intent?.getParcelableExtra(EXTRA_LAT_LONG, LatLng::class.java)
        } else {
            intent?.getParcelableExtra(EXTRA_LAT_LONG)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityPageCActivityBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.run {
            val mapsView = supportFragmentManager.findFragmentById(R.id.mapsView) as SupportMapFragment
            mapsView.getMapAsync { maps ->
                val oldPosition = maps.cameraPosition.target

                latLong?.let { latLongParcel ->
                    maps.moveCamera(CameraUpdateFactory.newLatLngZoom(latLongParcel, 18F))
                }

                maps.setOnCameraIdleListener {
                    val newPosition = maps.cameraPosition.target
                    if (newPosition != oldPosition) {
                        getLocationAndRead(newPosition){result ->
                            val findLocation = LatLng(result?.lat ?: 0.0, result?.lng ?: 0.0)

                            maps.animateCamera(CameraUpdateFactory.newLatLng(findLocation), 200,
                                object : GoogleMap.CancelableCallback {
                                    override fun onFinish() {

                                    }

                                    override fun onCancel() {

                                    }
                                })

                        }
                    }
                }
            }
        }
    }

    private fun getLocationAndRead(latLong: LatLng, result: (MapUiModel?) -> Unit) {
        viewModel.getLocation(latLong, getString(R.string.maps_key))

        viewModel.mapEvent.observe(this) {
            result.invoke(it)
        }
    }

    companion object {
        const val EXTRA_LAT_LONG = "PageCActivity.LatLong"
        fun createIntent(
            context: AppCompatActivity,
            latLong: LatLng?
        ): Intent {
            return Intent(context, PageCActivity::class.java).apply {
                putExtra(EXTRA_LAT_LONG, latLong)
            }
        }
    }
}