package windows;

import commons.Admin;
import commons.Book;
import commons.PseudoDatabase;
import commons.Student;
import commons.Student.BorrowAndDue;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;
import javax.swing.Timer;
import javax.swing.SwingWorker;

public class StudentDashboard extends javax.swing.JFrame {

    private StartupForm startForm;
    private PseudoDatabase psDatabase;
    private java.awt.CardLayout cards;
    private Student loggedStudent;
    private Book selectedBook;
    private final Timer time;
    private final DateTimeFormatter timePattern = DateTimeFormatter.ofPattern("hh:mm:ss a");
    private final DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("E MMM d, uuuu");

    /**
     * Creates new form StudentDashboard
     */
    public StudentDashboard() {
        readPseudoDatabaseSer();
        //addDummyValue();
        initComponents();
        //initialization for bookJList of borrow books panel
            bookJList.setListData(psDatabase.getAllBooks().stream().map(b -> b.getTitle()).collect(Collectors.toList()).toArray(new String[0]));
            bookJList.addListSelectionListener((e) -> {
                if (!borrowSelectedBookBttn.isEnabled()) {
                    borrowSelectedBookBttn.setEnabled(true);
                }
                if (alreadyBorrowedLbl.isVisible()) {
                    alreadyBorrowedLbl.setVisible(false);
                }
                selectedBook = psDatabase.getAllBooks().stream()
                        .reduce(null, (sub, current) -> (current.getTitle().equals(bookJList.getSelectedValue())
                        ? current : sub));
                if (selectedBook != null) {
                    bookTitleValueTxtArea.setText(selectedBook.getTitle());
                    bookAuthorValueTxtArea.setText(selectedBook.getAuthor());
                    bookCategoryValueLbl.setText(selectedBook.getCategory());
                    bookAvailableValueLbl.setText((selectedBook.getAvailable() != 0) ? "Available" : "Not Available");
                    if (bookAvailableValueLbl.getText().equals("Not Available")) {
                        borrowSelectedBookBttn.setEnabled(false);
                    }
                } else {
                    searchBookTxtFld.setText("");
                    bookTitleValueTxtArea.setText("");
                    bookAuthorValueTxtArea.setText("");
                    bookCategoryValueLbl.setText("");
                    bookAvailableValueLbl.setText("");
                    borrowSelectedBookBttn.setEnabled(false);
                }
            });
            //initialization for returnBookJList
            returnBookJList.addListSelectionListener((e) -> {
                if (!returnSelectedBookBttn.isEnabled()) {
                    returnSelectedBookBttn.setEnabled(true);
                }
                selectedBook = psDatabase.getAllBooks().stream()
                        .reduce(null, (sub, current) -> (current.getTitle().equals(returnBookJList.getSelectedValue())
                        ? current : sub));
                if (selectedBook != null) {
                    returnBookTitleValueTxtArea.setText(selectedBook.getTitle());
                    returnBookAuthorValueTxtArea.setText(selectedBook.getAuthor());
                    returnBookCategoryValueLbl.setText(selectedBook.getCategory());
                    returnDateBorrowedValueLbl.setText(psDatabase.getStudent(loggedStudent.getStudentID()).getBorrowAndDueInfo(selectedBook).getBorrowedDate().toString());
                    returnDueDateValueLbl.setText(psDatabase.getStudent(loggedStudent.getStudentID()).getBorrowAndDueInfo(selectedBook).getDueDate().toString());
                    if (returnDueDateValueLbl.getText().compareTo(LocalDate.now().toString()) < 0) {
                        returnDueDateValueLbl.setForeground(java.awt.Color.RED);
                        returnDueDateValueLbl.setToolTipText("This book is overdue, kindly return it to the library Admin");
                    } else {
                        returnDueDateValueLbl.setForeground(java.awt.Color.BLACK);
                        returnDueDateValueLbl.setToolTipText("YYYY-MM-DD");
                    }
                } else {
                    returnSelectedBookBttn.setEnabled(false);
                    returnBookTitleValueTxtArea.setText("");
                    returnBookAuthorValueTxtArea.setText("");
                    returnBookCategoryValueLbl.setText("");
                    returnDateBorrowedValueLbl.setText("");
                    returnDueDateValueLbl.setText("");
                }
            });
        time = new Timer(1000, e -> {
            currentTimeValueLbl.setText(LocalTime.now().format(timePattern));
        });
        time.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInternalFrame1 = new javax.swing.JInternalFrame();
        studentDashboardTopPanel = new javax.swing.JPanel();
        studentDashboardImgLbl = new javax.swing.JLabel();
        studentNameLbl = new javax.swing.JLabel();
        studentidLbl = new javax.swing.JLabel();
        studentNameValueLbl = new javax.swing.JLabel();
        studentIDValueLbl = new javax.swing.JLabel();
        courseSectionLbl = new javax.swing.JLabel();
        courseSectionValueLbl = new javax.swing.JLabel();
        currentTimeValueLbl = new javax.swing.JLabel();
        currentDateValueLbl = new javax.swing.JLabel();
        studentDashboardBottomPanel = new javax.swing.JPanel();
        westPanel = new javax.swing.JPanel();
        borrowBookBttn = new javax.swing.JButton();
        returnBookBttn = new javax.swing.JButton();
        logOutBttn = new javax.swing.JButton();
        centerPanel = new javax.swing.JPanel();
        borrowPanel = new javax.swing.JPanel();
        searchBookTxtFld = new javax.swing.JTextField();
        searchBookLbl = new javax.swing.JLabel();
        bookListParentScrollPane = new javax.swing.JScrollPane();
        bookJList = new javax.swing.JList<>();
        borrowSelectedBookBttn = new javax.swing.JButton();
        resetBookListBttn = new javax.swing.JButton();
        bookCategoryValueLbl = new javax.swing.JLabel();
        bookTitleValueParentScrollPane = new javax.swing.JScrollPane();
        bookTitleValueTxtArea = new javax.swing.JTextArea();
        bookAuthorValueParentScrollPane = new javax.swing.JScrollPane();
        bookAuthorValueTxtArea = new javax.swing.JTextArea();
        bookAvailableValueLbl = new javax.swing.JLabel();
        alreadyBorrowedLbl = new javax.swing.JLabel();
        penaltyCountLbl = new javax.swing.JLabel();
        penaltyCountValueLbl = new javax.swing.JLabel();
        returnPanel = new javax.swing.JPanel();
        returnSearchBookTxtFld = new javax.swing.JTextField();
        returnBookAuthorValueParentScrollPane = new javax.swing.JScrollPane();
        returnBookAuthorValueTxtArea = new javax.swing.JTextArea();
        returnBookListParentScrollPane = new javax.swing.JScrollPane();
        returnBookJList = new javax.swing.JList<>();
        returnBookCategoryValueLbl = new javax.swing.JLabel();
        returnResetBookListBttn = new javax.swing.JButton();
        returnSearchBookLbl = new javax.swing.JLabel();
        returnBookTitleValueParentScrollPane = new javax.swing.JScrollPane();
        returnBookTitleValueTxtArea = new javax.swing.JTextArea();
        returnDateBorrowedValueLbl = new javax.swing.JLabel();
        returnSelectedBookBttn = new javax.swing.JButton();
        returnDueDateValueLbl = new javax.swing.JLabel();

        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new javax.swing.ImageIcon("C:\\Users\\Gelnn Spencre\\Documents\\NetBeansProjects\\LibraryManagementSystem\\src\\resources\\studentDashboardIcon.png").getImage()
        );
        setUndecorated(true);

