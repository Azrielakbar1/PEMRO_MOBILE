
fun main() {
    val dataMahasiswa: ArrayList<Mahasiswa> = ArrayList()
    var idCounter = 1
    val menuList: List<String> = listOf(
        "1. Tambah Data",
        "2. List Data",
        "3. Edit Data",
        "4. Hapus Data",
        "5. Show Data (Key-Value)",
        "0. Keluar"
    )

    var running = true

    println("Sistem Manajemen Data Mahasiswa")

    while (running) {
        println("\n MENU ")
        menuList.forEach { println("│  $it") }
        println("")
        print("Pilih menu: ")

        when (readLine()?.trim()) {
            "1" -> { idCounter = tambahData(dataMahasiswa, idCounter) }
            "2" -> listData(dataMahasiswa)
            "3" -> editData(dataMahasiswa)
            "4" -> hapusData(dataMahasiswa)
            "5" -> showData(dataMahasiswa)
            "0" -> {
                println("\nTerima kasih! Program selesai.")
                running = false
            }
            else -> println("Pilihan tidak valid! Masukkan angka 0-5.")
        }
    }
}

fun tambahData(list: ArrayList<Mahasiswa>, idCounter: Int): Int {
    println("\n── TAMBAH DATA MAHASISWA ──")

    print("Nama  : ")
    val nama = readLine()?.trim() ?: ""

    print("NIM   : ")
    val nim = readLine()?.trim() ?: ""

    print("Nilai (kosongkan jika belum ada): ")
    val inputNilai = readLine()?.trim()

    val nilai: Double? = if (inputNilai.isNullOrBlank()) null
    else inputNilai.toDoubleOrNull()

    if (nama.isBlank() || nim.isBlank()) {
        println("NAMA DAN NIM TIDAK BOLEH KOSONG!")
        return idCounter
    }

    if (inputNilai != null && inputNilai.isNotBlank() && nilai == null) {
        println("Format nilai tidak valid! GUNAKAN ANGKA MAS/MBA !!")
        return idCounter
    }

    // Membuat object Mahasiswa
    val mahasiswa = Mahasiswa(idCounter, nama, nim, nilai)
    list.add(mahasiswa)

    val snapshot: MahasiswaData = mahasiswa.toDataClass()
    println("\nData berhasil ditambahkan!")
    println("   Mutable  Class → $mahasiswa")
    println("   Immutable Data → $snapshot")
    println("   Grade           → ${snapshot.grade}")

    return idCounter + 1
}

fun listData(list: ArrayList<Mahasiswa>) {
    println("\n── DAFTAR SEMUA MAHASISWA ──")

    if (list.isEmpty()) {
        println("Belum ada data mahasiswa.")
        return
    }

    println("%-5s %-20s %-15s %-10s %-5s".format("ID", "Nama", "NIM", "Nilai", "Grade"))
    println("─".repeat(60))

    list.forEach { mhs ->
        val snapshot = mhs.toDataClass()    // Pakai data class untuk display
        val nilaiStr = snapshot.nilai?.toString() ?: "N/A"
        println(
            "%-5d %-20s %-15s %-10s %-5s".format(
                snapshot.id, snapshot.nama, snapshot.nim, nilaiStr, snapshot.grade
            )
        )
    }

    println("─".repeat(60))
    println("Total: ${list.size} mahasiswa")
}

fun editData(list: ArrayList<Mahasiswa>) {
    println("\n── EDIT DATA MAHASISWA ──")

    if (list.isEmpty()) {
        println("Belum ada data mahasiswa.")
        return
    }

    listData(list)
    print("\nMasukkan ID mahasiswa yang ingin diedit: ")
    val id = readLine()?.toIntOrNull()

    val mahasiswa: Mahasiswa? = list.find { it.id == id }

    if (mahasiswa == null) {
        println("Mahasiswa dengan ID $id tidak ditemukan.")
        return
    }

    println("\nData saat ini: $mahasiswa")
    println("(Tekan Enter untuk melewati Pengisian yang tidak ingin diubah)")

    print("Nama baru  [${mahasiswa.nama}]: ")
    val namaBaru = readLine()?.trim()
    if (!namaBaru.isNullOrBlank()) mahasiswa.nama = namaBaru

    print("NIM baru   [${mahasiswa.nim}]: ")
    val nimBaru = readLine()?.trim()
    if (!nimBaru.isNullOrBlank()) mahasiswa.nim = nimBaru

    print("Nilai baru [${mahasiswa.nilai ?: "Belum ada"}]: ")
    val inputNilai = readLine()?.trim()
    if (!inputNilai.isNullOrBlank()) {
        val nilaiDouble = inputNilai.toDoubleOrNull()
        if (nilaiDouble != null) mahasiswa.nilai = nilaiDouble
        else println("⚠️  Format nilai tidak valid, nilai tidak diubah.")
    }

    println("\nData berhasil diperbarui!")
    println("   Data terbaru: $mahasiswa")
}

fun hapusData(list: ArrayList<Mahasiswa>) {
    println("\n── HAPUS DATA MAHASISWA ──")

    if (list.isEmpty()) {
        println("Belum ada data mahasiswa.")
        return
    }

    listData(list)
    print("\nMasukkan ID mahasiswa yang ingin dihapus: ")
    val id = readLine()?.toIntOrNull()

    val mahasiswa: Mahasiswa? = list.find { it.id == id }

    if (mahasiswa == null) {
        println("Mahasiswa dengan ID $id tidak ditemukan.")
        return
    }

    print("Yakin ingin menghapus '${mahasiswa.nama}'? (y/n): ")
    val konfirmasi = readLine()?.trim()?.lowercase()

    if (konfirmasi == "y") {
        list.remove(mahasiswa)
        println("Data '${mahasiswa.nama}' berhasil dihapus!")
    } else {
        println("Penghapusan dibatalkan.")
    }
}

fun showData(list: ArrayList<Mahasiswa>) {
    println("\n── SHOW DATA (KEY-VALUE FORMAT) ──")

    if (list.isEmpty()) {
        println("Belum ada data mahasiswa.")
        return
    }

    print("Masukkan ID mahasiswa (kosongkan untuk tampilkan semua): ")
    val inputId = readLine()?.trim()

    val targetList: List<Mahasiswa> = if (inputId.isNullOrBlank()) {
        list   // Tampilkan semua
    } else {
        val id = inputId.toIntOrNull()
        list.filter { it.id == id }
    }

    if (targetList.isEmpty()) {
        println("Data tidak ditemukan.")
        return
    }

    targetList.forEach { mhs ->
        println("\nMahasiswa #${mhs.id}")
        mhs.toMap().forEach { (key, value) ->
            println("│  %-8s : %s".format(key, value))
        }
        // Tambahan dari data class
        println("%-8s : %s".format("grade", mhs.toDataClass().grade))
        println("")
    }
}