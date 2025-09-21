package nvi.safe

fun main() {
    val storage = Storage("passwords.txt")
    storage.addPassword("qwerty123")
    val password = "Qwerty!@#a4"
    println(CheckUtility.checkPasswordStrength(password))
}