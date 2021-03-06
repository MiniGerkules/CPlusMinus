package byteCode

import javassist.ClassPool
import javassist.CtClass
import javassist.CtNewMethod
import parser.ast.*

/**
 * The class generates bytecode for the JVM on the abstract syntax tree.
 * The tree is built for the source code for the C+- language
 *
 * @property program JavaAssist library class that stores all generated bytecode
 */
class CodeGenerator {
    private val program: CtClass
    init {
        val cp = ClassPool.getDefault()
        program = cp.makeClass("Main")
        val main = CtNewMethod.make("public static void main(String[] args) { }", program)
        program.addMethod(main)
    }

    /**
     * The method returns the created class, which is ready to run in the JVM
     *
     * @return a class containing the main method and the code of the entire program based on AST
     */
    fun getProgram(): CtClass = program

    /**
     * The method starts bytecode generation
     *
     * @param root the root of the AST
     */
    fun makeProgram(root: MainFunNode) {
        val javaGenerator = JavaCodeGenerator()
        javaGenerator.generate(root)

        val main = program.getMethod("main", "([Ljava/lang/String;)V")
        main.setBody(javaGenerator.getCode())
    }
}