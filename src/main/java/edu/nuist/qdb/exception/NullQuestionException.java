package edu.nuist.qdb.exception;

/**
 * 题目为空异常
 */
public class NullQuestionException extends Exception {
    public NullQuestionException() {}

    public NullQuestionException(String msg) {
        super(msg + "题目为空");
    }
}
