package edu.nuist.qdb.exception;

public class NullBlankException extends Exception {
    public NullBlankException() {}

    public NullBlankException(String msg) {
        super("错误，题目" + msg + "没有可填的kong");
    }
}
