package parser.ast

import tokens.Token

/**
 * The class describing an AST node that is responsible for numbers
 *
 * @property value the token of type "Number"
 */
class NumberNode(override val value: Token): DataNode() {}

/**
 * The class describing an AST node that is responsible for variables
 *
 * @property value the token of type "Variable"
 */
class VariableNode(override val value: Token): DataNode() {}
