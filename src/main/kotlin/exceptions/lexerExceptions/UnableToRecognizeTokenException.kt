package exceptions.lexerExceptions

/**
 *
 */
class UnableToRecognizeTokenException(message: String) : Exception(message) {
    constructor(): this("")
}