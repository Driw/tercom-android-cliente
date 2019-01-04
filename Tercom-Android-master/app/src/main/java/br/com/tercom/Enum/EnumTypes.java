package br.com.tercom.Enum;

import android.util.SparseArray;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum created to keep all possible variable types that will come from CustomEditText's attributes from xml.
 */
public enum EnumTypes
{
    STRING(0,String.class,String.class),
    INTEGER(1,Integer.class,int.class),
    FLOAT(2,Float.class,float.class),
    SHORT(3, Short.class, short.class);


    /**
     * Using Map to keep EnumTypes int and string values, to be able to get EnumType by int.
     */
    //private static Map<Integer, EnumTypes> values = new HashMap<>();
    private static SparseArray<EnumTypes> values = new SparseArray<>();
    public final int typeIndex;
    public final Class classType;
    public final Class primitive;

    EnumTypes(int index,Class classType, Class primitive) {
        this.classType = classType;
        this.primitive = primitive;
        this.typeIndex = index;
    }

    /*
      Fill Map with EnumType's int and String. Run only once.
     */
    static
    {
        for(EnumTypes value : EnumTypes.values())
            values.append(value.typeIndex, value);
    }
    /**
     *
     * @param intType Enum's int value
     * @return EnumTypes of the given value
     */
    public static EnumTypes getEnum(int intType)
    {
        return values.get(intType);
    }

}
