package parser.ast

/**
 * The base class for all AST nodes
 *
 * @property nodes the list of child nodes
 */
abstract class ExpressionNode: ASTNode() {
    protected abstract val nodes: MutableList<ASTNode>

    /**
     * The method adds the [nextNode] in [nodes]
     *
     * @param nextNode the node to add in list of nodes
     */
    fun addNode(nextNode: ASTNode) {
        nodes.add(nextNode)
    }
}