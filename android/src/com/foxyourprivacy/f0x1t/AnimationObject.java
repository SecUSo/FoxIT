package com.foxyourprivacy.f0x1t;

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
     * @param name
     * @param toastDescription
     * @param icon
     * @param price
     * @author Tim
     */
    public AnimationObject(String name, String toastDescription, int icon, int price, boolean unlocked) {
        this.name = name;
        this.toastDescription = toastDescription;
        this.icon = icon;
        this.price = price;
        this.unlocked = unlocked;

        //fetch the unlocked status form ValueKeeper where it's stored
        ValueKeeper o = ValueKeeper.getInstance();
        o.addAnimationIfNotContained(name, unlocked);
        unlocked = o.isAnimationUnlocked(name);
    }

    /**
     * check if the animation is already unlocked by accessing ValueKeeper
     *
     * @author Tim
     */
    public void checkUnlocked() {
        ValueKeeper o = ValueKeeper.getInstance();
        unlocked = o.isAnimationUnlocked(name);
    }

    public boolean getUnlocked() {
        return unlocked;
    }

    public String getName() {
        return name;
    }

    public String getToastDescription() {
        return toastDescription;
    }

    public int getIcon() {
        return icon;
    }

    public int getPrice() {
        return price;
    }


}



