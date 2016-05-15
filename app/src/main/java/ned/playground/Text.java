package ned.playground;

/**
 * Phone number and message should be immutable
 * Name can be set as it can be trickier to obtain some point later
 */
class Text {
    private String phoneNumber;
    private String name;
    private String message;

    public Text(final String phoneNumber, final String name, final String message) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.message = message;
    }

    public Text(final String phoneNumber, final String message) {
        this(phoneNumber, "", message);
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
