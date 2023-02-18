package digel.synapsis.test.ui.feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import digel.synapsis.test.databinding.ActivityPageBActivityBinding

class PageBActivity : AppCompatActivity() {

    private lateinit var viewBinding : ActivityPageBActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityPageBActivityBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }
}