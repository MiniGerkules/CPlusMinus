package tokens

/**
 * Basic class for all possible token types
 *
 * @property regex the regular expression specifying the token
 */
abstract class TokenType() {
    abstract val regex: Regex
}