package lexer

import org.reflections.Reflections
import tokens.PossibleToken
import tokens.TokenType

/**
 * The class representing the lexical analyzer of the C+- language.
 *
 * @property pathToFileWithCode path to source code file
 * @property currentPosition the current position of the lexer in the code
 * @property tokensList list of all possible language tokens
 */
class Lexer(private val pathToFileWithCode: String) {
    private var currentPosition: Int = 0
    private var tokensList: MutableList<Class<*>>
    init {
        val reflections = Reflections("tokens")
        tokensList = reflections.getTypesAnnotatedWith(PossibleToken::class.java).toMutableList()
    }
}