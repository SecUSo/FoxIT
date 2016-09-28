package com.bp;

import java.util.HashMap;

/**
 * Created by Ich on 12.09.2016.
 */
//is going to store all the user's scores
public class ObserverSingleton {

    //it implements the singleton design pattern, therefor the class exists only once
    static ObserverSingleton instance;
    private int acornCount=0; //amount of acorn the player collected
    private int tokenCount=0;
    private ObserverSingleton() {};
    HashMap<String,Boolean> animationList =new HashMap<>();

    /**
     * create a new instance of the class at first call, return this instance at every other call
     * @author Tim
     * @return the instance of this class
     */
    public static ObserverSingleton getInstance(){
        if(instance==null){
            instance=new ObserverSingleton();
        }
        return instance;
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

    public boolean addTrophyIfNotContained(String animationName, boolean value){
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

}
