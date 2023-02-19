package digel.synapsis.test.ui.feature

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.BatteryManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.model.LatLng
import digel.synapsis.test.R
import digel.synapsis.test.databinding.ActivityPageABinding
import digel.synapsis.test.utils.extension.showSnackBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class PageAActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var viewBinding: ActivityPageABinding
    private val calendar by lazy { Calendar.getInstance() }
    private var latLng: LatLng? = null

    private val sensorManager: SensorManager by lazy {
        getSystemService(SENSOR_SERVICE) as SensorManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityPageABinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.run {
            txtTimeNow.text = calendar.time.toString()
            txtBatteryLevel.text =
                String.format(getString(R.string.str_battery_level), initBatteryLevel())
            checkLocationPermission()

            btnPageB.setOnClickListener {
                startActivity(Intent(this@PageAActivity, PageBActivity::class.java))
            }

            btnPageC.setOnClickListener {
                startActivity(PageCActivity.createIntent(this@PageAActivity, latLng))
            }
            lifecycleScope.launch(Dispatchers.Main) {
                txtAccelerometer.setOnClickListener {
                    registerSensorAccelerometer()
                }

                txtGyroscope.setOnClickListener {
                    registerSensorGyroscope()
                }

                txtMagnetometer.setOnClickListener {
                    registerSensorMagnetometer()
                }
            }
        }
    }

    private fun registerSensorAccelerometer() {

        // Specify the sensor you want to listen to
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also { accelerometer ->
            sensorManager.registerListener(
                this,
                accelerometer,
                SensorManager.SENSOR_DELAY_NORMAL,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }

    }

    private fun registerSensorGyroscope() {
        sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)?.also { gyroscope ->
            sensorManager.registerListener(
                this,
                gyroscope,
                SensorManager.SENSOR_DELAY_NORMAL,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    private fun registerSensorMagnetometer() {
        sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)?.also { magnetometer ->
            sensorManager.registerListener(
                this,
                magnetometer,
                SensorManager.SENSOR_DELAY_NORMAL,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {

        when (event?.sensor?.type) {
            Sensor.TYPE_ACCELEROMETER -> {
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]

                viewBinding.run {
                    txtAccelerometer.apply {
                        rotationX = y * 3f
                        rotationY = x * 3f
                        rotation = -x
                        translationX = x * -10
                        translationY = y * 10
                    }

                    val color = if (y.toInt() == 0 && x.toInt() == 0) Color.GREEN else Color.RED
                    txtAccelerometer.setBackgroundColor(color)

                    txtAccelerometer.text = String.format(getString(R.string.str_x_y_z), x, y, z)
                }
            }

            Sensor.TYPE_GYROSCOPE -> {
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]

                viewBinding.run {
                    txtGyroscope.text = String.format(getString(R.string.str_x_y_z), x, y, z)
                }
            }

            Sensor.TYPE_MAGNETIC_FIELD -> {
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]

                viewBinding.run {
                    txtMagnetometer.text = String.format(getString(R.string.str_x_y_z), x, y, z)
                }
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        return
    }

    override fun onDestroy() {
        sensorManager.unregisterListener(this)
        super.onDestroy()
    }

    private fun initBatteryLevel(): String {
        val iFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val batteryStatus: Intent? = registerReceiver(null, iFilter)
        val level: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        return level.toString()
    }

    private suspend fun getUserLocation() = withContext(Dispatchers.Main) {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        // cek apakah GPS atau network provider tersedia
        val isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (isGPSEnabled || isNetworkEnabled) {
            try {
                val locationListener = object : LocationListener {
                    override fun onLocationChanged(location: Location) {
                        // tangani pembaruan lokasi terbaru
                        locationManager.removeUpdates(this)
                    }

                    override fun onProviderDisabled(provider: String) {
                        // handle provider disabled
                    }

                    override fun onProviderEnabled(provider: String) {
                        // handle provider enabled
                        locationManager.removeUpdates(this)
                    }

                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                        // handle status changed
                    }
                }

                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES,
                    locationListener
                )
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES,
                    locationListener
                )

                // dapatkan lokasi terakhir
                val locationFromGps =
                    locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                val locationFromNetwork =
                    locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)


                // pilih lokasi yang lebih akurat
                val location = when {
                    locationFromGps != null && locationFromNetwork != null -> {
                        if (locationFromGps.accuracy > locationFromNetwork.accuracy) locationFromNetwork else locationFromGps
                    }
                    locationFromGps != null -> locationFromGps
                    locationFromNetwork != null -> locationFromNetwork
                    else -> null
                }

                viewBinding.txtGetLocation.text = String.format(
                    getString(R.string.str_lat_long),
                    location?.latitude,
                    location?.longitude
                )

                latLng = LatLng(
                    location?.latitude ?: 0.0,
                    location?.longitude ?: 0.0
                )

            } catch (e: SecurityException) {
                e.printStackTrace()
                viewBinding.root.showSnackBar(getString(R.string.str_failed_permission_location))
                // handle jika tidak diizinkan mengakses lokasi
                return@withContext null
            }
        } else {
            // handle jika GPS dan network tidak tersedia
            viewBinding.root.showSnackBar(getString(R.string.str_failed_permission_location_and_network))
            return@withContext null
        }
    }

    private fun checkLocationPermission() {
        try {
            if (ContextCompat.checkSelfPermission(
                    this@PageAActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            } else {
                lifecycleScope.launch {
                    getUserLocation()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                lifecycleScope.launch {
                    getUserLocation()
                }
            } else {
                viewBinding.root.showSnackBar(getString(R.string.str_failed_permission_location))
            }
        }

    companion object {
        // konstanta untuk waktu minimum pembaruan lokasi (dalam milidetik)
        private const val MIN_TIME_BW_UPDATES = 1000L

        // konstanta untuk jarak minimum pembaruan lokasi (dalam meter)
        private const val MIN_DISTANCE_CHANGE_FOR_UPDATES = 10.0f
    }


}