        studentDashboardTopPanel.setBackground(new java.awt.Color(0, 153, 255));
        studentDashboardTopPanel.setPreferredSize(new java.awt.Dimension(1000, 200));
        studentDashboardTopPanel.setLayout(null);

        studentDashboardImgLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/studentDashboardIcon.png"))); // NOI18N
        studentDashboardImgLbl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        studentDashboardTopPanel.add(studentDashboardImgLbl);
        studentDashboardImgLbl.setBounds(66, 33, 130, 130);

        studentNameLbl.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        studentNameLbl.setText("Name:");
        studentNameLbl.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        studentNameLbl.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        studentDashboardTopPanel.add(studentNameLbl);
        studentNameLbl.setBounds(214, 27, 74, 32);

        studentidLbl.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        studentidLbl.setText("Student ID:");
        studentDashboardTopPanel.add(studentidLbl);
        studentidLbl.setBounds(214, 80, 129, 32);

        studentNameValueLbl.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        studentNameValueLbl.setText("First Second M. Surname");
        studentDashboardTopPanel.add(studentNameValueLbl);
        studentNameValueLbl.setBounds(298, 27, 529, 32);

        studentIDValueLbl.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        studentIDValueLbl.setText("1032654578954623");
        studentDashboardTopPanel.add(studentIDValueLbl);
        studentIDValueLbl.setBounds(353, 78, 474, 32);

        courseSectionLbl.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        courseSectionLbl.setText("Course & Section:");
        studentDashboardTopPanel.add(courseSectionLbl);
        courseSectionLbl.setBounds(214, 133, 204, 29);

        courseSectionValueLbl.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        courseSectionValueLbl.setText("COURSE-SECTION");
        studentDashboardTopPanel.add(courseSectionValueLbl);
        courseSectionValueLbl.setBounds(428, 131, 403, 32);

        currentTimeValueLbl.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        currentTimeValueLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        currentTimeValueLbl.setText(LocalTime.now().format(timePattern));
        studentDashboardTopPanel.add(currentTimeValueLbl);
        currentTimeValueLbl.setBounds(882, 80, 260, 36);

        currentDateValueLbl.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        currentDateValueLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        currentDateValueLbl.setText(LocalDate.now().format(datePattern));
        studentDashboardTopPanel.add(currentDateValueLbl);
        currentDateValueLbl.setBounds(820, 30, 330, 36);

