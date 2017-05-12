/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.utils;

/**
 *
 * @author PNattawut
 */
/**
 * ****************************************************************************
 ** 1.- 'Result' return a value holding the result after a method is operated
 * which contain 'isSuccess' value that implied the operation is success or not
 * 'message' value can specify for the message after the operation is done.
 * 'excp' value contained the exception object if exception was occurred. 'obj'
 * value is the Object class contained the result value that up to which type of
 * object has returned from method (to implement the 'obj' as usable value need
 * to perform 'Casting'. purpose of 'obj' is for gathering object result and
 * opertaion result and so on.. 
 *****************************************************************************
 */
public class Result {

    private boolean success;
    private String message;
    private Object obj;
    private Exception excp;

    public Result(boolean isSuccess, String message, Object obj, Exception excp) {
        this.success = isSuccess;
        this.message = message;
        this.obj = obj;
        this.excp = excp;
    }

    public Result(boolean isSuccess, String message, Object obj) {
        this.success = isSuccess;
        this.message = message;
        this.obj = obj;
    }

    public Result(boolean isSuccess, String message, Exception excp) {
        this.success = isSuccess;
        this.message = message;
        this.excp = excp;
    }

    public Result(boolean isSuccess, Exception excp) {
        this.success = isSuccess;
        this.excp = excp;
    }

    public Result(boolean isSuccess, String message) {
        this.success = isSuccess;
        this.message = message;
    }

    public Result(int executeUpdate, String update_Success) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Exception getExcp() {
        return excp;
    }

    public void setExcp(Exception excp) {
        this.excp = excp;
    }

    public String toReformedResult(){
        return "--------Result HEAD---------"+
                "\n\n Status : "+success+
                "\n\n Message : "+message+
                "\n\n Exception : "+excp+
                "\n\n Object Print : "+obj+
                "--------Result END---------";
    }
}
