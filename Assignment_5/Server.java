import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(5000);
        System.out.println("Server started, waiting for client...");
        Socket socket = server.accept();
        System.out.println("Client connected.");

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        // --- 1. Say Hello ---
        String msg = dis.readUTF();
        System.out.println("Client says: " + msg);
        dos.writeUTF("Hello from Server!");

        // --- 2. File Transfer ---
        String fileName = dis.readUTF(); // receive file name from client
        File file = new File(fileName);
        if (file.exists()) {
            dos.writeUTF("FOUND");
            BufferedReader fr = new BufferedReader(new FileReader(file));
            String line;
            while ((line = fr.readLine()) != null) {
                dos.writeUTF(line); // send line by line
            }
            fr.close();
            dos.writeUTF("EOF"); // end of file
            System.out.println("File sent successfully: " + fileName);
        } else {
            dos.writeUTF("NOTFOUND");
        }

        // --- 3. Calculator ---
        String expr = dis.readUTF();
        System.out.println("Expression received: " + expr);

        try {
            String[] parts = expr.split(" ");
            double a = Double.parseDouble(parts[0]);
            String op = parts[1];
            double b = Double.parseDouble(parts[2]);
            double result = 0;

            switch (op) {
                case "+": result = a + b; break;
                case "-": result = a - b; break;
                case "*": result = a * b; break;
                case "/": result = a / b; break;
                default: dos.writeUTF("Invalid Operator"); break;
            }

            dos.writeUTF("Result: " + result);

        } catch (Exception e) {
            dos.writeUTF("Error in calculation.");
        }

        socket.close();
        server.close();
    }
}
