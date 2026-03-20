package org.example.project

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.jetbrains.compose.resources.painterResource
import pamtugas4.composeapp.generated.resources.Res
import pamtugas4.composeapp.generated.resources.foto_profil

// ═══════════════════════════════════════════════
// 1. DATA LAYER — UI State
// ═══════════════════════════════════════════════
data class ProfileUiState(
    val name: String = "Muhammad Fajri Firdaus",
    val nim: String = "123140050",
    val prodi: String = "Informatika", // Prodi sekarang menjadi State
    val bio: String = "Informatics Engineering Student at ITERA. Passionate about Mobile Development and AI Integration.",
    val email: String = "muhammad.123140050@student.itera.ac.id",
    val phone: String = "+62 899-3368-339",
    val location: String = "Palembang, Indonesia",
    val isDarkMode: Boolean = false,
    val isEditMode: Boolean = false,
    val showContact: Boolean = false,
    val showSuccess: Boolean = false
)

// ═══════════════════════════════════════════════
// 2. VIEWMODEL — Business Logic
// ═══════════════════════════════════════════════
class ProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun toggleDarkMode() { _uiState.update { it.copy(isDarkMode = !it.isDarkMode) } }
    fun toggleEditMode() { _uiState.update { it.copy(isEditMode = !it.isEditMode) } }
    fun toggleContact() { _uiState.update { it.copy(showContact = !it.showContact) } }
    fun dismissNotif() { _uiState.update { it.copy(showSuccess = false) } }

    fun saveChanges(n: String, nim: String, p: String, b: String, e: String, ph: String, l: String) {
        _uiState.update {
            it.copy(
                name = n, nim = nim, prodi = p, bio = b,
                email = e, phone = ph, location = l,
                isEditMode = false, showContact = true, showSuccess = true
            )
        }
    }
}

// ═══════════════════════════════════════════════
// 3. UI DESIGN (Azure Premium Edition)
// ═══════════════════════════════════════════════
private val AzureBlue = Color(0xFF0061FF)
private val AzureDark = Color(0xFF003692)
private val AzureLight = Color(0xFF60A5FA)

