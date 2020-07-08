package com.fd.userservice.support.enums;

public enum WeekEnums {

    MONDAY(1),TWO(2);

    WeekEnums(int index){
        this.index = index;
    }
    private int index;

    public int getIndex() {
        return index;
    }
}
