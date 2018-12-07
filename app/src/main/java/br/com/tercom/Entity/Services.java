package br.com.tercom.Entity;

import java.util.ArrayList;

import br.com.tercom.Annotation.BindObject;
import butterknife.BindView;

public class Services extends GenericEntity{

    private int id;
    private String name;
    private String description;
    @BindObject()
    private Tags tags;
    private boolean inactive;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Tags getTags() {
        return tags;
    }

    public void setTags(Tags tags) {
        this.tags = tags;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }
}
