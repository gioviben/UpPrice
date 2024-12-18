package org.example;

import java.util.List;

public class InfoWeb {
    private String Web_Name;
    private String prodotto;
    private String URL;
    private ByParameters byParameters;
    private double ref_price;
    private String find_type;

    public InfoWeb(String web_Name, String prodotto, String URL, ByParameters byParameters, double ref_price, String find_type) {
        Web_Name = web_Name;
        this.prodotto = prodotto;
        this.URL = URL;
        this.byParameters = byParameters;
        this.ref_price = ref_price;
        this.find_type = find_type;
    }

    public String getWeb_Name() {
        return Web_Name;
    }

    public void setWeb_Name(String web_Name) {
        Web_Name = web_Name;
    }

    public String getProdotto() {
        return prodotto;
    }

    public void setProdotto(String prodotto) {
        this.prodotto = prodotto;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public ByParameters getByParameters() {
        return byParameters;
    }

    public String getFind_type() {
        return find_type;
    }

    public void setByParameters(ByParameters byParameters) {
        this.byParameters = byParameters;
    }

    public double getRef_price() {
        return ref_price;
    }

    public void setRef_price(double ref_price) {
        this.ref_price = ref_price;
    }
}
