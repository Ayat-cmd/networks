package com.company;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP {

    public static void main(String[] args) {
        int port = 7171;

        //Проверим доступность порта
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Ожидаем данные...");
        } catch (IOException e) {
            System.out.println("Порт: " + port + " - ошибка подключения!");
            System.exit(-1);
        }

        //Создание клиента
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.out.println("Порт: " + port + " - ошибка подключения!");
            System.exit(-1);
        }

        BufferedReader readerServer = new BufferedReader(new InputStreamReader(System.in));

        //Поток ввода от клиента, получаем из него сообщения
        InputStream inClientStream = null;
        try {
            inClientStream = clientSocket.getInputStream();
        } catch (IOException e) {
            System.out.println("Невозможно получить поток ввода!");
            System.exit(-1);
        }

        String ln = null;
        //Читаем поток
        BufferedReader reader = new BufferedReader(new InputStreamReader(inClientStream));
        String readClientStream = null;
        try {
            while ((readClientStream = reader.readLine()) != null) {
                System.out.println("Сообщение от клиента: " + readClientStream);
                System.out.flush();

                //Отсылаем сообщение обратно клиенту
                ln = readerServer.readLine();
                OutputStream outClientStream = null;
                outClientStream = clientSocket.getOutputStream();
                DataOutputStream outDataClientStream = new DataOutputStream(outClientStream);
                try {
                    outDataClientStream.writeUTF(ln);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Невозможно прочесть сообщение!");
            System.exit(-1);
        }
    }
}
