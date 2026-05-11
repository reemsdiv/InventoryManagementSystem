package model;

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
