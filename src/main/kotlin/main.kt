import java.io.FileReader
import lexer.Lexer
import parser.Parser
import byteCode.ByteCodeGenerator
import exceptions.lexerExceptions.LexerException
import exceptions.parserExceptions.ParserException
import parser.ast.MainFunNode
import tokens.Token

/**
 * The method returns the extension of the file written in the string
 *
 * @returns the file extension or an empty string if the string isn't
 * represent a file path
 */
fun String.getExtension(): String {
    val i = lastIndexOf('.')
    return if (i > 0) substring(i+1)
           else ""
}

/**
 * The function is entry point to the program. It starts all processes
 * to compile source code
 *
 * @param args the array of command line arguments. The first and only
 * argument is the name of the source file.
 */
fun main(args: Array<String>) {
    if (args.size > 1) {
        println("You must enter 1 argument! This argument is path to file with C+- code")
        return
    } else if (args[0].getExtension() != "txt") {
        println("The file is not in text format.")
        return
    }

    val lexer = Lexer()
    try {
        FileReader(args[0]).buffered().forEachLine {
            lexer.lexicalAnalysis(it)
        }
    } catch (error: java.io.FileNotFoundException) {
        println("Error! Failed to open file!\n${error.message}")
        return
    }

    val lexerResult: List<Token>
    try {
        lexerResult = lexer.getTokens()
    } catch (error: LexerException) {
        println("Lexer error!\n${error.message}")
        return
    }

    val parser = Parser(lexerResult)
    val parserResult: MainFunNode
    try {
        parserResult = parser.parseCode()
    } catch (error: ParserException) {
        println("Parser error!\n${error.message}")
        return
    }

    val byteCodeGenerator = ByteCodeGenerator(parserResult)
    byteCodeGenerator.generateByteCode()
}