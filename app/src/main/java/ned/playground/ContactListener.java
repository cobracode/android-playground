package ned.playground;

import java.util.Map;

/**
 * Created by ned on 5/9/16.
 */
public interface ContactListener {

    //public void processContact(final String contactNumber, final String contactName);

    public void receiveContactNames(final Map<String, String> contacts);

}
