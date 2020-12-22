package commons;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Book implements Serializable {
    
    private String title;
    private String author;
    private String category;
    private int total_stock;
    private int available;
    
    private List<Student> who_borrowed;
    
    /*
    Constructor
    */
    
    public Book(String title, String author, String category, int total_stock) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.total_stock = total_stock;
        this.available = total_stock;
        this.who_borrowed = new ArrayList<>();
    }

    /*
    Setters/Mutators
    */
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public void setTotalStock(int total_stock) {
        this.total_stock = total_stock;
    }
    
    public void setAvailable(int available) {
        this.available = available;
    }
    
    /*
    Adding the student to who_borrowed List<Student>
    */
    
    public void addStudentWhoBorrowed(Student st) {
        this.who_borrowed.add(st);
    }
    
    
    /*
    Getters/Accessors
    */
    
    public String getTitle() {
        return title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public String getCategory() {
        return category;
    }
    
    public int getTotalStock() {
        return total_stock;
    }
    
    public int getAvailable() {
        return available;
    }
    
    public List<Student> getWhoBorrowed() {
        return who_borrowed;
    }
    
}
