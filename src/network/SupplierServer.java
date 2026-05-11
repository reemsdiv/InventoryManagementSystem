package network;

import java.io.*;
import java.net.*;

public class SupplierServer {

/**
 * SupplierServer listens for reorder requests from clients
 * and sends confirmation responses back to them.
 */
    public static void main(String[] args) {
        try {
            // Create server socket on port 5000
            ServerSocket server = new ServerSocket(5000);
            System.out.println("Supplier Server is running...");

            // Keep server running continuously
            while (true) {
                
                Socket socket = server.accept();   // Wait for a client connection
              
                // Input stream to receive data from client
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );
                
                // Output stream to send response to client
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                // Read reorder request sent by client
                String request = in.readLine();
             
                // Display received request in server console
                System.out.println("Received reorder request:");
                System.out.println(request);
               
                // Send confirmation response back to client
                out.println("Reorder request received successfully by supplier.");
               
                // Close client connection
                socket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
