package exceptions.parserExceptions

/**
 * The exception thrown when required token wasn't found
 *
 * @param message exception informational message
 */
class RequiredTokenNotFoundException(message: String): ParserException(message)