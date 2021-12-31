package com.company;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientUDP {
    public static void main(String args[])
    {
        DatagramSocket sock = null;
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        try
        {
            sock = new DatagramSocket();
            while(true)
            {
                //Ожидаем ввод сообщения серверу
                System.out.println("Введите сообщение серверу: ");
                String s = (String)cin.readLine();
                byte[] b = s.getBytes();

                //Отправляем сообщение
                DatagramPacket  dp = new DatagramPacket(b , b.length , InetAddress.getLocalHost() , 6464);
                sock.send(dp);

                //буфер для получения входящих данных
                byte[] buffer = new byte[65536];
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);

                //Получаем данные
                sock.receive(reply);
                byte[] data = reply.getData();
                s = new String(data, 0, reply.getLength());
                System.out.println("Сообщение от сервера : " + s);
            }
        }catch(IOException e)
        {
            System.err.println("IOException " + e);
        }
    }
}
