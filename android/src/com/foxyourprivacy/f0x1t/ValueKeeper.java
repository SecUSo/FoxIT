package com.foxyourprivacy.f0x1t;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.foxyourprivacy.f0x1t.Activities.FoxItActivity;
import com.foxyourprivacy.f0x1t.AsyncTasks.DBWrite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Ich on 12.09.2016.
 */
//stores all the user's scores
public class ValueKeeper {
//this class implements the singleton design pattern therefor all instances are to be created by calling getInstance()


    static ValueKeeper instance;
    public HashMap<String, Boolean> trophyList = new HashMap<>();
    public int currentEvaluation = 0;
    public long[] timeOfEvaluation = {1482879600000L, 1483138800000L, 1483484400000L, 1483743600000L};
    public Boolean wasEvaluationDisplayed = false;
    public ArrayList<String> deinstalledApps = new ArrayList<>();
    public Boolean onboardingStartedBefore = false;
    public Boolean analysisDoneBefore = false;
    HashMap<String, Boolean> animationList = new HashMap<>();
    HashMap<String, String> profilList = new HashMap<>();
    HashMap<Long, Long> applicationAccessAndDuration = new HashMap<>();
    long timeOfLastAccess = 0;
    long timeOfFirstAccess = 0;
    HashMap<Long, Long> applicationStartAndDuration = new HashMap<>();
    HashMap<Long, Long> applicationStartAndActiveDuration = new HashMap<>();
    Boolean notDisplayed = true;
    String vpnCode;
    //the absolute first start
    long timeOfFirstStart = 0;
    int dailyLectionsUnlocked = 0;
    boolean valueKeeperAlreadyRefreshed = false;
    int numberOfTimesOpenedAtNight = 0;
    int numberOfTimesOpenedAtMorning = 0;
    ArrayList<String> solvedClasses = new ArrayList<>();
    ArrayList<Long> appStartsTheLastTwoDays = new ArrayList<>();
    Boolean isEvaluationOutstanding = false;
    ArrayList<String> appsBefore = new ArrayList<>();
    HashMap<String, String> evaluationResults = new HashMap<>();
    Boolean freshlyStartet = true;
    private int acornCount = 0; //amount of acorn the player collected
    private int tokenCount = 0; //amount of token the player collected

    private ValueKeeper() {
        super();
    }

    /**
     * create a new instance of the class at first call, return this instance at every other call
     *
     * @return the instance of this class
     * @author Tim
     */
    public static ValueKeeper getInstance() {
        if (instance == null) {
            instance = new ValueKeeper();
        }
        return instance;
    }

    public void setEvaluationResults(HashMap<String, String> evaluationResults) {
        this.evaluationResults.putAll(evaluationResults);
        Log.d("MyApp", "EvaluationsResults: " + this.evaluationResults.toString());
    }

