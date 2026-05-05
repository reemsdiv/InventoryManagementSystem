/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package network;

/**
 *
 * @author User
 */
import java.io.*;
import java.net.*;

public class ReorderClient {
    public static String sendReorderRequest(
            String productId,
            String productName,
            int currentQty,
            int minStock,
            int requestedQty
    ) {
        try {
            Socket socket = new Socket("localhost", 5000);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            String request =
                    "Product ID: " + productId
                    + " | Product Name: " + productName
                    + " | Current Quantity: " + currentQty
                    + " | Minimum Stock: " + minStock
                    + " | Requested Quantity: " + requestedQty;

            out.println(request);

            String response = in.readLine();

            socket.close();

            return response;

        } catch (Exception e) {
            return "Unable to connect to the supplier server. Please make sure SupplierServer is running.";
        }
    }
    
}
