 import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ServerUDP_Simple {
    public static void main(String[] args) throws Exception {
        DatagramSocket server = new DatagramSocket(5000);
        byte[] buffer = new byte[1024];
        System.out.println("UDP Server started...");

        // --- 1. Say Hello ---
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        server.receive(packet);
        String clientMsg = new String(packet.getData(), 0, packet.getLength());
        System.out.println("Client says: " + clientMsg);

        InetAddress clientAddr = packet.getAddress();
        int clientPort = packet.getPort();
        String reply = "Hello from Server!";
        server.send(new DatagramPacket(reply.getBytes(), reply.length(), clientAddr, clientPort));

        // --- 2. File Transfer ---
        packet = new DatagramPacket(buffer, buffer.length);
        server.receive(packet);
        String fileName = new String(packet.getData(), 0, packet.getLength());

        File file = new File(fileName);
        if (file.exists()) {
            server.send(new DatagramPacket("FOUND".getBytes(), 5, clientAddr, clientPort));
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                server.send(new DatagramPacket(line.getBytes(), line.length(), clientAddr, clientPort));
            }
            server.send(new DatagramPacket("EOF".getBytes(), 3, clientAddr, clientPort));
            sc.close();
        } else {
            server.send(new DatagramPacket("NOTFOUND".getBytes(), 8, clientAddr, clientPort));
        }

        // --- 3. Calculator ---
        packet = new DatagramPacket(buffer, buffer.length);
        server.receive(packet);
        String expr = new String(packet.getData(), 0, packet.getLength());
        String result = "";
        try {
            String[] parts = expr.split(" ");
            double a = Double.parseDouble(parts[0]);
            String op = parts[1];
            double b = Double.parseDouble(parts[2]);
            double res = 0;
            switch (op) {
                case "+": res = a + b; break;
                case "-": res = a - b; break;
                case "*": res = a * b; break;
                case "/": res = a / b; break;
            }
            result = "Result: " + res;
        } catch (Exception e) {
            result = "Error in calculation.";
        }
        server.send(new DatagramPacket(result.getBytes(), result.length(), clientAddr, clientPort));

        server.close();
    }
}
