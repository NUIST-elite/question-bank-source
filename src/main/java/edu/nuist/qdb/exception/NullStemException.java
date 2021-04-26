package edu.nuist.qdb.exception;

/**
 * 题干为空异常
 */
public class NullStemException extends Exception {
    public NullStemException() {}

    public NullStemException(String msg) {
        super("错误，题目" + msg + "题干为空");
    }

}
