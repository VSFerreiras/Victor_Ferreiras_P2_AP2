package ucne.edu.victor_ferreiras_p2_ap2.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ucne.edu.victor_ferreiras_p2_ap2.domain.model.Gasto
import ucne.edu.victor_ferreiras_p2_ap2.ui.theme.Victor_Ferreiras_P2_AP2Theme

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GastoScreen(
    viewModel: GastoViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val bottomSheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Hidden,
        skipHiddenState = false
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState
    )

    LaunchedEffect(state.isAddingGasto) {
        if (state.isAddingGasto) {
            bottomSheetState.expand()
        } else {
            bottomSheetState.hide()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onEvent(GastoEvent.LoadGastos)
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            AddGastoBottomSheet(
                state = state,
                onEvent = viewModel::onEvent,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        },
        sheetSwipeEnabled = false,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) { paddingValues ->
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { viewModel.onEvent(GastoEvent.ShowAddGastoDialog) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Agregar gasto"
                    )
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(paddingValues)
            ) {
                Text(
                    text = "Registro de Gastos",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                if (state.isLoading && state.gastos.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator()
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Cargando gastos...")
                        }
                    }
                } else {
                    if (state.gastos.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("No hay gastos registrados")
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Presiona el botón + para agregar uno")
                            }
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp)
                        ) {
                            items(state.gastos) { gasto ->
                                GastoItem(gasto = gasto)
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }

                state.error?.let { error ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Error: $error",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GastoItem(
    gasto: Gasto
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = gasto.suplidor,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(text = "NCF: ${gasto.ncf}")
            Text(text = "Fecha: ${gasto.fecha.take(10)}")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "ITBIS: $${gasto.itbis}")
                Text(text = "Monto: $${gasto.monto}")
            }
        }
    }
}

@Composable
fun AddGastoBottomSheet(
    state: GastoState,
    onEvent: (GastoEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Agregar Gasto",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = state.suplidor,
            onValueChange = { onEvent(GastoEvent.UpdateSuplidor(it)) },
            label = { Text("Suplidor *") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.ncf,
            onValueChange = { onEvent(GastoEvent.UpdateNcf(it)) },
            label = { Text("NCF *") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.itbis,
            onValueChange = { onEvent(GastoEvent.UpdateItbis(it)) },
            label = { Text("ITBIS") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.monto,
            onValueChange = { onEvent(GastoEvent.UpdateMonto(it)) },
            label = { Text("Monto *") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { onEvent(GastoEvent.HideAddGastoDialog) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Cancelar")
            }

            Button(
                onClick = { onEvent(GastoEvent.AddGasto) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Guardar")
            }
        }

        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GastoScreenPreview() {
    Victor_Ferreiras_P2_AP2Theme {
        GastoScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun GastoItemPreview() {
    Victor_Ferreiras_P2_AP2Theme {
        GastoItem(
            gasto = Gasto(
                gastoId = 1,
                fecha = "2024-01-15T10:30:00.000Z",
                suplidor = "Proveedor Ejemplo",
                ncf = "B0100000001",
                itbis = 180.0,
                monto = 1000.0
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddGastoBottomSheetPreview() {
    Victor_Ferreiras_P2_AP2Theme {
        AddGastoBottomSheet(
            state = GastoState(
                suplidor = "Proveedor Test",
                ncf = "B0200000001",
                itbis = "180",
                monto = "1000"
            ),
            onEvent = {}
        )
    }
}