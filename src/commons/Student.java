package commons;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class Student implements Serializable {
    
    private final String name;
    private String course;
    private String section;
    private int penalty;
    private final String studentID;
    private char[] passkey;
    
    private List<Book> borrowed_books;
    private Map<Book, BorrowAndDue> borrow_and_due;
    private Set<Book> recordedAsOverdueBookSet;
    
    /*
    Constructor
    */
    
    public Student(String name, String course, String section,
            String studentID, char[] passkey) {
        this.name = name;
        this.course = course;
        this.section = section;
        this.studentID = studentID;
        this.passkey = passkey;
        this.borrowed_books = new ArrayList<>();
        this.borrow_and_due = new HashMap<>();
        this.penalty = 0;
        this.recordedAsOverdueBookSet = new HashSet<>();
    }
    
    /*
    Setters/Mutators
    */
    
    public void setCourse(String course) {
        this.course = course;
    }
    
    public void setSection(String section) {
        this.section = section;
    }
    
    public void setPassKey(char[] passkey) {
        this.passkey = passkey;
    }
    
    /*
    Getters/Accessors
    */
    
    public String getName() {
        return name;
    }
    
    public String getCourse() {
        return course;
    }
    
    public String getSection() {
        return section;
    }
    
    public String getStudentID() {
        return studentID;
    }
    
    public char[] getPassKey() {
        return passkey;
    }
    
    public int getPenaltyCount() {
        return penalty;
    }
    
    public List<Book> getBorrowedBooks() {
        return borrowed_books;
    }
    
    public Set<Book> getRecordedOverdueBooks() {
        return recordedAsOverdueBookSet;
    }
    
    /*
    this method resets the penalty count of this student
    */
    
    public void resetPenalty() {
        this.penalty = 0;
    }
    
    /*
    Method for adding to penalty counter. Note that penalty can only increase
    */
    
    public void incrementPenalty() {
        this.penalty += 1;
    }
    
    /*
    Adding or Removing an item from List<Book> borrowed_books
    */
    
    public void addBorrowedBook(Book b, BorrowAndDue bd) {
        borrowed_books.add(b);
        borrow_and_due.put(b, bd);
    }
    
    public void returnBorrowedBook(Book b) {
        borrowed_books.remove(b);
        borrow_and_due.remove(b);
    }
    
    /*
    Checking if a particular book is already borrowed
    */
    
    public boolean isBorrowed(Book b) {
        return borrowed_books.contains(b);
    }
    
    /*
    Accessing the borrowed_date and due_date of a book
    */
    
    public BorrowAndDue getBorrowAndDueInfo(Book b) {
        return borrow_and_due.get(b);
    }
    
    /*
    - this method updates the penalty (if any) of this student
    - the stream of keyset of borrow_and_due map is filtered if the book is overdue
    - after the stream has been collected as a List<Book>, incrementPenalty() will be 
    called only if the overdue book is not yet added to 
    the Set<String> recordedAsOverdueBookSet
    */
    
    public void updatePenalty() {
        List<Book> tmp = borrow_and_due.keySet().stream().filter(b -> borrow_and_due.get(b).getDueDate()
                .compareTo(LocalDate.now()) < 0
        ).collect(Collectors.toList());
        
        tmp.forEach(b -> {
           if (recordedAsOverdueBookSet.add(b)) {
               this.incrementPenalty();
           } 
        });
    }
    
    /*
    static class that holds information about:
    - date of when a book was borrowed
    - due date of the book
    */
    
    public static class BorrowAndDue implements Serializable {
        private final LocalDate borrowed_date;
        private final LocalDate due_date;
        
        /*
        Constructor
        */
        
        public BorrowAndDue(LocalDate borrowed_date, LocalDate due_date) {
            this.borrowed_date = borrowed_date;
            this.due_date = due_date;
        }
        
        /*
        Getters/Accessors
        */
        
        public LocalDate getBorrowedDate() {
            return borrowed_date;
        }
        
        public LocalDate getDueDate() {
            return due_date;
        }
    }
    
    
}
