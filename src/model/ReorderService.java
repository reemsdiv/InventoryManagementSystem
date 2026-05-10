/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author User
 */
import network.ReorderClient;

public class ReorderService {
    public String processReorder(
            String productId,
            String productName,
            int currentQty,
            int minStock,
            String requestedQtyText
    ) {
        if (requestedQtyText == null || requestedQtyText.trim().isEmpty()) {
            return "Please enter the requested quantity.";
        }

        int requestedQty;

        try {
            requestedQty = Integer.parseInt(requestedQtyText.trim());

            if (requestedQty <= 0) {
                return "Requested quantity must be greater than 0.";
            }

        } catch (NumberFormatException e) {
            return "Requested quantity must be a valid number.";
        }

        return ReorderClient.sendReorderRequest(
                productId,
                productName,
                currentQty,
                minStock,
                requestedQty
        );
    }
    
}