        getContentPane().add(studentDashboardTopPanel, java.awt.BorderLayout.PAGE_START);

        studentDashboardBottomPanel.setBackground(java.awt.Color.lightGray);
        studentDashboardBottomPanel.setLayout(new java.awt.BorderLayout());

        westPanel.setPreferredSize(new java.awt.Dimension(250, 500));
        westPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 10));

        borrowBookBttn.setBackground(new java.awt.Color(0, 153, 255));
        borrowBookBttn.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        borrowBookBttn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/borrowBookIcon.png"))); // NOI18N
        borrowBookBttn.setText("BORROW BOOK");
        borrowBookBttn.setBorderPainted(false);
        borrowBookBttn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        borrowBookBttn.setFocusable(false);
        borrowBookBttn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        borrowBookBttn.setPreferredSize(new java.awt.Dimension(250, 50));
        borrowBookBttn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                borrowBookBttnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                borrowBookBttnMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                borrowBookBttnMousePressed(evt);
            }
        });
        borrowBookBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrowBookBttnActionPerformed(evt);
            }
        });
        westPanel.add(borrowBookBttn);

        returnBookBttn.setBackground(new java.awt.Color(0, 153, 255));
        returnBookBttn.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        returnBookBttn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/returnBookIcon.png"))); // NOI18N
        returnBookBttn.setText("RETURN BOOK");
        returnBookBttn.setBorderPainted(false);
        returnBookBttn.setContentAreaFilled(false);
        returnBookBttn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        returnBookBttn.setFocusable(false);
        returnBookBttn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        returnBookBttn.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        returnBookBttn.setPreferredSize(new java.awt.Dimension(250, 50));
        returnBookBttn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                returnBookBttnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                returnBookBttnMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                returnBookBttnMousePressed(evt);
            }
        });
        returnBookBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnBookBttnActionPerformed(evt);
            }
        });
        westPanel.add(returnBookBttn);

        logOutBttn.setBackground(new java.awt.Color(0, 153, 255));
        logOutBttn.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        logOutBttn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/logOutIcon.png"))); // NOI18N
        logOutBttn.setText("LOG OUT");
        logOutBttn.setBorderPainted(false);
        logOutBttn.setContentAreaFilled(false);
        logOutBttn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logOutBttn.setFocusable(false);
        logOutBttn.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        logOutBttn.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        logOutBttn.setPreferredSize(new java.awt.Dimension(250, 50));
        logOutBttn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logOutBttnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logOutBttnMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                logOutBttnMousePressed(evt);
            }
        });
        logOutBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOutBttnActionPerformed(evt);
            }
        });
        westPanel.add(logOutBttn);

        studentDashboardBottomPanel.add(westPanel, java.awt.BorderLayout.WEST);

        centerPanel.setLayout(new java.awt.CardLayout());

        borrowPanel.setLayout(null);

        searchBookTxtFld.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        searchBookTxtFld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBookTxtFldActionPerformed(evt);
            }
        });
        borrowPanel.add(searchBookTxtFld);
        searchBookTxtFld.setBounds(562, 15, 343, 26);

        searchBookLbl.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        searchBookLbl.setText("Search Book:");
        borrowPanel.add(searchBookLbl);
        searchBookLbl.setBounds(456, 14, 100, 25);

        bookJList.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        bookJList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        bookJList.setListData(psDatabase.getAllBooks().stream().map(b -> b.getTitle()).collect(Collectors.toList()).toArray(new String[0]));
        bookJList.addListSelectionListener((e) -> {
            if (!borrowSelectedBookBttn.isEnabled()) { borrowSelectedBookBttn.setEnabled(true); }
            if (alreadyBorrowedLbl.isVisible()) { alreadyBorrowedLbl.setVisible(false); }
            selectedBook = psDatabase.getAllBooks().stream()
            .reduce(null, (sub, current) -> (current.getTitle().equals(bookJList.getSelectedValue()) ?
                current : sub));
        if (selectedBook != null) {

            bookTitleValueTxtArea.setText(selectedBook.getTitle());
            bookAuthorValueTxtArea.setText(selectedBook.getAuthor());
            bookCategoryValueLbl.setText(selectedBook.getCategory());
            bookAvailableValueLbl.setText((selectedBook.getAvailable() != 0) ? "Available" : "Not Available");
            if (bookAvailableValueLbl.getText().equals("Not Available")) {
                borrowSelectedBookBttn.setEnabled(false);
            }
        } else {
            searchBookTxtFld.setText("");
            bookTitleValueTxtArea.setText("");
            bookAuthorValueTxtArea.setText("");
            bookCategoryValueLbl.setText("");
            bookAvailableValueLbl.setText("");
            borrowSelectedBookBttn.setEnabled(false);
        }
    });
    bookListParentScrollPane.setViewportView(bookJList);

    borrowPanel.add(bookListParentScrollPane);
    bookListParentScrollPane.setBounds(562, 62, 343, 605);

    borrowSelectedBookBttn.setBackground(new java.awt.Color(0, 153, 255));
    borrowSelectedBookBttn.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
    borrowSelectedBookBttn.setForeground(new java.awt.Color(255, 255, 255));
    borrowSelectedBookBttn.setText("Borrow Selected Book");
    borrowSelectedBookBttn.setEnabled(false);
    borrowSelectedBookBttn.setFocusable(false);
    borrowSelectedBookBttn.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            borrowSelectedBookBttnActionPerformed(evt);
        }
    });
    borrowPanel.add(borrowSelectedBookBttn);
    borrowSelectedBookBttn.setBounds(286, 635, 270, 32);

    resetBookListBttn.setBackground(new java.awt.Color(51, 204, 0));
    resetBookListBttn.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
    resetBookListBttn.setForeground(new java.awt.Color(255, 255, 255));
    resetBookListBttn.setText("Refresh Book List");
    resetBookListBttn.setFocusable(false);
    resetBookListBttn.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            resetBookListBttnActionPerformed(evt);
        }
    });
    borrowPanel.add(resetBookListBttn);
    resetBookListBttn.setBounds(10, 635, 270, 32);

    bookCategoryValueLbl.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
    bookCategoryValueLbl.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Category", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 18))); // NOI18N
    bookCategoryValueLbl.setPreferredSize(new java.awt.Dimension(104, 83));
    borrowPanel.add(bookCategoryValueLbl);
    bookCategoryValueLbl.setBounds(10, 268, 546, 101);

    bookTitleValueTxtArea.setEditable(false);
    bookTitleValueTxtArea.setColumns(20);
    bookTitleValueTxtArea.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
    bookTitleValueTxtArea.setLineWrap(true);
    bookTitleValueTxtArea.setRows(2);
    bookTitleValueTxtArea.setWrapStyleWord(true);
    bookTitleValueTxtArea.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Title", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 18))); // NOI18N
    bookTitleValueParentScrollPane.setViewportView(bookTitleValueTxtArea);

    borrowPanel.add(bookTitleValueParentScrollPane);
    bookTitleValueParentScrollPane.setBounds(10, 62, 546, 85);

    bookAuthorValueParentScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    bookAuthorValueTxtArea.setEditable(false);
    bookAuthorValueTxtArea.setColumns(20);
    bookAuthorValueTxtArea.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
    bookAuthorValueTxtArea.setLineWrap(true);
    bookAuthorValueTxtArea.setRows(2);
    bookAuthorValueTxtArea.setWrapStyleWord(true);
    bookAuthorValueTxtArea.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Author/s", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 18))); // NOI18N
    bookAuthorValueParentScrollPane.setViewportView(bookAuthorValueTxtArea);

    borrowPanel.add(bookAuthorValueParentScrollPane);
    bookAuthorValueParentScrollPane.setBounds(10, 165, 546, 85);

    bookAvailableValueLbl.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
    bookAvailableValueLbl.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Availability", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 18))); // NOI18N
    borrowPanel.add(bookAvailableValueLbl);
    bookAvailableValueLbl.setBounds(10, 387, 546, 101);

    alreadyBorrowedLbl.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
    alreadyBorrowedLbl.setForeground(java.awt.Color.red);
    alreadyBorrowedLbl.setText("*you have already borrowed the selected book");
    alreadyBorrowedLbl.setVisible(false);
    borrowPanel.add(alreadyBorrowedLbl);
    alreadyBorrowedLbl.setBounds(10, 506, 365, 36);

    penaltyCountLbl.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
    penaltyCountLbl.setText("My Penalty Count: ");
    borrowPanel.add(penaltyCountLbl);
    penaltyCountLbl.setBounds(10, 11, 149, 30);

    penaltyCountValueLbl.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
    penaltyCountValueLbl.setText("0");
    borrowPanel.add(penaltyCountValueLbl);
    penaltyCountValueLbl.setBounds(165, 11, 63, 30);

    centerPanel.add(borrowPanel, "borrowCard");

    returnPanel.setLayout(null);

    returnSearchBookTxtFld.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
    returnSearchBookTxtFld.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            returnSearchBookTxtFldActionPerformed(evt);
        }
    });
    returnPanel.add(returnSearchBookTxtFld);
    returnSearchBookTxtFld.setBounds(562, 15, 343, 26);

    returnBookAuthorValueParentScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    returnBookAuthorValueTxtArea.setEditable(false);
    returnBookAuthorValueTxtArea.setColumns(20);
    returnBookAuthorValueTxtArea.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
    returnBookAuthorValueTxtArea.setLineWrap(true);
    returnBookAuthorValueTxtArea.setRows(2);
    returnBookAuthorValueTxtArea.setWrapStyleWord(true);
    returnBookAuthorValueTxtArea.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Author/s", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 18))); // NOI18N
    returnBookAuthorValueParentScrollPane.setViewportView(returnBookAuthorValueTxtArea);

    returnPanel.add(returnBookAuthorValueParentScrollPane);
    returnBookAuthorValueParentScrollPane.setBounds(10, 162, 546, 85);

    returnBookJList.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
    returnBookJList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    returnBookListParentScrollPane.setViewportView(returnBookJList);

    returnPanel.add(returnBookListParentScrollPane);
    returnBookListParentScrollPane.setBounds(562, 59, 343, 605);

    returnBookCategoryValueLbl.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
    returnBookCategoryValueLbl.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Category", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 18))); // NOI18N
    returnBookCategoryValueLbl.setPreferredSize(new java.awt.Dimension(104, 83));
    returnPanel.add(returnBookCategoryValueLbl);
    returnBookCategoryValueLbl.setBounds(10, 265, 546, 101);

    returnResetBookListBttn.setBackground(new java.awt.Color(51, 204, 0));
    returnResetBookListBttn.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
    returnResetBookListBttn.setForeground(new java.awt.Color(255, 255, 255));
    returnResetBookListBttn.setText("Refresh Book List");
    returnResetBookListBttn.setFocusable(false);
    returnResetBookListBttn.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            returnResetBookListBttnActionPerformed(evt);
        }
    });
    returnPanel.add(returnResetBookListBttn);
    returnResetBookListBttn.setBounds(10, 632, 270, 32);

    returnSearchBookLbl.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
    returnSearchBookLbl.setText("Search Borrowed Book:");
    returnPanel.add(returnSearchBookLbl);
    returnSearchBookLbl.setBounds(377, 14, 179, 25);

    returnBookTitleValueTxtArea.setEditable(false);
    returnBookTitleValueTxtArea.setColumns(20);
    returnBookTitleValueTxtArea.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
    returnBookTitleValueTxtArea.setLineWrap(true);
    returnBookTitleValueTxtArea.setRows(2);
    returnBookTitleValueTxtArea.setWrapStyleWord(true);
    returnBookTitleValueTxtArea.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Title", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 18))); // NOI18N
    returnBookTitleValueParentScrollPane.setViewportView(returnBookTitleValueTxtArea);

    returnPanel.add(returnBookTitleValueParentScrollPane);
    returnBookTitleValueParentScrollPane.setBounds(10, 59, 546, 85);

    returnDateBorrowedValueLbl.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
    returnDateBorrowedValueLbl.setToolTipText("YYYY-MM-DD");
    returnDateBorrowedValueLbl.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Date Borrowed", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 18))); // NOI18N
    returnPanel.add(returnDateBorrowedValueLbl);
    returnDateBorrowedValueLbl.setBounds(10, 384, 546, 101);

    returnSelectedBookBttn.setBackground(new java.awt.Color(0, 153, 255));
    returnSelectedBookBttn.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
    returnSelectedBookBttn.setForeground(new java.awt.Color(255, 255, 255));
    returnSelectedBookBttn.setText("Return Selected Book");
    returnSelectedBookBttn.setEnabled(false);
    returnSelectedBookBttn.setFocusable(false);
    returnSelectedBookBttn.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            returnSelectedBookBttnActionPerformed(evt);
        }
    });
    returnPanel.add(returnSelectedBookBttn);
    returnSelectedBookBttn.setBounds(286, 632, 270, 32);

    returnDueDateValueLbl.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
    returnDueDateValueLbl.setToolTipText("YYYY-MM-DD");
    returnDueDateValueLbl.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Due Date", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 18))); // NOI18N
    returnPanel.add(returnDueDateValueLbl);
    returnDueDateValueLbl.setBounds(10, 503, 546, 101);

    centerPanel.add(returnPanel, "returnCard");

    studentDashboardBottomPanel.add(centerPanel, java.awt.BorderLayout.CENTER);

    getContentPane().add(studentDashboardBottomPanel, java.awt.BorderLayout.CENTER);

    setSize(new java.awt.Dimension(1200, 900));
    setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void borrowBookBttnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_borrowBookBttnMouseEntered
        if (!borrowBookBttn.isContentAreaFilled()) {
            borrowBookBttn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 24));
        }
    }//GEN-LAST:event_borrowBookBttnMouseEntered

    private void borrowBookBttnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_borrowBookBttnMouseExited
        borrowBookBttn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 24));
    }//GEN-LAST:event_borrowBookBttnMouseExited

    private void returnBookBttnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_returnBookBttnMouseEntered
        if (!returnBookBttn.isContentAreaFilled()) {
            returnBookBttn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 24));
        }
    }//GEN-LAST:event_returnBookBttnMouseEntered

    private void returnBookBttnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_returnBookBttnMouseExited
        returnBookBttn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 24));
    }//GEN-LAST:event_returnBookBttnMouseExited

    private void logOutBttnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logOutBttnMouseEntered
        logOutBttn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 24));
    }//GEN-LAST:event_logOutBttnMouseEntered

    private void logOutBttnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logOutBttnMouseExited
        logOutBttn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 24));
    }//GEN-LAST:event_logOutBttnMouseExited

    private void logOutBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOutBttnActionPerformed
        startForm.getStudentBttn().setEnabled(true);
        //writePseudoDatabaseSer();
        dispose();
    }//GEN-LAST:event_logOutBttnActionPerformed

    private void borrowBookBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrowBookBttnActionPerformed
        cards = (java.awt.CardLayout) centerPanel.getLayout();
        cards.show(centerPanel, "borrowCard");
    }//GEN-LAST:event_borrowBookBttnActionPerformed

    private void returnBookBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnBookBttnActionPerformed
        cards = (java.awt.CardLayout) centerPanel.getLayout();
        cards.show(centerPanel, "returnCard");
        
    }//GEN-LAST:event_returnBookBttnActionPerformed

    private void borrowBookBttnMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_borrowBookBttnMousePressed
        borrowBookBttn.setContentAreaFilled(true);
        returnBookBttn.setContentAreaFilled(false);
    }//GEN-LAST:event_borrowBookBttnMousePressed

    private void returnBookBttnMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_returnBookBttnMousePressed
        borrowBookBttn.setContentAreaFilled(false);
        returnBookBttn.setContentAreaFilled(true);
    }//GEN-LAST:event_returnBookBttnMousePressed

    private void resetBookListBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetBookListBttnActionPerformed
        new SwingWorker<Void, Void>(){
            @Override
            protected Void doInBackground() {
                readPseudoDatabaseSer();
                return null;
            }
            @Override
            protected void done() {
                javax.swing.SwingUtilities.invokeLater(() -> {
                    bookJList.setListData(psDatabase.getAllBooks().stream()
                    .map(b -> b.getTitle()).collect(Collectors.toList()).toArray(new String[0]));
                });
            }
        }.execute();
    }//GEN-LAST:event_resetBookListBttnActionPerformed

    private void borrowSelectedBookBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrowSelectedBookBttnActionPerformed

        new SwingWorker<Boolean, Void>(){
            @Override
            protected Boolean doInBackground() {
                return psDatabase.getStudent(loggedStudent.getStudentID()).getBorrowedBooks().stream().anyMatch(b -> bookJList.getSelectedValue().equals(b.getTitle()));
            }
            @Override
            protected void done() {
                try {
                    if (get()) {
                        if (!alreadyBorrowedLbl.isVisible()) {
                            javax.swing.SwingUtilities.invokeLater(() -> 
                                    alreadyBorrowedLbl.setVisible(true));
                        }
                    } else {
                        selectedBook.setAvailable(selectedBook.getAvailable() - 1);
                        selectedBook.addStudentWhoBorrowed(psDatabase.getStudent(loggedStudent.getStudentID()));
                        psDatabase.getStudent(loggedStudent.getStudentID()).addBorrowedBook(selectedBook, new BorrowAndDue(LocalDate.now(), LocalDate.now().plusDays(1)));
                        //write after addition to borrowed books
                        writePseudoDatabaseSer();
                        JOptionPane.showMessageDialog(null, "You have successfully borrowed this book", "Library Management System", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(StudentDashboard.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExecutionException ex) {
                    Logger.getLogger(StudentDashboard.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.execute();
    }//GEN-LAST:event_borrowSelectedBookBttnActionPerformed

    private void returnResetBookListBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnResetBookListBttnActionPerformed
        javax.swing.SwingUtilities.invokeLater(() -> {
            returnSearchBookTxtFld.setText("");
            returnBookJList.setListData(psDatabase.getStudent(loggedStudent.getStudentID()).getBorrowedBooks().stream()
                    .map(b -> b.getTitle())
                    .collect(Collectors.toList())
                    .toArray(new String[0])
            );
            //THE SAME AS THE INITIALIZATION OF returnBookJList
        });
    }//GEN-LAST:event_returnResetBookListBttnActionPerformed

    private void searchBookTxtFldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBookTxtFldActionPerformed
        if (!searchBookTxtFld.getText().isEmpty()) {
            bookJList.setListData(psDatabase.getAllBooks().stream()
                    .filter(b -> b.getTitle().toLowerCase().contains(searchBookTxtFld.getText().toLowerCase()))
                    .map(b -> b.getTitle()).collect(Collectors.toList()).toArray(new String[0])
            );
        } else {
            resetBookListBttnActionPerformed(evt);
        }
    }//GEN-LAST:event_searchBookTxtFldActionPerformed

    private void returnSearchBookTxtFldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnSearchBookTxtFldActionPerformed
        if (!returnSearchBookTxtFld.getText().isEmpty()) {
            returnBookJList.setListData(loggedStudent.getBorrowedBooks().stream()
                    .filter(b
                            -> b.getTitle().toLowerCase().contains(returnSearchBookTxtFld.getText().toLowerCase()))
                    .map(b -> b.getTitle())
                    .collect(Collectors.toList())
                    .toArray(new String[0])
            );
        } else {
            returnResetBookListBttnActionPerformed(evt);
        }
    }//GEN-LAST:event_returnSearchBookTxtFldActionPerformed

    private void returnSelectedBookBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnSelectedBookBttnActionPerformed
        if (selectedBook != null) {
            selectedBook.setAvailable(selectedBook.getAvailable() + 1);
            selectedBook.getWhoBorrowed().remove(psDatabase.getStudent(loggedStudent.getStudentID()));
            psDatabase.getStudent(loggedStudent.getStudentID()).getBorrowedBooks().remove(selectedBook);
            psDatabase.getStudent(loggedStudent.getStudentID()).getRecordedOverdueBooks().remove(selectedBook);
        }
        new SwingWorker<Void, Void>(){
            @Override
            protected Void doInBackground() {
                writePseudoDatabaseSer();
                return null;
            }
            @Override
            protected void done() {
                returnBookJList.setListData(psDatabase.getStudent(loggedStudent.getStudentID()).getBorrowedBooks().stream()
                    .map(b -> b.getTitle())
                    .collect(Collectors.toList())
                    .toArray(new String[0])
                );
                JOptionPane.showMessageDialog(null, "You have succesfully returned the book", "Library Management System", JOptionPane.INFORMATION_MESSAGE);
            }
        }.execute();
        
    }//GEN-LAST:event_returnSelectedBookBttnActionPerformed

    private void logOutBttnMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logOutBttnMousePressed
        borrowBookBttn.setContentAreaFilled(false);
        returnBookBttn.setContentAreaFilled(false);
        logOutBttn.setContentAreaFilled(true);
    }//GEN-LAST:event_logOutBttnMousePressed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Windows".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(StudentDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(StudentDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(StudentDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(StudentDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new StudentDashboard().setVisible(true);
//            }
//        });
//    }

    /*
    this method deserializes the "psdb.ser" and assigns to the instance
    variable this.psDataBase of type PseudoDatabase
     */
    private void readPseudoDatabaseSer() {
        try {
            FileInputStream fileIn = new FileInputStream("psdb.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            this.psDatabase = (PseudoDatabase) objectIn.readObject();
            objectIn.close();
            fileIn.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StudentDashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StudentDashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StudentDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void writePseudoDatabaseSer() {
        try {
            FileOutputStream fileOut = new FileOutputStream("psdb.ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(this.psDatabase);
            objectOut.flush();
            objectOut.close();
            fileOut.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StudentDashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StudentDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected boolean verifyLogIn(String studentID, char[] password) {
        if (this.verifyStudentID(studentID)) {
            return this.psDatabase.verifyStudentLogIn(studentID, password);
        } else {
            return false;
        }
    }

    protected boolean verifyStudentID(String studentID) {
        return this.psDatabase.studentIDExists(studentID);
    }

    protected void studentLogged(Student st) {
        this.loggedStudent = st;
        this.studentNameValueLbl.setText(loggedStudent.getName());
        this.studentIDValueLbl.setText(loggedStudent.getStudentID());
        this.courseSectionValueLbl.setText(loggedStudent.getCourse().concat("-").concat(loggedStudent.getSection()));
        loggedStudent.updatePenalty();
        penaltyCountValueLbl.setText(Integer.toString(loggedStudent.getPenaltyCount()));

    }

    protected PseudoDatabase getPsdb() {
        return this.psDatabase;
    }

    protected void setStartForm(StartupForm startForm) {
        this.startForm = startForm;
        this.startForm.setStudentDashboard(this);
    }

    private void addDummyValue() {

        psDatabase.registerNewStudent(new Student("Gabriel Emmanuel M. Excija", "BSIT", "201", "2000173962", "bsit$99k99sti".toCharArray()));
        psDatabase.registerNewAdmin(new Admin("2000173962", "Gabriel Emmanuel M. Excija", "libAdmin%9909".toCharArray()));

        psDatabase.addBook(new Book("In Search of Lost Time", "Marcel Proust", "Modernist", 20));
        psDatabase.addBook(new Book("Don Quixote", "Miguel de Cervantes", "Romance", 20));
        psDatabase.addBook(new Book("One Hundred Years of Solitude", "Gabriel Garcia Marques", "Magic Realism", 20));
        psDatabase.addBook(new Book("The Great Gatsby", "F. Scott Fitzgerald", "Tragedy", 20));
        psDatabase.addBook(new Book("War and Peace", "Leo Tolstoy", "Historical Novel", 20));
        psDatabase.addBook(new Book("Moby Dick", "Herman Melville", "Adventure", 20));
        psDatabase.addBook(new Book("Hamlet", "William Shakespeare", "Tragedy", 20));
        psDatabase.addBook(new Book("The Odyssey", "Homer", "Epic Poetry", 20));
        psDatabase.addBook(new Book("Ulysses", "James Joyce", "Modernist", 20));
        psDatabase.addBook(new Book("Madame Bovary", "Gustave Flaubert", "Realist", 20));
        psDatabase.addBook(new Book("The Divine Comedy", "Dante Alighieri", "Epic Poetry", 20));
        psDatabase.addBook(new Book("Crime and Punishment", "Fyodor Dostoyevsky", "Psychological Fiction", 20));
        psDatabase.addBook(new Book("Wuthering Heights", "Emily Bronte", "Tragedy", 20));
        psDatabase.addBook(new Book("The Catcher in the Rye", "J. D. Salinger", "Realistic Fiction", 20));
        psDatabase.addBook(new Book("Pride and Prejudice", "Jane Austen", "Satire", 20));
        psDatabase.addBook(new Book("The Adventures of Huckleberry", "Mark Twain", "Adventure", 20));
        psDatabase.addBook(new Book("Alice\'s Adventures in Wonderland", "Lewis Caroll", "Adventure", 20));
        psDatabase.addBook(new Book("Nineteen Eighty Four", "George Orwell", "Dystopian", 20));
        psDatabase.addBook(new Book("Great Expectations", "Charles Dickens", "Fictional Autobiography", 20));
        psDatabase.addBook(new Book("To Kill a Mockingbird", "Harper Lee", "Southern Gothic", 20));
        psDatabase.addBook(new Book("Noli Me Tangere", "Jose Rizal", "Satire", 20));
        psDatabase.addBook(new Book("El Filibusterismo", "Jose Rizal", "Satire", 20));
        psDatabase.addBook(new Book("Ibong Adarna", "Jose dela Cruz", "Fantasy", 20));
        psDatabase.addBook(new Book("Florante At Laura", "Francisco Balagtas", "Epic Poetry", 20));
        psDatabase.addBook(new Book("David Copperfield", "Charles Dickens", "Fictional Autobiography", 20));
        psDatabase.addBook(new Book("The Shining", "Stephen King", "Horror", 20));
        psDatabase.addBook(new Book("It", "Stephen King", "Horror", 20));

        psDatabase.addBook(new Book("Pet Sematary", "Stephen King", "Horror", 1));

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel alreadyBorrowedLbl;
    private javax.swing.JScrollPane bookAuthorValueParentScrollPane;
    private javax.swing.JTextArea bookAuthorValueTxtArea;
    private javax.swing.JLabel bookAvailableValueLbl;
    private javax.swing.JLabel bookCategoryValueLbl;
    private javax.swing.JList<String> bookJList;
    private javax.swing.JScrollPane bookListParentScrollPane;
    private javax.swing.JScrollPane bookTitleValueParentScrollPane;
    private javax.swing.JTextArea bookTitleValueTxtArea;
    private javax.swing.JButton borrowBookBttn;
    private javax.swing.JPanel borrowPanel;
    private javax.swing.JButton borrowSelectedBookBttn;
    private javax.swing.JPanel centerPanel;
    private javax.swing.JLabel courseSectionLbl;
    private javax.swing.JLabel courseSectionValueLbl;
    private javax.swing.JLabel currentDateValueLbl;
    private javax.swing.JLabel currentTimeValueLbl;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JButton logOutBttn;
    private javax.swing.JLabel penaltyCountLbl;
    private javax.swing.JLabel penaltyCountValueLbl;
    private javax.swing.JButton resetBookListBttn;
    private javax.swing.JScrollPane returnBookAuthorValueParentScrollPane;
    private javax.swing.JTextArea returnBookAuthorValueTxtArea;
    private javax.swing.JButton returnBookBttn;
    private javax.swing.JLabel returnBookCategoryValueLbl;
    private javax.swing.JList<String> returnBookJList;
    private javax.swing.JScrollPane returnBookListParentScrollPane;
    private javax.swing.JScrollPane returnBookTitleValueParentScrollPane;
    private javax.swing.JTextArea returnBookTitleValueTxtArea;
    private javax.swing.JLabel returnDateBorrowedValueLbl;
    private javax.swing.JLabel returnDueDateValueLbl;
    private javax.swing.JPanel returnPanel;
    private javax.swing.JButton returnResetBookListBttn;
    private javax.swing.JLabel returnSearchBookLbl;
    private javax.swing.JTextField returnSearchBookTxtFld;
    private javax.swing.JButton returnSelectedBookBttn;
    private javax.swing.JLabel searchBookLbl;
    private javax.swing.JTextField searchBookTxtFld;
    private javax.swing.JPanel studentDashboardBottomPanel;
    private javax.swing.JLabel studentDashboardImgLbl;
    private javax.swing.JPanel studentDashboardTopPanel;
    private javax.swing.JLabel studentIDValueLbl;
    private javax.swing.JLabel studentNameLbl;
    private javax.swing.JLabel studentNameValueLbl;
    private javax.swing.JLabel studentidLbl;
    private javax.swing.JPanel westPanel;
    // End of variables declaration//GEN-END:variables
}
