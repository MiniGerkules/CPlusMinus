package tokens

import tokens.annotations.Keyword
import tokens.annotations.Lexeme

/**
 * Class describing the type of token <IntNumber&gt
 *
 * Integer -- a sequence of characters, where the sign comes first
 * plus or minus, after them the number from 1 to 9, and in all other places the numbers
 * from 0 to 9
 * Examples of numbers: 100; -100; -0; +0.
 */
@Lexeme
class IntNumber : TokenType() {
    override val regex: Regex = Regex("([-+]?\\s*([1-9]\\d*|0))")
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
@Lexeme
class FloatNumber : TokenType() {
    override val regex: Regex = Regex("([+-]?\\s*((0\\.\\d+)|([1-9]\\d*\\.\\d+)))")
}

/**
 * Class describing the type of token <CharValue&gt
 *
 * Char value is a single symbol allocated between two single quotes
 * Correct examples: 'a', '1', '(', '*', '%'
 * Incorrect examples: a, 'abc', 1
 */
@Lexeme
class CharValue : TokenType() {
    override val regex: Regex = Regex("'(.)'")
}

/**
 * Class describing the type of token <CharValue&gt
 *
 * Char value is a single symbol allocated between two single quotes
 * Examples: "Hello, World!", "12345"
 */
@Lexeme
class StringValue : TokenType() {
    override val regex: Regex = Regex("\"(.*)\"")
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
@Lexeme
class Identifier : TokenType() {
    override val regex: Regex = Regex("([_a-zA-Z])\\w*")
}

/**
 * Class that describes the end of expression.
 * In this project, every expression ends with a ';'
 *
 * Example: FloatNumber a = 1.0;
 */
@Lexeme
class ExpEnd : TokenType() {
    override val regex: Regex = Regex(";")
}

/**
 * Class describing the type of token <Space&gt
 *
 * Spaces in the C+- programming language are used to separate words, as
 * well as to increase the readability of the code.
 * Correct: ' ', '\t', '\n'
 */
@Lexeme
class Space : TokenType() {
    override val regex: Regex = Regex("[ \t\n]+")
}

/**
 * Class describing the type of token <Print&gt
 *
 * print writes in console the value of needing variable or a string
 * Example: print(a);
 */
@Keyword
class Print : TokenType() {
    override val regex: Regex = Regex("print")
}

/**
 * Class describing the type of token <Return&gt
 *
 * return finishes any program or function, returning variable,
 * number, expression etc.
 * Example: return 0;
 */
@Keyword
class Return : TokenType() {
    override val regex: Regex = Regex("return")
}

/**
 * Class describing the type of token <Assign&gt
 *
 * The '=' symbol is used to assign a value to a variable.
 * Example: int32 a = 5;
 */
@Lexeme
class Assign : ArithmeticOperator() {
    override val regex: Regex = Regex("=")
}

/**
 * Class describing the type of token <Plus&gt
 *
 * The '+' character is used to add valid C+- expressions.
 * Also, the '+' symbol can mean a unary plus.
 * Example 1: int32 a = 1232 + 32314;
 * Example 1: int32 a = +7;
 */
@Lexeme
class Plus : ArithmeticOperator() {
    override val regex: Regex = Regex("\\+")
}

/**
 * Class describing the type of token <Minus&gt
 *
 * The '-' character is used to subtract valid C+- expressions.
 * Also, the '-' symbol can mean a unary minus.
 * Example 1: int32 a = 1232 - 32314;
 * Example 2: int32 a = -5;
 */
@Lexeme
class Minus : ArithmeticOperator() {
    override val regex: Regex = Regex("-")
}

/**
 * Class describing the type of token <Multiply&gt
 *
 * The '+' character is used to subtract valid C+- expressions.
 * Example 1: int32 a = 5 * 3;
 */
@Lexeme
class Multiply : ArithmeticOperator() {
    override val regex: Regex = Regex("\\*")
}

/**
 * Class describing the type of token <Divide&gt
 *
 * The '/' character is used to subtract valid C+- expressions.
 * Example 1: int32 a = 6 / 2;
 */
@Lexeme
class Divide : ArithmeticOperator() {
    override val regex: Regex = Regex("/")
}

/**
 * Class describing the type of token <Modulus&gt
 *
 * The '%' character is used to subtract valid C+- expressions.
 * Example 1: int32 a = 5 % 3;
 */
@Lexeme
class Modulus : ArithmeticOperator() {
    override val regex: Regex = Regex("%")
}

/**
 * Class describing the type of token <Not&gt
 *
 * The '!' character is used to represent a not- expression.
 * Example 1: if (i != 0) { action };
 */
@Lexeme
class Not : LogicalOperator() {
    override val regex: Regex = Regex("!")
}

/**
 * Class describing the type of token <MoreThan&gt
 *
 * The '>' character is used to represent a comparing expression.
 * Example 1: if (i > 0) { action };
 */
@Lexeme
class MoreThan : LogicalOperator() {
    override val regex: Regex = Regex(">")
}

/**
 * Class describing the type of token <LessThan&gt
 *
 * The '<' character is used to represent a comparing expression.
 * Example 1: if (i < 0) { action };
 */
@Lexeme
class LessThan : LogicalOperator() {
    override val regex: Regex = Regex("<")
}

/**
 * Class describing the type of token <And&gt
 *
 * The '&&' character is used to represent a conjunction.
 * Example 1: if ((i < 0) && (i != 1)) { action };
 */
@Lexeme
class And : LogicalOperator() {
    override val regex: Regex = Regex("&&")
}

/**
 * Class describing the type of token <Or&gt
 *
 * The '<' character is used to represent a disjuncture.
 * Example 1: if ((i < 0) || (i != 1)) { action };
 */
@Lexeme
class Or : LogicalOperator() {
    override val regex: Regex = Regex("\\|\\|")
}

/**
 * Class describing the type of token <Void&gt
 *
 * void is a function type that returns nothing.
 * Example 1: void helloWorld();
 */
@Keyword
class Void : PrimitiveType() {
    override val regex: Regex = Regex("void")
}

/**
 * Class describing the type of token <Int32&gt
 *
 * int32 is a 4 bytes integer number.
 * Example 1: int32 a = 5 % 3;
 */
@Keyword
class Int32 : PrimitiveType() {
    override val regex: Regex = Regex("int32")
}

/**
 * Class describing the type of token <Float32&gt
 *
 * float32 is a 4 bytes floating point number.
 * Example 1: float32 a = 0.003;
 */
@Keyword
class Float32 : PrimitiveType() {
    override val regex: Regex = Regex("float32")
}

/**
 * Class describing the type of token <Char&gt
 *
 * char is a 1 byte character.
 * Example 1: char a = 'a';
 */
@Keyword
class Char : PrimitiveType() {
    override val regex: Regex = Regex("char")
}

/**
 * Class describing the type of token <Left Bracket&gt
 *
 * Left bracket means that the expression inside starts.
 * Example 1: if (a == b);
 */
@Lexeme
class LBracket : TokenType() {
    override val regex: Regex = Regex("\\(")
}

/**
 * Class describing the type of token <Right Bracket&gt
 *
 * Left bracket means that the expression inside ends.
 * Example 1: if (a == b);
 */
@Lexeme
class RBracket : TokenType() {
    override val regex: Regex = Regex("\\)")
}

/**
 * Class describing the type of token <Start Block&gt
 *
 * This Bracket type means that block of code is started
 * Example 1: if (a == b) { action };
 */
@Lexeme
class StartBlock : TokenType() {
    override val regex: Regex = Regex("\\{")
}

/**
 * Class describing the type of token <End Block&gt
 *
 * This Bracket type means that block of code is finished
 * Example 1: if (a == b) { action };
 */
@Lexeme
class EndBlock : TokenType() {
    override val regex: Regex = Regex("}")
}

/**
 * Class describing the type of token <If&gt
 *
 * if is a condition check
 * Example 1: if (a == b) { action };
 */
@Keyword
class If : TokenType() {
    override val regex: Regex = Regex("if")
}

/**
 * Class describing the type of token <Else&gt
 *
 * else is another way if condition doesn't meet the condition
 * Example 1: if (a == b) { action } else { other action };
 */
@Keyword
class Else : TokenType() {
    override val regex: Regex = Regex("else")
}

/**
 * Class describing the type of token <While&gt
 *
 * while is a loop that goes until the end of condition
 * Example 1: while (i != 0) { action };
 */
@Keyword
class While : TokenType() {
    override val regex: Regex = Regex("while")
}

/**
 * Class describing the type of token <For&gt
 *
 * for is a loop that goes until the end of condition
 * but with starting number and a given step
 * Example 1: for (i = 0; i < 10; i = i + 1) { action };
 */
@Keyword
class For : TokenType() {
    override val regex: Regex = Regex("for")
}
