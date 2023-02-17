package digel.synapsis.test.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import digel.synapsis.test.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding : ActivityMainBinding
    private val calendar by lazy { Calendar.getInstance() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.run {
            txtTimeNow.text = calendar.time.toString()
            txtDateNow.text = calendar.get(Calendar.DATE).toString()
        }
    }

}