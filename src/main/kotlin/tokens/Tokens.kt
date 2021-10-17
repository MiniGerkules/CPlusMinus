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

/**
 * Class that describes a name of any variable.
 * This name mustn't:
 *  1) start from numbers or punctuation marks;
 *  2) have any punctuation marks but underline '_';
 * This name must:
 *  1) start from letters in any case (lower, upper) or underline;
 *  2) have any numbers in any place but start.
 *
 * Correct: firstNumber, Second_number, _third_, four4, five5five
 * Incorrect: 1first, second.number
 */
@PossibleToken
class Variable : TokenType(Regex("([_a-zA-Z])\\w*")) {
    /**
     * The method returns a textual representation of the variable name.
     * @return Class name on string.
     */
    override fun toString(): String = regex.toString()

}

//class ExpEnd

//class Space

//class Assign

//class Plus

//class Minus

//class Multiply

//class Divide

//class Remainder