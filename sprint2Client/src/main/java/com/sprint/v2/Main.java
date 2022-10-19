package com.sprint.v2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.Field;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    private static final Logger LOG =LogManager.getLogger(Main.class);
    private static final String[] columns = {
            "id", "time", "type", "customer number", "kac type", "bank number", "currency", "pv type", "bicacc number",
            "i type", "reason", "amount", "name", "telephone number",
            "status", "update time"
    };

    private Map<String,String> testMap = new HashMap<String,String>();

    private static final String[] cashiColumns = {"id", "account", "currency", "amount"};

    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 5000)) {

            //socket.setSoTimeout(5000);
            BufferedReader echoes = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter stringToEcho = new PrintWriter(socket.getOutputStream(), true);
            //get the output stream from the socket
            OutputStream outputStream = socket.getOutputStream();
            //create an object output stream from the output stream
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            //get the input stream from the conneted socket
            InputStream inputStream = socket.getInputStream();
            //create a DataInputStream so we can read data from it
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            Scanner scanner = new Scanner(System.in);

            ClientRunnable client = new ClientRunnable(socket);

            JSONObject thing= setJSONObject();
            label:
            do {
                System.out.println("=============================");
                String echoString;
                System.out.println("Menu");
                System.out.println("0. create mgni with json");
                System.out.println("1. create mgni");
                System.out.println("2. create jiaoge");
                System.out.println("3. read mgni");
                System.out.println("4. update mgni");
                System.out.println("5. delete mgni");
                System.out.println("6. exit");
                System.out.println("Please choose 0~6:");
                echoString = scanner.nextLine();
                stringToEcho.println(echoString);
                if(echoString.equals("0")){
                    createDataWithJson(objectOutputStream, thing);
                }else if (echoString.equals("1")) {
                    createData(objectOutputStream);
                } else if (echoString.equals("2")) {
                    createJiaoge(objectOutputStream);
                } else if (echoString.equals("3")) {
                    readData(objectInputStream, stringToEcho);
                } else if (echoString.equals("4")) {
                    updateData(objectOutputStream, stringToEcho);
                } else if (echoString.equals("5")) {
                    deleteData(echoes, stringToEcho, echoString);
                } else if (echoString.equals("6")) {
                    System.out.println("bye bye");
                    break;
                } else {
                    System.out.println("不要亂按");
                }

            } while (true);
        } catch (SocketTimeoutException e) {
            System.out.println("The socket timed out");
        } catch (IOException e) {
            System.out.println("Client Error: " + e.getMessage());
        }catch(InvalidParameterException e){
            LOG.error(e.getMessage());
        }catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createDataWithJson(ObjectOutputStream objectOutputStream,JSONObject thing) throws IOException, InvalidParameterException {
        System.out.println("sending to the server...");
        objectOutputStream.writeObject(thing.toMap());
        System.out.println("sent");
    }
    private static void createData(ObjectOutputStream objectOutputStream) throws IOException, InvalidParameterException {
        List<String> strList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        for (String s : columns) {
            if (Objects.equals("id", s) || Objects.equals("i type", s) || Objects.equals("time", s) || Objects.equals("update time", s)) {
                System.out.println("loading");
            } else {
                System.out.println("Please input your " + s);
                String s1 = scanner.nextLine();
                strList.add(s1);
                check(s,s1);
            }
        }
        System.out.println("sending to the server...");
        objectOutputStream.writeObject(strList);
        objectOutputStream.writeObject(createCashiData(objectOutputStream, strList.get(1), strList.get(4)));
        System.out.println("sent");
    }

    private static void createJiaoge(ObjectOutputStream objectOutputStream) throws IOException {
        List<String> strList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        for (String s : columns) {
            if (Objects.equals("id", s) || Objects.equals("time", s) || Objects.equals("update time", s)) {
                System.out.println("loading");
            } else {
                System.out.println("Please input your " + s);
                strList.add(scanner.nextLine());
            }
        }
        System.out.println("sending to the server...");
        objectOutputStream.writeObject(strList);
        System.out.println("sent");
    }

    private static void readData(ObjectInputStream objectInputStream,PrintWriter stringToEcho) throws IOException, ClassNotFoundException {
        List<List<String>> strList = (List<List<String>>) objectInputStream.readObject();
        int num = 1;
        for (List<String> list : strList) {
            String cmno="";
            System.out.println(num);
            for (int i = 0; i < columns.length; i++) {
                System.out.println(columns[i] + ": " + list.get(i));
                if(i==3){
                    cmno = list.get(i);
                }
            }
            num++;
            readCashiData(objectInputStream,cmno, stringToEcho);
            System.out.println();
        }
        stringToEcho.println("done");
        System.out.println("yes!!!");
    }

    private static void readCashiData(ObjectInputStream objectInputStream, String cmno, PrintWriter stringToEcho) throws IOException, ClassNotFoundException {
        stringToEcho.println(cmno);
        List<List<String>> strList = (List<List<String>>) objectInputStream.readObject();
        System.out.println("cashi:");
        for (List<String> list : strList) {
            for (int i = 0; i < cashiColumns.length; i++) {
                System.out.println(" " + cashiColumns[i] + ": " + list.get(i));
            }
        }
    }

    private static void updateData(ObjectOutputStream objectOutputStream, PrintWriter stringToEcho) throws IOException {
        List<String> strList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("please input your id");
        stringToEcho.println(scanner.nextLine());
        for (String s : columns) {
            if (Objects.equals("id", s) || Objects.equals("i type", s) || Objects.equals("time", s) || Objects.equals("update time", s)) {
                System.out.println("loading");
            } else {
                System.out.println("Please input your " + s);
                strList.add(scanner.nextLine());
            }
        }
        System.out.println("sending to the server...");
        objectOutputStream.writeObject(strList);
        System.out.println("sent");
    }

    private static void deleteData(BufferedReader echoes, PrintWriter stringToEcho, String echoString) throws IOException {
        String response = "";
        Scanner scanner = new Scanner(System.in);
        do {
            response = echoes.readLine();
            System.out.println(response);
            if (Objects.equals(response, "done") || Objects.equals(response, "不要亂按!!!!"))
                break;
            stringToEcho.println(scanner.nextLine());
        } while (true);
    }

    //cashi
    private static List<List<String>> createCashiData(ObjectOutputStream objectOutputStream, String account, String currency) throws IOException {
        List<List<String>> resultList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("create cashi");

        do {
            List<String> strList = new ArrayList<>();
            strList.add(account);
            strList.add(currency);
            System.out.println("Please input your amount");
            strList.add(scanner.nextLine());
            resultList.add(strList);

            System.out.println();
            System.out.println("1. add more cashi");
            System.out.println("2. continue");
            System.out.println("Please input 1~2:");
        } while (!scanner.nextLine().equals("2"));
        return resultList;
    }

    public static void check(String s,String s1) throws InvalidParameterException{


        if(Objects.equals(s, "type")){

        }else if(Objects.equals(s, "customer number")){
           Pattern pattern = Pattern.compile("\\d{7}");
           Matcher matcher = pattern.matcher(s1);
           if(!matcher.matches()) throw new InvalidParameterException("customer number has to be 7 numbers");
        }else if(Objects.equals(s, "kac type")){
            Pattern pattern = Pattern.compile("[1-2]{1}");
            Matcher matcher = pattern.matcher(s1);
            if(!matcher.matches()) throw new InvalidParameterException("kac type has to be 1 or 2");
        }else if(Objects.equals(s, "bank number")){
            Pattern pattern = Pattern.compile("\\d{3}");
            Matcher matcher = pattern.matcher(s1);
            if(!matcher.matches()) throw new InvalidParameterException("customer number has to be 3 numbers");
        }else if(Objects.equals(s, "currency")){
            Pattern pattern = Pattern.compile("[TWD|USD]{3}");
            Matcher matcher = pattern.matcher(s1);
            if(!matcher.matches()) throw new InvalidParameterException("currency has to be TWD or USD");
        }else if(Objects.equals(s, "pv type")){
            Pattern pattern = Pattern.compile("[1-2]{1}");
            Matcher matcher = pattern.matcher(s1);
            if(!matcher.matches()) throw new InvalidParameterException("kac type has to be 1 or 2");
        }else if(Objects.equals(s, "bicacc number")){
            Pattern pattern = Pattern.compile("\\d{21}");
            Matcher matcher = pattern.matcher(s1);
            if(!matcher.matches()) throw new InvalidParameterException("bicacc number has to be 7 numbers");
        }else if(Objects.equals(s, "amount")){

        }else if(Objects.equals(s, "name")){

        }else if(Objects.equals(s, "telephone number")){
            Pattern pattern = Pattern.compile("\\d{10}");
            Matcher matcher = pattern.matcher(s1);
            if(!matcher.matches()) throw new InvalidParameterException("telephone number has to be 10 numbers");
        }else if(Objects.equals(s, "status")){
            Pattern pattern = Pattern.compile("[0-7]");
            Matcher matcher = pattern.matcher(s1);
            if(!matcher.matches()) throw new InvalidParameterException("status number has to be 10 numbers");
        }
    }

    public static JSONObject setJSONObject(){
        JSONObject result = new JSONObject();
        setMap(result);
        result.put("type", "1");
        result.put("cmno", "9875464");
        result.put("kacType","2");
        result.put("bankNo","021");
        result.put("curr","TWD");
        result.put("pvType","2");
        result.put("bicaccNo","147258369874563589647");
        result.put("reason","I have no reason");
        result.put("amount","5478");
        result.put("name","peter");
        result.put("telno","4567894561");
        result.put("status","4");
        return result;
    }

    public static void setMap(JSONObject object) {
        try {
            Field changeMap = object.getClass().getDeclaredField("map");
            changeMap.setAccessible(true);
            changeMap.set(object, new LinkedHashMap<>());
            changeMap.setAccessible(false);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            System.out.println(e.getMessage());
        }
    }
}