    public void reviveInstance() {


        //HashMap<String,Boolean> trophyList=new HashMap<>();

        DBHandler db = new DBHandler(FoxItActivity.getAppContext(), null, null, 1);
        HashMap<String, String> data = db.getIndividualData();
        db.close();
        Log.d("MyApp", "Data:" + data.toString());
        analysisDoneBefore = Boolean.valueOf(data.get("analysisDoneBefore"));
        onboardingStartedBefore = Boolean.valueOf(data.get("onboardingStartedBefore"));


        if (data.containsKey("timeOfFirstStart")) {
            timeOfFirstStart = Long.valueOf(data.get("timeOfFirstStart"));
        } else {
            timeOfFirstStart = System.currentTimeMillis();
        }

        if (data.containsKey("acornCount")) {

            acornCount = Integer.valueOf(data.get("acornCount"));
        }
        if (data.containsKey("tokenCount")) {
            tokenCount = Integer.valueOf(data.get("tokenCount"));
        }
        if (data.containsKey("vpnCode")) {
            vpnCode = data.get("vpnCode");
        }
        if (data.containsKey("notDisplayed")) {
            notDisplayed = Boolean.valueOf(data.get("notDisplayed"));
        }
        if (data.containsKey("currentEvaluation")) {
            currentEvaluation = Integer.valueOf(data.get("currentEvaluation"));
        }
        if (data.containsKey("dailyLectionsUnlocked")) {
            dailyLectionsUnlocked = Integer.valueOf(data.get("dailyLectionsUnlocked"));
        }
        if (data.containsKey("numberOfTimesOpenedAtNight")) {
            numberOfTimesOpenedAtNight = numberOfTimesOpenedAtNight + Integer.valueOf(data.get("numberOfTimesOpenedAtNight"));
        }
        if (data.containsKey("numberOfTimesOpenedAtMorning")) {
            numberOfTimesOpenedAtMorning = numberOfTimesOpenedAtMorning + Integer.valueOf(data.get("numberOfTimesOpenedAtMorning"));
        }


        animationList = new HashMap<>();
        profilList = new HashMap<>();
        applicationAccessAndDuration = new HashMap<>();
        applicationStartAndActiveDuration = new HashMap<>();
        applicationStartAndActiveDuration = new HashMap<>();
        deinstalledApps = new ArrayList<>();
        evaluationResults = new HashMap<>();
        trophyList = new HashMap<>();
        appsBefore = new ArrayList<>();
        for (String e : data.keySet()) {
            if (e.contains("ani:")) {

                animationList.put(e.substring(4), Boolean.parseBoolean(data.get(e)));

            } else {
                if (e.contains("dur:")) {
                    applicationAccessAndDuration.put(Long.parseLong(e.substring(4)), Long.parseLong(data.get(e)));
                } else {
                    if (e.contains("stD:")) {
                        applicationStartAndDuration.put(Long.parseLong(e.substring(4)), Long.parseLong(data.get(e)));
                    } else {
                        if (e.contains("stA:")) {
                            applicationStartAndActiveDuration.put(Long.parseLong(e.substring(4)), Long.parseLong(data.get(e)));
                        } else {
                            if (e.contains("evl:")) {
                                evaluationResults.put(e.substring(4), data.get(e));
                            } else {
                                if (e.contains("dap:")) {
                                    deinstalledApps.add(data.get(e));
                                } else {
                                    if (e.contains("scl:")) {
                                        solvedClasses.add(data.get(e));
                                    } else {
                                        if (e.contains("asd:")) {
                                            appStartsTheLastTwoDays.add(Long.valueOf(data.get(e)));
                                        } else {
                                            if (e.contains("tro:")) {
                                                trophyList.put(e.substring(4), Boolean.valueOf(data.get(e)));
                                            } else {
                                                if (e.contains("app:")) {
                                                    appsBefore.add(data.get(e));
                                                }

                                            }

                                        }

                                    }
                                }
                            }
                        }
                    }
                }

            }


        }
        Log.d("ValueKeeper", "revived appsize: " + appsBefore.size());

        valueKeeperAlreadyRefreshed = true;
    }


