package com.bp;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Ich on 12.09.2016.
 */
//stores all the user's scores
public class ValueKeeper {
//this class implements the singleton design pattern therefor all instances are to be created by calling getInstance()


    static ValueKeeper instance;
    private int acornCount=0; //amount of acorn the player collected
    private int tokenCount=0; //amount of token the player collected
    HashMap<String,Boolean> animationList =new HashMap<>();
    HashMap<String,String> profilList=new HashMap<>();
    HashMap<Long,Long> applicationAccessAndDuration =new HashMap<>();
    long timeOfLastAccess=0;
    long timeOfFirstAccess=0;
    HashMap<Long,Long> applicationStartAndDuration =new HashMap<>();
    HashMap<Long,Long> applicationStartAndActiveDuration =new HashMap<>();
    int currentEvaluation=0;
    int[] timeOfEvaluation ={1829816};
    Boolean notDisplayed=true;
    String vpnCode;

     String[] s={"a","b","c"};
    ArrayList<String> deinstalledApps=new ArrayList<String>(Arrays.asList(s)); //new Arraylist<>();
    Boolean isEvaluationOutstanding=false;

    ArrayList<String> appsBefore;


    public void setEvaluationResults(HashMap<String, String> evaluationResults) {
        this.evaluationResults.putAll(evaluationResults);
        Log.d("MyApp",this.evaluationResults.toString());
    }

    HashMap<String,String> evaluationResults =new HashMap<>();
    Boolean freshlyStartet=true;


    /**
     * create a new instance of the class at first call, return this instance at every other call
     * @author Tim
     * @return the instance of this class
     */
    public static ValueKeeper getInstance(){
        if(instance==null){
            instance=new ValueKeeper();
        }
        return instance;
    }



    private ValueKeeper(){
        super();
    }



    public void reviveInstance(){



        Log.d("MyApp","Wiederherstellung abgeschloßenYY");
        DBHandler db=new DBHandler(FoxItActivity.getAppContext(),null,null,1);
        HashMap<String,String> data =  db.getIndividualData();
        Log.d("MyApp","Data:"+data.toString());
        Log.d("MyApp","Wiederherstellung abgeschloßenXX");


        if(data.containsKey("acornCount")) {
            acornCount = Integer.valueOf(data.get("acornCount"));
            tokenCount = Integer.valueOf(data.get("tokenCount"));
            vpnCode = data.get("vpnCode");
            notDisplayed=Boolean.getBoolean(data.get("notDisplayed"));
            currentEvaluation=Integer.valueOf(data.get("currentEvaluation"));
            }


        animationList=new HashMap<>();
        profilList=new HashMap<>();
        applicationAccessAndDuration=new HashMap<>();
        applicationStartAndActiveDuration=new HashMap<>();
        applicationStartAndActiveDuration=new HashMap<>();
        deinstalledApps= new ArrayList<>();
        evaluationResults=new HashMap<>();
        for(String e:data.keySet()){
            if(e.contains("ani:")){

                animationList.put(e.substring(4),Boolean.parseBoolean(data.get(e)));

            }else{
                if(e.contains("dur:")){
                    applicationAccessAndDuration.put(Long.parseLong(e.substring(4)),Long.parseLong(data.get(e)));
                }else{
                    if(e.contains("stD:")){
                        applicationStartAndDuration.put(Long.parseLong(e.substring(4)),Long.parseLong(data.get(e)));
                    }else{
                        if(e.contains("stA:")) {
                            applicationStartAndActiveDuration.put(Long.parseLong(e.substring(4)), Long.parseLong(data.get(e)));
                       }else{
                            if(e.contains("evl:")){
                            evaluationResults.put(e.substring(4),data.get(e));}else{
                                if(e.contains("dap:")){
                                    deinstalledApps.add(data.get(e));
                                }
                            }
                        }
                        }
                    }

               }
            }

        Log.d("MyApp","deinstalAfter"+deinstalledApps.toString());
        }







