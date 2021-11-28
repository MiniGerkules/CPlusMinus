package parser.ast

import tokens.Token

/**
 * The class describing an AST node that is responsible for numbers
 *
 * @property number the token of type "Number"
 */
class NumberNode(val number: Token): ASTNode() {
}

/**
 * The class describing an AST node that is responsible for variables
 *
 * @property type the type of [variable]
 * @property variable the token of type "Variable"
 */
class VariableNode(val type: Token, val variable: Token): ASTNode() {
}
