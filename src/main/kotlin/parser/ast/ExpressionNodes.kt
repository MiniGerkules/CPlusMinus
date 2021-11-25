package parser.ast

import tokens.Token
import java.util.*

/**
 * The root of the AST. Also, the node is described an AST node that is
 * responsible for main function
 */
class MainFunNode: ExpressionNode() {
    override val nodes: MutableList<ASTNode> = mutableListOf()
}
