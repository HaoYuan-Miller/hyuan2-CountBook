package com.countbook.hyuan2.hyuan2_countbook;

import java.util.Date;

/**
 * Created by hyuan2 on 9/27/17.
 */

public class counter {
    private String itemName;
    private Date date;
    private int currentValue;
    private int initialValue;
    private String comment;

    public counter(String itemName,int initialValue) throws NegativeValueException {
        if (initialValue >= 0 ) {
            this.itemName = itemName;
            this.date = new Date();
            this.initialValue = initialValue;
            this.currentValue = initialValue;
        }
        else {
            throw new NegativeValueException();
        }
    }

    public counter(String itemName,int initialValue,String comment) throws NegativeValueException{
        if (initialValue >= 0) {
            this.itemName = itemName;
            this.date = new Date();
            this.initialValue = initialValue;
            this.currentValue = initialValue;
            this.comment = comment;
        }
        else {
            throw new NegativeValueException();
        }
    }

    public void incrementCounter(){
        ++this.currentValue;
        this.date = new Date();
    }

    public void decrementCounter() throws NegativeValueException{
        if (this.currentValue >= 0){
            --this.currentValue;
            this.date = new Date();
        }
        else {
            throw new NegativeValueException();
        }
    }

    public void resetCurrentValue(){
        this.currentValue = this.initialValue;
        this.date = new Date();
    }

    public void setItemName(String itemName){
        this.itemName = itemName;
    }

    public void setInitialValue(int initialValue) throws NegativeValueException{
        if (initialValue >= 0){
            this.initialValue = initialValue;
        }
        else{
            throw new NegativeValueException();
        }
    }

    public void setCurrentValue(int currentValue) throws NegativeValueException{
        if (currentValue >= 0){
            this.currentValue = currentValue;
        }
        else{
            throw new NegativeValueException();
        }
    }

    public void setComment(String comment){
        this.comment = comment;
    }
}
