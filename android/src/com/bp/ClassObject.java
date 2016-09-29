package com.bp;

import java.util.ArrayList;

/**
 * Created by Tim on 01.08.2016.
 */
public class ClassObject {

    private String name;
    private String descriptionText; //text describing the class content

    /**
     * @author Tim
     * @param name the className
     * @param classDescription the description of the class
     */
    public ClassObject(String name, String classDescription){
        this.name=name;
        this.descriptionText=classDescription;
    }



    //Getter
    public String getName(){return name;}
    public String getDescriptionText() {return descriptionText;}
    //Setter
    public void setDescriptionText(String descriptionText) {this.descriptionText = descriptionText;}


}
