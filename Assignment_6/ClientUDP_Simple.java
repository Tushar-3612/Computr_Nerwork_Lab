 import java.net.*;
import java.util.Scanner;

public class ClientUDP_Simple {
    public static void main(String[] args) throws Exception {
        DatagramSocket client = new DatagramSocket();
        InetAddress serverAddr = InetAddress.getByName("localhost");
        byte[] buffer = new byte[1024];
        Scanner sc = new Scanner(System.in);

        // --- 1. Say Hello ---
        System.out.print("Enter your hello message: ");
        String msg = sc.nextLine();
        client.send(new DatagramPacket(msg.getBytes(), msg.length(), serverAddr, 5000));
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        client.receive(packet);
        System.out.println("Server: " + new String(packet.getData(), 0, packet.getLength()));

        // --- 2. File Transfer ---
        System.out.print("Enter file name to request: ");
        String fileName = sc.nextLine();
        client.send(new DatagramPacket(fileName.getBytes(), fileName.length(), serverAddr, 5000));
        packet = new DatagramPacket(buffer, buffer.length);
        client.receive(packet);
        String status = new String(packet.getData(), 0, packet.getLength());
        if (status.equals("FOUND")) {
            System.out.println("Receiving file content:");
            while (true) {
                packet = new DatagramPacket(buffer, buffer.length);
                client.receive(packet);
                String line = new String(packet.getData(), 0, packet.getLength());
                if (line.equals("EOF")) break;
                System.out.println(line);
            }
        } else {
            System.out.println("File not found on server.");
        }

        // --- 3. Calculator ---
        System.out.print("Enter expression (e.g., 10 + 5): ");
        String expr = sc.nextLine();
        client.send(new DatagramPacket(expr.getBytes(), expr.length(), serverAddr, 5000));
        packet = new DatagramPacket(buffer, buffer.length);
        client.receive(packet);
        System.out.println(new String(packet.getData(), 0, packet.getLength()));

        client.close();
        sc.close();
    }
}
