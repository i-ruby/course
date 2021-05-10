package work.iruby.course.common;

import lombok.Data;

@Data
public class Message {
    private String message;

    private Message(String message) {
        this.message = message;
    }

    public static Message of(String message) {
        return new Message(message);
    }
}
