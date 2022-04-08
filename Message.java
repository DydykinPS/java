
package com.mycompany.timer;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Pavel
 */
public class Message implements Serializable {
    ArrayList<String> msg = new ArrayList<>();
    private int hour, min, sec;
    int id;
    String description;
    int fl = 0;
    
    Message(){
        hour = 0;
        min = 0;
        sec = 0;
        id = -1;
        description = "";
    }

    Message(int _h, int _m, int _s, String s, int _id){
        hour = _h;
        min = _m;
        sec = _s;
        description = s;
        id = _id;
    }

    public ArrayList<String> getMsg() {
        return msg;
    }
    
    public void setDescription(String description) {
        this.description = description;
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

    public void setId(int id) {
        this.id = id;
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

    public int getId() {
        return id;
    }
    
    public String getDescription() {
        return description;
    }

    public int getFl() {
        return fl;
    }

    public void setFl(int fl) {
        this.fl = fl;
    }
    
    @Override
    public String toString() {
        if(id == 0) {
           return "Часы:  " + hour + "  Минуты:  " + min + "  Секунды:  " + sec;
        } else if (id == 1){
        return "Событие " + "hour=" + hour + ", min=" + min + ", sec=" + sec + " "+ description;
        } else return "";
    }

    public boolean equal(Message obj) {
        if (obj == null) {
            return false;
        } else {
            return this.description == obj.description && this.hour == obj.hour &&
                    this.id == obj.id && this.min == obj.min && this.sec == obj.sec;
        }
    }
    
    
   
}
