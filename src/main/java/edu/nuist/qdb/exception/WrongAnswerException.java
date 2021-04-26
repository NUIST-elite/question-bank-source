package edu.nuist.qdb.exception;

public class WrongAnswerException extends Exception {
    public WrongAnswerException() {}

    public WrongAnswerException(String msg) {
        super("错误，题目" + msg);
    }
}
