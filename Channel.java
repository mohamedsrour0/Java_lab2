
import java.util.List;
import java.util.Objects;


// -------------------- SendResult --------------------

class SendResult {

    private final boolean delivered;
    private final String detail;

    public SendResult(boolean delivered, String detail) {
        this.delivered = delivered;
        this.detail = detail;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public String getDetail() {
        return detail;
    }
}


// -------------------- Channel Interface --------------------

interface Channel {

    SendResult send(String to, String body);

}


// -------------------- Email Channel --------------------

class EmailChannel implements Channel {

    @Override
    public SendResult send(String to, String body) {

        System.out.println("Sending EMAIL to: " + to);
        System.out.println("Message: " + body);

        return new SendResult(
                true,
                "Email delivered successfully"
        );
    }
}


// -------------------- SMS Channel --------------------

class SmsChannel implements Channel {

    @Override
    public SendResult send(String to, String body) {

        System.out.println("Sending SMS to: " + to);
        System.out.println("Message: " + body);

        return new SendResult(
                true,
                "SMS delivered successfully"
        );
    }
}


// -------------------- Push Channel --------------------

class PushChannel implements Channel {

    @Override
    public SendResult send(String to, String body) {

        System.out.println("Sending PUSH notification to: " + to);
        System.out.println("Message: " + body);

        return new SendResult(
                true,
                "Push notification delivered successfully"
        );
    }
}


// -------------------- WhatsApp Channel --------------------

class WhatsappChannel implements Channel {

    @Override
    public SendResult send(String to, String body) {

        System.out.println("Sending WHATSAPP message to: " + to);
        System.out.println("Message: " + body);

        return new SendResult(
                true,
                "WhatsApp message delivered successfully"
        );
    }
}


// -------------------- Notification Abstraction --------------------

abstract class Notification {

    private final Channel channel;

    protected Notification(Channel channel) {

        this.channel = Objects.requireNonNull(
                channel,
                "Channel cannot be null"
        );
    }

    protected abstract String format(String text);

    public SendResult send(String to, String text) {

        return channel.send(
                to,
                format(text)
        );
    }
}


// -------------------- Alert Notification --------------------

class AlertNotification extends Notification {

    public AlertNotification(Channel channel) {
        super(channel);
    }

    @Override
    protected String format(String text) {

        return "[ALERT] " + text;
    }
}


// -------------------- Reminder Notification --------------------

class ReminderNotification extends Notification {

    public ReminderNotification(Channel channel) {
        super(channel);
    }

    @Override
    protected String format(String text) {

        return "Reminder: " + text;
    }
}


// -------------------- Digest Notification --------------------

class DigestNotification extends Notification {

    public DigestNotification(Channel channel) {
        super(channel);
    }

    @Override
    protected String format(String text) {

        return "[DIGEST] " + text;
    }

    public SendResult sendDigest(
            String to,
            List<String> messages
    ) {

        String combinedMessages =
                String.join("; ", messages);

        return send(
                to,
                combinedMessages
        );
    }
}


// -------------------- Main --------------------

class Main {

    public static void main(String[] args) {


        // Channels

        Channel email =
                new EmailChannel();

        Channel sms =
                new SmsChannel();

        Channel push =
                new PushChannel();

        Channel whatsapp =
                new WhatsappChannel();


        // Notifications

        Notification emailAlert =
                new AlertNotification(email);

        Notification smsReminder =
                new ReminderNotification(sms);

        Notification pushAlert =
                new AlertNotification(push);

        DigestNotification whatsappDigest =
                new DigestNotification(whatsapp);


        // ---------------- TEST 1 ----------------

        System.out.println(
                "=== Email Alert ==="
        );

        SendResult result1 =
                emailAlert.send(
                        "john@example.com",
                        "Server is down!"
                );

        System.out.println(
                "Result: " +
                result1.getDetail()
        );


        // ---------------- TEST 2 ----------------

        System.out.println();

        System.out.println(
                "=== SMS Reminder ==="
        );

        SendResult result2 =
                smsReminder.send(
                        "+96170123456",
                        "Submit the lab assignment."
                );

        System.out.println(
                "Result: " +
                result2.getDetail()
        );


        // ---------------- TEST 3 ----------------

        System.out.println();

        System.out.println(
                "=== Push Alert ==="
        );

        SendResult result3 =
                pushAlert.send(
                        "user123",
                        "New login detected!"
                );

        System.out.println(
                "Result: " +
                result3.getDetail()
        );


        // ---------------- TEST 4 ----------------

        System.out.println();

        System.out.println(
                "=== WhatsApp Digest ==="
        );

        SendResult result4 =
                whatsappDigest.sendDigest(

                        "+96170123456",

                        List.of(
                                "Meeting at 10 AM",
                                "Lab submission tomorrow",
                                "Presentation on Friday"
                        )
                );

        System.out.println(
                "Result: " +
                result4.getDetail()
        );


        System.out.println();

        System.out.println(
                "Delivered: " +
                result4.isDelivered()
        );
    }
}