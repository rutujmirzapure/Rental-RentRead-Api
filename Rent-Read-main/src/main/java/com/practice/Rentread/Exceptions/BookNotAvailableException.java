package com.practice.Rentread.Exceptions;

public class BookNotAvailableException extends RuntimeException{

    public BookNotAvailableException(){
        super();
    }

    public BookNotAvailableException(String msg){
        super(msg);
    }
}
