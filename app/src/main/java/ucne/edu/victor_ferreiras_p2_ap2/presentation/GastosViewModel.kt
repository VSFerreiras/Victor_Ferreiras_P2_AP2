package ucne.edu.victor_ferreiras_p2_ap2.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ucne.edu.victor_ferreiras_p2_ap2.data.remote.GastoResponse
import ucne.edu.victor_ferreiras_p2_ap2.domain.model.Gasto
import ucne.edu.victor_ferreiras_p2_ap2.domain.CreateGasto
import ucne.edu.victor_ferreiras_p2_ap2.domain.GetAllGastos
import ucne.edu.victor_ferreiras_p2_ap2.domain.ValidateGasto
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class GastoViewModel @Inject constructor(
    private val getAllGastos: GetAllGastos,
    private val createGasto: CreateGasto,
    private val validateGasto: ValidateGasto
) : ViewModel() {

    private val _state = MutableStateFlow(GastoState())
    val state = _state.asStateFlow()

    init {
        loadGastos()
    }

    fun onEvent(event: GastoEvent) {
        when (event) {
            GastoEvent.LoadGastos -> loadGastos()
            GastoEvent.ShowAddGastoDialog -> showAddGastoDialog()
            GastoEvent.HideAddGastoDialog -> hideAddGastoDialog()
            GastoEvent.AddGasto -> addGasto()
            is GastoEvent.UpdateSuplidor -> updateSuplidor(event.suplidor)
            is GastoEvent.UpdateNcf -> updateNcf(event.ncf)
            is GastoEvent.UpdateItbis -> updateItbis(event.itbis)
            is GastoEvent.UpdateMonto -> updateMonto(event.monto)
        }
    }

    private fun loadGastos() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                println("=== CARGANDO GASTOS DESDE API ===")
                val gastos: List<Gasto> = getAllGastos()
                println("=== GASTOS CARGADOS: ${gastos.size} ===")
                _state.update { it.copy(gastos = gastos, isLoading = false) }
            } catch (e: Exception) {
                println("=== ERROR AL CARGAR GASTOS: ${e.message} ===")
                e.printStackTrace()
                _state.update { it.copy(error = "Error: ${e.message}", isLoading = false) }
            }
        }
    }

    private fun showAddGastoDialog() {
        println("=== MOSTRANDO DIALOG ===")
        _state.update { it.copy(isAddingGasto = true, error = null) }
    }

    private fun hideAddGastoDialog() {
        println("=== OCULTANDO DIALOG ===")
        _state.update {
            it.copy(
                isAddingGasto = false,
                suplidor = "",
                ncf = "",
                itbis = "",
                monto = "",
                error = null
            )
        }
    }

    private fun addGasto() {
        println("=== AGREGANDO GASTO ===")
        val currentState = _state.value

        val fecha = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).format(Date())

        val itbis = currentState.itbis.toDoubleOrNull() ?: 0.0
        val monto = currentState.monto.toDoubleOrNull() ?: 0.0

        val gasto = Gasto(
            fecha = fecha,
            suplidor = currentState.suplidor.trim(),
            ncf = currentState.ncf.trim(),
            itbis = itbis,
            monto = monto
        )

        println("=== GASTO CREADO: $gasto ===")

        val validationResult = validateGasto(gasto)
        if (!validationResult.successful) {
            _state.update { it.copy(error = validationResult.errorMessage) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                println("=== ENVIANDO GASTO A API ===")
                val response = createGasto(gasto)
                println("=== GASTO CREADO EN API: $response ===")
                loadGastos()
                hideAddGastoDialog()
            } catch (e: Exception) {
                println("=== ERROR AL CREAR GASTO: ${e.message} ===")
                e.printStackTrace()
                _state.update { it.copy(error = "Error: ${e.message}", isLoading = false) }
            }
        }
    }

    private fun updateSuplidor(suplidor: String) {
        _state.update { it.copy(suplidor = suplidor) }
    }

    private fun updateNcf(ncf: String) {
        _state.update { it.copy(ncf = ncf) }
    }

    private fun updateItbis(itbis: String) {
        _state.update { it.copy(itbis = itbis) }
    }

    private fun updateMonto(monto: String) {
        _state.update { it.copy(monto = monto) }
    }
}