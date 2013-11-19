package com.example.helloworld.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by geng.cheng on 13-10-15.
 */
public class Roadmap implements Serializable {

    private static final long serialVersionUID = -3653595313197466101L;
    private String            name;
    private String            time;
    private int		          uid;


    public int getUid(){
        return uid;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }
    public String getName(){
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getTime(){
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