@Composable
fun App(viewModel: ProfileViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // State Hoisting untuk form input teks
    var editName by remember(uiState.name) { mutableStateOf(uiState.name) }
    var editNim by remember(uiState.nim) { mutableStateOf(uiState.nim) }
    var editProdi by remember(uiState.prodi) { mutableStateOf(uiState.prodi) } // Hoisting Prodi
    var editBio by remember(uiState.bio) { mutableStateOf(uiState.bio) }
    var editEmail by remember(uiState.email) { mutableStateOf(uiState.email) }
    var editPhone by remember(uiState.phone) { mutableStateOf(uiState.phone) }
    var editLoc by remember(uiState.location) { mutableStateOf(uiState.location) }

    LaunchedEffect(uiState.showSuccess) {
        if (uiState.showSuccess) { delay(2500); viewModel.dismissNotif() }
    }

    val bgColor = if (uiState.isDarkMode) Color(0xFF0F172A) else Color(0xFFF8FAFC)
    val cardColor = if (uiState.isDarkMode) Color(0xFF1E293B) else Color.White
    val mainTextColor = if (uiState.isDarkMode) Color.White else Color(0xFF0F172A)
    val subTextColor = if (uiState.isDarkMode) Color(0xFF94A3B8) else Color(0xFF64748B)
    val accentColor = if (uiState.isDarkMode) AzureLight else AzureBlue

    MaterialTheme(
        colorScheme = if (uiState.isDarkMode) darkColorScheme(primary = AzureLight)
        else lightColorScheme(primary = AzureBlue)
    ) {
        Box(modifier = Modifier.fillMaxSize().background(bgColor)) {
            Box(modifier = Modifier.fillMaxWidth().height(220.dp).background(
                Brush.verticalGradient(listOf(AzureDark, AzureBlue))
            ))

            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Custom Theme Toggle
                Row(
                    modifier = Modifier.fillMaxWidth().padding(20.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (uiState.isDarkMode) "DARK MODE" else "LIGHT MODE",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    FilledIconButton(
                        onClick = { viewModel.toggleDarkMode() },
                        colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color.White.copy(alpha = 0.25f)),
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = if (uiState.isDarkMode) Icons.Rounded.WbSunny else Icons.Rounded.NightsStay,
                            contentDescription = null,
                            tint = if (uiState.isDarkMode) Color(0xFFFFD600) else Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                // Profile Identity Card
                Card(
                    modifier = Modifier.padding(horizontal = 24.dp).fillMaxWidth(),
                    shape = RoundedCornerShape(32.dp),
                    colors = CardDefaults.cardColors(containerColor = cardColor),
                    elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            shape = CircleShape,
                            border = BorderStroke(4.dp, accentColor.copy(alpha = 0.4f)),
                            modifier = Modifier.size(115.dp),
                            color = Color.Transparent
                        ) {
                            Image(
                                painter = painterResource(Res.drawable.foto_profil),
                                contentDescription = null,
                                modifier = Modifier.clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Spacer(Modifier.height(16.dp))
                        Text(uiState.name, fontSize = 24.sp, fontWeight = FontWeight.Black, color = mainTextColor)
                        // Teks Biru NIM & Prodi Dinamis
                        Text("NIM ${uiState.nim} • ${uiState.prodi}", fontSize = 13.sp, color = accentColor, fontWeight = FontWeight.ExtraBold)
                        Spacer(Modifier.height(16.dp))
                        Text(uiState.bio, textAlign = androidx.compose.ui.text.style.TextAlign.Center, fontSize = 14.sp, lineHeight = 22.sp, color = subTextColor)
                    }
                }

                Spacer(Modifier.height(24.dp))

                // Actions
                Row(modifier = Modifier.padding(horizontal = 24.dp).fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(onClick = { viewModel.toggleContact() }, modifier = Modifier.weight(1f).height(54.dp), shape = RoundedCornerShape(16.dp)) {
                        Icon(Icons.Rounded.ContactPage, null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp)); Text("Kontak", fontWeight = FontWeight.Bold)
                    }
                    OutlinedButton(onClick = { viewModel.toggleEditMode() }, modifier = Modifier.weight(1f).height(54.dp), shape = RoundedCornerShape(16.dp), border = BorderStroke(1.5.dp, accentColor)) {
                        Icon(Icons.Rounded.EditNote, null, modifier = Modifier.size(20.dp), tint = accentColor)
                        Spacer(Modifier.width(8.dp)); Text("Edit", color = accentColor, fontWeight = FontWeight.Bold)
                    }
                }

                // Animated Sections
                AnimatedVisibility(visible = uiState.showContact && !uiState.isEditMode) {
                    ContactGrid(uiState, cardColor, accentColor, mainTextColor)
                }

                AnimatedVisibility(visible = uiState.isEditMode) {
                    EditSection(
                        editName, { editName = it },
                        editNim, { editNim = it },
                        editProdi, { editProdi = it }, // Edit Prodi
                        editBio, { editBio = it },
                        editEmail, { editEmail = it }, editPhone, { editPhone = it },
                        editLoc, { editLoc = it },
                        { viewModel.saveChanges(editName, editNim, editProdi, editBio, editEmail, editPhone, editLoc) },
                        cardColor, accentColor, mainTextColor
                    )
                }

                // Copyright Tag
                Spacer(Modifier.height(48.dp))
                HorizontalDivider(modifier = Modifier.padding(horizontal = 60.dp), thickness = 0.5.dp, color = subTextColor.copy(alpha = 0.3f))
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "© 2026 Tugas PAM 4 — Fajri Firdaus • 050",
                    fontSize = 11.sp,
                    color = subTextColor.copy(alpha = 0.6f),
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
            }

            // Notification Pill
            AnimatedVisibility(visible = uiState.showSuccess, modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 40.dp)) {
                Surface(color = Color(0xFF0EA5E9), shape = RoundedCornerShape(50.dp), shadowElevation = 10.dp) {
                    Row(Modifier.padding(horizontal = 20.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Rounded.CheckCircle, null, tint = Color.White, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(10.dp)); Text("Profil Berhasil Diperbarui", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun ContactGrid(state: ProfileUiState, cardColor: Color, accent: Color, textColor: Color) {
    Card(
        modifier = Modifier.padding(24.dp).fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor.copy(alpha = 0.6f))
    ) {
        Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("Detail Kontak", fontWeight = FontWeight.Bold, color = textColor, fontSize = 16.sp)
            ContactInfoRow(Icons.Rounded.Mail, "Email", state.email, accent, textColor)
            ContactInfoRow(Icons.Rounded.Call, "WhatsApp", state.phone, accent, textColor)
            ContactInfoRow(Icons.Rounded.Map, "Alamat", state.location, accent, textColor)
        }
    }
}

@Composable
fun ContactInfoRow(icon: ImageVector, label: String, value: String, accent: Color, textColor: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(shape = CircleShape, color = accent.copy(alpha = 0.15f), modifier = Modifier.size(40.dp)) {
            Box(contentAlignment = Alignment.Center) { Icon(icon, null, tint = accent, modifier = Modifier.size(20.dp)) }
        }
        Spacer(Modifier.width(16.dp))
        Column {
            Text(label, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = accent)
            Text(value, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = textColor)
        }
    }
}

@Composable
fun EditSection(
    n: String, onN: (String) -> Unit,
    nim: String, onNim: (String) -> Unit,
    prodi: String, onProdi: (String) -> Unit, // Parameter Edit Prodi
    b: String, onB: (String) -> Unit,
    e: String, onE: (String) -> Unit,
    p: String, onP: (String) -> Unit,
    l: String, onL: (String) -> Unit,
    onSave: () -> Unit,
    cardColor: Color, accent: Color, textColor: Color
) {
    Card(
        modifier = Modifier.padding(24.dp).fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("Update Profil", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = textColor)
            OutlinedTextField(value = n, onValueChange = onN, label = { Text("Nama") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
            OutlinedTextField(value = nim, onValueChange = onNim, label = { Text("NIM") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
            OutlinedTextField(value = prodi, onValueChange = onProdi, label = { Text("Program Studi") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
            OutlinedTextField(value = b, onValueChange = onB, label = { Text("Bio") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), minLines = 2)
            OutlinedTextField(value = e, onValueChange = onE, label = { Text("Email") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
            OutlinedTextField(value = p, onValueChange = onP, label = { Text("WhatsApp") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
            OutlinedTextField(value = l, onValueChange = onL, label = { Text("Lokasi") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))

            Button(onClick = onSave, modifier = Modifier.fillMaxWidth().height(50.dp), shape = RoundedCornerShape(12.dp), colors = ButtonDefaults.buttonColors(containerColor = accent)) {
                Text("Simpan Perubahan", fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}