package model;

import util.CalendarTransfer;

import java.util.Calendar;

public class Book {
    private int id;
    private String name;
    private  int quantity;
    private BookCategory category;
    private String condition;
    private int price;

    public Book(int id, String name, int quantity, BookCategory category, String condition, int price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.category = category;
        this.condition = condition;
        this.price=price;
    }

    public Book() {
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BookCategory getCategory() {
        return category;
    }

    public void setCategory(BookCategory category) {
        this.category = category;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return id+","+name+","+quantity+","+category+","+condition+","+price;
    }
}
