package digel.synapsis.test.data.local.entity.request

import androidx.annotation.Keep

@Keep
data class AuthRequest(
    val username : String? = "",
    val password : String? = ""
)
