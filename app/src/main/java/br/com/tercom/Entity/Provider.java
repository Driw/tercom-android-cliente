package br.com.tercom.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;

import br.com.tercom.Annotation.BindObject;
import br.com.tercom.Interface.IProductValueItem;

import static br.com.tercom.Util.TextUtil.setCnpjMask;

@Entity(tableName = "Provider")
public class Provider extends GenericEntity implements IProductValueItem
{

    private int id;
    
    private String cnpj;

    private String companyName;

    private String fantasyName;

    private String site;
    
    private String spokesman;

    @BindObject()
    private Phone commercial;

    @BindObject()
    private Phone otherphone;

    private boolean inactive;

    @BindObject(type = BindObject.TYPE.LIST)
    private ArrayList<ProviderContact> contacts;

    @Override
    public String getName() {
        return fantasyName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCnpj() {
        if(cnpj == null)
            return "Não informado";
//        return setCnpjMask(cnpj);
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFantasyName() {
        if(fantasyName == null)
            return "Não informado";
        return fantasyName;
    }

    public void setFantasyName(String fantasyName) {
        this.fantasyName = fantasyName;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSpokesman() {
        if(spokesman == null)
            return "Representante não informado";
        return spokesman;
    }

    public void setSpokesman(String spokesman) {
        this.spokesman = spokesman;
    }

    public Phone getCommercial() {
        return commercial;
    }

    public void setCommercial(Phone commercial) {
        this.commercial = commercial;
    }

    public Phone getOtherphone() {
        return otherphone;
    }

    public void setOtherphone(Phone otherphone) {
        this.otherphone = otherphone;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public ArrayList<ProviderContact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<ProviderContact> contacts) {
        this.contacts = contacts;
    }
}
