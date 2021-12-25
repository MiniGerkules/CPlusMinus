package byteCode

import parser.ast.*
import tokens.Float32
import tokens.Int32
import tokens.Print

/**
 * The class generates valid Java code using AST
 *
 * @property root AST root node
 * @property listOfVariables the list contains the all variables in the program
 * @property javaCode Java code equivalent to AST
 */
class JavaCodeGenerator(private val root: MainFunNode) {
    private val listOfVariables: MutableList<VariableNode> = mutableListOf()
    private val javaCode = StringBuilder()

    /**
     * The method returns the generated Java code
     *
     * @return generated Java code
     */
    fun getCode(): String = javaCode.toString()

    /**
     * The method that generates code to display the values of all variables in a program
     */
    private fun printAllVariables() {
        for (variable in listOfVariables) {
            val codeToAdd = "System.out.print(\"The variable ${variable.variable.text} has type " +
                    "${variable.type.text} and value \");\nSystem.out.println(${variable.variable.text});\n"
            javaCode.append(codeToAdd)
        }
    }

    /**
     * The method generates Java code by AST
     */
    fun generate() {
        javaCode.append("{\n")
        recursiveGenerate(root)
        printAllVariables()
        javaCode.append('}')
    }

    /**
     * Recursively generating Java code to be converted to bytecode
     *
     * @param node the current node
     */
    private fun recursiveGenerate(node: ASTNode) {
        when (node) {
            is NumberNode -> javaCode.append(node.number.text)
            is VariableNode -> {
                if (!listOfVariables.any { it.variable.text == node.variable.text }) {
                    when (node.type.type) {
                        is Int32 -> javaCode.append("int ")
                        is Float32 -> javaCode.append("float ")
                    }
                    listOfVariables.add(node)
                }

                javaCode.append(node.variable.text)
            }
            is UnaryOperatorNode -> {
                when (node.operator.type) {
                    is Print -> {
                        javaCode.append("System.out.println(")
                        recursiveGenerate(node.operand)
                        javaCode.append(");")
                    }
                    else -> error("Something happened to the tree! Unable to generate bytecode!")
                }
            }
            is BinaryOperationNode -> {
                recursiveGenerate(node.leftOperand)
                javaCode.append(" ${node.operation.text} ")
                recursiveGenerate(node.rigthOperand)
            }
            is MainFunNode -> {
                for (elem in root.getNodes()) {
                    recursiveGenerate(elem)
                    javaCode.append(";\n")
                }
            }
            else -> error("Something happened to the tree! Unable to generate bytecode!")
        }
    }
}