package com.example.ProductService.model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@ApiModel(value = "Product Api model documentation", description = "Model")
@Entity
@Table(name = "products")
public class Product {
    @ApiModelProperty(value = "Unique id field of product object")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ApiModelProperty(value = "Product type")
    @Column(name = "type")
    private String type;

    @ApiModelProperty(value = "Product color")
    @Column(name = "color")
    private String color;

    @ApiModelProperty(value = "Product name")
    @Column(name = "name")
    private String name;

    @ApiModelProperty(value = "Product detail")
    @Column(name = "detail")
    private String detail;

    @ApiModelProperty(value = "Product create date")
    @Column(name = "createDate")
    private Date createDate;

    @ApiModelProperty(value = "Product update date")
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
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strCreateDate = dateFormat.format(createDate);
        String strUpdateDate = dateFormat.format(updateDate);

        return "Product [id=" + id + ", name=" + name + ", detail=" + detail
                + ", type=" + type + ", color="  + color + ", createDate=" + strCreateDate
                + ", updateDate=" + strUpdateDate + "]";
    }

}
