package com.sprint.v1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 5000)) {

            //socket.setSoTimeout(5000);
            BufferedReader echoes = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter stringToEcho = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);

            ClientRunnable client = new ClientRunnable(socket);
            label:
            do {
                System.out.println("=============================");
                String echoString;
                System.out.println("Menu");
                System.out.println("1. create mgni");
                System.out.println("2. create jiaoge");
                System.out.println("3. read mgni");
                System.out.println("4. update mgni");
                System.out.println("5. delete mgni");
                System.out.println("6. exit");
                System.out.println("Please choose 1~6:");
                echoString = scanner.nextLine();
                if(echoString.equals("6")){
                    System.out.println("bye bye");
                    break;
                }
                stringToEcho.println(echoString);
                process(echoes, stringToEcho, echoString);
            } while (true);
        } catch (SocketTimeoutException e) {
            System.out.println("The socket timed out");
        } catch (IOException e) {
            System.out.println("Client Error: " + e.getMessage());
        }
    }

    private static void process(BufferedReader echoes, PrintWriter stringToEcho, String echoString) throws IOException {
        String response = "";
        String option = echoString;
        System.out.println(option);
        stringToEcho.println(echoString);
        do {
            response = echoes.readLine();
            System.out.println(response);
            if (Objects.equals(response,"done") || Objects.equals(response,"不要亂按!!!!"))
                break;
            if (Objects.equals(option, "1") || Objects.equals(option, "2") ||Objects.equals(option, "4") || Objects.equals(option, "5")) {
                echoString = scanner.nextLine();
                stringToEcho.println(echoString);
            }
        } while (true);
    }
}
