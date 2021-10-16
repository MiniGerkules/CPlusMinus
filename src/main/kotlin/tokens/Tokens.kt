package tokens

/**
 * Class describing the type of token <IntNumber>
 *
 * Integer -- a sequence of characters, where the sign comes first
 * plus or minus, after them the number from 1 to 9, and in all other places the numbers
 * from 0 to 9
 * Examples of numbers: 100; -100; -0; +0
 */
@PossibleToken
class Number : TokenType(Regex("([-+]?\\s*([1-9]\\d*|0))")) {
    /**
     * The method returns a textual representation of the class name
     * @return Class name on string.
     */
    override fun toString(): String = "IntNumber"
}

/**
 * Class describing the type of token <FloatNumber>
 *
 * Fractional number -- a sequence of characters with a digit in the first place
 * from 0 to 9 (if 0 is in the first place, there must be a period after it),
 * and on all other digits from 0 to 9. A fractional number can contain only
 * one point
 * Examples of numbers: 200.100; 123.3; 123.0; 0.123; 0.0; -1.0; -0.123;
 * -0.00000000; +0.00000000; - 0.5
 * Examples of non-numbers: 0000.00
 */
@PossibleToken
class FloatNumber : TokenType(Regex("([+-]?\\s*((0\\.\\d+)|([1-9]\\d*\\.\\d+)))")) {
    /**
     * The method returns a textual representation of the class name
     * @return Class name on string.
     */
    override fun toString(): String = "FloatNumber"
}

//Examples: abs; sda_; _dsad9; a090_; as_09_dsa
//class Variable

//class ExpEnd

//class Space

//class Assign

//class Plus

//class Minus

//class Multiply

//class Divide

//class Remainder