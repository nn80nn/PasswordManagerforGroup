package nvi.safe

import java.io.File

class Storage(path: String) {
    val file: File

    init {
        file = File(path)
    }

    fun addPassword(password: String) {
        file.appendText(password + "\n")
    }

    fun removePassword(password: String): String {
        val passwords = file.readLines().toMutableList()
        if (passwords.remove(password)) {
            file.writeText(passwords.joinToString("\n") + "\n")
            return "Password wrote"
        } else {
            return "Password not found"
        }
    }
}