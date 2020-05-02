package leg;

import java.util.ArrayList;

/**
 * @author Kevin Martin
 * @version 1.1 2019-01-21
 * @since 2020-04-30
 *
 */

public class Legs extends ArrayList<Leg> implements Cloneable{
    private static final long serialVersionUID = 1L;

    public Legs clone(){
        return (Legs) super.clone();
    }
}
