package parser

import tokens.Token
import tokens.TokenType

/**
 * Parser of the C+- language
 *
 * @property tokens list of all tokens that meet in the code
 * @property currentIndex the current index of the parser in the [tokens]
 */
class Parser(private val tokens: List<Token>) {
    private var currentIndex: Int = 0

    /**
     * The method is checks the current token type to coincide with
     * possible token types
     *
     * @return the token if the current token type contains in the
     * [possibleTokenTypes] else null
     */
    private fun match(possibleTokenTypes: List<TokenType>): Token? {
        if (currentIndex < tokens.size)
            if (possibleTokenTypes.any { it.toString() == tokens[currentIndex].type.toString() })
                return tokens[currentIndex++]

        return null
    }

    /**
     * The method is checks the current token type to coincide with
     * possible token types
     *
     * @return the token
     */
    private fun require(possibleTokenTypes: List<TokenType>): Token {
        return match(possibleTokenTypes) ?:
            throw IllegalArgumentException("Error!!! Expected: $possibleTokenTypes. " +
                    "Actual ${tokens[currentIndex].type}")
    }

    /**
     * The method that starts parsing the C+- code
     */
    fun parseCode() {
        error("Not implement!")
    }
}