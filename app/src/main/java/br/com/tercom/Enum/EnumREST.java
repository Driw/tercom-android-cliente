package br.com.tercom.Enum;

public enum EnumREST {

    //SERVICE
    ADDRESS("address"),
    AVALIABLE("avaliable"),
    MANUFACTURE("manufacturer"),
    PRODUCT("product"),
    PRODUCTFAMILY("productFamily"),
    PRODUCTGROUP("productGroup"),
    PRODUCTPACKAGE("productPackage"),
    PRODUCTSECTOR("productSector"),
    PRODUCTSUBGROUP("productSubGroup"),
    PRODUCTUNIT("productUnit"),
    PROVIDER("provider"),
    PROVIDERCONTACT("providerContact"),
    PRODUCTVALUE("productPrice"),
    QUOTEDPRODUCTPRICE("quotedProductPrice"),
    QUOTEDSERVICEPRICE("quotedServicePrice"),
    SERVICE("service"),
    SITE("site"),
    PRODUCTTYPE("productType"),
    SERVICEPRICE("servicePrice"),
    PERMISSION("permission"),
    LOGINCUSTOMER("loginCustomer"),
    MANAGEPERMISSIONS("managePermissions"),
    ORDERACCEPTANCE("orderAcceptance"),
    ORDERACCEPTANCEPRODUCT("orderAcceptanceProduct"),
    ORDERACCEPTANCESERVICE("orderAcceptanceService"),





    //METHODS
    ADD("add"),
    GET("get"),
    GETALL("getAll"),
    GETCATEGORIES("getCategories"),
    GETCONTACTS("getContacts"),
    GETGROUPS("getGroups"),
    GETPRODUCTSUBGROUP("getSubGroups"),
    GETPRODUCTSUBSECTOR("getSubSectors"),
    GETPRODUCTSECTOR("getSector"),
    LIST("list"),
    REMOVE("remove"),
    SEARCH("search"),
    SET("set"),
    SETPHONE("setPhones"),
    GETSERVICE("getService"),
    LOGIN("login"),
    VERIFY("verify"),
    LOGOUT("logout"),
    ORDERREQUEST("orderRequest"),
    GETBYPRODUCT("getByProduct"),
    GETBYSERVICE("getByService"),
    ORDERITEMPRODUCT("orderItemProduct"),
    ORDERITEMSERVICE("orderItemService"),
    SETQUEUED("setQueued"),
    APPROVE("approve"),
    SETADDRESS("setAddress"),
    ORDERQUOTE("orderQuote"),

    //FILTERS
    FAMILY("family"),
    FANTASYNAME("fantasyName"),
    GROUP("group"),
    NAME("name"),
    SUBGROUP("subgroup"),
    SECTOR("sector"),
    GETBYCUSTOMER("getByCustomer") ,
    GETBYCUSTOMEREMPLOYEE("getByCustomerEmployee") ,
    CUSTOMER("customer");
    public String path;

    private EnumREST(String path) {
        this.path = path;
    }

}
