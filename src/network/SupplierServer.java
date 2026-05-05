/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package network;


import java.io.*;
import java.net.*;

public class SupplierServer {

  
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(5000);
            System.out.println("Supplier Server is running...");

            while (true) {
                Socket socket = server.accept();

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );

                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                String request = in.readLine();

                System.out.println("Received reorder request:");
                System.out.println(request);

                out.println("Reorder request received successfully by supplier.");

                socket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