    public void saveInstance() {

        Log.d("MyApp", "SavedInstance");
        HashMap<String, String> values = new HashMap<String, String>();
        values.put("onboardingStartedBefore", Boolean.toString(onboardingStartedBefore));
        values.put("analysisDoneBefore", Boolean.toString(analysisDoneBefore));
        values.put("acornCount", Integer.toString(acornCount));
        values.put("tokenCount", Integer.toString(tokenCount));
        values.put("notDisplayed", Boolean.toString(true));
        values.put("currentEvaluation", Integer.toString(currentEvaluation));
        values.put("numberOfTimesOpenedAtMorning", Integer.toString(numberOfTimesOpenedAtMorning));
        values.put("numberOfTimesOpenedAtNight", Integer.toString(numberOfTimesOpenedAtNight));
        values.put("vpnCode", vpnCode);
        values.put("dailyLectionsUnlocked", Integer.toString(dailyLectionsUnlocked));
        values.put("timeOfFirstStart", Long.toString(timeOfFirstStart));


        Log.d("MyApp", "currentEval:" + Integer.toString(currentEvaluation));
        //ArrayList<String> deinstalledApps=new ArrayList<>();


        for (Map.Entry<String, String> entry : evaluationResults.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            values.put("evl:" + key, value);
        }


        for (Map.Entry<String, Boolean> entry : animationList.entrySet()) {
            String key = entry.getKey();
            String value = Boolean.toString(entry.getValue());
            values.put("ani:" + key, value);

        }
        for (Map.Entry<String, Boolean> entry : trophyList.entrySet()) {
            String key = entry.getKey();
            String value = Boolean.toString(entry.getValue());
            values.put("tro:" + key, value);

        }

        for (Map.Entry<Long, Long> entry : applicationAccessAndDuration.entrySet()) {
            Long key = entry.getKey();
            Long value = entry.getValue();
            values.put("dur:" + Long.toString(key), Long.toString(value));

        }
        for (Map.Entry<Long, Long> entry : applicationStartAndDuration.entrySet()) {
            Long key = entry.getKey();
            Long value = entry.getValue();
            values.put("stD:" + Long.toString(key), Long.toString(value));

        }
        for (Map.Entry<Long, Long> entry : applicationStartAndActiveDuration.entrySet()) {
            Long key = entry.getKey();
            Long value = entry.getValue();
            values.put("stA:" + Long.toString(key), Long.toString(value));

        }
        values.put("currentEvaluation", Integer.toString(currentEvaluation));
        values.put("vpnCode", vpnCode);


        for (Map.Entry<String, String> entry : evaluationResults.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            values.put("eva:" + key, value);

        }
        int i = 0;
        // Log.d()
        for (String e : deinstalledApps) {
            values.put("dap:" + Integer.toString(i), e);
            i++;
        }

        int y = 0;
        // Log.d()
        for (String e : solvedClasses) {
            values.put("scl:" + Integer.toString(i), e);
            y++;
        }

        int x = 0;
        for (Long e : appStartsTheLastTwoDays) {
            values.put("asd:" + Integer.toString(x), Long.toString(e));
            x++;
        }


        final PackageManager pm = FoxItActivity.getAppContext().getPackageManager();
        //get a list of installed apps.
        if (pm == null) {
            Log.d("MyApp", "pm is Null");
        }
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        int t = 0;
        for (ApplicationInfo a : packages) {
            values.put("app:" + Integer.toString(t), pm.getApplicationLabel(a).toString());
            t++;
        }
        new DBWrite(FoxItActivity.getAppContext()).execute("clearAndSetValueKeeper", values);


    }


    /**
     * change the acorn count by the given value
     *
     * @param amount the amount of change (e.g. "-3" lowers the count by 3)
     * @author Tim
     */
    public void changeAcornCountBy(int amount) {
        if (amount != 0) {
            acornCount = acornCount + amount;
        }
    }

    /**
     * getter for the current acornCount
     *
     * @author Tim
     */
    public int getAcornCount() {
        return acornCount;
    }

    public boolean addAnimationIfNotContained(String animationName, boolean value) {
        if (animationList.containsKey(animationName)) {
            return true;
        } else {
            animationList.put(animationName, value);
            return false;
        }
    }

    public boolean isAnimationUnlocked(String animationName) {
        return animationList.get(animationName);

    }

    /**
     * @param animationName
     * @return true -> animation was found, false -> animation does not exist
     */
    public boolean unlockAnimation(String animationName) {
        if (animationList.containsKey(animationName)) {
            animationList.remove(animationName);
            animationList.put(animationName, true);
            return true;
        }
        return false;
    }

    public void changeTokenCountBy(int amount) {
        if (amount != 0) {
            tokenCount = tokenCount + amount;
        }
    }

    public int getTokenCount() {
        return tokenCount;
    }

    public int getCurrentEvaluation() {
        return currentEvaluation;
    }

    public int increaseCurrentEvaluation() {
        return currentEvaluation++;
    }

    public void setProfilList(ProfilListObject[] profilList) {
        for (ProfilListObject p : profilList) {
            this.profilList.put(p.getInputType(), p.getInput());
        }

    }

    public void setTimeOfLastAccess(long time) {
        timeOfLastAccess = time;
    }

    public void fillApplicationAccessAndDuration(long time) {
        if (applicationAccessAndDuration.containsKey(timeOfLastAccess)) {
            applicationAccessAndDuration.put(timeOfLastAccess, time - timeOfLastAccess);
        } else {
            applicationAccessAndDuration.put(timeOfLastAccess, time - timeOfLastAccess);
        }
        // Log.d("MyApp",applicationAccessAndDuration.toString());
    }

    public Boolean getFreshlyStartet() {
        return freshlyStartet;
    }

    public void setFreshlyStartet(Boolean freshlyStartet) {
        this.freshlyStartet = freshlyStartet;
    }

    public void fillApplicationStartAndDuration(long time) {
        applicationStartAndDuration.put(timeOfFirstAccess, time - timeOfFirstAccess);
        //Log.d("MyApp","TotalTime:"+applicationStartAndDuration.toString());
    }

