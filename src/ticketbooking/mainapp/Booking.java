package ticketbooking.mainapp;

import ticketbooking.checkrecords.CheckRecords;
import ticketbooking.changepassword.ChangePassword;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author lenovo
 */
public final class Booking extends javax.swing.JFrame implements MouseListener {

    /**
     * Creates new form booking
     *
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public Booking() throws ClassNotFoundException, SQLException {
        initComponents();
        seat1.addMouseListener(this);
        seat2.addMouseListener(this);
        seat3.addMouseListener(this);
        seat4.addMouseListener(this);
        seat5.addMouseListener(this);
        seat6.addMouseListener(this);
        seat7.addMouseListener(this);
        seat8.addMouseListener(this);
        seat9.addMouseListener(this);
        seat10.addMouseListener(this);
        seat11.addMouseListener(this);
        seat12.addMouseListener(this);

        if (connect()) {
            checkAvailability();
        }
    }
    
    int seat_no = 0;
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public boolean connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/bus", "root", "");

            conntxt.setText("Database Connected");
            conntxt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ticketbooking/mainapp/checked.png")));

            bookbtn.setEnabled(true);
            chkrecordsbtn.setEnabled(true);

            cstnametxt.setEnabled(true);
            jPanel1.setEnabled(true);
            phonetxt.setEnabled(true);
            mailtxt.setEnabled(true);
            selectbus.setEnabled(true);
            pricetxt.setEnabled(true);
            selectdate.setEnabled(true);
            busname.setEnabled(true);
            tickettxt.setEnabled(true);

            checkAvailability();

            Calendar cal = Calendar.getInstance();
            LocalTime local = LocalTime.now();

            if (local.getHour() > 16) {
                cal.add(Calendar.DAY_OF_YEAR, 1);
                Date date = cal.getTime();
                selectdate.setMinSelectableDate(date);
                selectdate.setDate(date);
            } else {
                Date date = cal.getTime();
//                System.out.println(date);
                selectdate.setMinSelectableDate(date);
                selectdate.setDate(date);
            }

            bookbtn.setForeground(new java.awt.Color(3, 14, 79));
            mailbtn.setForeground(new java.awt.Color(3, 14, 79));
            printbtn.setForeground(new java.awt.Color(3, 14, 79));
            chkrecordsbtn.setForeground(new java.awt.Color(3, 14, 79));

            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
            conntxt.setText("Database Not Connected");
            conntxt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ticketbooking/mainapp/warning.png")));
            bookbtn.setEnabled(false);
            chkrecordsbtn.setEnabled(false);
            printbtn.setEnabled(false);
            mailbtn.setEnabled(false);

            cstnametxt.setEnabled(false);
            jPanel1.setEnabled(false);
            phonetxt.setEnabled(false);
            mailtxt.setEnabled(false);
            selectbus.setEnabled(false);
            pricetxt.setEnabled(false);
            selectdate.setEnabled(false);
            busname.setEnabled(false);
            tickettxt.setEnabled(false);

            seat1.setEnabled(false);
            seat2.setEnabled(false);
            seat3.setEnabled(false);
            seat4.setEnabled(false);
            seat5.setEnabled(false);
            seat6.setEnabled(false);
            seat7.setEnabled(false);
            seat8.setEnabled(false);
            seat9.setEnabled(false);
            seat10.setEnabled(false);
            seat11.setEnabled(false);
            seat12.setEnabled(false);

            bookbtn.setForeground(new java.awt.Color(99, 102, 106));
            printbtn.setForeground(new java.awt.Color(99, 102, 106));
            mailbtn.setForeground(new java.awt.Color(99, 102, 106));
            chkrecordsbtn.setForeground(new java.awt.Color(99, 102, 106));

            passwdbtn.setText("Refresh");

            return false;
        }
    }

    public void bill() {
        String customer = cstnametxt.getText();
        int seats1 = seat_no;
        String price = pricetxt.getText();

        SimpleDateFormat date_form = new SimpleDateFormat("yyyy-MM-dd");
        String date = date_form.format(selectdate.getDate());

        String phn = phonetxt.getText();

        String dest = (String) selectbus.getSelectedItem();

        String mail = mailtxt.getText();

        try {
            ps = con.prepareStatement("select id, bkg_time  from book where date = ? and seatno = ?");
            ps.setString(1, date);
            ps.setInt(2, seats1);
            rs = ps.executeQuery();
            rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
        }

        tickettxt.setText("");
//        txtbill.setText("============================\n");
        try {
            tickettxt.setText(tickettxt.getText() + "\tTicket Id: " + rs.getString(1) + " \n");
        } catch (SQLException ex) {
            Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
        }
        tickettxt.setText(tickettxt.getText() + "==============================\n");
        tickettxt.setText(tickettxt.getText() + "Customer: " + "\t" + customer + "\n");
        tickettxt.setText(tickettxt.getText() + "Phone no.: " + "\t" + phn + "\n");
        tickettxt.setText(tickettxt.getText() + "Email Id: " + "\t" + mail + "\n");
        tickettxt.setText(tickettxt.getText() + "Destination: " + "\t" + dest + "\n");
        tickettxt.setText(tickettxt.getText() + "Seat no. : " + "\t" + seats1 + "\n");
        tickettxt.setText(tickettxt.getText() + "Price: " + "\tRs. " + price + "\n");
        tickettxt.setText(tickettxt.getText() + "Date & Time: " + "\t" + date + " 5pm\n");
        try {
            tickettxt.setText(tickettxt.getText() + "Booking Time: " + "\t" + rs.getString(2) + "\n");
        } catch (SQLException ex) {
            Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
        }
        tickettxt.setText(tickettxt.getText() + "==============================\n");
        tickettxt.setText(tickettxt.getText() + "\tThank You");
//        txtbill.setText(txtbill.getText()+"============================");

    }

    public void checkAvailability() {
        seat1.setEnabled(true);
        seat2.setEnabled(true);
        seat3.setEnabled(true);
        seat4.setEnabled(true);
        seat5.setEnabled(true);
        seat6.setEnabled(true);
        seat7.setEnabled(true);
        seat8.setEnabled(true);
        seat9.setEnabled(true);
        seat10.setEnabled(true);
        seat11.setEnabled(true);
        seat12.setEnabled(true);

        SimpleDateFormat date_form = new SimpleDateFormat("yyyy-MM-dd");
        String date = date_form.format(selectdate.getDate());
        String dest = (String) selectbus.getSelectedItem();

        try {
            ps = con.prepareStatement("select seatno from book where date = ? and dest = ?");
            ps.setString(1, date);
            ps.setString(2, dest);
            rs = ps.executeQuery();

            while (rs.next()) {
                switch (rs.getInt("seatno")) {
                    case 1 ->
                        seat1.setEnabled(false);
                    case 2 ->
                        seat2.setEnabled(false);
                    case 3 ->
                        seat3.setEnabled(false);
                    case 4 ->
                        seat4.setEnabled(false);
                    case 5 ->
                        seat5.setEnabled(false);
                    case 6 ->
                        seat6.setEnabled(false);
                    case 7 ->
                        seat7.setEnabled(false);
                    case 8 ->
                        seat8.setEnabled(false);
                    case 9 ->
                        seat9.setEnabled(false);
                    case 10 ->
                        seat10.setEnabled(false);
                    case 11 ->
                        seat11.setEnabled(false);
                    case 12 ->
                        seat12.setEnabled(false);
                    default -> {
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        phonetxt = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        seat1 = new javax.swing.JLabel();
        seat2 = new javax.swing.JLabel();
        seat3 = new javax.swing.JLabel();
        seat4 = new javax.swing.JLabel();
        seat5 = new javax.swing.JLabel();
        seat7 = new javax.swing.JLabel();
        seat8 = new javax.swing.JLabel();
        seat9 = new javax.swing.JLabel();
        seat10 = new javax.swing.JLabel();
        seat11 = new javax.swing.JLabel();
        seat6 = new javax.swing.JLabel();
        seat12 = new javax.swing.JLabel();
        busname = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        bookbtn = new javax.swing.JButton();
        selectdate = new com.toedter.calendar.JCalendar();
        jScrollPane1 = new javax.swing.JScrollPane();
        tickettxt = new javax.swing.JTextArea();
        chkrecordsbtn = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        selectbus = new javax.swing.JComboBox<>();
        pricetxt = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        cstnametxt = new javax.swing.JTextField();
        printbtn = new javax.swing.JButton();
        conntxt = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        mailtxt = new javax.swing.JTextField();
        passwdbtn = new javax.swing.JButton();
        mailbtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Bus Ticket Booking System");
        setAutoRequestFocus(false);
        setBackground(new java.awt.Color(153, 0, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(3, 14, 79));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.setInheritsPopupMenu(true);

        jLabel1.setFont(new java.awt.Font("Nunito", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ticketbooking/mainapp/bus-stop.png"))); // NOI18N
        jLabel1.setText("Bus Ticket Booking System");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Customer");

        phonetxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        phonetxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phonetxtActionPerformed(evt);
            }
        });
        phonetxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                phonetxtKeyTyped(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(244, 159, 28));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        seat1.setBackground(new java.awt.Color(255, 0, 0));
        seat1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        seat1.setForeground(new java.awt.Color(3, 14, 79));
        seat1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ticketbooking/mainapp/office-chair.png"))); // NOI18N
        seat1.setText("1");

        seat2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        seat2.setForeground(new java.awt.Color(3, 14, 79));
        seat2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ticketbooking/mainapp/office-chair.png"))); // NOI18N
        seat2.setText("2");

        seat3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        seat3.setForeground(new java.awt.Color(3, 14, 79));
        seat3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ticketbooking/mainapp/office-chair.png"))); // NOI18N
        seat3.setText("3");

        seat4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        seat4.setForeground(new java.awt.Color(3, 14, 79));
        seat4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ticketbooking/mainapp/office-chair.png"))); // NOI18N
        seat4.setText("4");

        seat5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        seat5.setForeground(new java.awt.Color(3, 14, 79));
        seat5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ticketbooking/mainapp/office-chair.png"))); // NOI18N
        seat5.setText("5");

        seat7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        seat7.setForeground(new java.awt.Color(3, 14, 79));
        seat7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ticketbooking/mainapp/office-chair.png"))); // NOI18N
        seat7.setText("7");

        seat8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        seat8.setForeground(new java.awt.Color(3, 14, 79));
        seat8.setIcon(new javax.swing.ImageIcon("C:\\Users\\lenovo\\Downloads\\office-chair.png")); // NOI18N
        seat8.setText("8");

        seat9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        seat9.setForeground(new java.awt.Color(3, 14, 79));
        seat9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ticketbooking/mainapp/office-chair.png"))); // NOI18N
        seat9.setText("9");

        seat10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        seat10.setForeground(new java.awt.Color(3, 14, 79));
        seat10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ticketbooking/mainapp/office-chair.png"))); // NOI18N
        seat10.setText("10");

        seat11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        seat11.setForeground(new java.awt.Color(3, 14, 79));
        seat11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ticketbooking/mainapp/office-chair.png"))); // NOI18N
        seat11.setText("11");

        seat6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        seat6.setForeground(new java.awt.Color(3, 14, 79));
        seat6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ticketbooking/mainapp/office-chair.png"))); // NOI18N
        seat6.setText("6");

        seat12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        seat12.setForeground(new java.awt.Color(3, 14, 79));
        seat12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ticketbooking/mainapp/office-chair.png"))); // NOI18N
        seat12.setText("12");

        busname.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        busname.setForeground(new java.awt.Color(3, 14, 79));
        busname.setText("Mumbai - Pune");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(seat7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(seat1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(seat2)
                    .addComponent(seat8, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(seat3)
                        .addGap(10, 10, 10)
                        .addComponent(seat4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(seat5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(seat6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(seat9)
                        .addGap(10, 10, 10)
                        .addComponent(seat10, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(seat11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(seat12)))
                .addGap(20, 20, 20))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(busname)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(busname)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(seat1)
                    .addComponent(seat3)
                    .addComponent(seat4)
                    .addComponent(seat5)
                    .addComponent(seat6)
                    .addComponent(seat2))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(seat7)
                    .addComponent(seat8)
                    .addComponent(seat9)
                    .addComponent(seat10)
                    .addComponent(seat11)
                    .addComponent(seat12))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Seats");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Price (in Rs)");

        bookbtn.setBackground(new java.awt.Color(244, 159, 28));
        bookbtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        bookbtn.setForeground(new java.awt.Color(3, 14, 79));
        bookbtn.setText("Book");
        bookbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookbtnActionPerformed(evt);
            }
        });

        selectdate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        selectdate.setForeground(new java.awt.Color(3, 14, 79));
        selectdate.setDecorationBackgroundColor(new java.awt.Color(255, 255, 255));
        selectdate.setFocusable(false);
        selectdate.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        selectdate.setMinSelectableDate(selectdate.getDate());
        selectdate.setWeekdayForeground(new java.awt.Color(3, 14, 79));
        selectdate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                selectdatePropertyChange(evt);
            }
        });

        tickettxt.setEditable(false);
        tickettxt.setColumns(20);
        tickettxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tickettxt.setRows(5);
        jScrollPane1.setViewportView(tickettxt);

        chkrecordsbtn.setBackground(new java.awt.Color(244, 159, 28));
        chkrecordsbtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        chkrecordsbtn.setForeground(new java.awt.Color(3, 14, 79));
        chkrecordsbtn.setText("Check Records");
        chkrecordsbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkrecordsbtnActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Phone No.");

        selectbus.setBackground(new java.awt.Color(255, 153, 102));
        selectbus.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        selectbus.setForeground(new java.awt.Color(3, 14, 79));
        selectbus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mumbai - Pune", "Mumbai - Ratnagiri", "Mumbai - Solapur", "Mumbai - Kolhapur" }));
        selectbus.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                selectbusItemStateChanged(evt);
            }
        });
        selectbus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectbusActionPerformed(evt);
            }
        });
        selectbus.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                selectbusPropertyChange(evt);
            }
        });

        pricetxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        pricetxt.setForeground(new java.awt.Color(255, 255, 255));
        pricetxt.setText("440");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Destination");

        cstnametxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cstnametxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cstnametxtActionPerformed(evt);
            }
        });

        printbtn.setBackground(new java.awt.Color(244, 159, 28));
        printbtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        printbtn.setForeground(new java.awt.Color(99, 102, 106));
        printbtn.setText("Print Ticket");
        printbtn.setEnabled(false);
        printbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printbtnActionPerformed(evt);
            }
        });

        conntxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        conntxt.setForeground(new java.awt.Color(255, 255, 255));
        conntxt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ticketbooking/mainapp/checked.png"))); // NOI18N
        conntxt.setText("Database Connnnected");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Email");

        mailtxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        mailtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mailtxtActionPerformed(evt);
            }
        });
        mailtxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                mailtxtKeyTyped(evt);
            }
        });

        passwdbtn.setBackground(new java.awt.Color(244, 159, 28));
        passwdbtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        passwdbtn.setForeground(new java.awt.Color(3, 14, 79));
        passwdbtn.setText("Change Password");
        passwdbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwdbtnActionPerformed(evt);
            }
        });

        mailbtn.setBackground(new java.awt.Color(244, 159, 28));
        mailbtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        mailbtn.setForeground(new java.awt.Color(99, 102, 106));
        mailbtn.setText("Send Mail");
        mailbtn.setEnabled(false);
        printbtn.setEnabled(false);
        mailbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mailbtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(28, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bookbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(35, 35, 35)
                                .addComponent(pricetxt, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel19)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                                        .addComponent(selectbus, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel17)
                                            .addComponent(jLabel18))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(mailtxt)
                                            .addComponent(phonetxt)
                                            .addComponent(cstnametxt)
                                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(selectdate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(chkrecordsbtn, javax.swing.GroupLayout.Alignment.TRAILING)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(conntxt)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwdbtn)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(mailbtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(printbtn)))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(conntxt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(passwdbtn))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(20, Short.MAX_VALUE)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(selectdate, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(chkrecordsbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(printbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(mailbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(144, 144, 144))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(cstnametxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(phonetxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(mailtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(selectbus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pricetxt))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bookbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void phonetxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phonetxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_phonetxtActionPerformed

    private void bookbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookbtnActionPerformed
        // TODO add your handling code here:

        if (connect()) {
            String customer = cstnametxt.getText();
            int seats1 = seat_no;
            String price = pricetxt.getText();

            SimpleDateFormat date_form = new SimpleDateFormat("yyyy-MM-dd");
            String date = date_form.format(selectdate.getDate());

            Long datetime = System.currentTimeMillis();
            Timestamp timestamp = new Timestamp(datetime);

            String phn = phonetxt.getText();

            String dest = (String) selectbus.getSelectedItem();

            String mail = mailtxt.getText();

            if (customer.isEmpty() || phn.isEmpty() || mail.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fill all the details ");
            } else if (phn.length() != 10) {
                JOptionPane.showMessageDialog(this, "Invalid phone no.");
            } else if (seats1 == 0) {
                JOptionPane.showMessageDialog(this, "Select a seat");
            } else if (!mail.contains("@") && !mail.contains(".com")) {
                JOptionPane.showMessageDialog(this, "Invalid email id");
            } else {
                try {
                    ps = con.prepareStatement("insert into book(cname, seatno, dest, price, date, bkg_time) values (?,?,?,?,?,?)");
                    ps.setString(1, customer);
                    ps.setInt(2, seats1);
                    ps.setString(3, dest);
                    ps.setString(4, price);
                    ps.setString(5, date);
                    ps.setString(6, timestamp.toString());
                    int k = ps.executeUpdate();
                    ps = con.prepareStatement("insert into contact(id, cstname, pno, mail) values ((SELECT id FROM bus.book ORDER BY id DESC LIMIT 1), ?, ?, ?)");
                    ps.setString(1, customer);;
                    ps.setString(2, phn);
                    ps.setString(3, mail);

                    int j = ps.executeUpdate();

                    if (k == 1 && j == 1) {
                        JOptionPane.showMessageDialog(this, "Seat Booked");
                        bill();
                        checkAvailability();
                        printbtn.setEnabled(true);
                        printbtn.setForeground(new java.awt.Color(3, 14, 79));

                        mailbtn.setEnabled(true);
                        mailbtn.setForeground(new java.awt.Color(3, 14, 79));

                        seat_no = 0;
                    } else {
                        JOptionPane.showMessageDialog(this, "Something went wrong!");
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_bookbtnActionPerformed

    private void chkrecordsbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkrecordsbtnActionPerformed
        // TODO add your handling code here:
        if (connect()) {
            CheckRecords c;
            try {
                c = new CheckRecords();
                c.setVisible(true);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_chkrecordsbtnActionPerformed

    private void cstnametxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cstnametxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cstnametxtActionPerformed

    private void selectbusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectbusActionPerformed
        // TODO add your handling code here:
        switch (selectbus.getSelectedIndex()) {
            case 0 ->
                pricetxt.setText("440");
            case 1 ->
                pricetxt.setText("395");
            case 2 ->
                pricetxt.setText("205");
            case 3 ->
                pricetxt.setText("470");
            default -> {
            }
        }
    }//GEN-LAST:event_selectbusActionPerformed

    private void phonetxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_phonetxtKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if ((c > '0' && c > '9' && c != KeyEvent.VK_BACK_SPACE) || phonetxt.getText().length() > 9) {
            evt.consume();
        }
    }//GEN-LAST:event_phonetxtKeyTyped

    private void printbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printbtnActionPerformed

        if (connect()) {
            try {
                // TODO add your handling code here:
                tickettxt.print();
            } catch (PrinterException ex) {
                Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_printbtnActionPerformed

    private void selectdatePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_selectdatePropertyChange
        // TODO add your handling code here:
        if ("calendar".equals(evt.getPropertyName())) {
            checkAvailability();
        }

    }//GEN-LAST:event_selectdatePropertyChange

    private void selectbusPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_selectbusPropertyChange


    }//GEN-LAST:event_selectbusPropertyChange

    private void selectbusItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_selectbusItemStateChanged
        // TODO add your handling code here:
        String dest = (String) selectbus.getSelectedItem();
        switch (dest) {
            case "Mumbai - Pune" ->
                busname.setText("Mumbai - Pune");
            case "Mumbai - Ratnagiri" ->
                busname.setText("Mumbai - Ratnagiri");
            case "Mumbai - Kolhapur" ->
                busname.setText("Mumbai - Kolhapur");
            case "Mumbai - Solapur" ->
                busname.setText("Mumbai - Solapur");
            default -> {
            }
        }

        checkAvailability();
    }//GEN-LAST:event_selectbusItemStateChanged

    private void mailtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mailtxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mailtxtActionPerformed

    private void mailtxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mailtxtKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_mailtxtKeyTyped

    private void passwdbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwdbtnActionPerformed
        // TODO add your handling code here:
        if ("Refresh".equals(passwdbtn.getText())) {
            if (connect()) {
                checkAvailability();
                passwdbtn.setText("Change Password");
            }
        } else {
            if (connect()) {
                var c = new ChangePassword();
                c.setVisible(true);
            }
        }
    }//GEN-LAST:event_passwdbtnActionPerformed

    private void mailbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mailbtnActionPerformed
        try {
            // TODO add your handling code here
            Desktop.getDesktop().browse(new URI("http://localhost/form.php"));
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_mailbtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Booking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Booking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Booking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Booking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new Booking().setVisible(true);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bookbtn;
    private javax.swing.JLabel busname;
    private javax.swing.JButton chkrecordsbtn;
    private javax.swing.JLabel conntxt;
    private javax.swing.JTextField cstnametxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton mailbtn;
    private javax.swing.JTextField mailtxt;
    private javax.swing.JButton passwdbtn;
    private javax.swing.JTextField phonetxt;
    private javax.swing.JLabel pricetxt;
    private javax.swing.JButton printbtn;
    private javax.swing.JLabel seat1;
    private javax.swing.JLabel seat10;
    private javax.swing.JLabel seat11;
    private javax.swing.JLabel seat12;
    private javax.swing.JLabel seat2;
    private javax.swing.JLabel seat3;
    private javax.swing.JLabel seat4;
    private javax.swing.JLabel seat5;
    private javax.swing.JLabel seat6;
    private javax.swing.JLabel seat7;
    private javax.swing.JLabel seat8;
    private javax.swing.JLabel seat9;
    private javax.swing.JComboBox<String> selectbus;
    private com.toedter.calendar.JCalendar selectdate;
    private javax.swing.JTextArea tickettxt;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == seat1 && seat1.isEnabled()) {
            seat_no = 1;
            seat1.setForeground(Color.red);
            seat2.setForeground(new java.awt.Color(3, 14, 79));
            seat3.setForeground(new java.awt.Color(3, 14, 79));
            seat4.setForeground(new java.awt.Color(3, 14, 79));
            seat5.setForeground(new java.awt.Color(3, 14, 79));
            seat6.setForeground(new java.awt.Color(3, 14, 79));
            seat7.setForeground(new java.awt.Color(3, 14, 79));
            seat8.setForeground(new java.awt.Color(3, 14, 79));
            seat9.setForeground(new java.awt.Color(3, 14, 79));
            seat10.setForeground(new java.awt.Color(3, 14, 79));
            seat11.setForeground(new java.awt.Color(3, 14, 79));
            seat12.setForeground(new java.awt.Color(3, 14, 79));

        } else if (e.getSource() == seat2 && seat2.isEnabled()) {
            seat_no = 2;
            seat2.setForeground(Color.red);
            seat1.setForeground(new java.awt.Color(3, 14, 79));
            seat3.setForeground(new java.awt.Color(3, 14, 79));
            seat4.setForeground(new java.awt.Color(3, 14, 79));
            seat5.setForeground(new java.awt.Color(3, 14, 79));
            seat6.setForeground(new java.awt.Color(3, 14, 79));
            seat7.setForeground(new java.awt.Color(3, 14, 79));
            seat8.setForeground(new java.awt.Color(3, 14, 79));
            seat9.setForeground(new java.awt.Color(3, 14, 79));
            seat10.setForeground(new java.awt.Color(3, 14, 79));
            seat11.setForeground(new java.awt.Color(3, 14, 79));
            seat12.setForeground(new java.awt.Color(3, 14, 79));
        } else if (e.getSource() == seat3 && seat3.isEnabled()) {
            seat_no = 3;
            seat3.setForeground(Color.red);
            seat2.setForeground(new java.awt.Color(3, 14, 79));
            seat1.setForeground(new java.awt.Color(3, 14, 79));
            seat4.setForeground(new java.awt.Color(3, 14, 79));
            seat5.setForeground(new java.awt.Color(3, 14, 79));
            seat6.setForeground(new java.awt.Color(3, 14, 79));
            seat7.setForeground(new java.awt.Color(3, 14, 79));
            seat8.setForeground(new java.awt.Color(3, 14, 79));
            seat9.setForeground(new java.awt.Color(3, 14, 79));
            seat10.setForeground(new java.awt.Color(3, 14, 79));
            seat11.setForeground(new java.awt.Color(3, 14, 79));
            seat12.setForeground(new java.awt.Color(3, 14, 79));
        } else if (e.getSource() == seat4 && seat4.isEnabled()) {
            seat_no = 4;
            seat4.setForeground(Color.red);
            seat2.setForeground(new java.awt.Color(3, 14, 79));
            seat3.setForeground(new java.awt.Color(3, 14, 79));
            seat1.setForeground(new java.awt.Color(3, 14, 79));
            seat5.setForeground(new java.awt.Color(3, 14, 79));
            seat6.setForeground(new java.awt.Color(3, 14, 79));
            seat7.setForeground(new java.awt.Color(3, 14, 79));
            seat8.setForeground(new java.awt.Color(3, 14, 79));
            seat9.setForeground(new java.awt.Color(3, 14, 79));
            seat10.setForeground(new java.awt.Color(3, 14, 79));
            seat11.setForeground(new java.awt.Color(3, 14, 79));
            seat12.setForeground(new java.awt.Color(3, 14, 79));
        } else if (e.getSource() == seat5 && seat5.isEnabled()) {
            seat_no = 5;
            seat5.setForeground(Color.red);
            seat2.setForeground(new java.awt.Color(3, 14, 79));
            seat3.setForeground(new java.awt.Color(3, 14, 79));
            seat4.setForeground(new java.awt.Color(3, 14, 79));
            seat1.setForeground(new java.awt.Color(3, 14, 79));
            seat6.setForeground(new java.awt.Color(3, 14, 79));
            seat7.setForeground(new java.awt.Color(3, 14, 79));
            seat8.setForeground(new java.awt.Color(3, 14, 79));
            seat9.setForeground(new java.awt.Color(3, 14, 79));
            seat10.setForeground(new java.awt.Color(3, 14, 79));
            seat11.setForeground(new java.awt.Color(3, 14, 79));
            seat12.setForeground(new java.awt.Color(3, 14, 79));
        } else if (e.getSource() == seat6 && seat6.isEnabled()) {
            seat_no = 6;
            seat6.setForeground(Color.red);
            seat2.setForeground(new java.awt.Color(3, 14, 79));
            seat3.setForeground(new java.awt.Color(3, 14, 79));
            seat4.setForeground(new java.awt.Color(3, 14, 79));
            seat5.setForeground(new java.awt.Color(3, 14, 79));
            seat1.setForeground(new java.awt.Color(3, 14, 79));
            seat7.setForeground(new java.awt.Color(3, 14, 79));
            seat8.setForeground(new java.awt.Color(3, 14, 79));
            seat9.setForeground(new java.awt.Color(3, 14, 79));
            seat10.setForeground(new java.awt.Color(3, 14, 79));
            seat11.setForeground(new java.awt.Color(3, 14, 79));
            seat12.setForeground(new java.awt.Color(3, 14, 79));
        } else if (e.getSource() == seat7 && seat7.isEnabled()) {
            seat_no = 7;
            seat7.setForeground(Color.red);
            seat2.setForeground(new java.awt.Color(3, 14, 79));
            seat3.setForeground(new java.awt.Color(3, 14, 79));
            seat4.setForeground(new java.awt.Color(3, 14, 79));
            seat5.setForeground(new java.awt.Color(3, 14, 79));
            seat6.setForeground(new java.awt.Color(3, 14, 79));
            seat1.setForeground(new java.awt.Color(3, 14, 79));
            seat8.setForeground(new java.awt.Color(3, 14, 79));
            seat9.setForeground(new java.awt.Color(3, 14, 79));
            seat10.setForeground(new java.awt.Color(3, 14, 79));
            seat11.setForeground(new java.awt.Color(3, 14, 79));
            seat12.setForeground(new java.awt.Color(3, 14, 79));
        } else if (e.getSource() == seat8 && seat8.isEnabled()) {
            seat_no = 8;
            seat8.setForeground(Color.red);
            seat2.setForeground(new java.awt.Color(3, 14, 79));
            seat3.setForeground(new java.awt.Color(3, 14, 79));
            seat4.setForeground(new java.awt.Color(3, 14, 79));
            seat5.setForeground(new java.awt.Color(3, 14, 79));
            seat6.setForeground(new java.awt.Color(3, 14, 79));
            seat7.setForeground(new java.awt.Color(3, 14, 79));
            seat1.setForeground(new java.awt.Color(3, 14, 79));
            seat9.setForeground(new java.awt.Color(3, 14, 79));
            seat10.setForeground(new java.awt.Color(3, 14, 79));
            seat11.setForeground(new java.awt.Color(3, 14, 79));
            seat12.setForeground(new java.awt.Color(3, 14, 79));
        } else if (e.getSource() == seat9 && seat9.isEnabled()) {
            seat_no = 9;
            seat9.setForeground(Color.red);
            seat2.setForeground(new java.awt.Color(3, 14, 79));
            seat3.setForeground(new java.awt.Color(3, 14, 79));
            seat4.setForeground(new java.awt.Color(3, 14, 79));
            seat5.setForeground(new java.awt.Color(3, 14, 79));
            seat6.setForeground(new java.awt.Color(3, 14, 79));
            seat7.setForeground(new java.awt.Color(3, 14, 79));
            seat8.setForeground(new java.awt.Color(3, 14, 79));
            seat1.setForeground(new java.awt.Color(3, 14, 79));
            seat10.setForeground(new java.awt.Color(3, 14, 79));
            seat11.setForeground(new java.awt.Color(3, 14, 79));
            seat12.setForeground(new java.awt.Color(3, 14, 79));
        } else if (e.getSource() == seat10 && seat10.isEnabled()) {
            seat_no = 10;
            seat10.setForeground(Color.red);
            seat2.setForeground(new java.awt.Color(3, 14, 79));
            seat3.setForeground(new java.awt.Color(3, 14, 79));
            seat4.setForeground(new java.awt.Color(3, 14, 79));
            seat5.setForeground(new java.awt.Color(3, 14, 79));
            seat6.setForeground(new java.awt.Color(3, 14, 79));
            seat7.setForeground(new java.awt.Color(3, 14, 79));
            seat8.setForeground(new java.awt.Color(3, 14, 79));
            seat9.setForeground(new java.awt.Color(3, 14, 79));
            seat1.setForeground(new java.awt.Color(3, 14, 79));
            seat11.setForeground(new java.awt.Color(3, 14, 79));
            seat12.setForeground(new java.awt.Color(3, 14, 79));
        } else if (e.getSource() == seat11 && seat11.isEnabled()) {
            seat_no = 11;
            seat11.setForeground(Color.red);
            seat2.setForeground(new java.awt.Color(3, 14, 79));
            seat3.setForeground(new java.awt.Color(3, 14, 79));
            seat4.setForeground(new java.awt.Color(3, 14, 79));
            seat5.setForeground(new java.awt.Color(3, 14, 79));
            seat6.setForeground(new java.awt.Color(3, 14, 79));
            seat7.setForeground(new java.awt.Color(3, 14, 79));
            seat8.setForeground(new java.awt.Color(3, 14, 79));
            seat9.setForeground(new java.awt.Color(3, 14, 79));
            seat10.setForeground(new java.awt.Color(3, 14, 79));
            seat1.setForeground(new java.awt.Color(3, 14, 79));
            seat12.setForeground(new java.awt.Color(3, 14, 79));
        } else if (e.getSource() == seat12 && seat12.isEnabled()) {
            seat_no = 12;
            seat12.setForeground(Color.red);
            seat2.setForeground(new java.awt.Color(3, 14, 79));
            seat3.setForeground(new java.awt.Color(3, 14, 79));
            seat4.setForeground(new java.awt.Color(3, 14, 79));
            seat5.setForeground(new java.awt.Color(3, 14, 79));
            seat6.setForeground(new java.awt.Color(3, 14, 79));
            seat7.setForeground(new java.awt.Color(3, 14, 79));
            seat8.setForeground(new java.awt.Color(3, 14, 79));
            seat9.setForeground(new java.awt.Color(3, 14, 79));
            seat10.setForeground(new java.awt.Color(3, 14, 79));
            seat11.setForeground(new java.awt.Color(3, 14, 79));
            seat1.setForeground(new java.awt.Color(3, 14, 79));
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
