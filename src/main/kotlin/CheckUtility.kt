package nvi.safe

import kotlin.math.log2
import kotlin.math.pow

class CheckUtility {
    companion object {

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
            return 2.0.pow(entropy) * 2 * speed
        }
    }
}