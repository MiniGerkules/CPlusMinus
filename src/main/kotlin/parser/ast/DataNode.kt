package parser.ast

import tokens.Token

/**
 * The class that describes the node with values (variables, numbers, etc.)
 *
 * @property value the token that contains value (variable or primitive type)
 */
abstract class DataNode: ASTNode() {
    protected abstract val value: Token
}
