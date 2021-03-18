package com.app.potholewarning.Models;

import com.google.firebase.FirebaseApp;

import java.util.Date;

public class Route {
    private  String name;
    private  String description;
    private  String id;
    private long pointCount;
    private Date createdAt;
    private  Date updatedAt;
    public Route(){
        name="";
        description="";
        createdAt= null;
        updatedAt= null;
        id=null;
        pointCount=0;
    }

    public Route(String name, String description, Date createdAt, Date updatedAt) {
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        id=null;
        pointCount=0;
    }

    public Route(String name, String description, Date createdAt) {
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = null;
        id=null;
        pointCount=0;
    }

    public long getPointCount() {
        return pointCount;
    }

    public void setPointCount(long pointCount) {
        this.pointCount = pointCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
