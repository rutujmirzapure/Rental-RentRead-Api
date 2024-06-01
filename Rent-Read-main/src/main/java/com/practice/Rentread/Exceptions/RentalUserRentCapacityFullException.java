package com.practice.Rentread.Exceptions;

public class RentalUserRentCapacityFullException extends  RuntimeException{

    public RentalUserRentCapacityFullException(){
        super();
    }

    public RentalUserRentCapacityFullException(String msg){
        super(msg);
    }
}
