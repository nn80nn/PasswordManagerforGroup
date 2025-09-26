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
}