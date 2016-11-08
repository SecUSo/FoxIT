package com.bp;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Tim on 25.06.2016.
 */
public abstract class Slide extends Fragment{
    //Strings which describes which slide shall be next, null if its the succeeding number
    public String nextSlide;
    public String backSlide;

    //Hashmap for the data describing the slide
    public HashMap<String,String> parameter=new HashMap<>();


    /**
     * a slide factory to create the right type of slide
     * @author Tim
     * @param slideDescription
     * @return a slide of the fitting type
     */
    public static Slide createSlide(String slideDescription){
        String type= descriptionToHashMap(slideDescription).get("type");
        //creates a different Type of Slide for each slideDescription
        switch (type){
            case "text": return new TextSlide();
            case "button": return new ButtonSlide();
            case "question": return new QuestionSlide();
            case "quiz4": return new Quiz4Slide();
            case "certificate": return new CertificateSlide();
            case "webimage": return new WebImageSlide();
            case "scalee": return new ScaleEvaluationSlide();
            case "texte": return new TextEvaluationSlide();
            default: return new TextEvaluationSlide();
        }

    }

    @Override
    /**
     * Enables to pass arguments to the fragment
     * @author Tim
     */
    public void setArguments(Bundle arg){
        //fetches the different attributes
        String  slideDescription=arg.getString("parameters");
        parameter= descriptionToHashMap(slideDescription);
        //fetches which silde is the succeeding and previous Slide
        nextSlide=parameter.get("next");
        backSlide =parameter.get("back");
    }

    /**callClassMethod to transform the slideDescription into a hashmap
     *@author Tim
     * @param slideDescription
     * @return a hashMap with fitting keys and values to describe the slide
     */
    public static HashMap<String,String> descriptionToHashMap(String slideDescription){
        HashMap<String,String> result=new HashMap<>();
        //splits the description in name~text parts
        String[] s= slideDescription.split("'");
        //splits the name~text part again and fills them into a hashmap:  key-name, value-text
        for(int i=0;i<s.length&&!s[i].isEmpty();i++){
            String key=s[i].substring(0, s[i].indexOf("~"));
            String value =s[i].substring(s[i].indexOf("~")+1,s[i].length());
            result.put(key,value);
        }
        return result;
    }

    public String next(){
        return nextSlide;
    }
    public String back(){
        return backSlide;
    }

    public boolean isLectionSolved(){
     return true;
    }
    abstract void fillLayout();


}