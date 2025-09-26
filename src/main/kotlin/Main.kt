package nvi.safe

import nvi.safe.PasswordGenerator.generateStrongPassword

fun main() {
    val storage = Storage("passwords.txt")
    storage.addPassword("qwerty123")
    val password = generateStrongPassword()
    println(password)
    println(CheckUtility.checkPasswordStrength(password))

    val key = SecureStorage.generateKey()
    println("Ключ: $key")

    val encrypted = SecureStorage.encrypt(password, key)
    println("Зашифровано: $encrypted")

    val encrypted2 = SecureStorage.encrypt(password, key)
    println("Зашифровано: $encrypted2")

    val decrypted = SecureStorage.decrypt(encrypted, key)
    println("Расшифровано: $decrypted")

    val decrypted2 = SecureStorage.decrypt(encrypted, key)
    println("Расшифровано: $decrypted2")

    println("Совпадает: ${password == decrypted}")
}