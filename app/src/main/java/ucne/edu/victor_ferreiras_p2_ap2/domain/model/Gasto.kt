package ucne.edu.victor_ferreiras_p2_ap2.domain.model

data class Gasto(
    val gastoId: Int = 0,
    val fecha: String,
    val suplidor: String,
    val ncf: String,
    val itbis: Double,
    val monto: Double
)