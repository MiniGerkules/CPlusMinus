package tokens

/**
 * Class describes logical operators,
 * such as "&&", "||", "!" etc.
 */
sealed class LogicalOperator: TokenType() {
    companion object {
        val types: List<TokenType> = listOf(Not(), Or(), And())
    }
}
