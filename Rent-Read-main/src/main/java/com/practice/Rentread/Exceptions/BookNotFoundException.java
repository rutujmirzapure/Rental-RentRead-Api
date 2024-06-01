package com.practice.Rentread.Exceptions;

public class BookNotFoundException extends RuntimeException{

    public BookNotFoundException(){
        super();
    }

    public BookNotFoundException(String msg){
        super(msg);
    }
}

