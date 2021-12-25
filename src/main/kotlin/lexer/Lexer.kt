package lexer

import exceptions.lexerExceptions.*
import org.reflections.Reflections
import tokens.*

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
        val temp = reflections.getTypesAnnotatedWith(PossibleToken::class.java).toList()

        allTokens = mutableListOf()
        for (class_ in temp) {
            allTokens.add(class_.getConstructor().newInstance() as TokenType)
        }

        // Swap FloatNumber and IntNumber places
        val float = allTokens.indexOf(FloatNumber())
        val int = allTokens.indexOf(IntNumber())
        allTokens[float] = allTokens[int].also { allTokens[int] = allTokens[float] }
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
