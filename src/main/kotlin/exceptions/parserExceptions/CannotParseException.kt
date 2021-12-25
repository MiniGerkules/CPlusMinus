package exceptions.parserExceptions

/**
 * The exception thrown if the expression could not be parsed
 *
 * @param message exception informational message
 */
class CannotParseException (message: String): ParserException(message)