package network;

import java.io.*;
import java.net.*;

/**
 * This client sends reorder requests to the supplier server
 * and receives a response from the server.
 */
public class ReorderClient {
    public static String sendReorderRequest(
            String productId,
            String productName,
            int currentQty,
            int minStock,
            int requestedQty
    ) {
        try {
            // Connect to the supplier server on localhost using port 5000
            Socket socket = new Socket("localhost", 5000);
           
            // Output stream to send data to the server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
           
            // Input stream to receive response from the server
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            // Build reorder request message
            String request =
                    "Product ID: " + productId
                    + " | Product Name: " + productName
                    + " | Current Quantity: " + currentQty
                    + " | Minimum Stock: " + minStock
                    + " | Requested Quantity: " + requestedQty;
           
            // Send request to the server
            out.println(request);

            // Read response from server
            String response = in.readLine();

            // Close socket connection
            socket.close();

            return response;

        // Return error message if connection fails
        } catch (Exception e) {
            return "Unable to connect to the supplier server. Please make sure SupplierServer is running.";
        }
    }
}