    public void saveInstance(){


        DBHandler db= new DBHandler(FoxItActivity.getAppContext(),null,null,1);

        db.insertIndividualValue("acornCount",Integer.toString(acornCount));
        db.insertIndividualValue("tokenCount",Integer.toString(tokenCount));
        db.insertIndividualValue("notDisplayed",Boolean.toString(true));
        db.insertIndividualValue("currentEvaluation",Integer.toString(currentEvaluation));

        Log.d("MyApp","currentEval:"+Integer.toString(currentEvaluation));
        //ArrayList<String> deinstalledApps=new ArrayList<>();


        for (Map.Entry<String, String> entry : evaluationResults.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            db.insertIndividualValue("evl:"+key,value);
        }



        for (Map.Entry<String, Boolean> entry : animationList.entrySet()) {
            String key = entry.getKey();
            String value = Boolean.toString(entry.getValue());
        db.insertIndividualValue("ani:"+key,value);

        }

        for (Map.Entry<Long, Long> entry : applicationAccessAndDuration.entrySet()) {
            Long key = entry.getKey();
            Long value = entry.getValue();
            db.insertIndividualValue("dur:"+Long.toString(key),Long.toString(value));

        }
        for (Map.Entry<Long, Long> entry : applicationStartAndDuration.entrySet()) {
            Long key = entry.getKey();
            Long value = entry.getValue();
            db.insertIndividualValue("stD:"+Long.toString(key),Long.toString(value));

        }
        for (Map.Entry<Long, Long> entry : applicationStartAndActiveDuration.entrySet()) {
            Long key = entry.getKey();
            Long value = entry.getValue();
            db.insertIndividualValue("stA:"+Long.toString(key),Long.toString(value));

        }
        db.insertIndividualValue("currentEvaluation",Integer.toString(currentEvaluation));
        db.insertIndividualValue("vpnCode",vpnCode);



        for (Map.Entry<String, String> entry : evaluationResults.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            db.insertIndividualValue("eva:"+key,value);

        }
        int i=0;
       // Log.d()
        for (String e : deinstalledApps) {
            db.insertIndividualValue("dap:"+Integer.toString(i),e);
            i++;
        }

        Log.d("MyApp","deinstalBefore"+deinstalledApps.toString());
    }


    /**
     * change the acorn count by the given value
     * @author Tim
     * @param amount the amount of change (e.g. "-3" lowers the count by 3)
     */
    public void changeAcornCountBy(int amount){
        if(amount!=0){
            acornCount=acornCount+amount;
        }
    }

    /**
     * getter for the current acornCount
     * @author Tim
     */
    public int getAcornCount(){
        return acornCount;
    }

    public boolean addAnimationIfNotContained(String animationName, boolean value){
        if(animationList.containsKey(animationName)){
         return true;
        }else{
            animationList.put(animationName,value);
            return false;
        }
    }
    public boolean isAnimationUnlocked(String animationName){
        return animationList.get(animationName);

    }

    /**
     *
     * @param animationName
     * @return true -> animation was found, false -> animation does not exist
     */
    public boolean unlockAnimation(String animationName){
        if(animationList.containsKey(animationName)){
            animationList.remove(animationName);
            animationList.put(animationName,true);
            return true;
        }
        return false;
    }

    public void changeTokenCountBy(int amount){
        if(amount!=0){
            tokenCount=tokenCount+amount;
        }
    }
    public int getTokenCount(){
        return tokenCount;
    }
    public int getCurrentEvaluation(){
        return currentEvaluation;
    }
    public int increaseCurrentEvaluation(){
     return currentEvaluation++;
    }
    public void setProfilList(ProfilListObject[] profilList){
        for(ProfilListObject p:profilList){
            this.profilList.put(p.getInputType(),p.getInput());
        }

    }
public void setTimeOfLastAccess(long time){
    timeOfLastAccess=time;
}
    public void fillApplicationAccessAndDuration(long time){
        if(applicationAccessAndDuration.containsKey(timeOfLastAccess)){
           applicationAccessAndDuration.put(timeOfLastAccess,time-timeOfLastAccess);
        }else{
            applicationAccessAndDuration.put(timeOfLastAccess,time-timeOfLastAccess);
        }
       // Log.d("MyApp",applicationAccessAndDuration.toString());
    }

    public Boolean getFreshlyStartet() {
        return freshlyStartet;
    }

    public void setFreshlyStartet(Boolean freshlyStartet) {
        this.freshlyStartet = freshlyStartet;
    }
    public void fillApplicationStartAndDuration(long time){
        applicationStartAndDuration.put(timeOfFirstAccess,time-timeOfFirstAccess);
    //Log.d("MyApp","TotalTime:"+applicationStartAndDuration.toString());
    }
    public void setTimeOfFirstAccess(long time){
        timeOfFirstAccess=time;
    }
    public void fillApplicationStartAndActiveCDuration(long time){
        long totalTime=0;
        for(Long t:applicationAccessAndDuration.values()){
            totalTime=totalTime+t;
        }

        applicationStartAndActiveDuration.put(timeOfFirstAccess,totalTime);
        //Log.d("MyApp","ActualTotalTime:"+applicationStartAndActiveDuration.toString());
    }

    public void setVpnCode(String vpnCode) {
        this.vpnCode = vpnCode;
    }

    public void setIsEvaluationOutstandingFalse(){
        isEvaluationOutstanding=false;
        notDisplayed=true;

    }

    public void removeFirstFromAppList(){
        Log.d("MyApp","Before:"+Integer.toString(deinstalledApps.size()));
        deinstalledApps.remove(0);
        Log.d("MyApp","After:"+Integer.toString(deinstalledApps.size()));
    }

}
