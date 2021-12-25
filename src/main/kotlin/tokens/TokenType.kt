package tokens

/**
 * Basic class for all possible token types
 *
 * @property regex the regular expression specifying the token
 */
abstract class TokenType {
    abstract val regex: Regex

    /**
     * Method comparing class objects
     */
    override fun equals(other: Any?): Boolean {
        return if (other == null)
            false
        else if (this === other)
            true
        else {
            if (other is TokenType)
                this.regex.pattern == other.regex.pattern
            else
                false
        }
    }

    /**
     * Method that generates a hash code for an object
     */
    override fun hashCode(): Int = regex.pattern.hashCode()
}