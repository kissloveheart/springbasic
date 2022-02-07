package com.example.springboot.session;

import com.example.springboot.dto.ProductInfoDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CartInfo {
    private final List<CartLineInfo> cartLines = new ArrayList<>();

    private CartLineInfo findLineById(Long Id) {
        for (CartLineInfo line : cartLines) {
            if (line.getProductInfo().getId().equals(Id)) {
                return line;
            }
        }
        return null;
    }

    public void addProduct(ProductInfoDTO productInfo, Integer quantity) {
        CartLineInfo line = this.findLineById(productInfo.getId());

        if (line == null) {
            line = new CartLineInfo();
            line.setQuantity(0);
            line.setProductInfo(productInfo);
            this.cartLines.add(line);
        }
        int newQuantity = line.getQuantity() + quantity;
        if (newQuantity <= 0) {
            this.cartLines.remove(line);
        } else {
            line.setQuantity(newQuantity);
        }
    }

    public void updateProduct(Long id, Integer quantity) {
        CartLineInfo line = this.findLineById(id);
        int newQuantity = quantity + line.getQuantity();
        if (line != null) {
            if (newQuantity <= 0) {
                this.cartLines.remove(line);
            } else {
                line.setQuantity(newQuantity);
            }
        }
    }

    public void removeProduct(ProductInfoDTO productInfo) {
        CartLineInfo line = this.findLineById(productInfo.getId());
        if (line != null) {
            this.cartLines.remove(line);
        }
    }

    public boolean isEmpty() {
        return this.cartLines.isEmpty();
    }

    public int getQuantityTotal() {
        Integer quantity = 0;
        for (CartLineInfo line : this.cartLines) {
            quantity += line.getQuantity();
        }
        return quantity;
    }

    public double getAmountTotal() {
        Double total = 0.0;
        for (CartLineInfo line : this.cartLines) {
            total += line.getProductInfo().getPrice()* line.getQuantity();
        }
        return total;
    }



}
