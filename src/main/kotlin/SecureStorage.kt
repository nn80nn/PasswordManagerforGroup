package nvi.safe

import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

object SecureStorage {

    /**
     * Генерирует новый случайный ключ
     */
    fun generateKey(): String {
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(256)
        val key = keyGenerator.generateKey()
        return Base64.getEncoder().encodeToString(key.encoded)
    }

    /**
     * Шифрует текст
     */
    fun encrypt(text: String, keyBase64: String): String {
        val keyBytes = Base64.getDecoder().decode(keyBase64)
        val key = SecretKeySpec(keyBytes, "AES")

        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, key)

        val encrypted = cipher.doFinal(text.toByteArray())
        val iv = cipher.iv

        val result = iv + encrypted

        return Base64.getEncoder().encodeToString(result)
    }

    /**
     * Расшифровывает текст
     */
    fun decrypt(encryptedBase64: String, keyBase64: String): String {
        val keyBytes = Base64.getDecoder().decode(keyBase64)
        val key = SecretKeySpec(keyBytes, "AES")

        val data = Base64.getDecoder().decode(encryptedBase64)

        val iv = data.sliceArray(0..11)  // первые 12 байт - IV
        val encrypted = data.sliceArray(12 until data.size)  // остальное - данные

        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val spec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.DECRYPT_MODE, key, spec)

        val decrypted = cipher.doFinal(encrypted)
        return String(decrypted)
    }
}