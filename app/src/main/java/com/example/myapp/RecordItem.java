package com.example.myapp;

public class RecordItem {
    private int id;
    private int kind;
    private float money;
    private String detail;
    private String date;
    private float out_sum;
    private float in_sum;

    public  RecordItem() {
        this.kind=0;
        this.money=0;
        this.detail="";
        this.date="";
        //this.out_sum=0;
       // this.in_sum=0;

    }

    public RecordItem(int kind, float money,String detail,String date){
        this.kind=kind;
        this.money=money;
        this.detail=detail;
        this.date=date;
        //this.out_sum=0;
        //this.in_sum=0;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
