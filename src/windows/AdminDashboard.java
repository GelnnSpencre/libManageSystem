package windows;

import commons.PseudoDatabase;
import commons.Admin;
import commons.Student;
import commons.Book;
import commons.Student.BorrowAndDue;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import java.util.List;

public class AdminDashboard extends javax.swing.JFrame {

    private StartupForm startForm;
    private PseudoDatabase psDatabase;
    private java.awt.CardLayout cards;
    private Admin loggedAdmin;
    private Book selectedBook;
    private Student selectedSt;
    private final DateTimeFormatter timePattern = DateTimeFormatter.ofPattern("hh:mm:ss a");
    private final DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("E MMM d, uuuu");
    private Timer timer;

    /**
     * Creates new form AdminDashboard
     */
    public AdminDashboard() {
        readPseudoDatabaseSer();
        initComponents();
        //initialization for bookStockJList
            bookJList.setListData(psDatabase.getAllBooks().stream()
                    .map(b -> b.getTitle())
                    .collect(Collectors.toList())
                    .toArray(new String[0])
            );
            bookJList.addListSelectionListener((e) -> {
                new SwingWorker<Void, Void>(){
                    @Override
                    protected Void doInBackground() {
                        //readPseudoDatabaseSer();
                        return null;
                    }
                    @Override
                    protected void done() {
                        if (!decreaseTotalQuantityBttn.isEnabled()) {
                            decreaseTotalQuantityBttn.setEnabled(true);
                        }
                        if (!increaseTotalQuantityBttn.isEnabled()) {
                            increaseTotalQuantityBttn.setEnabled(true);
                        }
                        selectedBook = psDatabase.getAllBooks().stream()
                                .reduce(null, (sub, current) -> (current.getTitle().equals(bookJList.getSelectedValue())
                                ? current : sub));
                        if (selectedBook != null) {
                            bookStockTitleValueTxtArea.setText(selectedBook.getTitle());
                            bookStockAuthorValueTxtArea.setText(selectedBook.getAuthor());
                            bookStockCategoryTxtFld.setText(selectedBook.getCategory());
                            bookStockTotalQuantityTxtFld.setText(Integer.toString(selectedBook.getTotalStock()));
                            bookStockAvailableQuantityTxtFld.setText(Integer.toString(selectedBook.getAvailable()));
                            if (!bookStockTotalQuantityTxtFld.getText().equals(bookStockAvailableQuantityTxtFld.getText())) {
                                decreaseTotalQuantityBttn.setEnabled(false);
                            }
                        } else {
                            searchBookTxtFld.setText("");
                            bookStockTitleValueTxtArea.setText("");
                            bookStockAuthorValueTxtArea.setText("");
                            bookStockCategoryTxtFld.setText("");
                            bookStockTotalQuantityTxtFld.setText("");
                            bookStockAvailableQuantityTxtFld.setText("");
                            decreaseTotalQuantityBttn.setEnabled(false);
                            increaseTotalQuantityBttn.setEnabled(false);
                        }
                    }
                }.execute(); 
            });

            //studentsWhoBorrowedJList
            studentsWhoBorrowedJList.addListSelectionListener((e) -> {
                selectedSt = psDatabase.getAllStudents().stream()
                        .reduce(null, (sub, current) -> current.getName().equals(studentsWhoBorrowedJList.getSelectedValue())
                        ? current : sub);
                if (selectedSt != null) {
                    BorrowAndDue dateInfo = psDatabase.getStudent(selectedSt.getStudentID()).getBorrowAndDueInfo(selectedBook);
                    //selectedSt.getBorrowAndDueInfo(selectedBook);
                    if (dateInfo == null) {
                        System.out.println("BorrowAndDue is null");
                    } else {
                        issuedBooksDateBorrowedValueLbl.setText(dateInfo.getBorrowedDate().toString());
                        issuedBooksDueDateValueLbl.setText(dateInfo.getDueDate().toString());
                        if (issuedBooksDueDateValueLbl.getText().compareTo(LocalDate.now().toString()) < 0) {
                            issuedBooksDueDateValueLbl.setForeground(java.awt.Color.RED);
                            issuedBooksDueDateValueLbl.setToolTipText("The selected is book is overdue");
                        } else {
                            issuedBooksDueDateValueLbl.setForeground(java.awt.Color.BLACK);
                            issuedBooksDueDateValueLbl.setToolTipText("");
                        }
                    }

                } else {
                    issuedBooksDateBorrowedValueLbl.setText("");
                    issuedBooksDueDateValueLbl.setText("");
                }
            });
            //issuedBooksJList
            issuedBooksJList.setListData(psDatabase.getAllBooks().stream()
                    .filter(b -> b.getWhoBorrowed().size() > 0)
                    .map(b -> b.getTitle())
                    .collect(Collectors.toList())
                    .toArray(new String[0])
            );
            //issuedBooksJList SelectionListener
            issuedBooksJList.addListSelectionListener((e) -> {
                new SwingWorker<Boolean, Void>() {
                    @Override
                    protected Boolean doInBackground() {
                        selectedBook = psDatabase.getAllBooks().stream()
                                .reduce(null, (sub, current)
                                        -> current.getTitle().equals(issuedBooksJList.getSelectedValue())
                                ? current : sub);
                        return selectedBook != null ? true : false;
                    }
                    @Override
                    protected void done() {
                        try {
                            if (get()) {
                                studentsWhoBorrowedJList.setListData(selectedBook.getWhoBorrowed().stream()
                                    .map(s -> s.getName())
                                    .collect(Collectors.toList())
                                    .toArray(new String[0])
                                );
                            }
                        } catch (InterruptedException ex) {
                            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ExecutionException ex) {
                            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }.execute();
            });          
            //initialization of all_students JList
            studentJList.setListData(psDatabase.getAllStudents().stream()
                    .map(s -> s.getName())
                    .collect(Collectors.toList())
                    .toArray(new String[0])
            );
            studentJList.addListSelectionListener((e) -> {
                if (!editCourseSectionBttn.isEnabled()) {
                    editCourseSectionBttn.setEnabled(true);
                }
                if (!unregisterSelectedStudentBttn.isEnabled()) {
                    unregisterSelectedStudentBttn.setEnabled(true);
                }
                if (!seeAllBorrowedBooks.isEnabled()) {
                    seeAllBorrowedBooks.setEnabled(true);
                }
                new SwingWorker<Void, Void>(){
                    @Override
                    protected Void doInBackground() {
                        selectedSt = psDatabase.getAllStudents().stream()
                            .reduce(null, (sub, current) -> current.getName().equals(studentJList.getSelectedValue())
                            ? current : sub);
                        return null;
                    }
                    @Override
                    protected void done() {
                        if (selectedSt != null) {
                            selectedSt.updatePenalty();
                            studentNameValueLbl.setText(selectedSt.getName());
                            studentCourseSectionValueLbl.setText(selectedSt.getCourse().concat("-").concat(selectedSt.getSection()));
                            studentIDValueLbl.setText(selectedSt.getStudentID());
                            studentPenaltyRecordValueLbl.setText(Integer.toString(selectedSt.getPenaltyCount()));
                        } else {
                            studentNameValueLbl.setText("");
                            studentCourseSectionValueLbl.setText("");
                            studentIDValueLbl.setText("");
                            studentPenaltyRecordValueLbl.setText("");
                            editCourseSectionBttn.setEnabled(false);
                            unregisterSelectedStudentBttn.setEnabled(false);
                            seeAllBorrowedBooks.setEnabled(false);
                        }
                    }
                }.execute();
            });

            //initialization of adminJList
            libraryAdminJList.setListData(psDatabase.getAllAdmins().stream()
                    .map(a -> a.getAdminName())
                    .collect(Collectors.toList())
                    .toArray(new String[0])
            );
            libraryAdminJList.addListSelectionListener(e -> {
                if (!removeSelectedAdminBttn.isEnabled()) {
                    removeSelectedAdminBttn.setEnabled(true);
                }
                Admin selectedAdmin = psDatabase.getAllAdmins().stream()
                        .reduce(null, (sub, current) -> current.getAdminName().equals(libraryAdminJList.getSelectedValue())
                        ? current : sub);
                if (selectedAdmin != null) {
                    selectedAdminNameValueLbl.setText(selectedAdmin.getAdminName());
                    selectedAdminIDValueLbl.setText(selectedAdmin.getAdminID());
                } else {
                    selectedAdminNameValueLbl.setText("");
                    selectedAdminIDValueLbl.setText("");
                    removeSelectedAdminBttn.setEnabled(false);
                }
            });
        timer = new Timer(1000, e -> {
            currentTimeValueLbl.setText(LocalTime.now().format(timePattern));
        });
        timer.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        adminDashboardTopPanel = new javax.swing.JPanel();
        adminDashboardImgLbl = new javax.swing.JLabel();
        adminNameLbl = new javax.swing.JLabel();
        adminidLbl = new javax.swing.JLabel();
        adminNameValueLbl = new javax.swing.JLabel();
        adminIDValueLbl = new javax.swing.JLabel();
        currentDateValueLbl = new javax.swing.JLabel();
        currentTimeValueLbl = new javax.swing.JLabel();
        adminDashboardBottomPanel = new javax.swing.JPanel();
        westPanel = new javax.swing.JPanel();
        addBookBttn = new javax.swing.JButton();
        bookStockBttn = new javax.swing.JButton();
        registerStudentBttn = new javax.swing.JButton();
        issuedBooksBttn = new javax.swing.JButton();
        studentsWestPanelBttn = new javax.swing.JButton();
        adminWestPanelBttn = new javax.swing.JButton();
        logOutBttn = new javax.swing.JButton();
        centerPanel = new javax.swing.JPanel();
        addBookPanel = new javax.swing.JPanel();
        addBookCardImgLbl = new javax.swing.JLabel();
        addNewBookBttn = new javax.swing.JButton();
        addBookTitleParentScrollPane = new javax.swing.JScrollPane();
        addBookTitleValueTxtArea = new javax.swing.JTextArea();
        addBookAuthorParentScrollPane = new javax.swing.JScrollPane();
        addBookAuthorValueTxtArea = new javax.swing.JTextArea();
        addBookCategoryValueTxtFld = new javax.swing.JTextField();
        addBookTotalQuantityValueTxtFld = new javax.swing.JTextField();
        bookStockPanel = new javax.swing.JPanel();
        searchBookLbl = new javax.swing.JLabel();
        searchBookTxtFld = new javax.swing.JTextField();
        bookListParentScrollPane = new javax.swing.JScrollPane();
        bookJList = new javax.swing.JList<>();
        filterByCategoryLbl = new javax.swing.JLabel();
        bookCategoriesComboBox = new javax.swing.JComboBox<>();
        addBookTitleParentScrollPane1 = new javax.swing.JScrollPane();
        bookStockTitleValueTxtArea = new javax.swing.JTextArea();
        addBookAuthorParentScrollPane1 = new javax.swing.JScrollPane();
        bookStockAuthorValueTxtArea = new javax.swing.JTextArea();
        bookStockCategoryTxtFld = new javax.swing.JTextField();
        bookStockTotalQuantityTxtFld = new javax.swing.JTextField();
        bookStockAvailableQuantityTxtFld = new javax.swing.JTextField();
        increaseTotalQuantityBttn = new javax.swing.JButton();
        decreaseTotalQuantityBttn = new javax.swing.JButton();
        refreshBookListBttn = new javax.swing.JButton();
        registerStudentPanel = new javax.swing.JPanel();
        registerStudentCardImgLbl = new javax.swing.JLabel();
        registerStudentNameTxtFld = new javax.swing.JTextField();
        registerStudentCourseTxtFld = new javax.swing.JTextField();
        registerStudentIDTxtFld = new javax.swing.JTextField();
        registerStudentPasswordFld = new javax.swing.JPasswordField();
        registerStudentNewBttn = new javax.swing.JButton();
        issuedBooksPanel = new javax.swing.JPanel();
        searchIssuedBookLbl = new javax.swing.JLabel();
        searchIssuedBooksTxtFld = new javax.swing.JTextField();
        issuedBooksListParentScrollPane = new javax.swing.JScrollPane();
        issuedBooksJList = new javax.swing.JList<>();
        studentsWhoBorrowedParentScrollPane = new javax.swing.JScrollPane();
        studentsWhoBorrowedJList = new javax.swing.JList<>();
        issuedBooksDateBorrowedValueLbl = new javax.swing.JLabel();
        issuedBooksDueDateValueLbl = new javax.swing.JLabel();
        resetIssuedBooksBttn = new javax.swing.JButton();
        studentsCardPanel = new javax.swing.JPanel();
        searchStudentLbl = new javax.swing.JLabel();
        searchStudentTxtFld = new javax.swing.JTextField();
        studentListParentScrollPane = new javax.swing.JScrollPane();
        studentJList = new javax.swing.JList<>();
        studentIDValueLbl = new javax.swing.JTextField();
        studentCourseSectionValueLbl = new javax.swing.JTextField();
        studentNameValueLbl = new javax.swing.JTextField();
        studentPenaltyRecordValueLbl = new javax.swing.JTextField();
        resetStudentListBttn = new javax.swing.JButton();
        unregisterSelectedStudentBttn = new javax.swing.JButton();
        editCourseSectionBttn = new javax.swing.JButton();
        seeAllBorrowedBooks = new javax.swing.JButton();
        adminCardPanel = new javax.swing.JPanel();
        libraryAdminListParentScrollPane = new javax.swing.JScrollPane();
        libraryAdminJList = new javax.swing.JList<>();
        selectedAdminNameValueLbl = new javax.swing.JTextField();
        selectedAdminIDValueLbl = new javax.swing.JTextField();
        registerNewAdminBttn = new javax.swing.JButton();
        newAdminNameTxtFld = new javax.swing.JTextField();
        newAdminIDTxtFld = new javax.swing.JTextField();
        newAdminPasswordFld = new javax.swing.JPasswordField();
        cancelRegistrationNewAdminBttn = new javax.swing.JButton();
        registerNowNewAdminBttn = new javax.swing.JButton();
        removeSelectedAdminBttn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new javax.swing.ImageIcon("C:\\Users\\Gelnn Spencre\\Documents\\NetBeansProjects\\LibraryManagementSystem\\src\\resources\\adminDashboardIcon.png").getImage()
        );
        setUndecorated(true);

        adminDashboardTopPanel.setBackground(new java.awt.Color(255, 255, 51));
        adminDashboardTopPanel.setPreferredSize(new java.awt.Dimension(1000, 200));
        adminDashboardTopPanel.setLayout(null);

        adminDashboardImgLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/adminDashboardIcon.png"))); // NOI18N
        adminDashboardImgLbl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        adminDashboardTopPanel.add(adminDashboardImgLbl);
        adminDashboardImgLbl.setBounds(66, 35, 130, 130);

        adminNameLbl.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        adminNameLbl.setText("Name:");
        adminNameLbl.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        adminNameLbl.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        adminDashboardTopPanel.add(adminNameLbl);
        adminNameLbl.setBounds(214, 27, 74, 32);

        adminidLbl.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        adminidLbl.setText("Admin ID:");
        adminDashboardTopPanel.add(adminidLbl);
        adminidLbl.setBounds(214, 77, 116, 32);

        adminNameValueLbl.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        adminNameValueLbl.setText("First Second M. Surname");
        adminDashboardTopPanel.add(adminNameValueLbl);
        adminNameValueLbl.setBounds(298, 27, 529, 32);

        adminIDValueLbl.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        adminIDValueLbl.setText("1032654578954623");
        adminDashboardTopPanel.add(adminIDValueLbl);
        adminIDValueLbl.setBounds(340, 77, 487, 32);

        currentDateValueLbl.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        currentDateValueLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        currentDateValueLbl.setText(LocalDate.now().format(datePattern));
        adminDashboardTopPanel.add(currentDateValueLbl);
        currentDateValueLbl.setBounds(840, 30, 320, 36);

        currentTimeValueLbl.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        currentTimeValueLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        currentTimeValueLbl.setText(LocalTime.now().format(timePattern));
        adminDashboardTopPanel.add(currentTimeValueLbl);
        currentTimeValueLbl.setBounds(900, 80, 260, 36);

        getContentPane().add(adminDashboardTopPanel, java.awt.BorderLayout.PAGE_START);

        adminDashboardBottomPanel.setBackground(java.awt.Color.lightGray);
        adminDashboardBottomPanel.setLayout(new java.awt.BorderLayout());

        westPanel.setBackground(java.awt.Color.white);
        westPanel.setPreferredSize(new java.awt.Dimension(250, 500));
        westPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 10));

