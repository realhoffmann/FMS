package FMS.util;

import FMS.entities.Flight;
import FMS.provided.Matcher;

public class OriginMatcher implements Matcher<Flight> {
    private String origin;

    public OriginMatcher(String o){
        this.origin = o;
    }

    @Override
    public boolean match(Flight t) {
        if (origin.equals(t.getOrigin())) {
            return true;
        } else {
            return false;
        }
    }
}
