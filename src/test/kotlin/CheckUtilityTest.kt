package nvi.test

import nvi.safe.CheckUtility
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

class CheckUtilityTest {
    @Test
    fun testCheckEntropy() {
        val password = "Qwerty123!@"
        val entropy = CheckUtility.checkEntropy(password)
        assertEquals(67.19376902455987, entropy)
        assertEquals(0.0, CheckUtility.checkEntropy(""))
    }

    @Test
    fun testCheckAverageBruteforceTime() {
        val password = "Qwerty123!@"
        val entropy = CheckUtility.checkEntropy(password)
        val bruteforceTime = CheckUtility.checkAverageBruteforceTime(password, 1e6.toFloat(), entropy)
        assertEquals(8.439369509258984e13, bruteforceTime)
    }

    @Test
    fun testCheckPasswordStrength() {
        val password1 = "qwerty123!"
        assertEquals("Password is bad!", CheckUtility.checkPasswordStrength(password1))
        val password2 = "Qwerty123!@"
        assertEquals("Password is strong!", CheckUtility.checkPasswordStrength(password2))

    }

    @Test // Илья. Тест к функции 2
    fun testCheckPasswordLength() {
        // Пароль слишком длинный
        assertEquals("Пароль слишком длинный (максимум 64 символов)",
            CheckUtility.checkPasswordLength("a".repeat(100)))
    }

    @Test // Илья. Тест к функции 3
    fun testCheckForSimplePatterns() {
        // Пароль содержит "abc"
        assertEquals("Пароль содержит очевидные комбинации",
            CheckUtility.checkForSimplePatterns("abcDEF123"))
    }

    @Test // Илья. Тест к функции 4
    fun testAnalyzePasswordComposition() {
        val password = "Test123!"
        val composition = CheckUtility.analyzePasswordComposition(password)

        // "Test123!" содержит:
        // - строчные: "est" (3 символа)
        // - заглавные: "T" (1 символ)
        // - цифры: "123" (3 символа)
        // - спецсимволы: "!" (1 символ)
        // - всего: 8 символов

        assertEquals(3, composition["строчные_буквы"])
        assertEquals(1, composition["заглавные_буквы"])
        assertEquals(3, composition["цифры"])
        assertEquals(1, composition["спецсимволы"])
        assertEquals(8, composition["всего_символов"])
    }
}