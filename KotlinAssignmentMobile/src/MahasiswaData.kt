data class MahasiswaData(
    val id: Int,
    val nama: String,
    val nim: String,
    val nilai: Double?
) {
    val grade: String
        get() = when {
            nilai == null -> "Belum dinilai"
            nilai >= 85.0 -> "A"
            nilai >= 75.0 -> "B"
            nilai >= 65.0 -> "C"
            nilai >= 55.0 -> "D"
            else          -> "E"
        }
}