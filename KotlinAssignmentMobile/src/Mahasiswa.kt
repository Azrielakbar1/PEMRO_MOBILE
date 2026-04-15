class Mahasiswa(
    id: Int,
    nama: String,
    nim: String,
    nilai: Double? = null
) {
    var id: Int = id
        get() = field
        set(value) {
            if (value > 0) field = value
            else println("⚠ID harus lebih dari 0")
        }

    var nama: String = nama
        get() = field.trim()
        set(value) {
            if (value.isNotBlank()) field = value
            else println("Nama tidak boleh kosong")
        }

    var nim: String = nim
        get() = field.uppercase()
        set(value) {
            if (value.isNotBlank()) field = value
            else println("NIM tidak boleh kosong")
        }

    var nilai: Double? = nilai
        get() = field
        set(value) {
            if (value == null || value in 0.0..100.0) field = value
            else println("Nilai harus antara 0-100")
        }

    fun toDataClass(): MahasiswaData = MahasiswaData(id, nama, nim, nilai)

    fun toMap(): Map<String, Any?> = mapOf(
        "id"    to id,
        "nama"  to nama,
        "nim"   to nim,
        "nilai" to (nilai ?: "Belum dinilai")
    )

    override fun toString(): String =
        "Mahasiswa(id=$id, nama=$nama, nim=$nim, nilai=${nilai ?: "Belum dinilai"})"
}