package byteCode

import parser.ast.*
import tokens.*

/**
 * The class generates valid Java code using AST
 *
 * @property listOfVariables the list contains the all variables in the program
 * @property javaCode Java code equivalent to AST
 */
class JavaCodeGenerator {
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
        javaCode.append("System.out.print(\"\\n\\n\");")
        for (variable in listOfVariables) {
            val codeToAdd = "System.out.print(\"The variable ${variable.variable.text} has type " +
                    "${variable.type.text} and value \");\nSystem.out.println(${variable.variable.text});\n"
            javaCode.append(codeToAdd)
        }
    }

    /**
     * The method generates Java code by AST
     *
     * @param root AST root node
     */
    fun generate(root: MainFunNode) {
        javaCode.append("{\n")
        recursiveGenerate(root)
        printAllVariables()
        javaCode.append('}')
    }

    /**
     * The method processes an AST node if it is a NumberNode
     */
    private fun numberNodeGen(node: NumberNode) {
        javaCode.append(node.number.text)
    }

    /**
     * The method processes an AST node if it is a VariableNode
     */
    private fun variableNodeGen(node: VariableNode) {
        if (!listOfVariables.any { it.variable.text == node.variable.text }) {
            when (node.type.type) {
                is Int32 -> javaCode.append("int ")
                is Float32 -> javaCode.append("float ")
            }
            listOfVariables.add(node)
        }

        javaCode.append(node.variable.text)
    }

    /**
     * The method processes an AST node if it is a VariableNode, and its type is Print
     */
    private fun printGen(node: ASTNode) {
        javaCode.append("System.out.print(")
        if (node is StringNode)
            javaCode.append(node.string.text)
        else
            recursiveGenerate(node)
        javaCode.append(");")
    }

    /**
     * The method processes an AST node if it is a UnaryOperatorNode
     */
    private fun unaryOperatorNodeGen(node: UnaryOperatorNode) {
        when (node.operator.type) {
            is Plus -> recursiveGenerate(node.operand)
            is Minus -> {
                javaCode.append("-")
                recursiveGenerate(node.operand)
            }
            is Print -> printGen(node.operand)
            else -> error("Something happened to the tree! Unable to generate bytecode!")
        }
    }

    /**
     * The method processes an AST node if it is a BinaryOperationNode
     */
    private fun binaryOperatorNodeGen(node: BinaryOperationNode) {
        recursiveGenerate(node.leftOperand)
        javaCode.append(" ${node.operator.text} ")
        recursiveGenerate(node.rigthOperand)
    }

    /**
     * The method processes an AST node if it is a MainFunNode
     */
    private fun mainFunNodeGen(node: MainFunNode) {
        for (elem in node.getNodes()) {
            recursiveGenerate(elem)
            javaCode.append(";\n")
        }
    }

    /**
     * Recursively generating Java code to be converted to bytecode
     *
     * @param node the current node
     */
    private fun recursiveGenerate(node: ASTNode) {
        when (node) {
            is NumberNode -> numberNodeGen(node)
            is VariableNode -> variableNodeGen(node)
            is UnaryOperatorNode -> unaryOperatorNodeGen(node)
            is BinaryOperationNode -> binaryOperatorNodeGen(node)
            is MainFunNode -> mainFunNodeGen(node)
            else -> error("Something happened to the tree! Unable to generate bytecode!")
        }
    }
}