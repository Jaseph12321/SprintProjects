package com.sprint.Entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Cashi {

    private Connection connection;

    public void createDate(BufferedReader input, PrintWriter output) throws SQLException, IOException {
        try (PreparedStatement statement = connection.prepareStatement("""
                INSERT INTO mgn_cashi
                	(cashi_mgni_id, cashi_acc_no, cashi_ccy, cashi_amt)
                	VALUES (?,?, ?, ?)
                """)) {

            LocalDateTime dateTime = LocalDateTime.now();
            String id = "MGI" + createMgniId(dateTime);
            output.println("Please input your account number");
            String account = input.readLine();
            output.println("Please input ccy");
            String ccy = input.readLine();
            output.println("Please input your amount");
            double amount = Double.parseDouble(input.readLine());

            statement.setString(1, id);
            statement.setString(2, account);
            statement.setString(3, ccy);
            statement.setDouble(4, amount);
            int rowInserted = statement.executeUpdate();
            System.out.println("Row inserted: " + rowInserted);
        }
    }

    public void readData(PrintWriter output) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("""
                SELECT * FROM mgn_cashi ORDER BY cashi_mgni_id
                """)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                boolean empty = true;
                while (resultSet.next()) {
                    empty = false;
                    String id = resultSet.getString("cashi_mgni_id");
                    String account = resultSet.getString("cashi_acc_no");
                    String ccy = resultSet.getString("cashi_ccy");
                    String amount = resultSet.getString("cashi_amt");
                    output.println(id + "," + account + "," + ccy + "," + amount);
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

    public void updateData(BufferedReader input, PrintWriter output, int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("""
                UPDATE mgn_cashi
                	SET
                		cashi_acc_no=?,
                		cashi_ccy=?,
                		cashi_amt=?
                	WHERE cashi_mgni_id=?
                """)) {
            output.println("Please input your account number");
            String account = input.readLine();
            output.println("Please input ccy");
            String ccy = input.readLine();
            output.println("Please input your amount");
            double amount = Double.parseDouble(input.readLine());

            statement.setString(1, account);
            statement.setString(2, ccy);
            statement.setDouble(3, amount);
            statement.setInt(4, id);
            int rowsUpdated = statement.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteData(int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("""
                 DELETE FROM mgn_cashi WHERE cashi_mgni_id = ?
                """)) {
            statement.setInt(1, id);
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
