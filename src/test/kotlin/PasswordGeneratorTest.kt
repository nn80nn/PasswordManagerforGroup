package nvi.test

import nvi.safe.PasswordGenerator
import org.junit.jupiter.api.*
import kotlin.test.assertTrue

class PasswordGeneratorTest {

    @Test
    fun testGenerateStrongPassword() {
        val password = PasswordGenerator.generateStrongPassword()

        // Проверяем длину пароля (должна быть от 8 до 16 символов)
        assertTrue(password.length in 8..16, "Password length should be between 8 and 16 characters")

        // Проверяем, что пароль содержит символы из разных наборов
        val lowercase = "abcdefghijklmnopqrstuvwxyz"
        val uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val numbers = "0123456789"
        val symbols = "!@#$%^&*()_+-=[]{}|;:,.<>?"
        val allChars = lowercase + uppercase + numbers + symbols

        // Проверяем, что все символы пароля допустимы
        assertTrue(password.all { it in allChars }, "All password characters should be from allowed charset")

        // Проверяем, что пароль не пустой
        assertTrue(password.isNotEmpty(), "Password should not be empty")
    }
}