        addBookBttn.setBackground(new java.awt.Color(255, 255, 51));
        addBookBttn.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        addBookBttn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/addBookIcon.png"))); // NOI18N
        addBookBttn.setText("ADD BOOK");
        addBookBttn.setBorderPainted(false);
        addBookBttn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addBookBttn.setFocusable(false);
        addBookBttn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        addBookBttn.setPreferredSize(new java.awt.Dimension(250, 50));
        addBookBttn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addBookBttnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addBookBttnMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                addBookBttnMousePressed(evt);
            }
        });
        addBookBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBookBttnActionPerformed(evt);
            }
        });
        westPanel.add(addBookBttn);

        bookStockBttn.setBackground(new java.awt.Color(255, 255, 51));
        bookStockBttn.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        bookStockBttn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/bookStockIcon.png"))); // NOI18N
        bookStockBttn.setText("BOOK STOCK");
        bookStockBttn.setBorderPainted(false);
        bookStockBttn.setContentAreaFilled(false);
        bookStockBttn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bookStockBttn.setFocusable(false);
        bookStockBttn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        bookStockBttn.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        bookStockBttn.setPreferredSize(new java.awt.Dimension(250, 50));
        bookStockBttn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bookStockBttnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bookStockBttnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bookStockBttnMouseExited(evt);
            }
        });
        bookStockBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookStockBttnActionPerformed(evt);
            }
        });
        westPanel.add(bookStockBttn);

        registerStudentBttn.setBackground(new java.awt.Color(255, 255, 51));
        registerStudentBttn.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        registerStudentBttn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/registerStudentIcon.png"))); // NOI18N
        registerStudentBttn.setText("REGISTER");
        registerStudentBttn.setBorderPainted(false);
        registerStudentBttn.setContentAreaFilled(false);
        registerStudentBttn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        registerStudentBttn.setFocusable(false);
        registerStudentBttn.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        registerStudentBttn.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        registerStudentBttn.setPreferredSize(new java.awt.Dimension(250, 50));
        registerStudentBttn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registerStudentBttnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                registerStudentBttnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                registerStudentBttnMouseExited(evt);
            }
        });
        registerStudentBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerStudentBttnActionPerformed(evt);
            }
        });
        westPanel.add(registerStudentBttn);

        issuedBooksBttn.setBackground(new java.awt.Color(255, 255, 51));
        issuedBooksBttn.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        issuedBooksBttn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/issuedBooksIcon.png"))); // NOI18N
        issuedBooksBttn.setText("ISSUED BOOKS");
        issuedBooksBttn.setBorderPainted(false);
        issuedBooksBttn.setContentAreaFilled(false);
        issuedBooksBttn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        issuedBooksBttn.setFocusable(false);
        issuedBooksBttn.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        issuedBooksBttn.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        issuedBooksBttn.setPreferredSize(new java.awt.Dimension(250, 50));
        issuedBooksBttn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                issuedBooksBttnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                issuedBooksBttnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                issuedBooksBttnMouseExited(evt);
            }
        });
        issuedBooksBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                issuedBooksBttnActionPerformed(evt);
            }
        });
        westPanel.add(issuedBooksBttn);

        studentsWestPanelBttn.setBackground(new java.awt.Color(255, 255, 51));
        studentsWestPanelBttn.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        studentsWestPanelBttn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/studentWestPanelIcon.png"))); // NOI18N
        studentsWestPanelBttn.setText("STUDENTS");
        studentsWestPanelBttn.setBorderPainted(false);
        studentsWestPanelBttn.setContentAreaFilled(false);
        studentsWestPanelBttn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentsWestPanelBttn.setFocusable(false);
        studentsWestPanelBttn.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        studentsWestPanelBttn.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        studentsWestPanelBttn.setPreferredSize(new java.awt.Dimension(250, 50));
        studentsWestPanelBttn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                studentsWestPanelBttnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                studentsWestPanelBttnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                studentsWestPanelBttnMouseExited(evt);
            }
        });
        studentsWestPanelBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentsWestPanelBttnActionPerformed(evt);
            }
        });
        westPanel.add(studentsWestPanelBttn);

        adminWestPanelBttn.setBackground(new java.awt.Color(255, 255, 51));
        adminWestPanelBttn.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        adminWestPanelBttn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/adminWestPanelIcon.png"))); // NOI18N
        adminWestPanelBttn.setText("ADMIN");
        adminWestPanelBttn.setBorderPainted(false);
        adminWestPanelBttn.setContentAreaFilled(false);
        adminWestPanelBttn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminWestPanelBttn.setFocusable(false);
        adminWestPanelBttn.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        adminWestPanelBttn.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        adminWestPanelBttn.setPreferredSize(new java.awt.Dimension(250, 50));
        adminWestPanelBttn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminWestPanelBttnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                adminWestPanelBttnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                adminWestPanelBttnMouseExited(evt);
            }
        });
        adminWestPanelBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminWestPanelBttnActionPerformed(evt);
            }
        });
        westPanel.add(adminWestPanelBttn);

        logOutBttn.setBackground(new java.awt.Color(255, 255, 51));
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
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logOutBttnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logOutBttnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logOutBttnMouseExited(evt);
            }
        });
        logOutBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOutBttnActionPerformed(evt);
            }
        });
        westPanel.add(logOutBttn);

        adminDashboardBottomPanel.add(westPanel, java.awt.BorderLayout.WEST);

        centerPanel.setLayout(new java.awt.CardLayout());

        addBookCardImgLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        addBookCardImgLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/addBookCardIcon.png"))); // NOI18N

        addNewBookBttn.setBackground(new java.awt.Color(0, 153, 255));
        addNewBookBttn.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        addNewBookBttn.setForeground(new java.awt.Color(255, 255, 255));
        addNewBookBttn.setText("Add Book");
        addNewBookBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewBookBttnActionPerformed(evt);
            }
        });

        addBookTitleParentScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        addBookTitleValueTxtArea.setColumns(20);
        addBookTitleValueTxtArea.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        addBookTitleValueTxtArea.setLineWrap(true);
        addBookTitleValueTxtArea.setRows(2);
        addBookTitleValueTxtArea.setWrapStyleWord(true);
        addBookTitleValueTxtArea.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Title", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N
        addBookTitleParentScrollPane.setViewportView(addBookTitleValueTxtArea);

        addBookAuthorParentScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        addBookAuthorValueTxtArea.setColumns(20);
        addBookAuthorValueTxtArea.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        addBookAuthorValueTxtArea.setLineWrap(true);
        addBookAuthorValueTxtArea.setRows(2);
        addBookAuthorValueTxtArea.setToolTipText("Separate multiple authors with comma");
        addBookAuthorValueTxtArea.setWrapStyleWord(true);
        addBookAuthorValueTxtArea.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Author/s", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N
        addBookAuthorParentScrollPane.setViewportView(addBookAuthorValueTxtArea);

        addBookCategoryValueTxtFld.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        addBookCategoryValueTxtFld.setToolTipText("Separate multiple categories with comma");
        addBookCategoryValueTxtFld.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Category", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N

        addBookTotalQuantityValueTxtFld.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        addBookTotalQuantityValueTxtFld.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Total Quantity", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N
        addBookTotalQuantityValueTxtFld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBookTotalQuantityValueTxtFldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout addBookPanelLayout = new javax.swing.GroupLayout(addBookPanel);
        addBookPanel.setLayout(addBookPanelLayout);
        addBookPanelLayout.setHorizontalGroup(
            addBookPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addBookPanelLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(addBookCardImgLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(addBookPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(addBookAuthorParentScrollPane, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addBookTitleParentScrollPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
                    .addComponent(addBookCategoryValueTxtFld)
                    .addComponent(addBookTotalQuantityValueTxtFld)
                    .addComponent(addNewBookBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 36, Short.MAX_VALUE))
        );
        addBookPanelLayout.setVerticalGroup(
            addBookPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addBookPanelLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(addBookPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(addBookCardImgLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(addBookPanelLayout.createSequentialGroup()
                        .addComponent(addBookTitleParentScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(addBookAuthorParentScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(addBookCategoryValueTxtFld, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(addBookTotalQuantityValueTxtFld, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addNewBookBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(78, Short.MAX_VALUE))
        );

        centerPanel.add(addBookPanel, "addBookCard");

        searchBookLbl.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        searchBookLbl.setText("Search Book:");

        searchBookTxtFld.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        searchBookTxtFld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBookTxtFldActionPerformed(evt);
            }
        });

        bookJList.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Books", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N
        bookJList.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        bookJList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        bookListParentScrollPane.setViewportView(bookJList);

        filterByCategoryLbl.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        filterByCategoryLbl.setText("Filter by Category:");

        bookCategoriesComboBox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        bookCategoriesComboBox = new javax.swing.JComboBox<>(psDatabase.getAllCategories().toArray(new String[0]));
        bookCategoriesComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookCategoriesComboBoxActionPerformed(evt);
            }
        });

        addBookTitleParentScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        bookStockTitleValueTxtArea.setEditable(false);
        bookStockTitleValueTxtArea.setColumns(20);
        bookStockTitleValueTxtArea.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        bookStockTitleValueTxtArea.setLineWrap(true);
        bookStockTitleValueTxtArea.setRows(2);
        bookStockTitleValueTxtArea.setWrapStyleWord(true);
        bookStockTitleValueTxtArea.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Title", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N
        addBookTitleParentScrollPane1.setViewportView(bookStockTitleValueTxtArea);

        addBookAuthorParentScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        bookStockAuthorValueTxtArea.setEditable(false);
        bookStockAuthorValueTxtArea.setColumns(20);
        bookStockAuthorValueTxtArea.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        bookStockAuthorValueTxtArea.setLineWrap(true);
        bookStockAuthorValueTxtArea.setRows(2);
        bookStockAuthorValueTxtArea.setToolTipText("Separate multiple authors with comma");
        bookStockAuthorValueTxtArea.setWrapStyleWord(true);
        bookStockAuthorValueTxtArea.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Author/s", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N
        addBookAuthorParentScrollPane1.setViewportView(bookStockAuthorValueTxtArea);

        bookStockCategoryTxtFld.setEditable(false);
        bookStockCategoryTxtFld.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        bookStockCategoryTxtFld.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Category", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N

        bookStockTotalQuantityTxtFld.setEditable(false);
        bookStockTotalQuantityTxtFld.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        bookStockTotalQuantityTxtFld.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Total Quantity", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N

        bookStockAvailableQuantityTxtFld.setEditable(false);
        bookStockAvailableQuantityTxtFld.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        bookStockAvailableQuantityTxtFld.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Available Quantity", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N

        increaseTotalQuantityBttn.setBackground(new java.awt.Color(0, 153, 255));
        increaseTotalQuantityBttn.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        increaseTotalQuantityBttn.setForeground(new java.awt.Color(255, 255, 255));
        increaseTotalQuantityBttn.setText("Increase Total Quantity");
        increaseTotalQuantityBttn.setEnabled(false);
        increaseTotalQuantityBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                increaseTotalQuantityBttnActionPerformed(evt);
            }
        });

        decreaseTotalQuantityBttn.setBackground(java.awt.Color.red);
        decreaseTotalQuantityBttn.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        decreaseTotalQuantityBttn.setForeground(new java.awt.Color(255, 255, 255));
        decreaseTotalQuantityBttn.setText("Decrease Total Quantity");
        decreaseTotalQuantityBttn.setToolTipText("You can only decrease the total quantity if all the copies of the selected book are returned");
        decreaseTotalQuantityBttn.setEnabled(false);
        decreaseTotalQuantityBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                decreaseTotalQuantityBttnActionPerformed(evt);
            }
        });

        refreshBookListBttn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/reload.png"))); // NOI18N
        refreshBookListBttn.setToolTipText("Refresh Books");
        refreshBookListBttn.setBorderPainted(false);
        refreshBookListBttn.setContentAreaFilled(false);
        refreshBookListBttn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        refreshBookListBttn.setFocusable(false);
        refreshBookListBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshBookListBttnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bookStockPanelLayout = new javax.swing.GroupLayout(bookStockPanel);
        bookStockPanel.setLayout(bookStockPanelLayout);
        bookStockPanelLayout.setHorizontalGroup(
            bookStockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bookStockPanelLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(bookStockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bookStockPanelLayout.createSequentialGroup()
                        .addComponent(filterByCategoryLbl)
                        .addGap(17, 17, 17)
                        .addComponent(bookCategoriesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(refreshBookListBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(searchBookLbl))
                    .addGroup(bookStockPanelLayout.createSequentialGroup()
                        .addComponent(decreaseTotalQuantityBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(increaseTotalQuantityBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(bookStockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(addBookAuthorParentScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(addBookTitleParentScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(bookStockCategoryTxtFld)
                        .addComponent(bookStockTotalQuantityTxtFld, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
                        .addComponent(bookStockAvailableQuantityTxtFld, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bookStockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bookListParentScrollPane)
                    .addComponent(searchBookTxtFld, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE))
                .addGap(47, 47, 47))
        );
        bookStockPanelLayout.setVerticalGroup(
            bookStockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bookStockPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bookStockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(refreshBookListBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 29, Short.MAX_VALUE)
                    .addGroup(bookStockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(bookCategoriesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(filterByCategoryLbl))
                    .addGroup(bookStockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(searchBookTxtFld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(searchBookLbl)))
                .addGap(18, 18, 18)
                .addGroup(bookStockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(bookStockPanelLayout.createSequentialGroup()
                        .addComponent(addBookTitleParentScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(addBookAuthorParentScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(bookStockCategoryTxtFld, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bookStockTotalQuantityTxtFld, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(bookStockAvailableQuantityTxtFld, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addGroup(bookStockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(decreaseTotalQuantityBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(increaseTotalQuantityBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(bookListParentScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        centerPanel.add(bookStockPanel, "bookStockCard");

        registerStudentCardImgLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        registerStudentCardImgLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/registerStudentCardIcon.png"))); // NOI18N

        registerStudentNameTxtFld.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        registerStudentNameTxtFld.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Name", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N

        registerStudentCourseTxtFld.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        registerStudentCourseTxtFld.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Course & Section", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N

        registerStudentIDTxtFld.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        registerStudentIDTxtFld.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Student ID", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N

        registerStudentPasswordFld.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        registerStudentPasswordFld.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Set Password", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N
        registerStudentPasswordFld.setEchoChar((char)8226);

        registerStudentNewBttn.setBackground(new java.awt.Color(0, 153, 255));
        registerStudentNewBttn.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        registerStudentNewBttn.setForeground(new java.awt.Color(255, 255, 255));
        registerStudentNewBttn.setText("Register Student");
        registerStudentNewBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerStudentNewBttnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout registerStudentPanelLayout = new javax.swing.GroupLayout(registerStudentPanel);
        registerStudentPanel.setLayout(registerStudentPanelLayout);
        registerStudentPanelLayout.setHorizontalGroup(
            registerStudentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, registerStudentPanelLayout.createSequentialGroup()
                .addGroup(registerStudentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, registerStudentPanelLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(registerStudentCardImgLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(registerStudentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(registerStudentPanelLayout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(registerStudentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(registerStudentNameTxtFld, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
                                    .addComponent(registerStudentCourseTxtFld, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(registerStudentPanelLayout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(registerStudentPasswordFld))
                            .addGroup(registerStudentPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(registerStudentNewBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(registerStudentPanelLayout.createSequentialGroup()
                        .addGap(408, 408, 408)
                        .addComponent(registerStudentIDTxtFld)))
                .addGap(36, 36, 36))
        );
        registerStudentPanelLayout.setVerticalGroup(
            registerStudentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(registerStudentPanelLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(registerStudentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(registerStudentPanelLayout.createSequentialGroup()
                        .addComponent(registerStudentNameTxtFld, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(registerStudentCourseTxtFld, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(registerStudentIDTxtFld, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(registerStudentPasswordFld, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(registerStudentNewBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(registerStudentCardImgLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(69, Short.MAX_VALUE))
        );

        centerPanel.add(registerStudentPanel, "registerStudentCard");

        searchIssuedBookLbl.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        searchIssuedBookLbl.setText("Search Issued Book:");

        searchIssuedBooksTxtFld.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        searchIssuedBooksTxtFld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchIssuedBooksTxtFldActionPerformed(evt);
            }
        });

        issuedBooksJList.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Issued Books", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N
        issuedBooksJList.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        issuedBooksJList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        issuedBooksListParentScrollPane.setViewportView(issuedBooksJList);

        studentsWhoBorrowedJList.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Students Who Borrowed", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N
        studentsWhoBorrowedJList.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        studentsWhoBorrowedJList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        studentsWhoBorrowedParentScrollPane.setViewportView(studentsWhoBorrowedJList);

        issuedBooksDateBorrowedValueLbl.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        issuedBooksDateBorrowedValueLbl.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Date Borrowed", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N

        issuedBooksDueDateValueLbl.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        issuedBooksDueDateValueLbl.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Due Date", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N

        resetIssuedBooksBttn.setBackground(new java.awt.Color(51, 204, 0));
        resetIssuedBooksBttn.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        resetIssuedBooksBttn.setForeground(new java.awt.Color(255, 255, 255));
        resetIssuedBooksBttn.setText("Refresh Issued Books");
        resetIssuedBooksBttn.setFocusable(false);
        resetIssuedBooksBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetIssuedBooksBttnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout issuedBooksPanelLayout = new javax.swing.GroupLayout(issuedBooksPanel);
        issuedBooksPanel.setLayout(issuedBooksPanelLayout);
        issuedBooksPanelLayout.setHorizontalGroup(
            issuedBooksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(issuedBooksPanelLayout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addGroup(issuedBooksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(issuedBooksPanelLayout.createSequentialGroup()
                        .addComponent(resetIssuedBooksBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(searchIssuedBookLbl))
                    .addGroup(issuedBooksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(issuedBooksDateBorrowedValueLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(studentsWhoBorrowedParentScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(issuedBooksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(issuedBooksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(searchIssuedBooksTxtFld)
                        .addComponent(issuedBooksListParentScrollPane))
                    .addComponent(issuedBooksDueDateValueLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(99, 99, 99))
        );
        issuedBooksPanelLayout.setVerticalGroup(
            issuedBooksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, issuedBooksPanelLayout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(issuedBooksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchIssuedBookLbl)
                    .addComponent(searchIssuedBooksTxtFld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resetIssuedBooksBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(issuedBooksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(studentsWhoBorrowedParentScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(issuedBooksListParentScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57)
                .addGroup(issuedBooksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(issuedBooksDateBorrowedValueLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(issuedBooksDueDateValueLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(71, 71, 71))
        );

        centerPanel.add(issuedBooksPanel, "issuedBooksCard");

        searchStudentLbl.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        searchStudentLbl.setText("Search Student:");

        searchStudentTxtFld.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        searchStudentTxtFld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchStudentTxtFldActionPerformed(evt);
            }
        });

        studentJList.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Students", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N
        studentJList.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        studentJList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        studentListParentScrollPane.setViewportView(studentJList);

        studentIDValueLbl.setEditable(false);
        studentIDValueLbl.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        studentIDValueLbl.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Student ID", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N

        studentCourseSectionValueLbl.setEditable(false);
        studentCourseSectionValueLbl.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        studentCourseSectionValueLbl.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Course & Section", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N

        studentNameValueLbl.setEditable(false);
        studentNameValueLbl.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        studentNameValueLbl.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Name", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N

        studentPenaltyRecordValueLbl.setEditable(false);
        studentPenaltyRecordValueLbl.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        studentPenaltyRecordValueLbl.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Penalty Record", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N

        resetStudentListBttn.setBackground(new java.awt.Color(51, 204, 0));
        resetStudentListBttn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        resetStudentListBttn.setForeground(new java.awt.Color(255, 255, 255));
        resetStudentListBttn.setText("Refresh Student List");
        resetStudentListBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetStudentListBttnActionPerformed(evt);
            }
        });

        unregisterSelectedStudentBttn.setBackground(java.awt.Color.red);
        unregisterSelectedStudentBttn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        unregisterSelectedStudentBttn.setForeground(new java.awt.Color(255, 255, 255));
        unregisterSelectedStudentBttn.setText("Unregister Selected Student");
        unregisterSelectedStudentBttn.setEnabled(false);
        unregisterSelectedStudentBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unregisterSelectedStudentBttnActionPerformed(evt);
            }
        });

        editCourseSectionBttn.setBackground(new java.awt.Color(0, 153, 255));
        editCourseSectionBttn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        editCourseSectionBttn.setForeground(new java.awt.Color(255, 255, 255));
        editCourseSectionBttn.setText("Edit");
        editCourseSectionBttn.setEnabled(false);
        editCourseSectionBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editCourseSectionBttnActionPerformed(evt);
            }
        });

        seeAllBorrowedBooks.setBackground(new java.awt.Color(0, 153, 255));
        seeAllBorrowedBooks.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        seeAllBorrowedBooks.setForeground(new java.awt.Color(255, 255, 255));
        seeAllBorrowedBooks.setText("See All Borrowed Books");
        seeAllBorrowedBooks.setEnabled(false);
        seeAllBorrowedBooks.setFocusable(false);
        seeAllBorrowedBooks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seeAllBorrowedBooksActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout studentsCardPanelLayout = new javax.swing.GroupLayout(studentsCardPanel);
        studentsCardPanel.setLayout(studentsCardPanelLayout);
        studentsCardPanelLayout.setHorizontalGroup(
            studentsCardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentsCardPanelLayout.createSequentialGroup()
                .addGroup(studentsCardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(studentsCardPanelLayout.createSequentialGroup()
                        .addGap(476, 476, 476)
                        .addComponent(searchStudentLbl))
                    .addGroup(studentsCardPanelLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addGroup(studentsCardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(editCourseSectionBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(studentsCardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(studentIDValueLbl, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(studentCourseSectionValueLbl, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(studentPenaltyRecordValueLbl, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(studentNameValueLbl, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, studentsCardPanelLayout.createSequentialGroup()
                                    .addComponent(unregisterSelectedStudentBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(resetStudentListBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(seeAllBorrowedBooks, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(6, 6, 6)
                .addGroup(studentsCardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(studentListParentScrollPane)
                    .addComponent(searchStudentTxtFld, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE))
                .addGap(49, 49, 49))
        );
        studentsCardPanelLayout.setVerticalGroup(
            studentsCardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentsCardPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(studentsCardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchStudentLbl)
                    .addComponent(searchStudentTxtFld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seeAllBorrowedBooks))
                .addGap(18, 18, 18)
                .addGroup(studentsCardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(studentListParentScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(studentsCardPanelLayout.createSequentialGroup()
                        .addComponent(studentNameValueLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(studentCourseSectionValueLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editCourseSectionBttn)
                        .addGap(26, 26, 26)
                        .addComponent(studentIDValueLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57)
                        .addComponent(studentPenaltyRecordValueLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(studentsCardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(resetStudentListBttn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(unregisterSelectedStudentBttn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(37, 37, 37))
        );

        centerPanel.add(studentsCardPanel, "studentsCard");

        adminCardPanel.setLayout(null);

        libraryAdminJList.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Library Admins", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N
        libraryAdminJList.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        libraryAdminJList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        libraryAdminListParentScrollPane.setViewportView(libraryAdminJList);

        adminCardPanel.add(libraryAdminListParentScrollPane);
        libraryAdminListParentScrollPane.setBounds(643, 56, 258, 591);

        selectedAdminNameValueLbl.setEditable(false);
        selectedAdminNameValueLbl.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        selectedAdminNameValueLbl.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Name", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N
        adminCardPanel.add(selectedAdminNameValueLbl);
        selectedAdminNameValueLbl.setBounds(37, 56, 561, 86);

        selectedAdminIDValueLbl.setEditable(false);
        selectedAdminIDValueLbl.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        selectedAdminIDValueLbl.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Administrator ID", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N
        adminCardPanel.add(selectedAdminIDValueLbl);
        selectedAdminIDValueLbl.setBounds(37, 171, 561, 86);

        registerNewAdminBttn.setBackground(new java.awt.Color(0, 153, 255));
        registerNewAdminBttn.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        registerNewAdminBttn.setForeground(new java.awt.Color(255, 255, 255));
        registerNewAdminBttn.setText("Register New Administrator");
        registerNewAdminBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerNewAdminBttnActionPerformed(evt);
            }
        });
        adminCardPanel.add(registerNewAdminBttn);
        registerNewAdminBttn.setBounds(39, 286, 561, 33);

        newAdminNameTxtFld.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        newAdminNameTxtFld.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Admin Name", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N
        newAdminNameTxtFld.setVisible(false);
        adminCardPanel.add(newAdminNameTxtFld);
        newAdminNameTxtFld.setBounds(39, 350, 559, 58);

        newAdminIDTxtFld.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        newAdminIDTxtFld.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Admin ID", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N
        newAdminIDTxtFld.setVisible(false);
        adminCardPanel.add(newAdminIDTxtFld);
        newAdminIDTxtFld.setBounds(39, 438, 559, 58);

        newAdminPasswordFld.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        newAdminPasswordFld.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Set Password", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N
        newAdminPasswordFld.setVisible(false);
        newAdminPasswordFld.setEchoChar((char)8226);
        adminCardPanel.add(newAdminPasswordFld);
        newAdminPasswordFld.setBounds(39, 526, 559, 58);

        cancelRegistrationNewAdminBttn.setBackground(java.awt.Color.red);
        cancelRegistrationNewAdminBttn.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cancelRegistrationNewAdminBttn.setForeground(new java.awt.Color(255, 255, 255));
        cancelRegistrationNewAdminBttn.setText("Cancel");
        cancelRegistrationNewAdminBttn.setVisible(false);
        cancelRegistrationNewAdminBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelRegistrationNewAdminBttnActionPerformed(evt);
            }
        });
        adminCardPanel.add(cancelRegistrationNewAdminBttn);
        cancelRegistrationNewAdminBttn.setBounds(39, 614, 270, 33);

        registerNowNewAdminBttn.setBackground(new java.awt.Color(0, 153, 255));
        registerNowNewAdminBttn.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        registerNowNewAdminBttn.setForeground(new java.awt.Color(255, 255, 255));
        registerNowNewAdminBttn.setText("Register Now");
        registerNowNewAdminBttn.setVisible(false);
        registerNowNewAdminBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerNowNewAdminBttnActionPerformed(evt);
            }
        });
        adminCardPanel.add(registerNowNewAdminBttn);
        registerNowNewAdminBttn.setBounds(327, 614, 270, 33);

        removeSelectedAdminBttn.setBackground(java.awt.Color.red);
        removeSelectedAdminBttn.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        removeSelectedAdminBttn.setForeground(new java.awt.Color(255, 255, 255));
        removeSelectedAdminBttn.setText("Remove Selected Admin");
        removeSelectedAdminBttn.setEnabled(false);
        removeSelectedAdminBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeSelectedAdminBttnActionPerformed(evt);
            }
        });
        adminCardPanel.add(removeSelectedAdminBttn);
        removeSelectedAdminBttn.setBounds(643, 12, 258, 33);

        centerPanel.add(adminCardPanel, "adminsCard");

        adminDashboardBottomPanel.add(centerPanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(adminDashboardBottomPanel, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(1200, 900));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void logOutBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOutBttnActionPerformed
        startForm.removeOpenDashboard(this);
        psDatabase.getAdmin(loggedAdmin.getAdminID()).setNowLogged(false);
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                writePseudoDatabaseSer();
                return null;
            }

            @Override
            protected void done() {
                dispose();
            }
        }.execute();
    }//GEN-LAST:event_logOutBttnActionPerformed

    private void addBookBttnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addBookBttnMouseEntered
        if (!addBookBttn.isContentAreaFilled()) {
            addBookBttn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 24));
        }
    }//GEN-LAST:event_addBookBttnMouseEntered

    private void addBookBttnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addBookBttnMouseExited
        addBookBttn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 24));
    }//GEN-LAST:event_addBookBttnMouseExited

    private void bookStockBttnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bookStockBttnMouseEntered
        if (!bookStockBttn.isContentAreaFilled()) {
            bookStockBttn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 24));
        }
    }//GEN-LAST:event_bookStockBttnMouseEntered

    private void bookStockBttnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bookStockBttnMouseExited
        bookStockBttn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 24));
    }//GEN-LAST:event_bookStockBttnMouseExited

    private void registerStudentBttnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerStudentBttnMouseEntered
        if (!registerStudentBttn.isContentAreaFilled()) {
            registerStudentBttn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 24));
        }
    }//GEN-LAST:event_registerStudentBttnMouseEntered

    private void registerStudentBttnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerStudentBttnMouseExited
        registerStudentBttn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 24));
    }//GEN-LAST:event_registerStudentBttnMouseExited

    private void issuedBooksBttnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_issuedBooksBttnMouseEntered
        if (!issuedBooksBttn.isContentAreaFilled()) {
            issuedBooksBttn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 24));
        }
    }//GEN-LAST:event_issuedBooksBttnMouseEntered

    private void issuedBooksBttnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_issuedBooksBttnMouseExited
        issuedBooksBttn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 24));
    }//GEN-LAST:event_issuedBooksBttnMouseExited

    private void studentsWestPanelBttnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_studentsWestPanelBttnMouseEntered
        if (!studentsWestPanelBttn.isContentAreaFilled()) {
            studentsWestPanelBttn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 24));
        }
    }//GEN-LAST:event_studentsWestPanelBttnMouseEntered

    private void studentsWestPanelBttnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_studentsWestPanelBttnMouseExited
        studentsWestPanelBttn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 24));
    }//GEN-LAST:event_studentsWestPanelBttnMouseExited

    private void adminWestPanelBttnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminWestPanelBttnMouseEntered
        if (!adminWestPanelBttn.isContentAreaFilled()) {
            adminWestPanelBttn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 24));
        }
    }//GEN-LAST:event_adminWestPanelBttnMouseEntered

    private void adminWestPanelBttnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminWestPanelBttnMouseExited
        adminWestPanelBttn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 24));
    }//GEN-LAST:event_adminWestPanelBttnMouseExited

    private void logOutBttnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logOutBttnMouseEntered
        logOutBttn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 24));
    }//GEN-LAST:event_logOutBttnMouseEntered

    private void logOutBttnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logOutBttnMouseExited
        logOutBttn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 24));
    }//GEN-LAST:event_logOutBttnMouseExited

    private void addBookBttnMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addBookBttnMousePressed
        addBookBttn.setContentAreaFilled(true);
        bookStockBttn.setContentAreaFilled(false);
        registerStudentBttn.setContentAreaFilled(false);
        issuedBooksBttn.setContentAreaFilled(false);
        studentsWestPanelBttn.setContentAreaFilled(false);
        adminWestPanelBttn.setContentAreaFilled(false);
        logOutBttn.setContentAreaFilled(false);
    }//GEN-LAST:event_addBookBttnMousePressed

    private void bookStockBttnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bookStockBttnMouseClicked
        addBookBttn.setContentAreaFilled(false);
        bookStockBttn.setContentAreaFilled(true);
        registerStudentBttn.setContentAreaFilled(false);
        issuedBooksBttn.setContentAreaFilled(false);
        studentsWestPanelBttn.setContentAreaFilled(false);
        adminWestPanelBttn.setContentAreaFilled(false);
        logOutBttn.setContentAreaFilled(false);
    }//GEN-LAST:event_bookStockBttnMouseClicked

    private void registerStudentBttnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerStudentBttnMouseClicked
        addBookBttn.setContentAreaFilled(false);
        bookStockBttn.setContentAreaFilled(false);
        registerStudentBttn.setContentAreaFilled(true);
        issuedBooksBttn.setContentAreaFilled(false);
        studentsWestPanelBttn.setContentAreaFilled(false);
        adminWestPanelBttn.setContentAreaFilled(false);
        logOutBttn.setContentAreaFilled(false);
    }//GEN-LAST:event_registerStudentBttnMouseClicked

    private void issuedBooksBttnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_issuedBooksBttnMouseClicked
        addBookBttn.setContentAreaFilled(false);
        bookStockBttn.setContentAreaFilled(false);
        registerStudentBttn.setContentAreaFilled(false);
        issuedBooksBttn.setContentAreaFilled(true);
        studentsWestPanelBttn.setContentAreaFilled(false);
        adminWestPanelBttn.setContentAreaFilled(false);
        logOutBttn.setContentAreaFilled(false);
    }//GEN-LAST:event_issuedBooksBttnMouseClicked

    private void studentsWestPanelBttnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_studentsWestPanelBttnMouseClicked
        addBookBttn.setContentAreaFilled(false);
        bookStockBttn.setContentAreaFilled(false);
        registerStudentBttn.setContentAreaFilled(false);
        issuedBooksBttn.setContentAreaFilled(false);
        studentsWestPanelBttn.setContentAreaFilled(true);
        adminWestPanelBttn.setContentAreaFilled(false);
        logOutBttn.setContentAreaFilled(false);
    }//GEN-LAST:event_studentsWestPanelBttnMouseClicked

    private void adminWestPanelBttnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminWestPanelBttnMouseClicked
        addBookBttn.setContentAreaFilled(false);
        bookStockBttn.setContentAreaFilled(false);
        registerStudentBttn.setContentAreaFilled(false);
        issuedBooksBttn.setContentAreaFilled(false);
        studentsWestPanelBttn.setContentAreaFilled(false);
        adminWestPanelBttn.setContentAreaFilled(true);
        logOutBttn.setContentAreaFilled(false);
    }//GEN-LAST:event_adminWestPanelBttnMouseClicked

    private void logOutBttnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logOutBttnMouseClicked
        addBookBttn.setContentAreaFilled(false);
        bookStockBttn.setContentAreaFilled(false);
        registerStudentBttn.setContentAreaFilled(false);
        issuedBooksBttn.setContentAreaFilled(false);
        studentsWestPanelBttn.setContentAreaFilled(false);
        adminWestPanelBttn.setContentAreaFilled(false);
        logOutBttn.setContentAreaFilled(true);
    }//GEN-LAST:event_logOutBttnMouseClicked

    private void addBookBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBookBttnActionPerformed
        cards = (java.awt.CardLayout) centerPanel.getLayout();
        cards.show(centerPanel, "addBookCard");
    }//GEN-LAST:event_addBookBttnActionPerformed

    private void bookStockBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookStockBttnActionPerformed
        readPseudoDatabaseSer();
        javax.swing.SwingUtilities.invokeLater(() -> {
            cards = (java.awt.CardLayout) centerPanel.getLayout();
            cards.show(centerPanel, "bookStockCard");
        });
    }//GEN-LAST:event_bookStockBttnActionPerformed

    private void registerStudentBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerStudentBttnActionPerformed
        cards = (java.awt.CardLayout) centerPanel.getLayout();
        cards.show(centerPanel, "registerStudentCard");
    }//GEN-LAST:event_registerStudentBttnActionPerformed

    private void issuedBooksBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_issuedBooksBttnActionPerformed
        cards = (java.awt.CardLayout) centerPanel.getLayout();
        cards.show(centerPanel, "issuedBooksCard");
    }//GEN-LAST:event_issuedBooksBttnActionPerformed

    private void studentsWestPanelBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentsWestPanelBttnActionPerformed
        cards = (java.awt.CardLayout) centerPanel.getLayout();
        cards.show(centerPanel, "studentsCard");
    }//GEN-LAST:event_studentsWestPanelBttnActionPerformed

    private void adminWestPanelBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminWestPanelBttnActionPerformed
        cards = (java.awt.CardLayout) centerPanel.getLayout();
        cards.show(centerPanel, "adminsCard");
    }//GEN-LAST:event_adminWestPanelBttnActionPerformed

    private void addNewBookBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewBookBttnActionPerformed
        new SwingWorker<Boolean, Void>() {

            @Override
            protected Boolean doInBackground() {
                if ((addBookTitleValueTxtArea.getText().isEmpty() || addBookAuthorValueTxtArea.getText().isEmpty())
                        || (addBookCategoryValueTxtFld.getText().isEmpty() || addBookTotalQuantityValueTxtFld.getText().isEmpty())) {
                    JOptionPane.showMessageDialog(null, "Please fill all fields", "Library Management System", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                try {
                    Integer.parseInt(addBookTotalQuantityValueTxtFld.getText());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid input format for Total Quantity", "Library Management System", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                if (Integer.parseInt(addBookTotalQuantityValueTxtFld.getText()) < 1) {
                    JOptionPane.showMessageDialog(null, "Total quantity must be higher than 0", "Library Management System", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                if (psDatabase.getAllBooks().stream().map(b -> b.getTitle()).anyMatch(b -> addBookTitleValueTxtArea.getText().toLowerCase().equals(b.toLowerCase()))) {
                    JOptionPane.showMessageDialog(null, "The book you entered already exists in the Library Management System", "Library Management System", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                return true;
            }

            @Override
            protected void done() {
                try {
                    if (get()) {
                        Book newBook = new Book(addBookTitleValueTxtArea.getText().trim(),
                            addBookAuthorValueTxtArea.getText().trim(),
                            addBookCategoryValueTxtFld.getText().trim(),
                            Integer.parseInt(addBookTotalQuantityValueTxtFld.getText()));
                        psDatabase.addBook(newBook);
                        addBookTitleValueTxtArea.setText("");
                        addBookAuthorValueTxtArea.setText("");
                        addBookCategoryValueTxtFld.setText("");
                        addBookTotalQuantityValueTxtFld.setText("");
                        writePseudoDatabaseSer();
                        JOptionPane.showMessageDialog(null, "Successfully added to the Library Management System", "Library Management System", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExecutionException ex) {
                    Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }.execute();
    }//GEN-LAST:event_addNewBookBttnActionPerformed

    private void bookCategoriesComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookCategoriesComboBoxActionPerformed
        new SwingWorker<Void, Void>(){
            @Override
            protected Void doInBackground() {
                bookJList.setListData(psDatabase.getAllBooks().stream()
                    .filter(b -> b.getCategory().toUpperCase().contains((String)bookCategoriesComboBox.getSelectedItem()))
                    .map(b -> b.getTitle())
                    .collect(Collectors.toList()).toArray(new String[0])
                );
                return null;
            }   
        }.execute();
        
    }//GEN-LAST:event_bookCategoriesComboBoxActionPerformed

    private void searchBookTxtFldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBookTxtFldActionPerformed
        if (!searchBookTxtFld.getText().isEmpty()) {
            bookJList.setListData(psDatabase.getAllBooks().stream()
                    .filter(b -> b.getTitle().toLowerCase().contains(searchBookTxtFld.getText().toLowerCase()))
                    .map(b -> b.getTitle()).collect(Collectors.toList()).toArray(new String[0])
            );
        } else {
            bookJList.setListData(psDatabase.getAllBooks().stream()
                .map(b -> b.getTitle())
                .collect(Collectors.toList())
                .toArray(new String[0])
            );
        }
    }//GEN-LAST:event_searchBookTxtFldActionPerformed

    private void increaseTotalQuantityBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_increaseTotalQuantityBttnActionPerformed
        String input = JOptionPane.showInputDialog("Enter amount to add:");
        if (input != null) {
            try {
                selectedBook.setTotalStock(selectedBook.getTotalStock() + Integer.parseInt(input));
                selectedBook.setAvailable(selectedBook.getAvailable() + Integer.parseInt(input));
                bookStockTitleValueTxtArea.setText("");
                bookStockAuthorValueTxtArea.setText("");
                bookStockCategoryTxtFld.setText("");
                bookStockTotalQuantityTxtFld.setText("");
                bookStockAvailableQuantityTxtFld.setText("");
                new SwingWorker<Void, Void>(){
                    @Override
                    protected Void doInBackground() {
                        writePseudoDatabaseSer();
                        return null;
                    }
                    @Override
                    protected void done() {
                        JOptionPane.showMessageDialog(null, "Success", "Library Management Sytem", JOptionPane.INFORMATION_MESSAGE);
                    }
                }.execute();
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Invalid input", "Library Management System", JOptionPane.ERROR_MESSAGE);
            }
        }

    }//GEN-LAST:event_increaseTotalQuantityBttnActionPerformed

    private void decreaseTotalQuantityBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_decreaseTotalQuantityBttnActionPerformed
        JOptionPane.showMessageDialog(null,
                "If the total available stock of the book after the substraction is lower than 1, the book will be removed from the System",
                "Library Management System",
                JOptionPane.WARNING_MESSAGE);
        String input = JOptionPane.showInputDialog("Enter amount to subtract:");
        if (input != null) {
            try {
                selectedBook.setTotalStock(selectedBook.getTotalStock() - Integer.parseInt(input));
                selectedBook.setAvailable(selectedBook.getAvailable() - Integer.parseInt(input));

                bookStockTitleValueTxtArea.setText("");
                bookStockAuthorValueTxtArea.setText("");
                bookStockCategoryTxtFld.setText("");
                bookStockTotalQuantityTxtFld.setText("");
                bookStockAvailableQuantityTxtFld.setText("");

                if (selectedBook.getTotalStock() < 1) {
                    new SwingWorker<Void, Void>(){
                        @Override
                        protected Void doInBackground() {
                            String[] categories = selectedBook.getCategory().split(",");
                            psDatabase.getAllBooks().remove(psDatabase.getAllBooks().stream()
                                .reduce(null, (sub, current) -> current.getTitle().equals(selectedBook.getTitle())
                                    ? current : sub)
                            );
                            for (String c : categories) {
                                if (!psDatabase.getAllBooks().stream()
                                        .anyMatch(b -> b.getCategory().toUpperCase().contains(c.trim().toUpperCase()))) {
                                    psDatabase.getAllCategories().remove(c.toUpperCase().trim());
                                }
                            }
                            
                            return null;
                        }
                    }.execute(); 
                }
                new SwingWorker<Void, Void>(){
                    @Override
                    protected Void doInBackground() {
                        writePseudoDatabaseSer();
                        return null;
                    }
                    @Override
                    protected void done() {
                        refreshBookListBttnActionPerformed(evt);
                        JOptionPane.showMessageDialog(null, "Successful operation", "Library Management Sytem", JOptionPane.INFORMATION_MESSAGE);
                    }
                }.execute();
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Invalid input", "Library Management System", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_decreaseTotalQuantityBttnActionPerformed

    private void addBookTotalQuantityValueTxtFldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBookTotalQuantityValueTxtFldActionPerformed
        addNewBookBttnActionPerformed(evt);
    }//GEN-LAST:event_addBookTotalQuantityValueTxtFldActionPerformed

    private void registerStudentNewBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerStudentNewBttnActionPerformed
        if ((registerStudentNameTxtFld.getText().isEmpty() || registerStudentCourseTxtFld.getText().isEmpty())
                || (registerStudentIDTxtFld.getText().isEmpty() || registerStudentPasswordFld.getPassword().length == 0)) {
            JOptionPane.showMessageDialog(null, "Please fill all fields", "Library Management System", JOptionPane.ERROR_MESSAGE);
        } else if (psDatabase.getAllStudents().stream().anyMatch(st -> registerStudentIDTxtFld.getText().equals(st.getStudentID()))) {
            JOptionPane.showMessageDialog(null, "The Student ID you entered is already taken", "Library Management System", JOptionPane.ERROR_MESSAGE);
        } else if (registerStudentPasswordFld.getPassword().length < 10) {
            JOptionPane.showMessageDialog(null, "Password length must greater than (9) characters", "Library Management System", JOptionPane.ERROR_MESSAGE);
        } else {
            String[] courseAndSectionSplit = registerStudentCourseTxtFld.getText().trim().split("-");
            Student newStudent = new Student(registerStudentNameTxtFld.getText().trim(),
                    courseAndSectionSplit[0],
                    courseAndSectionSplit[1],
                    registerStudentIDTxtFld.getText().trim(),
                    registerStudentPasswordFld.getPassword()
            );
            psDatabase.registerNewStudent(newStudent);
            new SwingWorker<Void, Void>(){
                @Override
                protected Void doInBackground() {
                    writePseudoDatabaseSer();
                    return null;
                }
                @Override
                protected void done() {
                    JOptionPane.showMessageDialog(null, "Student registration successful", "Library Management System", JOptionPane.INFORMATION_MESSAGE);
                    registerStudentNameTxtFld.setText("");
                    registerStudentCourseTxtFld.setText("");
                    registerStudentIDTxtFld.setText("");
                    registerStudentPasswordFld.setText("");
                }
            }.execute();
        }
    }//GEN-LAST:event_registerStudentNewBttnActionPerformed

    private void searchIssuedBooksTxtFldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchIssuedBooksTxtFldActionPerformed
        if (!searchIssuedBooksTxtFld.getText().isEmpty()) {
            issuedBooksJList.setListData(psDatabase.getAllBooks().stream()
                    .filter(b -> b.getWhoBorrowed().size() > 0
                    && b.getTitle().toLowerCase().contains(searchIssuedBooksTxtFld.getText().toLowerCase()))
                    .map(b -> b.getTitle())
                    .collect(Collectors.toList())
                    .toArray(new String[0])
            );
            studentsWhoBorrowedJList.setListData(new String[0]);
        } else {
            resetIssuedBooksBttnActionPerformed(evt);
        }
    }//GEN-LAST:event_searchIssuedBooksTxtFldActionPerformed

    private void resetIssuedBooksBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetIssuedBooksBttnActionPerformed
        new SwingWorker<Void, Void>(){
            @Override
            protected Void doInBackground() {
                readPseudoDatabaseSer();
                return null;
            }
            @Override
            protected void done() {
                issuedBooksJList.setListData(psDatabase.getAllBooks().stream()
                    .filter(b -> b.getWhoBorrowed().size() > 0)
                    .map(b -> b.getTitle())
                    .collect(Collectors.toList())
                    .toArray(new String[0])
                    );
                studentsWhoBorrowedJList.setListData(new String[0]);
            }
        }.execute();
    }//GEN-LAST:event_resetIssuedBooksBttnActionPerformed

    private void resetStudentListBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetStudentListBttnActionPerformed
        new SwingWorker<Void, Void>(){
            @Override
            protected Void doInBackground() {
                readPseudoDatabaseSer();
                return null;
            }
            @Override
            protected void done() {
                studentJList.setListData(psDatabase.getAllStudents().stream()
                        .map(s -> s.getName())
                        .collect(Collectors.toList())
                        .toArray(new String[0])
                );
                searchStudentTxtFld.setText("");
            }
        }.execute();
    }//GEN-LAST:event_resetStudentListBttnActionPerformed

    private void unregisterSelectedStudentBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unregisterSelectedStudentBttnActionPerformed
        new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() {
                selectedSt = psDatabase.getAllStudents().stream()
                        .reduce(null, (sub, current) -> current.getName().equals(studentJList.getSelectedValue())
                        ? current : sub);
                if (selectedSt.getBorrowedBooks().size() > 0) {
                    return false;
                } else {
                    psDatabase.removeStudent(selectedSt);
                    studentJList.setListData(psDatabase.getAllStudents().stream()
                        .map(s -> s.getName())
                        .collect(Collectors.toList())
                        .toArray(new String[0])
                    );
                    searchStudentTxtFld.setText("");
                    writePseudoDatabaseSer();
                    return true;
                }
            }

            protected void done() {
                try {
                    if (get()) {
                        JOptionPane.showMessageDialog(null, "Successful operation", "Library Management System", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, selectedSt.getName().concat(" still has books to return"), "Library Management System", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExecutionException ex) {
                    Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.execute();
    }//GEN-LAST:event_unregisterSelectedStudentBttnActionPerformed

    private void searchStudentTxtFldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchStudentTxtFldActionPerformed
        if (!searchStudentTxtFld.getText().isEmpty()) {
            studentJList.setListData(psDatabase.getAllStudents().stream()
                    .filter(s -> s.getName().toLowerCase().contains(searchStudentTxtFld.getText().toLowerCase()))
                    .map(s -> s.getName())
                    .collect(Collectors.toList())
                    .toArray(new String[0])
            );
        } else {
            resetStudentListBttnActionPerformed(evt);
        }
    }//GEN-LAST:event_searchStudentTxtFldActionPerformed

    private void editCourseSectionBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editCourseSectionBttnActionPerformed
        JOptionPane.showMessageDialog(null, "The format of the input should be: COURSE-SECTION", "Library Management System", JOptionPane.INFORMATION_MESSAGE);
        String newCourseSection = JOptionPane.showInputDialog("Enter Course-Section: ");
        if (newCourseSection != null) {
            String[] courseSectionArray = newCourseSection.trim().toUpperCase().split("-");
            if (courseSectionArray.length != 2) {
                JOptionPane.showMessageDialog(null, "Invalid input format", "Library Management System", JOptionPane.ERROR_MESSAGE);
            } else {
                selectedSt = psDatabase.getAllStudents().stream()
                        .reduce(null, (sub, current) -> current.getName().equals(studentJList.getSelectedValue())
                        ? current : sub);
                selectedSt.setCourse(courseSectionArray[0]);
                selectedSt.setSection(courseSectionArray[1]);
                new SwingWorker<Void, Void>(){
                    @Override
                    protected Void doInBackground() {
                        writePseudoDatabaseSer();
                        return null;
                    }
                    @Override
                    protected void done() {
                        JOptionPane.showMessageDialog(null, "Successful operation", "Library Management System", JOptionPane.INFORMATION_MESSAGE);
                    }
                }.execute();
            }//end of inner if-else condition
        }//end of outer if condition
    }//GEN-LAST:event_editCourseSectionBttnActionPerformed

    private void seeAllBorrowedBooksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seeAllBorrowedBooksActionPerformed
        SelectedStudentBorrowedBooks dialog = new SelectedStudentBorrowedBooks(this, true);
        selectedSt = psDatabase.getAllStudents().stream()
                .reduce(null, (sub, current) -> current.getName().equals(studentJList.getSelectedValue())
                ? current : sub);
        dialog.initializeStudentAndPsdatabase(selectedSt, psDatabase);
        dialog.setVisible(true);
    }//GEN-LAST:event_seeAllBorrowedBooksActionPerformed

    private void registerNewAdminBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerNewAdminBttnActionPerformed
        registerNewAdminBttn.setEnabled(false);
        newAdminNameTxtFld.setVisible(true);
        newAdminIDTxtFld.setVisible(true);
        newAdminPasswordFld.setVisible(true);
        cancelRegistrationNewAdminBttn.setVisible(true);
        registerNowNewAdminBttn.setVisible(true);
    }//GEN-LAST:event_registerNewAdminBttnActionPerformed

    private void cancelRegistrationNewAdminBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelRegistrationNewAdminBttnActionPerformed
        registerNewAdminBttn.setEnabled(true);
        newAdminNameTxtFld.setVisible(false);
        newAdminIDTxtFld.setVisible(false);
        newAdminPasswordFld.setVisible(false);
        cancelRegistrationNewAdminBttn.setVisible(false);
        registerNowNewAdminBttn.setVisible(false);
    }//GEN-LAST:event_cancelRegistrationNewAdminBttnActionPerformed

    private void registerNowNewAdminBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerNowNewAdminBttnActionPerformed
        if ((newAdminNameTxtFld.getText().isEmpty() || newAdminIDTxtFld.getText().isEmpty())
                || newAdminPasswordFld.getPassword().length == 0) {
            JOptionPane.showMessageDialog(null, "Please fill all fields", "Library Management System", JOptionPane.WARNING_MESSAGE);
        } else if (newAdminPasswordFld.getPassword().length < 10) {
            JOptionPane.showMessageDialog(null, "Password length must be longer than (9) characters", "Library Management System", JOptionPane.WARNING_MESSAGE);
        } else if (psDatabase.getAdminLoginMap().containsKey(newAdminIDTxtFld.getText())) {
            JOptionPane.showMessageDialog(null, "The Admin ID you entered is already taken", "Library Management System", JOptionPane.WARNING_MESSAGE);
        } else {
            psDatabase.registerNewAdmin(new Admin(
                    newAdminIDTxtFld.getText(),
                    newAdminNameTxtFld.getText(),
                    newAdminPasswordFld.getPassword()
            ));
            new SwingWorker<Void, Void>(){
                @Override
                protected Void doInBackground() {
                    writePseudoDatabaseSer();
                    libraryAdminJList.setListData(psDatabase.getAllAdmins().stream()
                            .map(a -> a.getAdminName())
                            .collect(Collectors.toList())
                            .toArray(new String[0])
                    );
                    return null;
                }
                @Override
                protected void done() {
                    JOptionPane.showMessageDialog(null, "Admin registration successful", "Library Management System", JOptionPane.INFORMATION_MESSAGE);
                    //this method is to hide the components below again
                    cancelRegistrationNewAdminBttnActionPerformed(evt);
                }
            }.execute();
        }

    }//GEN-LAST:event_registerNowNewAdminBttnActionPerformed

    private void removeSelectedAdminBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeSelectedAdminBttnActionPerformed
        int answer;
        if (adminIDValueLbl.getText().equals(loggedAdmin.getAdminID())) {
            answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to be removed as a Library Admin?");
            if (answer == JOptionPane.YES_OPTION) {
                psDatabase.removeAdmin(loggedAdmin);
                JOptionPane.showMessageDialog(null, "Operation successful", "Library Management System", JOptionPane.INFORMATION_MESSAGE);
                new SwingWorker<Void, Void>(){
                    @Override
                    protected Void doInBackground() {
                        writePseudoDatabaseSer();
                        return null;
                    }
                    @Override
                    protected void done() {
                        dispose();
                    }
                }.execute();
            }
        } else {
            answer = JOptionPane.showConfirmDialog(null, "Confirm action?");
            if (answer == JOptionPane.YES_OPTION) {
                Admin toBeRemoved = psDatabase.getAllAdmins().stream().reduce(null, (sub, current)
                        -> current.getAdminID().equals(selectedAdminIDValueLbl.getText())
                        ? current : sub);
                psDatabase.removeAdmin(toBeRemoved);
                libraryAdminJList.setListData(psDatabase.getAllAdmins().stream()
                        .map(a -> a.getAdminName())
                        .collect(Collectors.toList())
                        .toArray(new String[0])
                );
                new SwingWorker<Void, Void>(){
                    @Override
                    protected Void doInBackground() {
                        writePseudoDatabaseSer();
                        return null;
                    }
                    @Override
                    protected void done() {
                        JOptionPane.showMessageDialog(null, "Operation successful", "Library Management System", JOptionPane.INFORMATION_MESSAGE);
                    }
                }.execute();
            }
        }

    }//GEN-LAST:event_removeSelectedAdminBttnActionPerformed

    private void refreshBookListBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBookListBttnActionPerformed
        readPseudoDatabaseSer();
        new SwingWorker<Void, Void>(){
            @Override
            protected Void doInBackground() {
                //readPseudoDatabaseSer();
                //reset JComboBox of categories
                bookCategoriesComboBox.removeAllItems(); 
                for (String c : psDatabase.getAllCategories().toArray(new String[0])) {
                    bookCategoriesComboBox.addItem(c);
                }
                return null;
            }
            @Override
            protected void done() {
                //reset Book Stock Jlist
                bookJList.setListData(psDatabase.getAllBooks().stream()
                        .map(b -> b.getTitle())
                        .collect(Collectors.toList())
                        .toArray(new String[0])
                );
            }
        }.execute();
        
    }//GEN-LAST:event_refreshBookListBttnActionPerformed
    
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
//            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(() -> {
//            new AdminDashboard().setVisible(true);
//        });
//    }

    private void readPseudoDatabaseSer() {
        try {
            FileInputStream fileIn = new FileInputStream("psdb.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            psDatabase = (PseudoDatabase) objectIn.readObject();
            objectIn.close();
            fileIn.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void writePseudoDatabaseSer() {
        try {
            FileOutputStream fileOut = new FileOutputStream("psdb.ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(psDatabase);
            objectOut.flush();
            objectOut.close();
            fileOut.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StudentDashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StudentDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected boolean verifyLogIn(String adminID, char[] adminPass) {
        if (verifyAdminID(adminID)) {
            return psDatabase.verifyAdminLogIn(adminID, adminPass);
        } else {
            return false;
        }
    }

    protected boolean verifyAdminID(String adminID) {
        return psDatabase.adminIDExists(adminID);
    }

    protected void adminLogged(Admin adm) {
        loggedAdmin = adm;
        loggedAdmin.setNowLogged(true);
        adminNameValueLbl.setText(loggedAdmin.getAdminName());
        adminIDValueLbl.setText(loggedAdmin.getAdminID());
        new SwingWorker<Void, Void>(){
            @Override
            protected Void doInBackground() {
                writePseudoDatabaseSer();
                return null;
            }
        }.execute();
    }

    protected PseudoDatabase getPsdb() {
        return this.psDatabase;
    }

    protected void setStartForm(StartupForm startForm) {
        this.startForm = startForm;
        this.startForm.addOpenDashboard(this);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane addBookAuthorParentScrollPane;
    private javax.swing.JScrollPane addBookAuthorParentScrollPane1;
    private javax.swing.JTextArea addBookAuthorValueTxtArea;
    private javax.swing.JButton addBookBttn;
    private javax.swing.JLabel addBookCardImgLbl;
    private javax.swing.JTextField addBookCategoryValueTxtFld;
    private javax.swing.JPanel addBookPanel;
    private javax.swing.JScrollPane addBookTitleParentScrollPane;
    private javax.swing.JScrollPane addBookTitleParentScrollPane1;
    private javax.swing.JTextArea addBookTitleValueTxtArea;
    private javax.swing.JTextField addBookTotalQuantityValueTxtFld;
    private javax.swing.JButton addNewBookBttn;
    private javax.swing.JPanel adminCardPanel;
    private javax.swing.JPanel adminDashboardBottomPanel;
    private javax.swing.JLabel adminDashboardImgLbl;
    private javax.swing.JPanel adminDashboardTopPanel;
    private javax.swing.JLabel adminIDValueLbl;
    private javax.swing.JLabel adminNameLbl;
    private javax.swing.JLabel adminNameValueLbl;
    private javax.swing.JButton adminWestPanelBttn;
    private javax.swing.JLabel adminidLbl;
    private javax.swing.JComboBox<String> bookCategoriesComboBox;
    private javax.swing.JList<String> bookJList;
    private javax.swing.JScrollPane bookListParentScrollPane;
    private javax.swing.JTextArea bookStockAuthorValueTxtArea;
    private javax.swing.JTextField bookStockAvailableQuantityTxtFld;
    private javax.swing.JButton bookStockBttn;
    private javax.swing.JTextField bookStockCategoryTxtFld;
    private javax.swing.JPanel bookStockPanel;
    private javax.swing.JTextArea bookStockTitleValueTxtArea;
    private javax.swing.JTextField bookStockTotalQuantityTxtFld;
    private javax.swing.JButton cancelRegistrationNewAdminBttn;
    private javax.swing.JPanel centerPanel;
    private javax.swing.JLabel currentDateValueLbl;
    private javax.swing.JLabel currentTimeValueLbl;
    private javax.swing.JButton decreaseTotalQuantityBttn;
    private javax.swing.JButton editCourseSectionBttn;
    private javax.swing.JLabel filterByCategoryLbl;
    private javax.swing.JButton increaseTotalQuantityBttn;
    private javax.swing.JButton issuedBooksBttn;
    private javax.swing.JLabel issuedBooksDateBorrowedValueLbl;
    private javax.swing.JLabel issuedBooksDueDateValueLbl;
    private javax.swing.JList<String> issuedBooksJList;
    private javax.swing.JScrollPane issuedBooksListParentScrollPane;
    private javax.swing.JPanel issuedBooksPanel;
    private javax.swing.JList<String> libraryAdminJList;
    private javax.swing.JScrollPane libraryAdminListParentScrollPane;
    private javax.swing.JButton logOutBttn;
    private javax.swing.JTextField newAdminIDTxtFld;
    private javax.swing.JTextField newAdminNameTxtFld;
    private javax.swing.JPasswordField newAdminPasswordFld;
    private javax.swing.JButton refreshBookListBttn;
    private javax.swing.JButton registerNewAdminBttn;
    private javax.swing.JButton registerNowNewAdminBttn;
    private javax.swing.JButton registerStudentBttn;
    private javax.swing.JLabel registerStudentCardImgLbl;
    private javax.swing.JTextField registerStudentCourseTxtFld;
    private javax.swing.JTextField registerStudentIDTxtFld;
    private javax.swing.JTextField registerStudentNameTxtFld;
    private javax.swing.JButton registerStudentNewBttn;
    private javax.swing.JPanel registerStudentPanel;
    private javax.swing.JPasswordField registerStudentPasswordFld;
    private javax.swing.JButton removeSelectedAdminBttn;
    private javax.swing.JButton resetIssuedBooksBttn;
    private javax.swing.JButton resetStudentListBttn;
    private javax.swing.JLabel searchBookLbl;
    private javax.swing.JTextField searchBookTxtFld;
    private javax.swing.JLabel searchIssuedBookLbl;
    private javax.swing.JTextField searchIssuedBooksTxtFld;
    private javax.swing.JLabel searchStudentLbl;
    private javax.swing.JTextField searchStudentTxtFld;
    private javax.swing.JButton seeAllBorrowedBooks;
    private javax.swing.JTextField selectedAdminIDValueLbl;
    private javax.swing.JTextField selectedAdminNameValueLbl;
    private javax.swing.JTextField studentCourseSectionValueLbl;
    private javax.swing.JTextField studentIDValueLbl;
    private javax.swing.JList<String> studentJList;
    private javax.swing.JScrollPane studentListParentScrollPane;
    private javax.swing.JTextField studentNameValueLbl;
    private javax.swing.JTextField studentPenaltyRecordValueLbl;
    private javax.swing.JPanel studentsCardPanel;
    private javax.swing.JButton studentsWestPanelBttn;
    private javax.swing.JList<String> studentsWhoBorrowedJList;
    private javax.swing.JScrollPane studentsWhoBorrowedParentScrollPane;
    private javax.swing.JButton unregisterSelectedStudentBttn;
    private javax.swing.JPanel westPanel;
    // End of variables declaration//GEN-END:variables
}
