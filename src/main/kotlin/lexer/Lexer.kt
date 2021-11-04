package lexer

import org.reflections.Reflections
import tokens.PossibleToken
import tokens.Token
import tokens.TokenType
import java.io.File
import java.io.FileNotFoundException

/**
 * The class representing the lexical analyzer of the C+- language.
 *
 * @property pathToFileWithCode path to source code file
 * @property currentPosition the current position of the lexer in the code
 * @property allTokens list of all possible language tokens
 * @property tokensList list of all tokens that meet in the code
 * @property code all C+- code in [file][pathToFileWithCode]
 */
class Lexer(private val pathToFileWithCode: String) {
    private lateinit var tokensList: MutableList<Token>
    private lateinit var code: String

    private var currentPosition: Int = 0
    private val allTokens: MutableList<TokenType>
    init {
        val reflections = Reflections("tokens")
        val temp = reflections.getTypesAnnotatedWith(PossibleToken::class.java).toMutableList()

        allTokens = mutableListOf()
        for (class_ in temp) {
            allTokens.add(class_.getConstructor().newInstance() as TokenType)
        }
    }

    /**
     * The method parses the code from the file from the file.
     *
     * @exception FileNotFoundException throws when the path is not found (or doesn't exist)
     * @exception IllegalArgumentException throws when the lexer can't define the token
     */
    fun lexicalAnalysis(): List<Token> {
        val file = File(pathToFileWithCode)

        if (!file.isFile)
            throw FileNotFoundException("Can't find the file by $pathToFileWithCode")

        code = file.readText()
        while (nextToken());

        return tokensList.filter { it.type.toString() != "Space" }
    }

    /**
     * The method define tokens in the [code]
     *
     * @exception IllegalArgumentException throws when the lexer can't define the token
     */
    private fun nextToken(): Boolean {
        if (currentPosition == code.length - 1)
            return false

        for (token in allTokens) {
            val result = token.regex.find(code, currentPosition)

            if (result != null) {
                tokensList.add(Token(token, result.value, currentPosition))
                currentPosition += result.value.length

                return true
            }
        }

        throw IllegalArgumentException("The error was detected at position $currentPosition!")
    }
}