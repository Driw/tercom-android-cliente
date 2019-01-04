package br.com.tercom.Boundary.BoundaryUtil.Component;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import br.com.tercom.Application.AppTercom;
import br.com.tercom.Enum.EnumTypes;
import br.com.tercom.R;

public class CustomEditText extends android.support.v7.widget.AppCompatEditText {
    private String Entity;
    private String Attribute;
    private EnumTypes AttrType;

    public CustomEditText(Context context) {
        super(context);
        verifyAttributes(null);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        verifyAttributes(attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        verifyAttributes(attrs);
    }

    private void verifyAttributes(AttributeSet attrs)
    {
        TypedArray attributes = AppTercom.getContext().obtainStyledAttributes(attrs, R.styleable.CustomEditText);
        String attribute = attributes.getString(R.styleable.CustomEditText_Attribute);
        String entity = attributes.getString(R.styleable.CustomEditText_Entity);
        int attrType = attributes.getInt(R.styleable.CustomEditText_AttrType, -1);
        if(attribute == null || entity == null || attrType == -1)
            throw new RuntimeException
                    (
                    String.format("AttrType, Entity and Attribute MUST be set! Activity: %s Component: %s",
                            this.getContext().getClass().toString(),
                            this.getResources().getResourceEntryName(this.getId()))
                    );
        else
        {
            setEntity(entity);
            setAttribute(attribute);
            setAttrType(attrType);
        }
    }

    public String getEntity() {
        return Entity == null ? "" : Entity;
    }

    public void setEntity(String entity) {
        this.Entity = entity;
    }

    public String getAttribute() {
        return Attribute == null ? "" : Attribute;
    }

    public void setAttribute(String attribute) {
        this.Attribute = attribute;
    }

    public EnumTypes getAttrType() { return AttrType; }

    public void setAttrType(int attrType) { AttrType = EnumTypes.getEnum(attrType); }
}
