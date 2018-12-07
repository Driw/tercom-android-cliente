package br.com.tercom.Util;

import android.util.Pair;

/**
 * Created by Felipe on 09/02/2018.
 */

public class CustomPair<T> extends Pair<Boolean, T> {
    /**
     * Constructor for a Pair.
     *
     * @param first  the first object in the Pair
     * @param second the second object in the pair
     */
    public CustomPair(boolean first, T second) {
        super(first, second);
    }
}
