package nvi.safe

import nvi.safe.PasswordGenerator.generateStrongPassword

fun main() {
    val storage = Storage("passwords.txt")
    storage.addPassword("qwerty123")
    val password = generateStrongPassword()
    println(password)
    println(CheckUtility.checkPasswordStrength(password))
}