package com.foxyourprivacy.f0x1t;

import android.content.Context;
import android.util.Log;

import com.foxyourprivacy.f0x1t.asynctasks.DBWrite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Ich on 12.09.2016.
 */
//stores all the user's scores
public class ValueKeeper {
//this class implements the singleton design pattern therefor all instances are to be created by calling getInstance()


    static ValueKeeper instance;
    public HashMap<String, Boolean> trophyList = new HashMap<>();
    public int currentEvaluation;
    public Boolean onboardingStartedBefore = false;
    public Boolean analysisDoneBefore = false;
    public HashMap<String, Boolean> animationList = new HashMap<>();
    HashMap<String, String> profilList = new HashMap<>();
    HashMap<Long, Long> applicationAccessAndDuration = new HashMap<>();
    long timeOfLastAccess = 0;
    long timeOfFirstAccess = 0;
    HashMap<Long, Long> applicationStartAndDuration = new HashMap<>();
    HashMap<Long, Long> applicationStartAndActiveDuration = new HashMap<>();
    Boolean notDisplayed = true;
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
    Boolean freshlyStarted = true;
    private String username;
    private int acornCount = 0; //amount of acorn the player collected
    private int tokenCount = 0; //amount of token the player collected



    private long timeOfLastServerAccess =0;


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

    public void reviveInstance(Context context) {//TODO: make async and a loading screen


        //HashMap<String,Boolean> trophyList=new HashMap<>();

        DBHandler db = new DBHandler(context, null, null, 1);
        HashMap<String, String> data = db.getIndividualData();
        dailyLectionsUnlocked = db.howManyDailiesUnlocked();
        //Log.d("ValuesTest","Revived Data:\n" + data.toString()+"\nData size: "+Integer.toString(data.size()));
        db.close();
        analysisDoneBefore = Boolean.valueOf(data.get("analysisDoneBefore"));
        onboardingStartedBefore = Boolean.valueOf(data.get("onboardingStartedBefore"));


        if (data.containsKey("timeOfFirstStart")) {
            timeOfFirstStart = Long.valueOf(data.get("timeOfFirstStart"));
        } else {
            timeOfFirstStart = System.currentTimeMillis();
        }

        if(data.containsKey("timeOfLastServerAccess")){
            timeOfLastServerAccess=Long.valueOf(data.get("timeOfLastServerAccess"));
        }


        if (data.containsKey("acornCount")) {

            acornCount = Integer.valueOf(data.get("acornCount"));
        }
        if (data.containsKey("tokenCount")) {
            tokenCount = Integer.valueOf(data.get("tokenCount"));
        }
        if (data.containsKey("username")) {
            username = data.get("username");
        }
        if (data.containsKey("notDisplayed")) {
            notDisplayed = Boolean.valueOf(data.get("notDisplayed"));
        }
        if (data.containsKey("currentEvaluation")) {
            currentEvaluation = Integer.valueOf(data.get("currentEvaluation"));
        }
        if (data.containsKey("dailyLectionsUnlocked")) {
            // dailyLectionsUnlocked = Integer.valueOf(data.get("dailyLectionsUnlocked"));
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
        evaluationResults = new HashMap<>();
        trophyList = new HashMap<>();
        for (String e : data.keySet()) {
            if (e.contains("ani:")) {

                animationList.put(e.substring(4), Boolean.parseBoolean(data.get(e)));

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
                            if (e.contains("scl:")) {
                                solvedClasses.add(data.get(e));
                            } else {
                                if (e.contains("asd:")) {
                                    appStartsTheLastTwoDays.add(Long.valueOf(data.get(e)));
                                } else {
                                    if (e.contains("tro:")) {
                                        trophyList.put(e.substring(4), Boolean.valueOf(data.get(e)));
                                    } else {
                                        if (e.contains("dur:")) {
                                            applicationAccessAndDuration.put(Long.parseLong(e.substring(4)), Long.parseLong(data.get(e)));

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        animationList.put("Schwanzwedeln",true);
        valueKeeperAlreadyRefreshed = true;
    }

    public void saveInstance(Context context) {
        HashMap<String, String> values = new HashMap<String, String>();
        values.put("onboardingStartedBefore", Boolean.toString(onboardingStartedBefore));
        values.put("analysisDoneBefore", Boolean.toString(analysisDoneBefore));
        values.put("acornCount", Integer.toString(acornCount));
        values.put("tokenCount", Integer.toString(tokenCount));
        values.put("notDisplayed", Boolean.toString(true));
        values.put("currentEvaluation", Integer.toString(currentEvaluation));
        values.put("numberOfTimesOpenedAtMorning", Integer.toString(numberOfTimesOpenedAtMorning));
        values.put("numberOfTimesOpenedAtNight", Integer.toString(numberOfTimesOpenedAtNight));
        values.put("username", username);
        values.put("dailyLectionsUnlocked", Integer.toString(dailyLectionsUnlocked));
        values.put("timeOfFirstStart", Long.toString(timeOfFirstStart));
        values.put("timeOfLastServerAccess",Long.toString(timeOfLastServerAccess));


        Log.d("ValueKeeper", "currentEval:" + Integer.toString(currentEvaluation));
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

        for (Map.Entry<String, String> entry : evaluationResults.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            values.put("eva:" + key, value);

        }


        int i = 0;
        // Log.d()
        for (String e : solvedClasses) {
            values.put("scl:" + Integer.toString(i), e);
            i++;
        }

        int x = 0;
        for (Long e : appStartsTheLastTwoDays) {
            values.put("asd:" + Integer.toString(x), Long.toString(e));
            x++;
        }

        new DBWrite(context).execute("insertIndividualData", values);
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
        if (animationList.containsKey(animationName)) {
            return animationList.get(animationName);
        }
        return false;
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

    public Boolean getFreshlyStarted() {
        return freshlyStarted;
    }

    public void setFreshlyStarted(Boolean freshlyStartet) {
        this.freshlyStarted = freshlyStartet;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String userna) {
        this.username = userna;
    }

    public void setIsEvaluationOutstandingFalse() {
        isEvaluationOutstanding = false;
        notDisplayed = true;

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
            }}
            appStartsTheLastTwoDays.add(time);
           

    }

    public int getSizeOfAppStarts() {
        return appStartsTheLastTwoDays.size();
    }


    public void increaseDailyLectionsUnlocked() {
        dailyLectionsUnlocked++;
    }

    public long getTimeOfLastServerAccess() {
        return timeOfLastServerAccess;
    }

    public void setTimeOfThisServerAccess() {
        this.timeOfLastServerAccess = System.currentTimeMillis();
    }

    public void setTimeOfNextServerAccess(long nextTime) {
        this.timeOfLastServerAccess = nextTime - 259200000;
    }


}


