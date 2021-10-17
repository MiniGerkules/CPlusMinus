package lexer

import tokens.TokenType

/**
 * The class representing the lexical analyzer of the C+- language.
 *
 * @property pathToFileWithCode path to source code file
 * @property currentPosition the current position of the lexer in the code
 * @property tokensList list of all possible language tokens
 */
class Lexer(val pathToFileWithCode: String) {
    var currentPosition: Int = 0
    var tokensList: MutableList<TokenType> = mutableListOf()
}








