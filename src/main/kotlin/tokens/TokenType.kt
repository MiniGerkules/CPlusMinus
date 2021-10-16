package tokens

/**
 * Base class for all possible token types
 *
 * @property regex the regular expression specifying the token
 */
abstract class TokenType(val regex: Regex) {
}