package tokens

/**
 * Class describes arithmetic operators,
 * such as +, -, %, / etc.
 */
sealed class ArithmeticOperator: TokenType() {
    companion object {
        val types: List<TokenType> = listOf(Assign(), Plus(), Minus(), Multiply(), Divide(), Modulus())
    }
}
