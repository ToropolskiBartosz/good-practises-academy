package pl.praktycznajava.module2.encapsulation.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(staticName = "of")
public class Product {

    String name;
    BigDecimal price;
    int stockQuantity;
    double weight;


    public void setStockQuantity(int quantity) {
        if (stockQuantity < quantity) {
            throw new InsufficientStockException(this, quantity);
        }
        stockQuantity = stockQuantity - quantity;
    }

    public BigDecimal getPriceMultiplyQuantity(int quantity) {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public double getTotalWeight(int quantity) {
        return weight * quantity;
    }
}
