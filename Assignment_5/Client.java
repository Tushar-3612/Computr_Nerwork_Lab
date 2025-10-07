import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 5000);

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        // --- 1. Say Hello ---
        System.out.print("Enter your hello message: ");
        String msg = userInput.readLine();
        dos.writeUTF(msg);
        System.out.println("Server: " + dis.readUTF());

        // --- 2. File Transfer ---
        System.out.print("Enter file name to request: ");
        String fileName = userInput.readLine();
        dos.writeUTF(fileName);

        String status = dis.readUTF();
        if (status.equals("FOUND")) {
            System.out.println("Receiving file content:");
            String line;
            while (!(line = dis.readUTF()).equals("EOF")) {
                System.out.println(line);
            }
        } else {
            System.out.println("File not found on server.");
        }

        // --- 3. Calculator ---
        System.out.print("Enter expression (e.g., 10 + 5): ");
        String expr = userInput.readLine();
        dos.writeUTF(expr);
        System.out.println(dis.readUTF());

        socket.close();
    }
}
