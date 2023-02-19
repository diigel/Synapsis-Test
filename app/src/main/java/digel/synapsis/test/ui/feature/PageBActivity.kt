package digel.synapsis.test.ui.feature

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import digel.synapsis.test.BuildConfig
import digel.synapsis.test.R
import digel.synapsis.test.databinding.ActivityPageBActivityBinding

class PageBActivity : AppCompatActivity() {

    private lateinit var viewBinding : ActivityPageBActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityPageBActivityBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.run {
            txtManufacture.text = String.format(getString(R.string.str_manufacture),Build.MANUFACTURER)
            txtModel.text = String.format(getString(R.string.str_model),Build.MODEL)
            txtBuild.text =  String.format(getString(R.string.str_build),Build.ID)
            txtSdk.text =  String.format(getString(R.string.str_sdk),Build.VERSION.SDK_INT)
            txtVersionCode.text =  String.format(getString(R.string.str_manufacture),BuildConfig.VERSION_CODE.toString())
        }
    }
}