package br.com.tercom.Entity;



import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;


import java.lang.annotation.Annotation;
import java.util.Collection;

import br.com.tercom.Annotation.BindObject;


public class GenericEntitiyAnnotation  {

    protected <T> T toObject(String json) throws JSONException {
        JSONObject jO  = new JSONObject(json);
//        Collection<? extends Element> annotatedElements = roundEnvironment.getElementsAnnotatedWith(BindObject.class);

        return (T) new Object();

    }

}
