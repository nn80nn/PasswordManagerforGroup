package nvi.test

import nvi.safe.CheckUtility
import nvi.safe.Storage
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

class StorageTest {
    val storage = Storage("passwords.txt")

    @Test
    fun testAddPassword() {
        val flag = storage.addPassword("PasswordForTest!@1")
        assertEquals("Password added!", flag)
    }

    @Test
    fun testRemovePassword() {
        storage.addPassword("PasswordForTest!@1")
        val flag1 = storage.removePassword("PasswordForTest!@1")
        assertEquals("Password removed", flag1)
        val flag2 = storage.removePassword("blablabla")
        assertEquals("Password not found", flag2)
    }

    @Test
    fun testCheckPasswordUniqueness() {
        // Очищаем файл перед тестом
        if (storage.file.exists()) {
            storage.file.delete()
        }

        // Добавляем пароль и проверяем уникальность нового пароля
        storage.addPassword("existingPass")
        val result2 = storage.checkPasswordUniqueness("newUniquePass")
        assertEquals("Это уникальный пароль!", result2)
    }
}