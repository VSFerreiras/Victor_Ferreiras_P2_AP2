package ucne.edu.victor_ferreiras_p2_ap2.presentation

import ucne.edu.victor_ferreiras_p2_ap2.domain.model.Gasto

data class GastoState(
    val gastos: List<Gasto> = emptyList(),
    val suplidor: String = "",
    val ncf: String = "",
    val itbis: String = "",
    val monto: String = "",
    val isAddingGasto: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)