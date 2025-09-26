package nvi.safe

import PasswordEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File


class EnhancedStorage(path: String) {
    private val file: File
    private val gson = Gson()

    init {
        file = File(path)
        if (!file.exists()) {
            file.createNewFile()
            file.writeText("[]")
        }
    }

    private fun readPasswords(): MutableList<PasswordEntity> {
        return try {
            val content = file.readText()
            if (content.trim().isEmpty()) {
                mutableListOf()
            } else {
                val type = object : TypeToken<MutableList<PasswordEntity>>() {}.type
                gson.fromJson(content, type) ?: mutableListOf()
            }
        } catch (e: Exception) {
            mutableListOf()
        }
    }

    private fun writePasswords(passwords: List<PasswordEntity>) {
        val json = gson.toJson(passwords)
        file.writeText(json)
    }

    fun addPassword(password: PasswordEntity): String {
        val passwords = readPasswords()
        passwords.add(password)
        writePasswords(passwords)
        return "Password added!"
    }

    fun removePassword(urlName: String): String {
        val passwords = readPasswords()
        val removed = passwords.removeIf { it.UrlName == urlName }
        return if (removed) {
            writePasswords(passwords)
            "Password removed"
        } else {
            "Password not found"
        }
    }

    fun getAllPasswords(): List<PasswordEntity> {
        return readPasswords()
    }

    fun findPassword(urlName: String): PasswordEntity? {
        return readPasswords().find { it.UrlName == urlName }
    }
}