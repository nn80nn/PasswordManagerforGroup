package nvi.safe

import kotlin.math.log2
import kotlin.math.pow

class CheckUtility {
    companion object {

        // Илья. Функция 2
        /**
         * Проверяет длину пароля и возвращает понятное сообщение
         */
        fun checkPasswordLength(password: String, minLength: Int = 8, maxLength: Int = 64): String {
            return when {
                password.length < minLength -> "Пароль слишком короткий (минимум $minLength символов)"
                password.length > maxLength -> "Пароль слишком длинный (максимум $maxLength символов)"
                else -> "Длина пароля корректна"
            }
        }

        // Илья. Функция 3
        /**
         * Проверяет пароль на наличие простых комбинаций и возвращает сообщение
         */
        fun checkForSimplePatterns(password: String): String {
            val simplePatterns = listOf("123", "000", "111", "abc", "qwerty", "password")

            return if (simplePatterns.any { password.contains(it, ignoreCase = true) }) {
                "Пароль содержит очевидные комбинации"
            } else {
                "Пароль не содержит очевидных комбинаций"
            }
        }

        // Илья. Функция 4
        /**
         * Анализирует пароль и возвращает количество символов каждого типа
         *
         * @param password пароль для анализа
         * @return Map с количеством символов по типам
         */
        fun analyzePasswordComposition(password: String): Map<String, Int> {
            return mapOf(
                "строчные_буквы" to password.count { it.isLowerCase() },
                "заглавные_буквы" to password.count { it.isUpperCase() },
                "цифры" to password.count { it.isDigit() },
                "спецсимволы" to password.count { !it.isLetterOrDigit() },
                "всего_символов" to password.length
            )
        }

        /***
         * Проверяет пароль на соответствие всем необходмым требованиям (наличие заглавных букв, цифр, спецсимволов)
         *
         * @param password пароль
         * @return строка с характеристикой пароля
         */
        fun checkPasswordStrength(password: String): String {
            val containsUppercase = password.any { it.isUpperCase() }
            val containsNumber = password.any { it.isDigit() }
            val containsSpecialSymbols = password.any { it in "@!#$%^&*" }
            val goodLength = password.length > 6
            val strong = containsNumber && containsUppercase
                    && containsSpecialSymbols && goodLength

            if (strong) {
                return "Password is strong!"
            } else {
                return "Password is bad!"
            }
        }

        /***
         * Проверяет энтропию по формуле
         * H = L * log2(N)
         * где:
         *  - L = длина
         *  - N = размер алфавита
         *  @param password пароль
         *  @param n размер алфавита
         *  @return энтропия пароля H
         */
        fun checkEntropy(password: String, n: Int = 69): Double {
            return password.length * log2(n.toDouble())
        }

        /***
         * Проверяет примерную скорость взлома по формуле
         * 2^H / 2 * R
         * где:
         *  - R - средняя скорость подбора
         *  - H - энтропия пароля
         *  @param password пароль
         *  @param speed скорость
         *  @param entropy энтропия
         *  @return средняя скорость подбора
         */
        fun checkAverageBruteforceTime(password: String, speed: Float, entropy: Double): Double {
            return 2.0.pow(entropy) / (2 * speed)
        }
    }
}