    public void setTimeOfFirstAccess(long time) {
        timeOfFirstAccess = time;
    }

    public void fillApplicationStartAndActiveCDuration(long time) {
        long totalTime = 0;
        for (Long t : applicationAccessAndDuration.values()) {
            totalTime = totalTime + t;
        }

        applicationStartAndActiveDuration.put(timeOfFirstAccess, totalTime);
        //Log.d("MyApp","ActualTotalTime:"+applicationStartAndActiveDuration.toString());
    }

    public String getVpnCode() {
        return vpnCode;
    }

    public void setVpnCode(String vpnCode) {
        this.vpnCode = vpnCode;
    }

    public void setIsEvaluationOutstandingFalse() {
        isEvaluationOutstanding = false;
        notDisplayed = true;

    }

    public void removeFirstFromAppList() {
        Log.d("ValueKeeper", "remove1stfromapplistBefore:" + Integer.toString(deinstalledApps.size()));
        deinstalledApps.remove(0);
        Log.d("ValueKeeper", "remove1stfromapplistAfter:" + Integer.toString(deinstalledApps.size()));
    }


    public boolean isTrophyUnlocked(String trophyName) {
        if (trophyList.containsKey(trophyName)) {
            return trophyList.get(trophyName);
        }
        return false;
    }

    public void insertSolvedClass(String className) {
        if (!solvedClasses.contains(className)) {
            solvedClasses.add(className);
        }
    }

    public int getNumberOfSolvedClasses() {
        return solvedClasses.size();
    }

    public long getTimeOfFirstStart() {
        return timeOfFirstStart;
    }

    public int increaseNumberNight() {
        return numberOfTimesOpenedAtNight++;
    }

    public int increaseNumberMorning() {
        return numberOfTimesOpenedAtMorning++;
    }

    public int getNumberOfTimesOpenedAtNight() {
        return numberOfTimesOpenedAtNight;
    }

    public int getNumberOfTimesOpenedAtMorning() {
        return numberOfTimesOpenedAtMorning;
    }

    public void addAppStarts(Long time) {
        for (int i = 0; i < appStartsTheLastTwoDays.size(); i++) {
            if (appStartsTheLastTwoDays.get(i) < time - 2 * 86400000) {
                appStartsTheLastTwoDays.remove(i);
            }
            appStartsTheLastTwoDays.add(time);
        }
    }

    public int getSizeOfAppStarts() {
        return appStartsTheLastTwoDays.size();
    }

    public ArrayList<String> compareAppLists() {

        final PackageManager pm = FoxItActivity.getAppContext().getPackageManager();
        //get a list of installed apps.
        if (pm == null) {
            Log.d("MyApp", "pm is Null");
        }
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);


        ArrayList<String> appsNow = new ArrayList<>();
        for (ApplicationInfo n : packages) {
            appsNow.add(pm.getApplicationLabel(n).toString());
        }

        Collections.sort(appsNow, new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {

                if (lhs.equals(rhs)) {
                    return 0;
                }
                if (lhs == null) { //TODO ist immer falsch, weil vorher equals darauf aufgerufen wurde. entweder mit == vergleichen oben, anders anordnen oder was weiß ich was das machen soll :D
                    return -1;
                }
                if (rhs == null) {
                    return 1;
                }
                return lhs.compareTo(rhs);
            }

        });


        Collections.sort(appsBefore, new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {

                if (lhs.equals(rhs)) {
                    return 0;
                }
                if (lhs == null) {//TODO ist immer falsch, weil vorher equals darauf aufgerufen wurde. entweder mit == vergleichen oben, anders anordnen oder was weiß ich was das machen soll :D
                    return -1;
                }
                if (rhs == null) {
                    return 1;
                }
                return lhs.compareTo(rhs);
            }

        });

        ArrayList<String> result = new ArrayList<String>();

        if (appsNow.equals(appsBefore)) {
            Log.d("MyApp", "No change was found");
            return result;
        } else {
            for (String b : appsBefore) {

                if (!appsNow.contains(b)) {
                    result.add(b);
                }
            }

            //Log.d("MyApp", result.toString());
            return result;
        }

    }

    public void increaseDailyLectionsUnlocked() {
        dailyLectionsUnlocked++;
    }


}


