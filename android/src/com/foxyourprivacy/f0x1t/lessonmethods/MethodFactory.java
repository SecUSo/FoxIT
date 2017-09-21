package com.foxyourprivacy.f0x1t.lessonmethods;

import android.app.Activity;

/**
 * Created by Tim on 19.07.2016.
 */
public class MethodFactory {
    //the activity the methodFactory is created in
    Activity activity;

    public MethodFactory(Activity activity) {
        this.activity = activity;
    }

    /**
     * methodLeft to implement the methodFactory-designpattern, creates subclasses of methodLeft all designated to a single purpose
     *
     * @param method the string contained in the lessonDescription describing which methodLeft is to be created
     * @return a subclass of Method implementing a fitting methodLeft()
     */
    public Method createMethod(String method) {
        Method methodClass;
        switch (method) {
            case "stateSwitch": {
                methodClass = new MethodStateSwitch();
                methodClass.setActivity(activity);
                return methodClass;
            }
            case "slideSwitch": {
                methodClass = new MethodSlideSwitch();
                methodClass.setActivity(activity);
                return methodClass;
            }
            case "scoreAdd": {
                methodClass = new MethodScoreAdd();
                methodClass.setActivity(activity);
                return methodClass;
            }
            case "changeAcornCount": {
                methodClass = new MethodChangeAcornCount();
                methodClass.setActivity(activity);
                return methodClass;
            }
            case "changeTokenCount": {
                methodClass = new MethodChangeTokenCount();
                methodClass.setActivity(activity);
                return methodClass;
            }
            case "webSwitch": {
                methodClass = new MethodWebSwitch();
                methodClass.setActivity(activity);
                return methodClass;
            }

            default: {
                methodClass = new MethodDoLittle();
                methodClass.setActivity(activity);
                return methodClass;
            }
        }

    }


}
