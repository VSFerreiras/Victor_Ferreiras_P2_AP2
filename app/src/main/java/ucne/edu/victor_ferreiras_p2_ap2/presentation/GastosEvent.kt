package ucne.edu.victor_ferreiras_p2_ap2.presentation

sealed class GastoEvent {
    data object LoadGastos : GastoEvent()
    data object ShowAddGastoDialog : GastoEvent()
    data object HideAddGastoDialog : GastoEvent()
    data object AddGasto : GastoEvent()
    data class UpdateSuplidor(val suplidor: String) : GastoEvent()
    data class UpdateNcf(val ncf: String) : GastoEvent()
    data class UpdateItbis(val itbis: String) : GastoEvent()
    data class UpdateMonto(val monto: String) : GastoEvent()
}