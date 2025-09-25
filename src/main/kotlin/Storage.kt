package nvi.safe

import java.io.File

class Storage(path: String) {
    val file: File

    init {
        file = File(path)
    }

    /***
     * Добавляет пароль в .txt файл
     *
     * @param password пароль
     * @return строка об успехе/неуспехе
     */
    fun addPassword(password: String): String {
        file.appendText(password + "\n")
        return "Password added!"
    }

    /***
     * Удаляет пароль из .txt файла
     *
     * @param password пароль для удаления
     * @return строка об успехе/неуспехе
     */
    fun removePassword(password: String): String {
        val passwords = file.readLines().toMutableList()
        if (passwords.remove(password)) {
            file.writeText(passwords.joinToString("\n") + "\n")
            return "Password removed"
        } else {
            return "Password not found"
        }
    }
}