package com.foxyourprivacy.f0x1t.trophies;

import com.foxyourprivacy.f0x1t.ValueKeeper;

/**
 * Trophy that is unlocked by collecting a number of acorns
 * Created by Tim on 17.09.2016.
 */
public class AcornTrophy extends TrophyObject {

    public AcornTrophy(String name, int scoreNeeded, String toastDescription, String trophyDescription, boolean visibleScore, int icon, int iconSolved) {
        super(name, scoreNeeded, toastDescription, trophyDescription, visibleScore, icon, iconSolved);
    }


    /**
     * check whether the trophy's conditions for unlocking are currently met and unlocks it
     *
     * @return true ->trophy is unlocked, false -> trophy is locked
     * @author Tim
     */
    @Override
    public boolean checkScore() {
        //unlock the trophy if the current score succeeds the needed score
        unlocked = (scoreCurrently >= scoreNeeded);
        return unlocked;
    }


    /**fetch the current arcornCount from ObserverSingletonvand update the trophy's score
     * @author Tim
     */
    @Override
    public void updateScore() {
        ValueKeeper o = ValueKeeper.getInstance();
        scoreCurrently = o.getAcornCount();

    }


}
