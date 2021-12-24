package exceptions.parserExceptions

/**
 * The exception thrown when trying to handle non-existent tokens
 *
 * @param message exception informational message
 */
class AllTokensProcessedException(message: String): ParserException(message)
