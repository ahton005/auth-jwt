package ru.zyablov.t1.authjwt.exception;

public class AuthorityNotFoundException extends Throwable {
    public AuthorityNotFoundException(String msg) {
        super(msg);
    }
}
