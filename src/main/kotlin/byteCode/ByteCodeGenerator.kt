package byteCode

import javassist.ClassPool
import javassist.CtNewMethod
import parser.ast.*
import tokens.Float32
import tokens.Int32
import tokens.Print

/**
 * The class generates bytecode for the JVM on the abstract syntax tree.
 * The tree is built for the source code for the C+- language
 *
 * @property rootNode the root of the AST
 * @property codeForJavaAssist the code of main method to be converted to bytecode
 * @property listOfVariables the list contains the all variables in the program
 */
class ByteCodeGenerator(private val rootNode: MainFunNode) {
    private val codeForJavaAssist: StringBuilder = StringBuilder()
    private val listOfVariables: MutableList<VariableNode> = mutableListOf()

    /**
     * The method starts bytecode generation
     */
    fun generateByteCode() {
        val cp = ClassPool.getDefault()
        val program = cp.makeClass("Main")
        val main = CtNewMethod.make("public static void main(String[] args) { }", program)
        program.addMethod(main)

        codeForJavaAssist.append("{\n")
        recursiveGenerate(rootNode)
        printAllVariables()
        codeForJavaAssist.append('}')

//        println("\n\nGenerated code:")
//        println(codeForJavaAssist.toString())
        main.setBody(codeForJavaAssist.toString())
        program.writeFile("/home/eugene/Рабочий стол")
    }

    /**
     * The method that generates code to display the values of all variables in a program
     */
    private fun printAllVariables() {
        for (variable in listOfVariables) {
            val codeToAdd = "System.out.print(\"The variable ${variable.variable.text} has type " +
                    "${variable.type.text} and value \");\nSystem.out.println(${variable.variable.text});\n"
            codeForJavaAssist.append(codeToAdd)
        }
    }

    /**
     * Recursively generating Java code to be converted to bytecode
     *
     * @param node the current node
     */
    private fun recursiveGenerate(node: ASTNode) {
        when (node) {
            is NumberNode -> codeForJavaAssist.append(node.number.text)
            is VariableNode -> {
                if (!listOfVariables.any { it.variable.text == node.variable.text }) {
                    when (node.type.type) {
                        is Int32 -> codeForJavaAssist.append("int ")
                        is Float32 -> codeForJavaAssist.append("float ")
                    }
                    listOfVariables.add(node)
                }

                codeForJavaAssist.append(node.variable.text)
            }
            is UnaryOperatorNode -> {
                when (node.operator.type) {
                    is Print -> {
                        codeForJavaAssist.append("System.out.println(")
                        recursiveGenerate(node.operand)
                        codeForJavaAssist.append(");")
                    }
                    else -> error("Something happened to the tree! Unable to generate bytecode!")
                }
            }
            is BinaryOperationNode -> {
                recursiveGenerate(node.leftOperand)
                codeForJavaAssist.append(" ${node.operation.text} ")
                recursiveGenerate(node.rigthOperand)
            }
            is MainFunNode -> {
                for (elem in rootNode.getNodes()) {
                    recursiveGenerate(elem)
                    codeForJavaAssist.append(";\n")
                }
            }
            else -> error("Something happened to the tree! Unable to generate bytecode!")
        }
    }
}