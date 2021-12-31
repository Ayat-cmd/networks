package com.company;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class ServerUDP {
    public static void main(String args[])
    {
        try
        {
            //Создаем сокет
            DatagramSocket sock = new DatagramSocket(6464);
            BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
            //буфер для получения входящих данных
            byte[] buffer = new byte[65536];
            DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);

            System.out.println("Ожидаем данные...");

            while(true)
            {
                //Получаем данные
                sock.receive(incoming);
                byte[] data = incoming.getData();
                String s = new String(data, 0, incoming.getLength());

                System.out.println("Сообщение от клиента: " + s);

                //Отправляем данные клиенту
                System.out.println("Введите сообщение клиенту: ");
                String ss = (String)cin.readLine();

                //Отправляем сообщение
                DatagramPacket dp = new DatagramPacket(ss.getBytes() , ss.getBytes().length , incoming.getAddress() , incoming.getPort());
                sock.send(dp);
            }
        }

        catch(IOException e)
        {
            System.err.println("IOException " + e);
        }
    }
}
