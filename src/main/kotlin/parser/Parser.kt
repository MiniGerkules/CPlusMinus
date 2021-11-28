package parser

import parser.ast.*
import tokens.*

/**
 * Parser of the C+- language
 *
 * @property tokens list of all tokens that meet in the code
 * @property currentIndex the current index of the parser in the [tokens]
 * @property currentNode the current node, which we will fill with child nodes
 */
class Parser(private val tokens: List<Token>) {
    private var currentIndex: Int = 0
    private lateinit var currentNode: ExpressionNode

    /**
     * The method is checks the current token type to coincide with
     * possible token types
     *
     * @return the token if the current token type contains in the
     * [possibleTokenTypes] else null
     */
    private fun match(possibleTokenTypes: List<TokenType>): Token? {
        if (currentIndex < tokens.size)
            if (possibleTokenTypes.any { it::class == tokens[currentIndex]::class })
                return tokens[currentIndex]

        return null
    }

    /**
     * The method is checks the current token type to coincide with
     * possible token types
     *
     * @return returns the required token and increases [currentIndex] by 1
     * @throws IllegalArgumentException if the required token type wasn't
     * found. In this case, the [currentIndex] increase doesn't occur
     */
    private fun require(possibleTokenTypes: List<TokenType>): Token {
        val temp = match(possibleTokenTypes)

        if (temp != null) {
            ++currentIndex
            return temp
        } else {
            throw IllegalArgumentException("Error!!! Expected: $possibleTokenTypes " +
                    "actual: ${tokens[currentIndex].type} on position " +
                    "${tokens[currentIndex].position}")
        }
    }

    /**
     * The function parses the expression of C+- language
     */
    private fun parseExpression() {
        // Expression can start from type or identifier
        val temp = match(listOf(Identifier()) + PrimitiveType.types)
            ?: throw error("A type or identifier is expected at the beginning of an expression!" +
                           " Error on position ${tokens[currentIndex].position}")
    }

    /**
     * The method that starts parsing the C+- code
     *
     * @return the root of the AST
     */
    fun parseCode(): ExpressionNode {
        val start = MainFunNode()
        currentNode = start

        while (currentIndex < tokens.size) {
            parseExpression()
            require(listOf(ExpEnd()))
        }

        return start
    }
}