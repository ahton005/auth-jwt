package ru.zyablov.t1.authjwt.exception;

public class UserExistException extends Throwable {
    public UserExistException(String msg) {
        super(msg);
    }
}
