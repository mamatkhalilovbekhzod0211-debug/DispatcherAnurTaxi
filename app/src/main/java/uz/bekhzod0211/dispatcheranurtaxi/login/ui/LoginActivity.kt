package uz.bekhzod0211.dispatcheranurtaxi.login.ui

import android.content.Intent
import android.os.Bundle
import android.preference.Preference
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import uz.bekhzod0211.dispatcheranurtaxi.data.domain.repository.impl.AuthRepository
import uz.bekhzod0211.dispatcheranurtaxi.login.viewmodel.AuthViewModel
import uz.bekhzod0211.dispatcheranurtaxi.login.viewmodel.LoginUiState
import uz.bekhzod0211.dispatcheranurtaxi.main.MainActivity
import uz.bekhzod0211.dispatcheranurtaxi.ui.theme.DispatcherAnurTaxiTheme
import uz.devmi.usale.core.cache.PreferencesManager
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class LoginActivity: ComponentActivity(){
    @Inject
     lateinit var prefs: PreferencesManager
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (prefs.token.isNotEmpty()){
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        enableEdgeToEdge()
        setContent {
            DispatcherAnurTaxiTheme {
                LoginScreen()
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigation.collect { event ->
                    when (event) {
                        AuthViewModel.AuthNavEvent.GoToMainActivity -> {
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoginScreen(
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state by viewModel.loginState.collectAsState()

    val randNumber = Random.nextInt(9999999)+1000000

    var phone by remember { mutableStateOf("+99890$randNumber") }
    var password by remember { mutableStateOf("admin123456") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Telefon") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Parol") },
//            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.login(phone, password) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Kirish")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (state) {
            is LoginUiState.Loading -> Text("Yuklanmoqda...")
            is LoginUiState.Success -> Text("${(state as LoginUiState.Success).data.user.fullName}, Xush kelibsiz")
            is LoginUiState.Error -> Text("Xato: ${(state as LoginUiState.Error).message}")
            else -> {}
        }
    }
}