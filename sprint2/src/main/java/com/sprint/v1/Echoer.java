package com.sprint.v1;

import com.sprint.Entity.Mgni;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Echoer extends Thread implements Runnable {
    private final Socket socket;

    private Logger LOG = LogManager.getLogger(Echoer.class);
    private final ArrayList<Echoer> threadList;
    private Connection connection;

    private Mgni mgni;

    public Echoer(Socket socket, ArrayList<Echoer> threadList) throws IOException {
        this.socket = socket;
        this.threadList = threadList;
    }


    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            Class.forName("org.mariadb.jdbc.Driver");
            initDatabaseConnection();
            while (threadList.size() > 0) {
                System.out.println("hello");
                String echoString = input.readLine();
                System.out.println("Received client input: " + echoString);

                menu(input, output, echoString);
                System.out.println("nextloop");

            }
            closeDatabaseConnection();
        } catch (NullPointerException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        } catch (RuntimeException | ClassNotFoundException | SQLException | IOException e) {
            if (e instanceof RuntimeException) {
                ((RuntimeException) e).printStackTrace();
                System.out.println("Connection reset");
            }
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                //Oh,well!
                LOG.debug(e.getMessage());
            }
        }
    }

    private void menu(BufferedReader input, PrintWriter output, String option) throws SQLException, IOException {

        Scanner scanner = new Scanner(System.in);

        option = input.readLine();
        switch (option) {
            case "1" -> {
                createData(input, output);
                output.println("done");
                break;
            }
            case "2" -> {
                createJiaoGeData(input, output);
                output.println("done");
                break;
            }
            case "3" -> readData(output);
            case "4" -> {
                output.println("Please input id you want to update");
                String id = input.readLine();
                updateData(input, output, id);
                output.println("done");
                break;
            }
            case "5" -> {
                output.println("Please input id you want to delete");
                String id = input.readLine();
                deleteData(id);
                output.println("done");
                break;
            }
            default -> output.println("不要亂按!!!!");
        }

    }


    private void initDatabaseConnection() throws SQLException, ClassNotFoundException {
        System.out.println("Connecting to the database...");
        connection = DriverManager.getConnection("jdbc:mariadb://localhost:3307/sprint1", "root", "2giiuali");
        System.out.println("connect success");
    }

    private void closeDatabaseConnection() throws SQLException {
        System.out.println("Closing database connection ...");
        connection.close();
        System.out.println("closed success");
    }

    public void createData(BufferedReader input, PrintWriter output) throws SQLException, IOException {
        try (PreparedStatement statement = connection.prepareStatement("""
                INSERT INTO mgn_mgni
                	(mgni_id, mgni_time, mgni_type, mgni_cm_no, mgni_kac_type, mgni_bank_no, mgni_ccy, mgni_pv_type, mgni_bicacc_no, mgni_p_reason, mgni_amt, mgni_ct_name, mgni_ct_tel, mgni_status, mgni_u_time)
                	VALUES (?, NOW(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())
                """)) {

            LocalDateTime dateTime = LocalDateTime.now();
            String id = createMgniId(dateTime);
            output.println("Please input type");
            String type = input.readLine();
            output.println("Please input cm no");
            String cmno = input.readLine();
            //output.println("Please input kac type");
            String kacType = "1";
            output.println("Please input bank no");
            String bankno = input.readLine();
            output.println("Please input ccy");
            String ccy = input.readLine();
            output.println("Please input pv type");
            String pvType = input.readLine();
            output.println("Please input bicacc no");
            String bicaccNo = input.readLine();
            output.println("Please input reason");
            String reason = input.readLine();
            output.println("Please input your amount");
            double amount = Double.parseDouble(input.readLine());
            output.println("Please input your name");
            String name = input.readLine();
            output.println("Please input telephone number");
            String telno = input.readLine();
            output.println("Please input status");
            String status = input.readLine();

            statement.setString(1, id);
            statement.setString(2, type);
            statement.setString(3, cmno);
            statement.setString(4, kacType);
            statement.setString(5, bankno);
            statement.setString(6, ccy);
            statement.setString(7, pvType);
            statement.setString(8, bicaccNo);
            statement.setString(9, reason);
            statement.setDouble(10, amount);
            statement.setString(11, name);
            statement.setString(12, telno);
            statement.setString(13, status);
            int rowInserted = statement.executeUpdate();
            System.out.println("Row inserted: " + rowInserted);
        }
    }

    //2.
    public void createJiaoGeData(BufferedReader input, PrintWriter output) throws SQLException, IOException {
        try (PreparedStatement statement = connection.prepareStatement("""
                INSERT INTO mgn_mgni
                	(mgni_id, mgni_time, mgni_type, mgni_cm_no, mgni_kac_type, mgni_bank_no, mgni_ccy, mgni_pv_type, mgni_bicacc_no, mgni_i_type,mgni_p_reason, mgni_amt, mgni_ct_name, mgni_ct_tel, mgni_status, mgni_u_time)
                	VALUES (?, NOW(), ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, NOW())
                """)) {

            LocalDateTime dateTime = LocalDateTime.now();
            String id = createMgniId(dateTime);
            output.println("Please input type");
            String type = input.readLine();
            output.println("Please input cm no");
            String cmno = input.readLine();
            output.println("Please input kac type");
            String kacType = "2";
            output.println("Please input bank no");
            String bankno = input.readLine();
            output.println("Please input ccy");
            String ccy = input.readLine();
            output.println("Please input pv type");
            String pvType = input.readLine();
            output.println("Please input bicacc no");
            String bicaccNo = input.readLine();
            output.println("Please input itype no");
            String itype = input.readLine();
            output.println("Please input reason");
            String reason = input.readLine();
            output.println("Please input your amount");
            double amount = Double.parseDouble(input.readLine());
            output.println("Please input your name");
            String name = input.readLine();
            output.println("Please input telephone number");
            String telno = input.readLine();
            output.println("Please input status");
            String status = input.readLine();

            statement.setString(1, id);
            statement.setString(2, type);
            statement.setString(3, cmno);
            statement.setString(4, kacType);
            statement.setString(5, bankno);
            statement.setString(6, ccy);
            statement.setString(7, pvType);
            statement.setString(8, bicaccNo);
            statement.setString(9, itype);
            statement.setString(10, reason);
            statement.setDouble(11, amount);
            statement.setString(12, name);
            statement.setString(13, telno);
            statement.setString(14, status);
            int rowInserted = statement.executeUpdate();
            System.out.println("Row inserted: " + rowInserted);
        }
    }

    //3.
    public void readData(PrintWriter output) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("""
                SELECT * FROM mgn_mgni ORDER BY mgni_id
                """)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                boolean empty = true;
                int i = 0;
                while (resultSet.next()) {
                    empty = false;
                    i++;
                    output.println(i + ".");
                    output.println("id: " + resultSet.getString("mgni_id"));
                    output.println("time: " + resultSet.getTime("mgni_time").toString());
                    output.println("type: " + resultSet.getString("mgni_type"));
                    output.println("customer number: " + resultSet.getString("mgni_cm_no"));
                    output.println("kac type: " + resultSet.getString("mgni_kac_type"));
                    output.println("bank number: " + resultSet.getString("mgni_bank_no"));
                    output.println("currency: " + resultSet.getString("mgni_ccy"));
                    output.println("pv type: " + resultSet.getString("mgni_pv_type"));
                    output.println("bicacc number: " + resultSet.getString("mgni_bicacc_no"));
                    output.println("I type: " + resultSet.getString("mgni_i_type"));
                    output.println("reason: " + resultSet.getString("mgni_p_reason"));
                    output.println("amount: " + resultSet.getDouble("mgni_amt"));
                    output.println("name: " + resultSet.getString("mgni_ct_name"));
                    output.println("telephone: " + resultSet.getString("mgni_ct_tel"));
                    output.println("status: " + resultSet.getString("mgni_status"));
                    output.println("update time: " + resultSet.getTime("mgni_u_time").toString());
                    output.println();
                }
                if (empty) {
                    output.println("\t (no data)");
                }

                Thread.sleep(5000);
                output.println("done");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //4.
    public void updateData(BufferedReader input, PrintWriter output, String id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("""
                UPDATE mgn_mgni
                	SET
                		mgni_type=?,
                		mgni_cm_no=?,
                		mgni_kac_type=?,
                		mgni_bank_no=?,
                		mgni_ccy=?,
                		mgni_pv_type=?,
                		mgni_bicacc_no=?,
                		mgni_p_reason=?,
                		mgni_amt=?,
                		mgni_ct_name=?,
                		mgni_ct_tel=?,
                		mgni_status=?,
                		mgni_u_time=NOW()
                	WHERE mgni_id=?
                """)) {
            LocalDateTime dateTime = LocalDateTime.now();
            output.println("Please input type");
            String type = input.readLine();
            output.println("Please input cm no");
            String cmno = input.readLine();
            output.println("Please input kac type");
            String kacType = "1";
            output.println("Please input bank no");
            String bankno = input.readLine();
            output.println("Please input ccy");
            String ccy = input.readLine();
            output.println("Please input pv type");
            String pvType = input.readLine();
            output.println("Please input bicacc no");
            String bicaccNo = input.readLine();
            output.println("Please input reason");
            String reason = input.readLine();
            output.println("Please input your amount");
            double amount = Double.parseDouble(input.readLine());
            output.println("Please input your name");
            String name = input.readLine();
            output.println("Please input telephone number");
            String telno = input.readLine();
            output.println("Please input status");
            String status = input.readLine();

            statement.setString(1, type);
            statement.setString(2, cmno);
            statement.setString(3, kacType);
            statement.setString(4, bankno);
            statement.setString(5, ccy);
            statement.setString(6, pvType);
            statement.setString(7, bicaccNo);
            statement.setString(8, reason);
            statement.setDouble(9, amount);
            statement.setString(10, name);
            statement.setString(11, telno);
            statement.setString(12, status);
            statement.setString(13, id);

            int rowInserted = statement.executeUpdate();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //5.
    public void deleteData(String id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("""
                 DELETE FROM mgn_mgni WHERE mgni_id = ?
                """)) {
            statement.setString(1, id);
            int rowsDeleted = statement.executeUpdate();
            System.out.println("Rows deleted: " + rowsDeleted);
        }
    }

    public String createMgniId(LocalDateTime t) {
        String s = t.toString();
        String date = s.substring(0, 10);
        String time = s.substring(11, 23);
        String[] split1 = date.split("-");
        String[] split2 = time.split(":");
        System.out.println(split2[0]);
        String s3 = split2[2];
        String a = s3.substring(0, 2);
        String b = s3.substring(3);

        StringBuilder combine = new StringBuilder();
        for (String i : split1) combine.append(i);
        for (int i = 0; i < split2.length - 1; i++) combine.append(split2[i]);
        combine.append(a).append(b);

        return "MGI" + combine.toString();
    }
}
