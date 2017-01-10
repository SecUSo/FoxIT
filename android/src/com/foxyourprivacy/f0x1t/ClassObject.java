package com.foxyourprivacy.f0x1t;

/**
 * Created by Tim on 01.08.2016.
 */
public class ClassObject {

    private String name;
    private String descriptionText; //text describing the class content

    /**
     * @param name             the className
     * @param classDescription the description of the class
     * @author Tim
     */
    public ClassObject(String name, String classDescription) {
        this.name = name;
        this.descriptionText = classDescription;
    }


    //Getter
    public String getName() {
        return name;
    }

    public String getDescriptionText() {
        return descriptionText;
    }

    //Setter
    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }


}
