package lab_2;


enum OrderResult {
    SUCCESS,
    OUT_OF_STOCK,
    PAYMENT_FAILED,
    SHIPPING_FAILED,
    NOTIFICATION_FAILED
}



class PaymentException extends RuntimeException {
    public PaymentException(String message) {
        super(message);
    }
}

class ShippingException extends RuntimeException {
    public ShippingException(String message) {
        super(message);
    }
}

class NotificationException extends RuntimeException {
    public NotificationException(String message) {
        super(message);
    }
}


// -------------------- SERVICES --------------------

class InventoryService {

    public boolean isAvailable(String item, int qty) {
        System.out.println(item + " is available");
        return true;
    }
}


class PaymentService {

    public boolean charge(String account, double amount) {

        if (account == null) {
            throw new PaymentException("Invalid payment account");
        }

        System.out.println("Payment successful");
        return true;
    }

    public void refund(String account, double amount) {
        System.out.println("Refunding " + amount + " to " + account);
    }
}


class ShippingService {

    public boolean schedule(String item, String address) {

        if (address == null) {
            throw new ShippingException("Invalid shipping address");
        }

        System.out.println(item + " will be shipped to " + address);
        return true;
    }

    public void cancel(String item, String address) {
        System.out.println("Cancelling shipping of " + item);
    }
}


class NotificationService {

    public boolean sendConfirmation(String email) {

        if (email == null) {
            throw new NotificationException("Invalid email");
        }

        System.out.println("Sending confirmation to " + email);
        return true;
    }
}



public class CheckoutFacade_v2 {

    private final InventoryService inventory;
    private final PaymentService payment;
    private final ShippingService shipping;
    private final NotificationService notification;


    public CheckoutFacade_v2(
            InventoryService inventory,
            PaymentService payment,
            ShippingService shipping,
            NotificationService notification) {

        this.inventory = inventory;
        this.payment = payment;
        this.shipping = shipping;
        this.notification = notification;
    }


    public OrderResult placeOrder(
            String item,
            int qty,
            String account,
            double amount,
            String address,
            String email) {

        if (!inventory.isAvailable(item, qty)) {
            return OrderResult.OUT_OF_STOCK;
        }


        try {

            if (!payment.charge(account, amount)) {
                return OrderResult.PAYMENT_FAILED;
            }

        } catch (PaymentException e) {

            System.out.println("Payment error: " + e.getMessage());
            return OrderResult.PAYMENT_FAILED;
        }


        try {

            if (!shipping.schedule(item, address)) {

                payment.refund(account, amount);

                return OrderResult.SHIPPING_FAILED;
            }

        } catch (ShippingException e) {

            payment.refund(account, amount);

            System.out.println("Shipping error: " + e.getMessage());

            return OrderResult.SHIPPING_FAILED;
        }


        try {

            if (!notification.sendConfirmation(email)) {

                shipping.cancel(item, address);
                payment.refund(account, amount);

                return OrderResult.NOTIFICATION_FAILED;
            }

        } catch (NotificationException e) {

            shipping.cancel(item, address);
            payment.refund(account, amount);

            System.out.println("Notification error: " + e.getMessage());

            return OrderResult.NOTIFICATION_FAILED;
        }


        return OrderResult.SUCCESS;
    }
}
