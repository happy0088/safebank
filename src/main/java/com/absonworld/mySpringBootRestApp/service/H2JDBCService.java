package com.absonworld.mySpringBootRestApp.service;

import com.absonworld.mySpringBootRestApp.entity.AccountDetails;
import com.absonworld.mySpringBootRestApp.entity.Beneficiary;
import com.absonworld.mySpringBootRestApp.entity.Transactions;
import com.absonworld.mySpringBootRestApp.entity.User;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class H2JDBCService {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:~/test";

    //  Database credentials
    static final String USER = "sa";
    static final String PASS = "";
    static Connection conn = null;
    static Statement stmt = null;

    // public static void init(String[] args) {
    static {
        try {
            conn = getConnection();
            dropTable();
            createTable(conn);
            insertData();
            printTableData("USER");
            printTableData("ACCOUNT");
            printTableData("TRANSACTIONS");
            printTableData("BENEFICIARY");
            // gc();

        } catch (Exception se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } /*finally {
            //finally block used to close resources
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            } // nothing we can do
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            } //end finally try
        } //end try*/
        System.out.println("Goodbye!");
    }

    private static void readData() throws SQLException {
        String sql = "SELECT cid, fullName, userName, email,password FROM USER";
        ResultSet rs = stmt.executeQuery(sql);

        // STEP 4: Extract data from result set
        while (rs.next()) {
            // Retrieve by column name
            int cid = rs.getInt("cid");
            // int age = rs.getInt("age");
            String fullName = rs.getString("fullName");
            String userName = rs.getString("userName");
            String email = rs.getString("email");
            String password = rs.getString("password");

            // Display values
            System.out.print("CID: " + cid);
            System.out.print(", fullName: " + fullName);
            System.out.print(", userName: " + userName);
            System.out.println(", email: " + email);
            System.out.println(", password: " + password);
        }

    }

    public static void printTableData(String table) {
        ResultSet resultSet = null;
        try {
            resultSet = stmt.executeQuery("SELECT * from " + table);

            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = resultSet.getString(i);
                    System.out.print(rsmd.getColumnName(i) + ": " + columnValue);
                }
                System.out.println("");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertData() throws SQLException {
        // STEP 3: Execute a query
        stmt = conn.createStatement();
        String sql = "INSERT INTO USER(cid,fullName,userName,email,password) " + "VALUES (10001,'Happy Singh', 'happy','happy@gmail.com','happy')";
        stmt.executeUpdate(sql);

        sql = "INSERT INTO USER(cid,fullName,userName,email,password) " + "VALUES (10002,'Abhishek kumar', 'abhishek','abhishek@gmail.com','abhi')";
        stmt.executeUpdate(sql);

        sql = "INSERT INTO USER(cid,fullName,userName,email,password) " + "VALUES (10003,'Lavy R', 'lavy','lavy@gmail.com','lavy')";
        stmt.executeUpdate(sql);

        sql = "INSERT INTO ACCOUNT(cid,balance,accountId) " + "VALUES (10001,5000,1234001)";
        stmt.executeUpdate(sql);

        sql = "INSERT INTO ACCOUNT(cid,balance,accountId) " + "VALUES (10002,3000,1234002)";
        stmt.executeUpdate(sql);

        sql = "INSERT INTO BENEFICIARY(cid,accountId,payeeAccountId,payeeName) " + "VALUES (10011,1234011,'5555001','gogo')";
        stmt.executeUpdate(sql);

        sql = "INSERT INTO BENEFICIARY(cid,accountId,payeeAccountId,payeeName) " + "VALUES (10012,1234011,'5555002','gogi')";
        stmt.executeUpdate(sql);


        sql = "INSERT INTO TRANSACTIONS(cid,amount,accountId,payeeAccountId) " + "VALUES (10001,100,1234001,'5555001')";
        stmt.executeUpdate(sql);

        sql = "INSERT INTO TRANSACTIONS(cid,amount,accountId,payeeAccountId) " + "VALUES (10001,200,1234001,'5555002')";
        stmt.executeUpdate(sql);

        sql = "INSERT INTO TRANSACTIONS(cid,amount,accountId,payeeAccountId) " + "VALUES (10002,300,1234002,'5555002')";
        stmt.executeUpdate(sql);

        System.out.println("Inserted records into the table...");
    }

    public static Connection getConnection() {
        // STEP 1: Register JDBC driver
        try {
            Class.forName(JDBC_DRIVER);
            //STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void createTable(Connection conn) {
        //STEP 3: Execute a query
        System.out.println("Creating table in given database...");
        try {
            stmt = conn.createStatement();

            String sql = "CREATE TABLE if not exists  USER " +
                    "(cid INTEGER not NULL auto_increment, " +
                    " fullName VARCHAR(255), " +
                    " userName VARCHAR(255), " +
                    " email VARCHAR(255), " +
                    " password VARCHAR(255), " +
                    " PRIMARY KEY ( email ))";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE if not exists  ACCOUNT " +
                    "(accountId INTEGER not NULL auto_increment, " +
                    " cid INTEGER, " +
                    " balance INTEGER, " +
                    " PRIMARY KEY ( accountId,cid ))";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE if not exists  TRANSACTIONS " +
                    "(transactionId INTEGER not NULL auto_increment, " +
                    " cid INTEGER, " +
                    " accountId INTEGER, " +
                    " amount INTEGER, " +
                    " payeeAccountId INTEGER, " +
                    " PRIMARY KEY ( cid,transactionId ))";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE if not exists  BENEFICIARY " +
                    "(beneficiaryId INTEGER not NULL auto_increment, " +
                    " cid INTEGER, " +
                    " accountId INTEGER, " +
                    " payeeAccountId INTEGER, " +
                    " payeeName VARCHAR(255), " +
                    " PRIMARY KEY ( accountId,payeeAccountId ))";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE if not exists  SESSION " +
                    "(cid INTEGER, " +
                    " sessionId VARCHAR(255), " +
                    " PRIMARY KEY ( sessionId,cid ))";
            stmt.executeUpdate(sql);


            System.out.println("Created table in given database...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void dropTable() {
        //STEP 3: Execute a query
        System.out.println("Dropping table in given database...");
        try {
            stmt = conn.createStatement();

            String sql = "Drop TABLE  USER ";
            stmt.executeUpdate(sql);

             sql = "Drop TABLE  ACCOUNT ";
            stmt.executeUpdate(sql);

             sql = "Drop TABLE  BENEFICIARY ";
            stmt.executeUpdate(sql);

            sql = "Drop TABLE  TRANSACTIONS ";
            stmt.executeUpdate(sql);

            sql = "Drop TABLE  SESSION ";
            stmt.executeUpdate(sql);

            System.out.println("Dropped table in given database...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void gc() {
        // STEP 4: Clean-up environment
        try {
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int createUser(User userDetails) {

        try {
            stmt = conn.createStatement();

            String sql = "INSERT INTO USER(fullName,userName,email,password) " +
                    "VALUES (" + userDetails.getFullName() + ", " + userDetails.getUserName() + ", " + userDetails.getEmail() + "," +
                    " " + userDetails.getPassword() + ")";

            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public User getUserDetails(String userName, String password) {
        printTableData("USER");
        User user = null;
        String sql = "SELECT * FROM USER where userName = \'" + userName + "\' and password = \'" + password + "\'";
        // String sql = "SELECT * FROM USER where userName ='Happy'";
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            // STEP 4: Extract data from result set
            //if (rs.getFetchSize() > 0)
            {
                while (rs.next()) {
                    user = new User();
                    // Retrieve by column name
                    user.setCid(rs.getInt("cid"));
                    user.setFullName(rs.getString("fullName"));
                    user.setUserName(rs.getString("userName"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public AccountDetails getAccountDetails(int cid) {
        AccountDetails accountDetails = null;
        String sql = "SELECT * FROM ACCOUNT where cid = " + cid;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            // STEP 4: Extract data from result set

                while (rs.next()) {
                    accountDetails = new AccountDetails();
                    // Retrieve by column name
                    accountDetails.setAccountId(rs.getInt("accountId"));
                    accountDetails.setBalance(rs.getInt("balance"));
                    accountDetails.setCid(rs.getInt("cid"));
                }
            printTableData("ACCOUNT");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accountDetails;
    }

    public List<Beneficiary> getBeneficiaryDetails(int cid) {
        List<Beneficiary> beneficiaryList = new ArrayList<>();
        String sql = "SELECT * FROM BENEFICIARY where cid = " + cid;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            // STEP 4: Extract data from result set
            while (rs.next()) {
                Beneficiary beneficiary = new Beneficiary();
                // Retrieve by column name
                beneficiary.setAccountId(rs.getInt("accountId"));
                beneficiary.setPayeeAccountId(rs.getInt("payeeAccountId"));
                beneficiary.setPayeeName(rs.getString("payeeName"));
                beneficiary.setCid(rs.getInt("cid"));
                beneficiaryList.add(beneficiary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        printTableData("BENEFICIARY");
        return beneficiaryList;
    }


    public int updatePassword(int cid, String password) {
        String sql = "update USER set password =  \'" + password + "\' where cid =" + cid;
        try {
            stmt = conn.createStatement();
            int count = stmt.executeUpdate(sql);
            printTableData("USER");
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int addPayee(Beneficiary beneficiaryDetails) {
        try {
            stmt = conn.createStatement();
            String sql = "INSERT INTO BENEFICIARY(cid,accountId,payeeAccountId,payeeName) " + "VALUES (" +
                    beneficiaryDetails.getCid() + ", " + beneficiaryDetails.getAccountId() + ", " + "\'" + beneficiaryDetails.getPayeeAccountId() + "\',\'" + beneficiaryDetails.getPayeeName() + "\')";

            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Transactions> getTransactionDetails(int cid) {
        List<Transactions> transactionsList = new ArrayList<>();
        String sql = "SELECT * FROM TRANSACTIONS where cid = " + cid;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            // STEP 4: Extract data from result set
            while (rs.next()) {
                Transactions transactions = new Transactions();
                // Retrieve by column name
                transactions.setAccountId(rs.getInt("accountId"));
                transactions.setPayeeAccountId(rs.getInt("payeeAccountId"));
                transactions.setAmount(rs.getInt("amount"));
                transactions.setCid(rs.getInt("cid"));
                transactionsList.add(transactions);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        printTableData("TRANSACTIONS");
        return transactionsList;
    }

    public int updateAccountBalance(int updatedAmount, int cid) {
        String sql = "update ACCOUNT set balance =  " + updatedAmount + " where cid =" + cid;
        try {
            stmt = conn.createStatement();
            int count = stmt.executeUpdate(sql);
            printTableData("USER");
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;

    }

    public String createSession(int cid) {
        String sessionId=createSessionId(cid);
        int status=0;
        try {
            stmt = conn.createStatement();
            String sql = "INSERT INTO SESSION(sessionId,cid) VALUES (\'" + sessionId + "\', " + cid +")";
            status= stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(status>0){
            return sessionId;
        }

        return null;
    }

    public String fetchSession(int cid) {
        List<Transactions> transactionsList = new ArrayList<>();
        String sql = "SELECT sessionId FROM SESSION where cid = " + cid;
        ResultSet rs = null;
        String sessionId=null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            // STEP 4: Extract data from result set
            while (rs.next()) {
                sessionId= rs.getString("sessionId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        printTableData("SESSION");
        return sessionId;
    }


    private String createSessionId(int cid) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        System.out.println(generatedString);
        return generatedString;
    }

    public boolean validateSession(String sessionId) {
        List<Transactions> transactionsList = new ArrayList<>();
        String sql = "SELECT sessionId FROM SESSION where sessionId = \'" + sessionId+"\'";
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            // STEP 4: Extract data from result set
            while (rs.next()) {
                //sessionId= rs.getString("sessionId");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        printTableData("SESSION");
        return false;
    }

    public List<User> getAllUserDetails() {

        printTableData("USER");
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM USER ";
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            // STEP 4: Extract data from result set
            //if (rs.getFetchSize() > 0)
            {
                while (rs.next()) {
                    User user = new User();
                    // Retrieve by column name
                    user.setCid(rs.getInt("cid"));
                    user.setFullName(rs.getString("fullName"));
                    user.setUserName(rs.getString("userName"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    userList.add(user);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public List<Transactions> getTransactionDetailsByName(String payeeName) {

        List<Transactions> transactionsList = new ArrayList<>();
        String sql = "SELECT t.cid, t.amount, t.payeeAccountId, t.accountId FROM TRANSACTIONS as t " +
                "join BENEFICIARY as b on t.payeeAccountId = b.payeeAccountId  where b.payeeName  =  \'" + payeeName+"\'";
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            // STEP 4: Extract data from result set
            while (rs.next()) {
                Transactions transactions = new Transactions();
                // Retrieve by column name
                transactions.setAccountId(rs.getInt("accountId"));
                transactions.setPayeeAccountId(rs.getInt("payeeAccountId"));
                transactions.setAmount(rs.getInt("amount"));
                transactions.setCid(rs.getInt("cid"));
                transactionsList.add(transactions);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        printTableData("TRANSACTIONS");
        return transactionsList;
    }
}