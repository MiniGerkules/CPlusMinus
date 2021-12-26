package parser

import exceptions.parserExceptions.*
import parser.ast.*
import tokens.*

/**
 * Parser of the C+- language
 *
 * @property tokens list of all tokens that meet in the code
 * @property currentIndex the current index of the parser in the [tokens]
 * @property rootNode the current node, which we will fill with child nodes
 * @property allVariables the list of all declared variables
 */
class Parser(private val tokens: List<Token>) {
    private var currentIndex: Int = 0
    private lateinit var rootNode: MainFunNode
    private val allVariables: MutableList<VariableNode> = mutableListOf()

    /**
     * The method is checks the current token type to coincide with
     * possible token types
     *
     * @throws AllTokensProcessedException if [currentIndex] > the size of [tokens]
     *
     * @return the token if the current token type contains in the
     * [possibleTokenTypes] else null
     */
    private fun match(possibleTokenTypes: List<TokenType>): Token? {
        if (currentIndex < tokens.size) {
            return if (possibleTokenTypes.any { it == tokens[currentIndex].type })
                tokens[currentIndex]
            else
                null
        } else {
            throw AllTokensProcessedException("All tokens processed! " +
                    "An $possibleTokenTypes token types was required, but all tokens" +
                    "have already been processed!")
        }
    }

    /**
     * The method is checks the current token type to coincide with
     * possible token types
     *
     * @throws RequiredTokenNotFoundException if the required token type wasn't found
     * @throws AllTokensProcessedException if [currentIndex] > the size of [tokens]
     *
     * @return returns the required token and increases [currentIndex] by 1
     */
    private fun require(possibleTokenTypes: List<TokenType>): Token {
        val result = match(possibleTokenTypes)
        if (result != null) {
            ++currentIndex
            return result
        } else {
            throw RequiredTokenNotFoundException("Error!!! Expected: $possibleTokenTypes " +
                "actual: ${tokens[currentIndex].type} on position ${tokens[currentIndex].position}")
        }
    }

    /**
     * The method checks if the given variable has already been declared
     *
     * @throws CannotParseException if the expression could not be parsed
     */
    private fun variableExist(identifier: Token): VariableNode {
        return allVariables.find { it.variable.text == identifier.text } ?:
            throw CannotParseException("Unknown identifier found at position" +
                    "${identifier.position}!")
    }

    /**
     * Method parses unary operations
     *
     * @throws CannotParseException if the expression could not be parsed
     *
     * @param unaryOperators the list of unary operators
     */
    private fun parseUnary(unaryOperators: List<TokenType>): UnaryOperatorNode {
        val operator = require(unaryOperators)
        val operand = parseFormula()
        return UnaryOperatorNode(operator, operand)
    }

    /**
     * The method parses variables, numbers or functions
     *
     * @throws RequiredTokenNotFoundException if the required token type wasn't found
     * @throws CannotParseException if the expression couldn't be parsed
     */
    private fun parseVarOrNumOrFunOrBra(): ASTNode {
        val varOrNumOrFun = require(listOf(Identifier(), IntNumber(), FloatNumber(), LBracket()))

        return when(varOrNumOrFun.type) {
            is Identifier -> variableExist(varOrNumOrFun)
            is IntNumber -> NumberNode(varOrNumOrFun)
            is FloatNumber -> NumberNode(varOrNumOrFun)
            is LBracket -> {
                val node = parseFormula()
                require(listOf(RBracket()))
                node
            }
            else -> throw CannotParseException("Error! Can't parse expression!") // Unreachable code
        }
    }

    /**
     * The method parses arithmetic expression
     *
     * @throws RequiredTokenNotFoundException if the required token type wasn't found
     * @throws CannotParseException if the expression couldn't be parsed
     * @return the root node of this expression
     */
    private fun parseFormula(): ASTNode {
        val operators = ArithmeticOperator.types.subList(1, ArithmeticOperator.types.size - 1)
        val unaryOperators = listOf(Plus(), Minus())

        var leftOperand = if (match(unaryOperators) != null)
            parseUnary(unaryOperators)
        else
            parseVarOrNumOrFunOrBra()

        while (match(operators) != null) {
            val operator = require(operators)
            val rightOperand = parseVarOrNumOrFunOrBra()
            leftOperand = BinaryOperationNode(operator, leftOperand, rightOperand)
        }

        return leftOperand
    }

    /**
     * The method parses assignment expression or function call
     *
     * @throws RequiredTokenNotFoundException if the required token type wasn't found
     * @throws CannotParseException if the expression could not be parsed
     */
    private fun parseIdentifier(identifier: Token) {
        val variable = variableExist(identifier)

        // for fun add the check by '('
        val assign = require(listOf(Assign()))
        val rightPart = parseFormula()

        rootNode.addNode(BinaryOperationNode(assign, variable, rightPart))
    }

    /**
     * Method parses a string or character
     */
    private fun parseStringOrChar(): StringNode {
        val stringOrChar = require(listOf(StringValue(), CharValue()))
        return StringNode(stringOrChar)
    }

    /**
     * The method parses the expression that should to display
     *
     * @throws RequiredTokenNotFoundException if the required token type wasn't found
     * @param printOperator the token describing the output to the screen
     */
    private fun parsePrint(printOperator: Token) {
        require(listOf(LBracket()))
        val node: ASTNode = if (match(listOf(StringValue(), CharValue())) != null)
            parseStringOrChar()
        else
            parseFormula()
        require(listOf(RBracket()))

        rootNode.addNode(UnaryOperatorNode(printOperator, node))
    }

    /**
     * The method parses the definition of a variable or function
     *
     * @throws CannotParseException if the expression could not be parsed
     *
     * @throws RequiredTokenNotFoundException if the required token type wasn't found
     */
    private fun parseDeclaration(type: Token) {
        val identifier = require(listOf(Identifier()))

        val variable = VariableNode(type, identifier)
        rootNode.addNode(variable)
        allVariables.add(variable)

        if (match(listOf(Assign())) != null)
            parseIdentifier(identifier)
    }

    /**
     * The function parses the expression of C+- language
     *
     * @throws RequiredTokenNotFoundException if the required token type wasn't found
     * @throws CannotParseException if the expression could not be parsed
     */
    private fun parseExpression() {
        // Expression can start from type or identifier
        val temp = require(listOf(Identifier(), Print()) + PrimitiveType.types)

        when (temp.type) {
            is Identifier -> parseIdentifier(temp)
            is Print -> parsePrint(temp)
            is PrimitiveType -> parseDeclaration(temp)
        }
    }

    /**
     * The method that starts parsing the C+- code
     *
     * @throws CannotParseException if the expression could not be parsed
     * @throws RequiredTokenNotFoundException if the required token type wasn't found
     * @return the root of the AST
     */
    fun parseCode(): MainFunNode {
        rootNode = MainFunNode()
        allVariables.clear()

        while (currentIndex < tokens.size) {
            parseExpression()
            require(listOf(ExpEnd()))
        }

        return rootNode
    }
}