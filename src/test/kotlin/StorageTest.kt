package nvi.test

import nvi.safe.CheckUtility
import nvi.safe.Storage
import org.junit.jupiter.api.*
import kotlin.test.assertEquals

class StorageTest {
    val storage = Storage("passwords.txt")

    @Test
    fun testAddPassword() {
        val flag = storage.addPassword("PasswordForTest!@1")
        assertEquals("Password added!", flag)
    }

    @Test
    fun testRemovePassword() {
        val flag1 = storage.removePassword("PasswordForTest!@1")
        assertEquals("Password removed", flag1)
        val flag2 = storage.removePassword("blablabla")
        assertEquals("Password not found", flag2)
    }
}