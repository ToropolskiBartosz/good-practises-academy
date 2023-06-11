package pl.praktycznajava.module3.valueobjects.challenge2.model;

import lombok.Value;
import pl.praktycznajava.module3.valueobjects.challenge2.CurrencyConverter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Value
public class Price {
    public static final int MAX_PERCENT = 100;

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

    public Price subtract(Price discount, CurrencyConverter currencyConverter) {
        BigDecimal convertedDiscountedAmount = currencyConverter.convertTo(discount.getTotalAmount(), discount.getCurrency(), this.currency);
        return subtract(convertedDiscountedAmount);
    }

    public Price subtract(BigDecimal discountedAmount) {
        BigDecimal discount = totalAmount.subtract(discountedAmount);
        return Price.of(discount, this.currency);
    }

    public Price subtractPercentageDiscount(int percentageDiscount) {
        BigDecimal percentageDiscountAmount = totalAmount.multiply(BigDecimal.valueOf(percentageDiscount))
                .divide(BigDecimal.valueOf(MAX_PERCENT), RoundingMode.HALF_UP);
        BigDecimal discountedAmount = totalAmount.subtract(percentageDiscountAmount);
        return Price.of(discountedAmount, this.currency);
    }

    public boolean greaterThan(Price price, CurrencyConverter currencyConverter) {
        if(currency != price.getCurrency()) {
            Price convertedCurrencyPrice = convertCurrency(currencyConverter, price.getCurrency());
            return convertedCurrencyPrice.greaterThan(price.getTotalAmount());
        } else {
            return greaterThan(price.getTotalAmount());
        }
    }

    public boolean greaterThan(BigDecimal amount) {
        return totalAmount.compareTo(amount) > 0;
    }
}
