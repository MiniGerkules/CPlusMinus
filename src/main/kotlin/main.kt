import lexer.Lexer
import parser.Parser
import byteCode.ByteCodeGenerator

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

    val lexer = Lexer(args[0])
    val parser = Parser(lexer.lexicalAnalysis())
    val byteCodeGenerator = ByteCodeGenerator(parser.parseCode())
    byteCodeGenerator.generateByteCode()
}