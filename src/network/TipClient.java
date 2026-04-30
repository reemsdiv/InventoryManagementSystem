/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package network;

/**
 *
 * @author User
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class TipClient {
     public static String getTip() {

        StringBuilder tips = new StringBuilder();

        try {
            Socket socket = new Socket("localhost", 5000);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            String line;
            while ((line = in.readLine()) != null) {
                tips.append(line).append("\n");
            }

            socket.close();

        } catch (Exception e) {
            return "Unable to connect to the server.\nPlease make sure TipServer is running.";
        }

        return tips.toString();
    }
    
}
