package ucne.edu.victor_ferreiras_p2_ap2.domain

import ucne.edu.victor_ferreiras_p2_ap2.domain.model.Gasto
import javax.inject.Inject


class ValidateGasto @Inject constructor() {
    operator fun invoke(gasto: Gasto): ValidationResult {
        if (gasto.suplidor.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "El suplidor es requerido"
            )
        }

        if (gasto.ncf.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "El NCF es requerido"
            )
        }

        if (gasto.itbis < 0) {
            return ValidationResult(
                successful = false,
                errorMessage = "El ITBIS no puede ser negativo"
            )
        }

        if (gasto.monto <= 0) {
            return ValidationResult(
                successful = false,
                errorMessage = "El monto debe ser mayor a 0"
            )
        }

        return ValidationResult(successful = true)
    }
}

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)