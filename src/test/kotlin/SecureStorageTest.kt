package nvi.test

import SecureStorage
import org.junit.jupiter.api.*
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class SecureStorageTest {

    @Test
    fun testGenerateKey() {
        val key = SecureStorage.generateKey()

        // Проверяем, что ключ не пустой
        assertTrue(key.isNotEmpty(), "Generated key should not be empty")

        // Проверяем, что два сгенерированных ключа разные
        val key2 = SecureStorage.generateKey()
        assertNotEquals(key, key2, "Two generated keys should be different")

        // Проверяем, что ключ в формате Base64 (содержит только допустимые символы)
        val base64Pattern = Regex("^[A-Za-z0-9+/]*={0,2}$")
        assertTrue(base64Pattern.matches(key), "Key should be in valid Base64 format")
    }

    @Test
    fun testEncrypt() {
        val key = SecureStorage.generateKey()
        val plaintext = "Test password 123!"

        val encrypted = SecureStorage.encrypt(plaintext, key)

        // Проверяем, что зашифрованный текст не равен исходному
        assertNotEquals(plaintext, encrypted, "Encrypted text should be different from plaintext")

        // Проверяем, что зашифрованный текст не пустой
        assertTrue(encrypted.isNotEmpty(), "Encrypted text should not be empty")

        // Проверяем, что результат в формате Base64
        val base64Pattern = Regex("^[A-Za-z0-9+/]*={0,2}$")
        assertTrue(base64Pattern.matches(encrypted), "Encrypted text should be in valid Base64 format")
    }

    @Test
    fun testDecrypt() {
        val key = SecureStorage.generateKey()
        val originalText = "Secret password 456!"

        val encrypted = SecureStorage.encrypt(originalText, key)
        val decrypted = SecureStorage.decrypt(encrypted, key)

        // Проверяем, что расшифрованный текст равен исходному
        assertEquals(originalText, decrypted, "Decrypted text should match original text")
    }
}