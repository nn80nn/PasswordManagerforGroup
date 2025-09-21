package nvi.safe

class CheckUtility {
    companion object {
        fun checkPasswordStrength(password: String): String {
            val containsUppercase = password.any { it.isUpperCase() }
            val containsNumber = password.any { it.isDigit() }
            val containsSpecialSymbols = password.any { it in "@!#$%^&*" }
            val strong = containsNumber && containsUppercase
                    && containsSpecialSymbols && password.length > 6

            if (containsUppercase && containsNumber && containsSpecialSymbols) {
                return "Password is strong!"
            } else {
                return "Password is bad!"
            }
        }
    }
}