package pl.praktycznajava.module3.valueobjects.challenge2;

import pl.praktycznajava.module3.valueobjects.challenge2.model.Currency;
import pl.praktycznajava.module3.valueobjects.challenge2.model.Order;
import pl.praktycznajava.module3.valueobjects.challenge2.model.Price;

import java.math.BigDecimal;

public class OrderService {

    public static final Currency FREE_SHIPPING_THRESHOLD_CURRENCY = Currency.USD;
    public static final BigDecimal FREE_SHIPPING_THRESHOLD_AMOUNT = BigDecimal.valueOf(100);
    public static final int MAX_PERCENT = 100;
    OrderRepository orderRepository;
    CurrencyConverter currencyConverter;

    // TODO do obiektu domenowego nie można przekazać konkretnego konwertera, ale można przekazać jego interfejs
    // TODO możesz zmienić interfejs CurrencyConverter

    public boolean hasFreeShipping(String orderId) {
        Order order = orderRepository.findBy(orderId);
        Price price = order.getPrice();
        if(price.getCurrency() != FREE_SHIPPING_THRESHOLD_CURRENCY) {
            Price convertedCurrencyPrice = price.convertCurrency(currencyConverter, FREE_SHIPPING_THRESHOLD_CURRENCY);
            return convertedCurrencyPrice.greaterThan(FREE_SHIPPING_THRESHOLD_AMOUNT);
        } else {
            return price.greaterThan(FREE_SHIPPING_THRESHOLD_AMOUNT);
        }
    }

    public void addDiscount(String orderId, BigDecimal discount, Currency discountCurrency) {
        Order order = orderRepository.findBy(orderId);
        Price price = order.getPrice();
        Price priceDiscounted = price.ofValueDiscount(currencyConverter, discount, discountCurrency);
        order.changeTotalPrice(priceDiscounted);
        orderRepository.save(order);
    }

    public void addPercentageDiscount(String orderId, int percentageDiscount) {
        Order order = orderRepository.findBy(orderId);
        Price price = order.getPrice();
        Price pricePercentageDiscount = price.ofPercentageDiscount(percentageDiscount, MAX_PERCENT);
        order.changeTotalPrice(pricePercentageDiscount);
        orderRepository.save(order);
    }

}