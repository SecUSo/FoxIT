package com.foxyourprivacy.f0x1t;

/**
 * Created by Tim on 01.08.2016.
 */
public class ClassObject {

    private String name;
    private String descriptionText; //text describing the class content
    private int position;

    /**
     * @param name             the className
     * @param classDescription the description of the class
     * @author Tim
     */
    public ClassObject(String name, String classDescription, int classposition) {
        this.name = name;
        this.descriptionText = classDescription;
        this.position = classposition;
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

    public int getPosition() {
        return position;
    }


}
