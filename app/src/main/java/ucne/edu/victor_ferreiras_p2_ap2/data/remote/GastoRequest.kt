package ucne.edu.victor_ferreiras_p2_ap2.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class GastoRequest(
    val fecha: String,
    val suplidor: String,
    val ncf: String,
    val itbis: Double,
    val monto: Double
)