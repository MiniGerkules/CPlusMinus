package lexer

import java.io.File

import org.junit.Assert.*
import org.junit.Test
import tokens.*

/**
 * The class that tests the lexer of the C+- language
 *
 * @property workingDir the set of folders that create a path
 * to the working directory
 */
class LexerTest {
    private val workingDir: Array<String> = arrayOf("src", "test", "resources")

    /**
     * The method creates the path to the [workingDir]
     *
     * @param fileName the name of the file with the C+- code
     * @return the path to the [fileName] in [workingDir] in your OS
     */
    private fun getFilePath(fileName: String): String {
        val path: StringBuilder = StringBuilder()

        for (part in workingDir)
            path.append(part).append(File.separator)

        return path.append(fileName).toString()
    }

    /**
     * The method that checks equals expected result and result that returns by lexer
     *
     * @param path the path to file with the C+- code
     * @param absRes expected result
     */
    private fun checker(path: String, absRes: List<TokenType>) {
        val lexer = Lexer(path)

        try {
            val methodRes = lexer.lexicalAnalysis()

            assertEquals(absRes.size, methodRes.size)
            for (i in methodRes.indices)
                assertEquals(methodRes[i].type::class, absRes[i]::class)
        } catch (error: Exception) {
            fail(error.message)
        }
    }

    /**
     * The test tests the work lexer using the simple example. The source
     * data is stored in file resources/simpleCode.txt
     */
    @Test
    fun `test lexical analysis`() {
        val path: String = getFilePath("simpleCode.txt")

        // The result that should be
        val absRes: List<TokenType> = listOf(
            Int32(), Identifier() // Add the following types
        )

        checker(path, absRes)
    }

    /**
     * The test tests the recognition of primitive types. The source
     * data is stored in file resources/primitiveTypes.txt
     */
    @Test
    fun `test recognition of primitive types`() {
        val path: String = getFilePath("primitiveTypes.txt")

        // The result that should be
        val absRes: List<TokenType> = listOf(
            Int32(), Identifier(), Assign(), IntNumber(), ExpEnd(), // First line of file
            Float32(), Identifier(), Assign(), FloatNumber(), ExpEnd() // Second
        )

        checker(path, absRes)
    }
}