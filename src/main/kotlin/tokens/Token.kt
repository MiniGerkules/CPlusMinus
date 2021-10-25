package tokens

/**
 * The class describing the language token
 *
 * @property type the type of token
 * @property text the text of the token
 * @property position the position of the token in the source code
 */
class Token(val type: TokenType, val text: String, val position: Int) {
}