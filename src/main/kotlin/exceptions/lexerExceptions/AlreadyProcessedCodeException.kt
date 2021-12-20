package exceptions.lexerExceptions

/**
 * The exception that occurs when trying to re-process the processed code
 */
class AlreadyProcessedCodeException(message: String) : Exception(message)