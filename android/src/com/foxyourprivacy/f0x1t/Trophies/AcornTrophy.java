package com.foxyourprivacy.f0x1t.Trophies;

import com.foxyourprivacy.f0x1t.ValueKeeper;

/**
 * Created by Ich on 17.09.2016.
 */
public class AcornTrophy extends TrophyObject {

    public AcornTrophy(String name, int scoreNeeded, String toastDescription, String trophyDescribtion, boolean visibleScore, int icon, int iconSolved) {
        super(name, scoreNeeded, toastDescription, trophyDescribtion, visibleScore, icon, iconSolved);
    }


    @Override
/**check whether the trophy's conditions for unlocking are currently met and unlocks it
 * @author Tim
 * @return true ->trophy is unlocked, false -> trophy is locked
 */
    public boolean checkScore() {
        //unlock the trophy if the current score succeeds the needed score
        unlocked = (scoreCurrently >= scoreNeeded);
        return unlocked;
    }


    @Override
/**fetch the current arcornCount from ObserverSingletonvand update the trophy's score
 * @author Tim
 */
    public void updateScore() {
        ValueKeeper o = ValueKeeper.getInstance();
        scoreCurrently = o.getAcornCount();

    }


}
