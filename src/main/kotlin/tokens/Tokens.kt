package tokens

/**
 * Class describing the type of token <IntNumber&gt
 *
 * Integer -- a sequence of characters, where the sign comes first
 * plus or minus, after them the number from 1 to 9, and in all other places the numbers
 * from 0 to 9
 * Examples of numbers: 100; -100; -0; +0.
 */
@PossibleToken
class IntNumber : TokenType(Regex("([-+]?\\s*([1-9]\\d*|0))")) {
    /**
     * The method returns a textual representation of the class name
     * @return Class name on string.
     */
    override fun toString(): String = "IntNumber"
}

/**
 * Class describing the type of token <FloatNumber&gt
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
class Identifier : TokenType(Regex("([_a-zA-Z])\\w*")) {
    /**
     * The method returns a textual representation of the variable name.
     * @return Class name on string.
     */
    override fun toString(): String = "Identifier"
}

/**
 * Class that describes the end of expression.
 * In this project, every expression ends with a ';'
 *
 * Example: FloatNumber a = 1.0;
 */
@PossibleToken
class ExpEnd : TokenType(Regex(";")) {
    /**
     * The method returns a textual representation of the expression.
     * @return Class name on string.
     */
    override fun toString(): String = "ExpEnd"
}

/**
 * Class describing the type of token <Space&gt
 *
 * Spaces in the C+- programming language are used to separate words, as
 * well as to increase the readability of the code.
 * Correct: ' ', '\t', '\n'
 */
@PossibleToken
class Space : TokenType(Regex("[ \t\n]+")) {
    /**
     * The method returns a textual representation of the class name
     * @return Class name on string.
     */
    override fun toString(): String = "Space"
}

/**
 * Class describing the type of token <Assign&gt
 *
 * The '=' symbol is used to assign a value to a variable.
 * Example: int32 a = 5;
 */
@PossibleToken
class Assign : TokenType(Regex("=")) {
    /**
     * The method returns a textual representation of the class name
     * @return Class name on string.
     */
    override fun toString(): String = "Assign"
}

/**
 * Class describing the type of token <Plus&gt
 *
 * The '+' character is used to add valid C+- expressions.
 * Also, the '+' symbol can mean a unary plus.
 * Example 1: int32 a = 1232 + 32314;
 * Example 1: int32 a = +7;
 */
@PossibleToken
class Plus : TokenType(Regex("\\+")) {
    /**
     * The method returns a textual representation of the class name
     * @return Class name on string.
     */
    override fun toString(): String = "Plus"
}

/**
 * Class describing the type of token <Minus&gt
 *
 * The '-' character is used to subtract valid C+- expressions.
 * Also, the '-' symbol can mean a unary minus.
 * Example 1: int32 a = 1232 - 32314;
 * Example 2: int32 a = -5;
 */
@PossibleToken
class Minus : TokenType(Regex("-")) {
    /**
     * The method returns a textual representation of the class name
     * @return Class name on string.
     */
    override fun toString(): String = "Minus"
}

/**
 * Class describing the type of token <Multiply&gt
 *
 * The '+' character is used to subtract valid C+- expressions.
 * Example 1: int32 a = 5 * 3;
 */
@PossibleToken
class Multiply : TokenType(Regex("\\*")) {
    /**
     * The method returns a textual representation of the class name
     * @return Class name on string.
     */
    override fun toString(): String = "Multiply"
}

/**
 * Class describing the type of token <Divide&gt
 *
 * The '/' character is used to subtract valid C+- expressions.
 * Example 1: int32 a = 6 / 2;
 */
@PossibleToken
class Divide : TokenType(Regex("/")) {
    /**
     * The method returns a textual representation of the class name
     * @return Class name on string.
     */
    override fun toString(): String = "Divide"
}

/**
 * Class describing the type of token <Modulus&gt
 *
 * The '%' character is used to subtract valid C+- expressions.
 * Example 1: int32 a = 5 % 3;
 */
@PossibleToken
class Modulus : TokenType(Regex("%")) {
    /**
     * The method returns a textual representation of the class name
     * @return Class name on string.
     */
    override fun toString(): String = "Modulus"
}

/**
 * Class describing the type of token <Int32&gt
 *
 * int32 is a 4 bytes integer number.
 * Example 1: int32 a = 5 % 3;
 */
@PossibleToken
class Int32 : TokenType(Regex("int32")) {
    /**
     * The method returns a textual representation of the class name
     * @return Class name on string.
     */
    override fun toString(): String = "Int32"
}

/**
 * Class describing the type of token <Float32&gt
 *
 * float32 is a 4 bytes floating point number.
 * Example 1: float32 a = 0.003;
 */
@PossibleToken
class Float32 : TokenType(Regex("float32")) {
    /**
     * The method returns a textual representation of the class name
     * @return Class name on string.
     */
    override fun toString(): String = "Float32"
}

/**
 * Class describing the type of token <Char&gt
 *
 * char is a 1 byte character.
 * Example 1: char a = 'a';
 */
@PossibleToken
class Char : TokenType(Regex("char")) {
    /**
     * The method returns a textual representation of the class name
     * @return Class name on string.
     */
    override fun toString(): String = "Char"
}

/**
 * Class describing the type of token <Left Bracket&gt
 *
 * Left bracket means that the expression inside starts.
 * Example 1: if (a == b);
 */
@PossibleToken
class LBracket : TokenType(Regex("\\(")) {
    /**
     * The method returns a textual representation of the class name
     * @return Class name on string.
     */
    override fun toString(): String = "LBracket"
}

/**
 * Class describing the type of token <Right Bracket&gt
 *
 * Left bracket means that the expression inside ends.
 * Example 1: if (a == b);
 */
@PossibleToken
class RBracket : TokenType(Regex("\\)")) {
    /**
     * The method returns a textual representation of the class name
     * @return Class name on string.
     */
    override fun toString(): String = "RBracket"
}

/**
 * Class describing the type of token <Start Block&gt
 *
 * This Bracket type means that block of code is started
 * Example 1: if (a == b) { action };
 */
@PossibleToken
class StartBlock : TokenType(Regex("\\{")) {
    /**
     * The method returns a textual representation of the class name
     * @return Class name on string.
     */
    override fun toString(): String = "StartBlock"
}

/**
 * Class describing the type of token <End Block&gt
 *
 * This Bracket type means that block of code is finished
 * Example 1: if (a == b) { action };
 */
@PossibleToken
class EndBlock : TokenType(Regex("}")) {
    /**
     * The method returns a textual representation of the class name
     * @return Class name on string.
     */
    override fun toString(): String = "EndBlock"
}