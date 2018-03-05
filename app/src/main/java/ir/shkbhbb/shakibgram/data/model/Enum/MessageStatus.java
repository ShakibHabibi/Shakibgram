package ir.shkbhbb.shakibgram.data.model.Enum;

public enum MessageStatus {

    POSTING(1),
    POSTED(2),
    RECEIVED(3),
    SEEN(4),
    FAILED(5);

    private int messageStatus;

    MessageStatus(int messageStatus) {
        this.messageStatus = messageStatus;
    }

    public int getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(int messageStatus) {
        this.messageStatus = messageStatus;
    }
}
