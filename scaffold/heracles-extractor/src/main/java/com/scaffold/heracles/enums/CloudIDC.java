package com.scaffold.heracles.enums;

/**
 * Created by Karl on 2022/3/3
 **/
public enum CloudIDC {
    HT2("HT2", "http://apiconf-bjht.esa.oppo.local"),
    HT3("HT3", "http://apiconf-bjht.esa.oppo.local"),
    CP("CP", "http://apiconf-bjcp.esa.oppo.local"),
    LY("LY", "http://apiconf-gzly.esa.oppo.local"),
    TEST_CLOUD("TEST_CLOUD", "http://apiconf-test.esa.wanyol.com");

    private CloudIDC(String name, String apiDomain) {
        this.apiDomain = apiDomain;
        this.name = name;
    }

    private String name;
    private String apiDomain;

    public String getName() {
        return name;
    }

    public String getApiDomain() {
        return apiDomain;
    }

}
