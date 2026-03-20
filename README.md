# 📱 My Profile App — MVVM Premium Edition

<p align="center">
  <img width="200" height="1600" alt="Image" src="https://github.com/user-attachments/assets/ba739d17-abd9-4ec9-82f7-8c4d52ca8479" text=Light+Mode+View" />
  &nbsp;&nbsp;
  <img width="200" height="1600" alt="Image" src="https://github.com/user-attachments/assets/94cc1b60-3b31-45c1-b818-f52e728a9202" text=Dark+Mode+View" />
  &nbsp;&nbsp;
  <img width="200" height="1600" alt="Image" src="https://github.com/user-attachments/assets/717361cb-429d-423c-85e1-13410b823116" text=Edit+Form+View+light" />
  <img width="200" height="1600" alt="Image" src="https://github.com/user-attachments/assets/096bd5bc-31ab-4de0-b85e-a42be4481b66" text=Edit+Form+View+dark" />
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white"/>
  <img src="https://img.shields.io/badge/Compose_Multiplatform-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white"/>
  <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white"/>
  <img src="https://img.shields.io/badge/MVVM-FF6B35?style=for-the-badge&logo=databricks&logoColor=white"/>
</p>

---

## 👨‍💻 Identitas Mahasiswa

| Nama | NIM | Prodi |
|------|-----|-------|
| Muhammad Fajri Firdaus | 123140050 | Teknik Informatika |

📌 **Tugas 4 — Pengembangan Aplikasi Mobile (PAM)**
Institut Teknologi Sumatera · Semester Genap 2025/2026

---

## 📖 Tentang Aplikasi

Aplikasi profil ini merupakan implementasi tingkat lanjut yang menerapkan **State Management** dan **arsitektur MVVM** (Model-View-ViewModel). Seluruh UI bersifat reaktif dan sinkron dengan **Single Source of Truth** di dalam ViewModel.

---

## ✨ Fitur Utama

| Fitur | Keterangan |
|-------|------------|
| 🌊 Azure Header | Banner gradient dengan foto profil circular & verified badge |
| 🆔 Dynamic Identity | NIM dan Program Studi bersifat reaktif dan dapat diedit sepenuhnya |
| 📞 Info Kontak | Informasi Email, WhatsApp, dan Lokasi yang tersinkronisasi |
| ✏️ Premium Edit | Form edit modern dengan State Hoisting untuk seluruh field profil |
| 🌙 Active Dark Mode | Toggle tema cerdas dengan optimasi kontras font agar tetap menyala |
| ✅ Success Pill | Notifikasi animasi setelah berhasil melakukan perubahan data |

---

## Cara Penggunaan

**1. Tampilan Identitas Utama**  
Saat aplikasi dibuka, Anda akan disambut dengan Header Gradient Azure Blue yang mewah.  
Layar menampilkan Floating Profile Card yang berisi Foto Profil dengan Verified Badge, Nama, serta NIM & Program Studi yang interaktif.  

**2. Navigasi Informasi Kontak**  
Tekan tombol biru "Detail Kontak" untuk memunculkan informasi tambahan.  
Card kontak akan muncul dengan animasi Expand Vertical yang halus.  
Informasi yang ditampilkan meliputi: Email, WhatsApp, dan Alamat.  
Tekan kembali tombol untuk menyembunyikan kontak dengan animasi Shrink.  

**3. Melakukan Pembaruan Profil (Edit Mode)**  
Tekan tombol "Edit Profil" (Outlined Button) di bagian tengah layar.  
Form penyuntingan akan muncul secara dinamis. Anda dapat mengubah:  
- Data Identitas: Nama, NIM, dan Program Studi.  
- Bio: Deskripsi singkat diri Anda.  
- Data Kontak: Email, nomor WhatsApp, dan lokasi.  
Setelah selesai, tekan tombol "Simpan Perubahan".  
Success Pill Notification berwarna biru akan muncul dari bawah layar sebagai konfirmasi bahwa data telah tersimpan di ViewModel.  

**4. Personalisasi Tema (Dark Mode)**  
Gunakan Switch Intuitif di pojok kanan atas layar yang dilengkapi ikon Matahari/Bulan.  
Aplikasi akan beralih antara Light Mode (Bersih & Terang) dan Dark Mode (Deep Slate & Azure Glow).  
Pada Dark Mode, seluruh tipografi otomatis berubah menjadi Putih Terang untuk memastikan teks tetap "hidup" dan mudah dibaca.  

---

## 📐 Konsep yang Diterapkan

### State Management
```kotlin
// remember + mutableStateOf untuk state yang survive recomposition
var uiState by mutableStateOf(ProfileUiState())
    private set
```

### State Hoisting — LabeledTextField
```kotlin
// Stateless: value turun dari ViewModel, event naik ke ViewModel
@Composable
fun LabeledTextField(
    value         : String,              // ← state turun ↓
    onValueChange : (String) -> Unit,   // ← event naik ↑
    ...
)
```

### Immutable Update dengan .copy()
```kotlin
// Setiap update state menggunakan .copy() — tidak mutate langsung
uiState = uiState.copy(
    profile = uiState.profile.copy(name = editName.trim())
)
```

---

## 🛠️ Yang Digunakan

* **State** — `mutableStateOf` `remember` `ProfileUiState`
* **ViewModel** — `ProfileViewModel` dengan state hoisting callbacks
* **Layout** — `Column` `Row` `Box` `Scaffold` `TopAppBar`
* **Komponen** — `Text` `Button` `Card` `Switch` `OutlinedTextField`
* **Modifier** — `padding` `clip` `background` `border` `fillMaxWidth` `scale`
* **Animasi** — `AnimatedVisibility` `AnimatedContent` `expandVertically` `fadeIn` `fadeOut`

---

## ▶️ Cara Menjalankan

```bash
git clone https://github.com/fajrifirdaus/PAM-M4.git
```

Buka di Android Studio → Sync Gradle → Run ▶️

---
