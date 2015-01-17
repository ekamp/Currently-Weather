package Model.ResponseParsing;

/**
 * Created by erikkamp on 1/17/15.
 */
public class GetAddressEvent {

    private String address;

    public GetAddressEvent(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}
