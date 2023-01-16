package ru.yandex.practicum.filmorate.exception;

/**
 * Исключение, выбрасываемое при непрохождении валидации
 */
public class ValidationException extends Exception {

    public ValidationException(final String message) {
        super(message);
    }
}
