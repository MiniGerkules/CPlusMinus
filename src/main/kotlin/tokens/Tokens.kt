package tokens

/**
 * Class describing the type of token <Number>
 */
@PossibleToken
class Number : TokenType(Regex("[1-9]+(\\.\\d+)?")) {
    /**
     * The method returns a textual representation of the class name
     * @return Class name on string.
     */
    override fun toString(): String = "Number"
}

//class Variable

//class ExpEnd

//class Space

//class Assign

//class Plus

//class Minus

//class Multiply

//class Divide

//class Remainder