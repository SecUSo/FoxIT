package com.foxyourprivacy.f0x1t.trophies;

/**
 * Trophy objects are shown in the achievements view and can be unlocked through various actions
 * usually those actions have to do with using the app a lot
 * Created by Ich on 17.09.2016.
 */
abstract public class TrophyObject {

    final int scoreNeeded; //the value that has to be reached for the trophy to be unlocked
    private final String name; //the trophy's name
    private final String trophyDescription;
    private final String toastDescription; //the toast displayed when you click the trophy (should describe it)
    private final boolean visibleScore; //if the progress is displayed (eg. 4/10) or not (???)
    private final int icon; //the trophy's icon resource path
    private final int iconSolved;
    int scoreCurrently; //the current value
    boolean unlocked; //whether the trophy is already unlocked

    TrophyObject(String name, int scoreNeeded, String toastDescription, String trophyDescription, boolean visibleScore, int icon, int iconSolved) {
        this.name = name;
        this.scoreNeeded = scoreNeeded;
        this.toastDescription = toastDescription;
        this.trophyDescription = trophyDescription;
        //TODO was soll currentScore eigentlich zugewiesen werden?
        this.visibleScore = visibleScore;
        this.icon = icon;
        this.iconSolved = iconSolved;
    }

    /**
     * check whether the trophy's conditions for unlocking are currently met and unlocks it
     *
     * @return true ->trophy is unlocked, false -> trophy is locked
     * @author Tim
     */
    abstract public boolean checkScore();


    /**
     * fetch the current score of the trophy from ValueKeeper and update it
     *
     * @author Tim
     */
    abstract public void updateScore();


    public String getName() {
        return name;
    }

    public int getScoreNeeded() {
        return scoreNeeded;
    }

    public int getIcon() {
        return icon;
    }

    public boolean isVisibleScore() {
        return visibleScore;
    }

    public String getToastDescription() {
        return toastDescription;
    }

    public int getIconSolved() {
        return iconSolved;
    }

    public String getTrophyDescription() {
        return trophyDescription;
    }

}
