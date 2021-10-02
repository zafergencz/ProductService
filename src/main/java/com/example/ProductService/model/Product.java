package com.example.ProductService.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "type")
    private String type;

    @Column(name = "color")
    private String color;

    @Column(name = "name")
    private String name;

    @Column(name = "detail")
    private String detail;

    @Column(name = "createDate")
    private Date createDate;

    @Column(name = "updateDate")
    private Date updateDate;

    public Product(){

    }

    public Product(String name, String detail, String type, String color, Date createDate, Date updateDate) {
        this.name = name;
        this.detail = detail;
        this.type = type;
        this.color = color;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", detail=" + detail
                + ", type=" + type + ", color="  + color + ", createDate=" + createDate.toString()
                + ", updateDate=" + updateDate.toString() + "]";
    }

}
