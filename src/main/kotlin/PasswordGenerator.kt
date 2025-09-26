package nvi.safe

import java.security.SecureRandom

object PasswordGenerator {
    fun generateStrongPassword() : String {
        val lowercase = "abcdefghijklmnopqrstuvwxyz"
        val uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val numbers = "0123456789"
        val symbols = "!@#$%^&*()_+-=[]{}|;:,.<>?"
        val charset = lowercase + uppercase + numbers + symbols
        val randomLength = { kotlin.random.Random.nextInt(8, 17) }
        val secureRandom = SecureRandom()
        return (1..randomLength())
            .map { charset[secureRandom.nextInt(charset.length)] }
            .joinToString("")
    }
}