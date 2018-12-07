package br.com.tercom.Util;

import android.view.ViewGroup;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import br.com.tercom.Enum.EnumTypes;
import br.com.tercom.Boundary.BoundaryUtil.Component.CustomEditText;


/**
 * Class for filling entities properties in given ViewGroup, based on ViewGroup's CustomDataEditText.
 */
public abstract class GenericEntityFiller
{
    /**
     * Default Entities path in this project
     */
    private static String entitiesPath = "br.com.tercom.Entity.";
    private static EnumTypes type;
    /**
     * Method created to loop the received ViewGroup, getting each CustomEditText and their attributes to fill the designed Entity.
     * @param vg ViewGroup of the calling Activity where there's one or more CustomDataEditText.
     * @return HashMap containing the Entities, with properties values that user set. In case there is more than one Entity defined in the CustomDataEditText, the method will fill the HashMap with all the entities involved.
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     */
    public static HashMap<String, Object> fillEntity(ViewGroup vg) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        HashMap<String, Object> entities = new HashMap<>();
        for(int i = 0; i < vg.getChildCount(); i++)
        {
            if(vg.getChildAt(i) instanceof CustomEditText)
            {
                CustomEditText txt = (CustomEditText) vg.getChildAt(i);
                if(!entities.containsKey(txt.getEntity()))
                    entities.put(txt.getEntity(), Class.forName(entitiesPath + txt.getEntity()).newInstance());
                type = txt.getAttrType();
                if(!txt.getText().toString().equals(""))
                {
                    Method m = entities.get(txt.getEntity()).getClass().getDeclaredMethod("set" + txt.getAttribute(), type.primitive);
                    m.invoke(entities.get(txt.getEntity()), castValue(type.classType, txt.getText().toString()));
                }
            }
        }
        return entities;
    }

    private static <T> T castValue(Class<T> classType, String value)
    {
        switch(type)
        {
            case INTEGER:
                return classType.cast(Integer.parseInt(value));
            case FLOAT:
                return classType.cast(Float.parseFloat(value));
            case SHORT:
                return classType.cast(Short.parseShort(value));
            default:
                return classType.cast(value);
        }
    }
}
