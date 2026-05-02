
package network;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

// A socket-based server that accepts client connections and delivers predefined inventory tips

public class TipServer {
    
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(5000);
            System.out.println("Tip Server is running...");

            while (true) {
                Socket socket = server.accept();

                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                String tips =
                        "Inventory Tips:\n\n"
                        + "1. Check low-stock products regularly.\n\n"
                        + "2. Restock items before they reach the minimum stock level.\n\n"
                        + "3. Avoid overstocking slow-moving products.\n\n"
                        + "4. Review product quantities at the end of each day.\n\n"
                        + "5. Keep product prices updated.\n\n"
                        + "6. Generate stock reports to track inventory changes.";

                out.println(tips);

                socket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }  
    }   
}
