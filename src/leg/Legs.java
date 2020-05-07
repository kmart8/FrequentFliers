package leg;

import java.util.ArrayList;

/**
 * This class extends ArrayList and is an aggregate of Leg objects.
 *
 * @author Kevin Martin
 * @version 1.1 2020-03-23
 * @since 2020-03-23
 *
 */

public class Legs extends ArrayList<Leg> implements Cloneable{
    private static final long serialVersionUID = 1L;

    public Legs clone(){
        return (Legs) super.clone();
    }
}
