package com.fd.common.exception;

public class CodeException extends RuntimeException {
    public CodeException(String message) {
        super(message);
    }
    //

    /**
     * 包裹某个异常的同时不要丢弃它原本的信息
     * 否则你将会丢失堆栈信息和原异常的消息，这将会令异常分析变得什么的困难
     * @param message
     * @param exception
     */
    public CodeException(String message,Exception exception) {
        super(message,exception);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
