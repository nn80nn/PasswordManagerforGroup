package nvi.test

import nvi.safe.PasswordEntity
import nvi.safe.EnhancedStorage
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import java.io.File

class EnhancedStorageTest {
    private val testFilePath = "test_passwords.json"
    private lateinit var storage: EnhancedStorage

    @BeforeEach
    fun setUp() {
        storage = EnhancedStorage(testFilePath)
    }

    @AfterEach
    fun tearDown() {
        val file = File(testFilePath)
        if (file.exists()) {
            file.delete()
        }
    }

    @Test
    fun testAddPassword() {
        val password = PasswordEntity("example.com", "testPassword123", false)
        val result = storage.addPassword(password)
        assertEquals("Password added!", result)
    }

    @Test
    fun testRemovePassword() {
        val password = PasswordEntity("example.com", "testPassword123", false)
        storage.addPassword(password)

        val result1 = storage.removePassword("example.com")
        assertEquals("Password removed", result1)

        val result2 = storage.removePassword("nonexistent.com")
        assertEquals("Password not found", result2)
    }

    @Test
    fun testGetAllPasswords() {
        val password1 = PasswordEntity("example1.com", "password1", true)
        val password2 = PasswordEntity("example2.com", "password2", false)

        storage.addPassword(password1)
        storage.addPassword(password2)

        val passwords = storage.getAllPasswords()
        assertEquals(2, passwords.size)
        assertTrue(passwords.contains(password1))
        assertTrue(passwords.contains(password2))
    }

    @Test
    fun testFindPassword() {
        val password = PasswordEntity("findme.com", "secretPassword", true)
        storage.addPassword(password)

        val found = storage.findPassword("findme.com")
        assertEquals(password, found)

        val notFound = storage.findPassword("notfound.com")
        assertNull(notFound)
    }
}