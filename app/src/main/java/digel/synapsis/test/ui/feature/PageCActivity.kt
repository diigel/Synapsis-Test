package digel.synapsis.test.ui.feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import digel.synapsis.test.databinding.ActivityPageCActivityBinding

class PageCActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityPageCActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityPageCActivityBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.run {

        }
    }
}