package edu.nyu.lk.cookzilla.model;

public class Ingredients {
    private Integer ingredientsid;

    private String name;

    private Float quantities;

    private String purchaselink;

    private String allergen;

    public Integer getIngredientsid() {
        return ingredientsid;
    }

    public void setIngredientsid(Integer ingredientsid) {
        this.ingredientsid = ingredientsid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Float getQuantities() {
        return quantities;
    }

    public void setQuantities(Float quantities) {
        this.quantities = quantities;
    }

    public String getPurchaselink() {
        return purchaselink;
    }

    public void setPurchaselink(String purchaselink) {
        this.purchaselink = purchaselink == null ? null : purchaselink.trim();
    }

    public String getAllergen() {
        return allergen;
    }

    public void setAllergen(String allergen) {
        this.allergen = allergen == null ? null : allergen.trim();
    }
}