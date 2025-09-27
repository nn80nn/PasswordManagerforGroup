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

    // Илья: Функция 1
    /**
     * Проверяет пароль на повторение в хранилище и возвращает понятное сообщение
     *
     * @param password пароль для проверки
     * @return сообщение о результате проверки
     */
    fun checkPasswordUniqueness(password: String): String {
        if (!file.exists()) {
            return "Это уникальный пароль! (файл хранилища еще не создан)"
        }

        val passwords = file.readLines()
        return if (passwords.contains(password)) {
            "Внимание! Такой пароль уже существует в хранилище!"
        } else {
            "Это уникальный пароль!"
        }
    }
}