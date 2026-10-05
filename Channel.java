public interface Channel {

    void send(String to, String body);

}

 class EmailChannel implements Channel {

    @Override
    public void send(String to, String body) {
        System.out.println("Sending EMAIL to: " + to);
        System.out.println("Message: " + body);
    }

}

 class SmsChannel implements Channel {

    @Override
    public void send(String to, String body) {
        System.out.println("Sending SMS to: " + to);
        System.out.println("Message: " + body);
    }

}

 abstract class Notification {

    protected final Channel channel;

    protected Notification(Channel channel) {
        this.channel = channel;
    }

    protected abstract String format(String text);

    public void send(String to, String text) {
        String formattedMessage = format(text);
        channel.send(to, formattedMessage);
    }

}

 class AlertNotification extends Notification {

    public AlertNotification(Channel channel) {
        super(channel);
    }

    @Override
    protected String format(String text) {
        return "[ALERT] " + text;
    }

}


 class ReminderNotification extends Notification {

    public ReminderNotification(Channel channel) {
        super(channel);
    }

    @Override
    protected String format(String text) {
        return "Reminder: " + text;
    }

}

 class PushChannel implements Channel {

    @Override
    public void send(String to, String body) {
        System.out.println("Sending PUSH notification to: " + to);
        System.out.println("Message: " + body);
    }

}

 class Main {

    public static void main(String[] args) {

        Channel email = new EmailChannel();
        Channel sms = new SmsChannel();
        Channel push = new PushChannel();

        Notification alertByEmail =
                new AlertNotification(email);

        Notification reminderBySms =
                new ReminderNotification(sms);

        Notification alertByPush =
                new AlertNotification(push);

        System.out.println("=== Email Alert ===");
        alertByEmail.send(
                "john@example.com",
                "Server is down!"
        );

        System.out.println();

        System.out.println("=== SMS Reminder ===");
        reminderBySms.send(
                "+96170123456",
                "Submit the lab assignment."
        );

        System.out.println();

        System.out.println("=== Push Alert ===");
        alertByPush.send(
                "user123",
                "New login detected!"
        );
    }

}