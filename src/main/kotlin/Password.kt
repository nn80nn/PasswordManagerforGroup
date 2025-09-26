package nvi.safe

data class PasswordEntity(
    val UrlName: String,
    val SavedPassword: String,
    val Encrypted: Boolean
)
