package ru.yandex.praktikum.project.exceptions;

import java.io.IOException;

public class SaveException extends RuntimeException {
    public SaveException(String message) {
        super(message);
    }
}
