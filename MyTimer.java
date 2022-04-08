/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.timer;

/**
 *
 * @author Pavel
 */
public class MyTimer {
    private int hour, min, sec;
    
    MyTimer(){
        hour = 0;   
        min = 0;                        
        sec = 0;
    }

    MyTimer(int _h, int _m, int _s){
        hour = _h;
        min = _m;
        sec = _s;
    }
    
    void Tick(){
        sec++;
        if(sec == 60) {
            sec = 0;
            min++;
        }
        if(min == 60) {
            min = 0;
            hour++;
        }
    }
    
    public int getHour() {
        return hour;
    }

    public int getMin() {
        return min;
    }

    public int getSec() {
        return sec;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setSec(int sec) {
        this.sec = sec;
    }
   
}
