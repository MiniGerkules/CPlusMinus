package tokens

/**
 * Class describes primitive types,
 * such as int32, float32, char and void
 */
sealed class PrimitiveType: TokenType() {
    companion object {
        val types: List<TokenType> = listOf(Int32(), Float32(), Char(), Void())
    }
}
