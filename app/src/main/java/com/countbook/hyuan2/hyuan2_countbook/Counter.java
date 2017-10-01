/*
 * Counter
 *
 * Version 1.0
 *
 * September 30, 2017
 *
 * Copyright (c) 2017 Hao Yuan, CMPUT301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact hyuan2@ualberta.ca
 */

package com.countbook.hyuan2.hyuan2_countbook;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a Counter
 * @author Hao Yuan
 * @version 1.0
 * @since 1.0
 * Created by hyuan2 on 9/27/17.
 */

public class Counter {
    private String itemName;    /* attribute for counter's name */
    private Date date;          /* attribute for counter's date */
    private int currentValue; /* attribute for counter's current value */
    private int initialValue; /* attribute for counter's initial value */
    private String comment;     /* attribute for counter's comment */


    /**
     * Construct a counter object
     * @param itemName
     * @param initialValue
     * @param comment comment can be ""
     * @throws NegativeValueException refuse negative values
     */
    public Counter(String itemName,int initialValue,String comment) throws NegativeValueException{
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

    /**
     * a method that increments current value by 1
     */
    public void incrementCounter(){
        ++this.currentValue;
        this.date = new Date();
    }
    /**
     * a method that decrements current value by 1 if current value > 0
     */
    public void decrementCounter() throws NegativeValueException{
        if (this.currentValue > 0){
            --this.currentValue;
            this.date = new Date();
        }
        else {
            throw new NegativeValueException();
        }
    }

    /**
     * a method that resets current value to initial value
     */
    public void resetCurrentValue(){
        this.currentValue = this.initialValue;
        this.date = new Date();
    }

    /**
     * setters below
     */
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

    /**
     * getters below returns a string representing each attribute
     */
    public String getItemName(){return "Name: "+this.itemName;}
    public String getCurrentValue(){return "Current value: "+Integer.toString(currentValue);}
    public String getInitialValue() {return "Initial value: "+Integer.toString(initialValue);}
    public String getComment() {return "Comment: "+this.comment;}
    public String getDate(){return "Date: " + new SimpleDateFormat("yyyy-MM-dd").format(this.date);}

    /**
     * Override toString method to return minimal information(name,date and current value)
     * @return Name+Date+Current value
     */
    @Override
    public String toString(){
        return this.getItemName() +"\n" +this.getDate()+"\n"+this.getCurrentValue();
    }
}
