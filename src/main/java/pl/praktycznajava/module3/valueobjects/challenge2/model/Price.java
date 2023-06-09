package pl.praktycznajava.module3.valueobjects.challenge2.model;

import lombok.Value;
import pl.praktycznajava.module3.valueobjects.challenge2.CurrencyConverter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Value
public class Price {
    BigDecimal totalAmount;
    Currency currency;

    private Price(BigDecimal totalAmount, Currency currency) {
        if(totalAmount == null || totalAmount.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Invalid total amount price");
        }
        if(currency == null){
            throw new IllegalArgumentException("Currency can't be null");
        }
        this.totalAmount = totalAmount;
        this.currency = currency;
    }

    public static Price of(BigDecimal totalAmount, Currency currency) {
        return new Price(totalAmount, currency);
    }

    public Price convertCurrency(CurrencyConverter currencyConverter, Currency currency) {
        BigDecimal convertAmount = currencyConverter.convertTo(totalAmount, this.currency, currency);
        return Price.of(convertAmount, currency);
    }

    public Price ofValueDiscount(CurrencyConverter currencyConverter, BigDecimal discount, Currency discountCurrency) {
        BigDecimal convertedDiscountedAmount = currencyConverter.convertTo(discount, discountCurrency, this.currency);
        return ofValueDiscount(convertedDiscountedAmount);
    }

    public Price ofValueDiscount(BigDecimal discountedAmount) {
        BigDecimal discount = totalAmount.subtract(discountedAmount);
        return Price.of(discount, this.currency);
    }

    public Price ofPercentageDiscount(int percentageDiscount, int maxPercent) {
        BigDecimal percentageDiscountAmount = totalAmount.multiply(BigDecimal.valueOf(percentageDiscount))
                .divide(BigDecimal.valueOf(maxPercent), RoundingMode.HALF_UP);
        BigDecimal discountedAmount = totalAmount.subtract(percentageDiscountAmount);
        return Price.of(discountedAmount, this.currency);
    }

    public boolean greaterThan(BigDecimal amount) {
        return totalAmount.compareTo(amount) > 0;
    }
}
