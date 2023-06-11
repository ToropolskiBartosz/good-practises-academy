package pl.praktycznajava.module3.valueobjects.challenge2;

import pl.praktycznajava.module3.valueobjects.challenge2.model.Currency;
import pl.praktycznajava.module3.valueobjects.challenge2.model.Order;
import pl.praktycznajava.module3.valueobjects.challenge2.model.Price;

import java.math.BigDecimal;

public class OrderService {

    public static final Price FREE_SHIPPING_THRESHOLD = Price.of(BigDecimal.valueOf(100), Currency.USD);

    OrderRepository orderRepository;
    CurrencyConverter currencyConverter;

    // TODO do obiektu domenowego nie można przekazać konkretnego konwertera, ale można przekazać jego interfejs
    // TODO możesz zmienić interfejs CurrencyConverter

    public boolean hasFreeShipping(String orderId) {
        Order order = orderRepository.findBy(orderId);
        Price price = order.getPrice();
        return price.greaterThan(FREE_SHIPPING_THRESHOLD, currencyConverter);
    }

    public void addDiscount(String orderId, BigDecimal discount, Currency discountCurrency) {
        Order order = orderRepository.findBy(orderId);
        Price price = order.getPrice();
        Price discountPrice = Price.of(discount, discountCurrency);
        Price priceDiscounted = price.subtract(discountPrice, currencyConverter);
        order.changeTotalPrice(priceDiscounted);
        orderRepository.save(order);
    }

    public void addPercentageDiscount(String orderId, int percentageDiscount) {
        Order order = orderRepository.findBy(orderId);
        Price price = order.getPrice();
        Price pricePercentageDiscount = price.subtractPercentageDiscount(percentageDiscount);
        order.changeTotalPrice(pricePercentageDiscount);
        orderRepository.save(order);
    }

}