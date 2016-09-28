package com.bp;

/**
 * Created by Ich on 18.09.2016.
 */
public class AnimationObject {
String name; //the animations name
String toastDescription; //a short description of the animation
int icon; //the path for an image representing the animation
int price; //the animation's price in acorn
boolean unlocked; //whether the animation is already unlocked

    /**
     * @author Tim
     * @param name
     * @param toastDescription
     * @param icon
     * @param price
     */
    public AnimationObject(String name, String toastDescription, int icon, int price){
    this.name=name;
    this.toastDescription=toastDescription;
    this.icon=icon;
    this.price=price;

    //fetch the unlocked status form ObserverSingleton where it's stored
    ObserverSingleton o =ObserverSingleton.getInstance();
    o.addTrophyIfNotContained(name,unlocked);
    unlocked=o.isAnimationUnlocked(name);
}

    /**
     * check if the animation is already unlocked by accessing ObserverSingleton
     * @author Tim
     */
    public void checkUnlocked(){
    ObserverSingleton o=ObserverSingleton.getInstance();
    unlocked=o.isAnimationUnlocked(name);
}

    public boolean getUnlocked(){
        return unlocked;
    }
    public String getName(){
        return name;
    }
    public String getToastDescription(){
        return toastDescription;
    }
    public int getIcon(){
        return icon;
    }
    public int getPrice(){
        return price;
    }



}



