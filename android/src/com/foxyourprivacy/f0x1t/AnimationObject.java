package com.foxyourprivacy.f0x1t;

/**
 * The animations, that can be bought in the trophy section and are shown there
 * Created by Tim on 18.09.2016.
 */
public class AnimationObject {
    private final String name; //the animations name
    private final String toastDescription; //a short description of the animation
    private final int icon; //the path for an image representing the animation
    private final int price; //the animation's price in acorn
    private boolean unlocked; //whether the animation is already unlocked

    /**
     * @param name name for the animation
     * @param toastDescription text to give a hint about the animation for the user
     * @param icon image adress for the image to be displayed as depiction
     * @param price number of acorns this animation costs
     * @author Tim
     */
    public AnimationObject(String name, String toastDescription, int icon, int price) {
        this.name = name;
        this.toastDescription = toastDescription;
        this.icon = icon;
        this.price = price;
        ValueKeeper v=ValueKeeper.getInstance();
        this.unlocked = v.isAnimationUnlocked(name);

        //fetch the unlocked status form ValueKeeper where it's stored
        ValueKeeper o = ValueKeeper.getInstance();
        o.addAnimationIfNotContained(name, this.unlocked);

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



