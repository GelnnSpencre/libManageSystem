package commons;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class PseudoDatabase implements Serializable {
    
    /*
    - List<Student> all_students will contain all registered Students in the 
    library management system
    */
    
    private List<Student> all_students;
    
    /*
    - List<Admin> all_admins will contain all registered Admins in the library
    management system
    */
    
    private List<Admin> all_admins;
    
    /*
    - List<Book> all_books will contain all the books inside the library
    management system
    */
    
    private List<Book> all_books;
    
    /*
    - List<String> categories will contain all unique book categories
    */
    
    private List<String> categories;
    
    /*
    - Map<String, Studen> student_login_map will contain the mapping 
    for every StudentID and Student Object
    */
    
    private Map<String, Student> student_login_map;
    
    /*
    - Map<String, Admin> admin_login_map will contain the mapping for
    every AdminID and Admin Object
    */
    
    private Map<String, Admin> admin_login_map;
    
    /*
    Constructor
    */
    
    public PseudoDatabase() {
        all_books = new ArrayList<>();
        categories = new ArrayList<>();
        student_login_map = new HashMap<>();
        all_students = new ArrayList<>();
        all_admins = new ArrayList<>();
        admin_login_map = new HashMap<>();
    }
    
    /*
    - method for adding a book into List<Book> all_books
    - the given book will only be added if its unique
    - the category of the book will be checked if its unique, if true,
    it will be added to List<String> categories
    */
    
    public void addBook(Book b) {
        if (!containsBook(b)) {
            all_books.add(b);
        }
        for (String c : b.getCategory().trim().toUpperCase().split(",")) {
            if (!categories.contains(c.trim())) {
                categories.add(c.trim());
            }
        }
    }
    
    /*
    method for removing a book from the List<Book> all_books
    */
    
    public void removeBook(Book b) {
        this.all_books.remove(b);
    }
    
    /*
    - method that checks if a given book already
    exists in List<Book> all_books
    */
    
    public boolean containsBook(Book b) {
        return all_books.contains(b);
    }
    
    /*
    - method for adding a new StudentID-StudentPassword mapping into
    Map<String, Character[]> student_login_map
    - the given Student will only be added if its StudentID is unique
    */
    
    public void registerNewStudent(Student st) {
        if (!studentIDExists(st.getStudentID())) {
            student_login_map.put(st.getStudentID(), st);
            all_students.add(st);
        }
    }
    
    /*
    method for removing a Student from Map<String, Character[]> student_login_map
    */
    
    public void removeStudent(Student st) {
        student_login_map.remove(st.getStudentID());
        all_students.remove(st);
    }
    
    /*
    method that verifies if a given studentID is unique
    */
    
    public boolean studentIDExists(String studentID) {
        return student_login_map.containsKey(studentID);
    }
    
    /*
    method that verifies if a given set of credentials is correct
    */
    
    public boolean verifyStudentLogIn(String studentID, char[] wrapperPasskey) {
        return Arrays.equals(student_login_map.get(studentID).getPassKey(), wrapperPasskey);
    }
    
    /*
    method that retrieves a Student object based on the studentID argument
    */
    
    public Student getStudent(String studentID) {
        return student_login_map.get(studentID);
    }
    
    /*
    - method for adding a new AdminID-AdminPassword mapping into
    Map<String, Character[]> admin_login_map
    - the given Admin will only be added if its AdminID is unique
    */
    
    public void registerNewAdmin(Admin adm) {
        if (!adminIDExists(adm.getAdminID())) {
            admin_login_map.put(adm.getAdminID(), adm);
            all_admins.add(adm);
        }
    }
    
    /*
    method for removing an Admin from Map<String, Character[]> admin_login_map
    */
    
    public void removeAdmin(Admin adm) {
        admin_login_map.remove(adm.getAdminID());
        all_admins.remove(adm);
    }
    
    /*
    method that verifies if a given adminID is unique
    */
    
    public boolean adminIDExists(String adminID) {
        return admin_login_map.containsKey(adminID);
    }
    
    /*
    method that verifies if a given set of credentials is correct
    */
    
    public boolean verifyAdminLogIn(String adminID, char[] wrapperPasskey) {
        return Arrays.equals(admin_login_map.get(adminID).getAdminPassKey(), wrapperPasskey);
    }
    
    /*
    method that retrieves an Admin object based on the AdminID argument
    */
    
    public Admin getAdmin(String adminID) {
        return admin_login_map.get(adminID);
    }
    
    /*
    Getters/Accessors
    */
    
    public List<Student> getAllStudents() {
        return all_students;
    }
    
    public List<Admin> getAllAdmins() {
        return all_admins;
    }
    
    public List<Book> getAllBooks() {
        return all_books;
    }
    
    public List<String> getAllCategories() {
        return categories;
    }
    
    public Map<String, Student> getStudentLoginMap() {
        return student_login_map;
    }
    
    public Map<String, Admin> getAdminLoginMap() {
        return admin_login_map;
    }
    
}
