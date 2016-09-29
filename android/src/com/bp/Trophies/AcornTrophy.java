package com.bp.Trophies;
import com.bp.ValueKeeper;

/**
 * Created by Ich on 17.09.2016.
 */
public class AcornTrophy extends TrophyObject {

    public AcornTrophy(String name, int scoreNeeded, String toastDescription, boolean visibleScore, int icon) {
        super(name, scoreNeeded, toastDescription, visibleScore, icon);
    }


    @Override
/**check whether the trophy's conditions for unlocking are currently met and unlocks it
 * @author Tim
 * @return true ->trophy is unlocked, false -> trophy is locked
 */
    public boolean checkScore() {
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
