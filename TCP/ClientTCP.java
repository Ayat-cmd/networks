package com.company;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientTCP {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 7171;
        String data;

        //Создаем сокет
        Socket socket = null;
        try {
            socket = new Socket(host, port);
        } catch (UnknownHostException e) {
            System.out.println("Неизвестный хост: " + host);
            System.exit(-1);
        } catch (IOException e) {
            System.out.println("Ошибка ввода/вывода при создании сокета " + host
                    + ":" + port);
            System.exit(-1);
        }

        System.out.println("Введите сообщение серверу: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        //поток вывода, через который проходят сообщения
        OutputStream out = null;
        try {
            out = socket.getOutputStream();
        } catch (IOException e) {
            System.out.println("Невозможно получить поток вывода!");
            System.exit(-1);
        }

        //транслируем сообщения пользователя в поток вывода
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
        String ln = null;
        try {
            while ((ln = reader.readLine()) != null) {
                writer.write(ln + "\n");
                writer.flush();

                //Читаем обратное сообщение от сервера
                try{
                    InputStream iStream = socket.getInputStream();
                    DataInputStream inStream = new DataInputStream(iStream);
                    data = inStream.readUTF();
                    System.out.println("Сервер ответил: " + data);
                    System.out.println("Введите сообщение серверу: ");
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка записи сообщения!");
            System.exit(-1);
        }
    }
}
