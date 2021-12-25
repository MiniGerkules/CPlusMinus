package lexer

import exceptions.lexerExceptions.*
import org.reflections.Reflections
import tokens.*
import tokens.annotations.*

/**
 * The class representing the lexical analyzer of the C+- language.
 *
 * @property tokensList list of all tokens that meet in the code
 * @property currentCodeLine the current line of code to be split into tokens
 * @property currentPosition the current position of the lexer in the code
 * @property allTokens list of all possible language tokens
 */
class Lexer {
    private var tokensList: MutableList<Token> = mutableListOf()
    private var currentCodeLine: String = ""
    private var currentPosition: Int = 0
    private var numberOfLines = 0
    private val allTokens: MutableList<TokenType>
    init {
        val reflections = Reflections("tokens")
        allTokens = mutableListOf()

        var temp = reflections.getTypesAnnotatedWith(Keyword::class.java).toList()
        addTokens(temp)

        temp = reflections.getTypesAnnotatedWith(Lexeme::class.java).toList()
        addTokens(temp)

        // Swap FloatNumber and IntNumber places
        val floatIndex = allTokens.indexOf(FloatNumber())
        val intIndex = allTokens.indexOf(IntNumber())
        if (floatIndex > intIndex)
            allTokens[floatIndex] = allTokens[intIndex].also { allTokens[intIndex] = allTokens[floatIndex] }
    }

    /**
     * A method that adds classes found by reflection to the general list of tokens
     *
     * @param toAdd the list of token types to add
     */
    private fun addTokens(toAdd: List<Class<*>>) {
        for (elem in toAdd) {
            allTokens.add(elem.getConstructor().newInstance() as TokenType)
        }
    }

    /**
     * The method returns all processed tokens
     *
     * @return all processed tokens except [Space]
     */
    fun getTokens(): List<Token> = tokensList.filter { it.type != Space() }.toList()

    /**
     * The method parses the code from the file from the file.
     *
     * @param codeLine line of code to be split into tokens
     */
    fun lexicalAnalysis(codeLine: String) {
        currentCodeLine = codeLine.trim()
        currentPosition = 0
        ++numberOfLines

        while (hasToken())
            nextToken()
    }

    /**
     * The method shows if there are still unprocessed tokens
     *
     * @return true if there are raw tokens, false otherwise
     */
    private fun hasToken(): Boolean = currentPosition < currentCodeLine.length

    /**
     * The method define tokens in the [code]
     *
     * @exception UnableToRecognizeTokenException throws when the lexer can't define the token
     */
    @OptIn(ExperimentalStdlibApi::class)
    private fun nextToken() {
        for (token in allTokens) {
            val result = token.regex.matchAt(currentCodeLine, currentPosition)

            if (result != null) {
                tokensList.add(Token(token, result.value, currentPosition))
                currentPosition += result.value.length
                return
            }
        }

        throw UnableToRecognizeTokenException("The error is seen on line $numberOfLines at position $currentPosition!" +
                "\n$currentCodeLine\n" + " ".repeat(currentPosition) + "^")
    }
}
