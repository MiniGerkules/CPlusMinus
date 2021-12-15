package parser.ast

import tokens.Token

/**
 * The root of the AST. Also, the node is described an AST node that is
 * responsible for main function
 *
 * @nodes children nodes
 */
class MainFunNode: ASTNode() {
    val nodes: MutableList<ASTNode> = mutableListOf()

    /**
     * The method adds the [nextNode] in [nodes]
     *
     * @param nextNode the node to add in list of nodes
     */
    fun addNode(nextNode: ASTNode) {
        nodes.add(nextNode)
    }
}

/**
 * The class defines the node of the AST that describes binary operations
 */
class BinaryOperationNode(val operation: Token, val leftOperand: ASTNode,
                          val rigthOperand: ASTNode): ASTNode() {
}

/**
 * The class defines the node of the AST that describes unary operations
 */
class UnaryOperatorNode(val operator: Token, val operand: ASTNode): ASTNode() {
}
