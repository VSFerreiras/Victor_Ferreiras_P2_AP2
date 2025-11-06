package ucne.edu.victor_ferreiras_p2_ap2.data.remote.dto

data class GastoDto(
    val gastoId: Int? = null,
    val ncf: String,
    val fecha: String,
    val suplidor: String,
    val monto: Float,
    val itbis: Float,
)