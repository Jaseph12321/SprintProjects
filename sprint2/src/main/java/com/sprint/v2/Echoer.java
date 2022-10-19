package com.sprint.v2;

import com.sprint.Entity.Mgni;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class Echoer extends Thread implements Runnable {
    private final Socket socket;

    private final String[] keyList = {"type","cmno","kacType","bankNo", "curr"
                            , "pvType","bicaccNo","reason","amount","name","telno","status"};
    private final Logger LOG = LogManager.getLogger(Echoer.class);
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

            OutputStream outputStream = socket.getOutputStream();
            //create an object output stream from the output stream
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            //get the input stream from the conneted socket
            InputStream inputStream = socket.getInputStream();
            //create a DataInputStream so we can read data from it
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            Class.forName("org.mariadb.jdbc.Driver");

            initDatabaseConnection();
            while (threadList.size() > 0) {
                System.out.println(threadList.size());
                System.out.println("hello");
                String echoString = input.readLine();
                if(echoString ==null) break;
                System.out.println("Received client input: " + echoString);

                menu(input, output, objectInputStream, objectOutputStream, echoString);
                System.out.println("nextloop");

            }
            closeDatabaseConnection();
        } catch (NullPointerException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        } catch (RuntimeException | ClassNotFoundException | SQLException | IOException e) {
            if (e instanceof RuntimeException) {
                ((RuntimeException) e).printStackTrace();
                LOG.debug(e.getMessage());
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

    private void menu(BufferedReader input, PrintWriter output, ObjectInputStream inputStream, ObjectOutputStream outputStream, String option) throws SQLException, IOException {

        System.out.println("the option is " + option);
        switch (option) {
            case "0" -> {
                createDataWithJson(inputStream);
                break;
            }
            case "1" -> {
                createData(inputStream);
                break;
            }
            case "2" -> {
                createJiaoGeData(inputStream);
                break;
            }
            case "3" -> readData(outputStream, input);
            case "4" -> {
                String id = input.readLine();
                updateData(inputStream, id);
                break;
            }
            case "5" -> {
                output.println("please input your id");
                String id = input.readLine();
                deleteData(id);
                output.println("done");
                break;
            }
            case "6" -> System.out.println("finished");
            default -> System.out.println("不要亂按!!!!");
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


    public void createDataWithJson(ObjectInputStream objectInputStream) throws SQLException, IOException {
        try (PreparedStatement statement = connection.prepareStatement("""
                INSERT INTO mgn_mgni
                	(mgni_id, mgni_time, mgni_type, mgni_cm_no, mgni_kac_type, mgni_bank_no, mgni_ccy, mgni_pv_type, mgni_bicacc_no, mgni_p_reason, mgni_amt, mgni_ct_name, mgni_ct_tel, mgni_status, mgni_u_time)
                	VALUES (?, NOW(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())
                """)) {

            Map jObject = (Map) objectInputStream.readObject();
            List<String> strList = new ArrayList<>();
            for(String s : keyList){
                strList.add(jObject.get(s).toString());
            }
            LocalDateTime dateTime = LocalDateTime.now();
            String id = createMgniId(dateTime);
            strList.add(0, id);

            for (int i = 0; i < strList.size(); i++) {
                if (i == 9) {
                    System.out.println(strList.get(i));
                    statement.setDouble(i + 1, Double.parseDouble(strList.get(i)));
                } else {
                    statement.setString(i + 1, strList.get(i));
                }
            }
            int rowInserted = statement.executeUpdate();
            System.out.println("Row inserted: " + rowInserted);
           // createCashi(objectInputStream,id);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void createData(ObjectInputStream objectInputStream) throws SQLException, IOException {
        try (PreparedStatement statement = connection.prepareStatement("""
                INSERT INTO mgn_mgni
                	(mgni_id, mgni_time, mgni_type, mgni_cm_no, mgni_kac_type, mgni_bank_no, mgni_ccy, mgni_pv_type, mgni_bicacc_no, mgni_p_reason, mgni_amt, mgni_ct_name, mgni_ct_tel, mgni_status, mgni_u_time)
                	VALUES (?, NOW(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())
                """)) {

            List<String> strList = (List<String>) objectInputStream.readObject();
            LocalDateTime dateTime = LocalDateTime.now();
            String id = createMgniId(dateTime);
            strList.add(0, id);

            for (int i = 0; i < strList.size(); i++) {
                if (i == 9) {
                    System.out.println(strList.get(i));
                    statement.setDouble(i + 1, Double.parseDouble(strList.get(i)));
                } else {
                    statement.setString(i + 1, strList.get(i));
                }
            }
            int rowInserted = statement.executeUpdate();
            System.out.println("Row inserted: " + rowInserted);
            createCashi(objectInputStream,id);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //2.
    public void createJiaoGeData(ObjectInputStream objectInputStream) throws SQLException, IOException {
        try (PreparedStatement statement = connection.prepareStatement("""
                INSERT INTO mgn_mgni
                	(mgni_id, mgni_time, mgni_type, mgni_cm_no, mgni_kac_type, mgni_bank_no, mgni_ccy, mgni_pv_type, mgni_bicacc_no, mgni_i_type,mgni_p_reason, mgni_amt, mgni_ct_name, mgni_ct_tel, mgni_status, mgni_u_time)
                	VALUES (?, NOW(), ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, NOW())
                """)) {

            List<String> strList = (List<String>) objectInputStream.readObject();
            LocalDateTime dateTime = LocalDateTime.now();
            String id = createMgniId(dateTime);
            strList.add(0, id);

            for (int i = 0; i < strList.size(); i++) {
                if (i == 10) {
                    statement.setDouble(i + 1, Double.parseDouble(strList.get(i)));
                } else {
                    statement.setString(i + 1, strList.get(i));
                }
            }
            int rowInserted = statement.executeUpdate();
            System.out.println("Row inserted: " + rowInserted);
        } catch (ClassNotFoundException e) {
            LOG.debug(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    //3.
    public void readData(ObjectOutputStream objectOutputStream,BufferedReader input) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("""
                SELECT * FROM mgn_mgni ORDER BY mgni_id
                """)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                boolean empty = true;
                List<List<String>> rows = new ArrayList<>();
                while (resultSet.next()) {
                    List<String> strList = new ArrayList<>();
                    empty = false;
                    for (int i = 0; i < 16; i++) {
                        strList.add(resultSet.getString(i + 1));
                    }
                    rows.add(strList);
                }
                objectOutputStream.writeObject(rows);
                readcashiData(objectOutputStream, input);
                Thread.sleep(5000);
            } catch (InterruptedException | IOException e) {
                LOG.debug(e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    public void readcashiData(ObjectOutputStream objectOutputStream,BufferedReader input) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("""
                SELECT * FROM mgn_cashi where cashi_acc_no = ?
                """)) {
            do{
                String id = input.readLine();
                if(Objects.equals(id, "done")) break;

                statement.setString(1,id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    boolean empty = true;
                    List<List<String>> rows = new ArrayList<>();
                    while (resultSet.next()) {
                        List<String> strList = new ArrayList<>();
                        empty = false;
                        for (int i = 0; i < 4; i++) {
                            strList.add(resultSet.getString(i + 1));
                        }
                        rows.add(strList);
                    }
                    objectOutputStream.writeObject(rows);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }while(true);
        } catch (IOException e) {
            LOG.debug(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    //4.
    public void updateData(ObjectInputStream objectInputStream, String id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("""
                UPDATE mgn_mgni
                	SET
                		mgni_type=?,mgni_cm_no=?,mgni_kac_type=?,mgni_bank_no=?,mgni_ccy=?,
                		mgni_pv_type=?,mgni_bicacc_no=?,mgni_p_reason=?,mgni_amt=?,mgni_ct_name=?,
                		mgni_ct_tel=?,mgni_status=?,mgni_u_time=NOW()
                	    WHERE mgni_id=?
                """)) {
            List<String> strList = (List<String>) objectInputStream.readObject();

            strList.add(id);
            for (String str : strList) {
                System.out.println(str);
            }
            for (int i = 0; i < strList.size(); i++) {
                if (i == 8) {
                    statement.setDouble(i + 1, Double.parseDouble(strList.get(i)));
                } else {
                    statement.setString(i + 1, strList.get(i));
                }
            }
            int rowInserted = statement.executeUpdate();
            System.out.println("Row inserted: " + rowInserted);
        } catch (IOException | ClassNotFoundException e) {
            LOG.debug(e.getMessage());
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

    public void createCashi(ObjectInputStream objectInputStream, String id) {
        try (PreparedStatement statement = connection.prepareStatement("""
                INSERT INTO mgn_cashi
                	(cashi_mgni_id, cashi_acc_no, cashi_ccy, cashi_amt)
                	VALUES (?, ?, ?,?)
                """)) {
            int rowInserted = 0;
            List<List<String>> strList = (List<List<String>>) objectInputStream.readObject();
            for (List<String> str : strList) {
                LocalDateTime date = LocalDateTime.now();
                str.add(0, createMgniId(date));
                for (int i = 0; i < str.size(); i++) {
                    if (i == 3) {
                        System.out.println(str.get(i));
                        statement.setDouble(i + 1, Double.parseDouble(str.get(i)));
                    } else {
                        statement.setString(i + 1, str.get(i));
                    }
                }
                 rowInserted += statement.executeUpdate();
            }
             System.out.println("Row inserted: " + rowInserted);
        } catch (ClassNotFoundException | SQLException | IOException e) {
            LOG.debug(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
