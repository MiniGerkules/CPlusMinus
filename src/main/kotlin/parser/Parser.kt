package parser

import parser.ast.*
import tokens.*

/**
 * Parser of the C+- language
 *
 * @property tokens list of all tokens that meet in the code
 * @property currentIndex the current index of the parser in the [tokens]
 * @property rootNode the current node, which we will fill with child nodes
 * @property allVariable the list of all declared variables
 */
class Parser(private val tokens: List<Token>) {
    private var currentIndex: Int = 0
    private lateinit var rootNode: MainFunNode
    private val allVariable: MutableList<VariableNode> = mutableListOf()

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
     * The method checks if the given variable has already been declared
     *
     * @throws IllegalArgumentException if the expression could not be parsed
     */
    private fun variableExist(identifier: Token): VariableNode {
        return allVariable.find { it.variable.text == identifier.text } ?:
            throw IllegalArgumentException("Unknown identifier found at position" +
                    "${identifier.position}!")
    }

    /**
     * The method parses variables, numbers or functions
     *
     * @throws IllegalArgumentException if the expression couldn't be parsed
     */
    private fun parseVarOrNumOrFun(): ASTNode {
        val varOrNumOrFun = require(listOf(Identifier(), IntNumber(), FloatNumber()))

        return when(varOrNumOrFun.type) {
            is Identifier -> variableExist(varOrNumOrFun)
            is IntNumber -> NumberNode(varOrNumOrFun)
            is FloatNumber -> NumberNode(varOrNumOrFun)
            else -> throw IllegalArgumentException("Error! Can't parse expression!") // Unreachable code
        }
    }

    /**
     * The method parses arithmetic expression
     *
     * @return the root node of this expression
     */
    private fun parseFormula(): BinaryOperationNode {
        // This realization needs to be rewritten in future!!!
        /*
            a = 5 * 3;
            a = 5 + 3;
            a = 5 - 3;
            a = 5 / 3;
         */

        val leftOperand = parseVarOrNumOrFun()
        val operator = require(ArithmeticOperator.types)
        val rightOperand = parseVarOrNumOrFun()

        return BinaryOperationNode(operator, leftOperand, rightOperand)
    }

    /**
     * The method parses assignment expression or function call
     *
     * @throws IllegalArgumentException if the expression could not be parsed
     */
    private fun parseIdentifier() {
        val identifier = require(listOf(Identifier()))
        val variable = variableExist(identifier)

        // for fun add the check by '('
        val assign = require(listOf(Assign()))
        val rightPart = parseFormula()

        rootNode.addNode(BinaryOperationNode(assign, variable, rightPart))
    }

    /**
     * The method parses the definition of a variable or function
     *
     * @throws IllegalArgumentException if the expression could not be parsed
     */
    private fun parseDeclaration() {
        val type = require(PrimitiveType.types)
        val identifier = require(listOf(Identifier()))
        // for fun add the check by '('
        require(listOf(ExpEnd()))

        val variable = VariableNode(type, identifier)
        rootNode.addNode(variable)
        allVariable.add(variable)
    }

    /**
     * The function parses the expression of C+- language
     */
    private fun parseExpression() {
        // Expression can start from type or identifier
        val temp = match(listOf(Identifier()) + PrimitiveType.types)
            ?: throw IllegalArgumentException("A type or identifier is expected at the " +
                                              "beginning of an expression! Error on position" +
                                              " ${tokens[currentIndex].position}")

        when (temp.type) {
            is Identifier -> parseIdentifier()
            is PrimitiveType -> parseDeclaration()
        }
    }

    /**
     * The method that starts parsing the C+- code
     *
     * @return the root of the AST
     */
    fun parseCode(): MainFunNode {
        rootNode = MainFunNode()
        allVariable.clear()

        while (currentIndex < tokens.size) {
            parseExpression()
            require(listOf(ExpEnd()))
        }

        return rootNode
    }
}