package exceptions.lexerExceptions

/**
 * The exception that is thrown when the lexer cannot recognize the language token
 *
 * @param message exception informational message
 */
class UnableToRecognizeTokenException(message: String) : LexerException(message)
