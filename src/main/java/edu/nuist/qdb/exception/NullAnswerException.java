package edu.nuist.qdb.exception;

public class NullAnswerException extends Exception {
    public NullAnswerException() {}

    public NullAnswerException(String msg) {
        super("错误，题目" + msg + "答案为空");
    }
}
