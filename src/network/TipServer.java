/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package network;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TipServer {

    /**
     * @param args the command line arguments
     */
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
