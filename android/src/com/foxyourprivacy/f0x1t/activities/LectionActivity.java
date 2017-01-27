package com.foxyourprivacy.f0x1t.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.foxyourprivacy.f0x1t.DBHandler;
import com.foxyourprivacy.f0x1t.LectionObject;
import com.foxyourprivacy.f0x1t.R;
import com.foxyourprivacy.f0x1t.ValueKeeper;
import com.foxyourprivacy.f0x1t.lessonmethods.Method;
import com.foxyourprivacy.f0x1t.lessonmethods.MethodFactory;
import com.foxyourprivacy.f0x1t.slides.EvaluationSlide;
import com.foxyourprivacy.f0x1t.slides.QuizSlide;
import com.foxyourprivacy.f0x1t.slides.Slide;

import java.util.HashMap;

/**
 * manages the visualisation of slides
 */
public class LectionActivity extends FoxITActivity {
    public LectionObject lection;
    String lectionDescription;
    int slideNumber = 0;
    Toolbar toolbar;
    boolean didEvaluationStart = false;
    boolean isEvaluation = false;
    String className = "nothing";

    HashMap<String, String> evaluationResults = new HashMap<>();
    Handler handler;

    /**
     * @author Tim
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lection_activity);
        lectionDescription = getIntent().getStringExtra("lection");
        className = getIntent().getStringExtra("classname");
        handler = new Handler();

        // sets our toolbar as the actionbar
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Toolbar");
        }

        //setting the lectionObject, it's used for storing the slides
        long l = 0;
        lection = new LectionObject(getIntent().getStringExtra("name"), lectionDescription, getIntent().getIntExtra("type", 0), getIntent().getIntExtra("delay", 0), getIntent().getLongExtra("freetime", l), getIntent().getIntExtra("status", 1), getIntent().getIntExtra("acorn", 3));


        if (lection.getProcessingStatus() < 2 && lection.getProcessingStatus() != -99) {
            DBHandler db = new DBHandler(this, null, null, 1);
            db.changeLectionToRead(lection.getLectionName());
            db.close();

        }
        //add the first slide to the activities' context
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        Slide firstSlide = lection.slideHashMap.get("0");
        //if the first slide is a QuizSlide show the question mark
        if (firstSlide instanceof QuizSlide) {
            setImage("check");
        }
        transaction.add(R.id.lection_frame, firstSlide, "slide");
        transaction.commit();


        if (lection.getType() == -99) {
            isEvaluation = true;
            RelativeLayout outerFrame = (RelativeLayout) findViewById(R.id.count_frame);
            outerFrame.setBackgroundColor(Color.LTGRAY);
        }

        //if the first slide does not have a previous slide, hide the backArrow
        final ImageButton backButton = (ImageButton) findViewById(R.id.back_button);
        if (lection.slideHashMap.get(Integer.toString(slideNumber)).back() == null) {
            backButton.setVisibility(View.GONE);
        }

        // define the proper button behavior for switching slides, and change the button image accordingly
        ImageButton nextButton = (ImageButton) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                int slideToJumpTo; //aka the next slideNumber
                Slide currentSlide = lection.slideHashMap.get(Integer.toString(slideNumber));

                //if the next slide is a QuizSlide show the question mark
                if (currentSlide.next() != null && currentSlide.next().equals("quiz")) {
                    ((QuizSlide) currentSlide).evaluation();
                    //if there is no next slide after the next show the cross
                    if ((currentSlide.next() == null) && (lection.lectionInfoHashMap.get(Integer.toString(slideNumber + 1)) == null)) {
                        setImage("kreuz");
                        //if there is a next slide after the next slide set the arrow
                    } else {
                        setImage("pfeil_rechts");
                    }
                } else {
                    if (!(currentSlide instanceof EvaluationSlide) || ((EvaluationSlide) currentSlide).evaluation()) {

                        //if the current slide defines a next slides that's the slideNumberToJumpTo
                        if (currentSlide.next() != null) {
                            slideToJumpTo = Integer.parseInt(currentSlide.next());
                        } else {
                            //otherwise its the slide with the next higher number
                            slideToJumpTo = slideNumber + 1;
                        }
                        //if the slide with the retrieved number exists jump to it
                        if (lection.lectionInfoHashMap.get(Integer.toString(slideToJumpTo)) != null) {
                            jumpToSlide(Integer.toString(slideToJumpTo));
                        } else {
                            //otherwise close the Activity
                            goBackToLectionList(slideNumber);
                        }
                    }
                }
                ft.commit();
            }
        });
        // define the proper button behavior for going back one slide, and change the button image accordingly
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Slide currentSlide = lection.slideHashMap.get(Integer.toString(slideNumber));
                //if the slide defines a backSlide jump to it
                if (currentSlide.back() != null) {
                    jumpToSlide(currentSlide.back());
                } else {
                    //otherwise if its not prevented to jump back jump to the last slide in the backStack of slides
                    if (!lection.getBackSlide().equals("noBack")) {
                        ft.show(lection.slideHashMap.get(lection.getBackSlide()));
                        //if the last slide has a nextSlide show the arrow otherwise show the cross symbol
                        if (!((lection.slideHashMap.get(lection.getBackSlide()).next() == null) && (lection.slideHashMap.get(Integer.toString(Integer.parseInt(lection.getBackSlide()) + 1)) == null))) {
                            setImage("pfeil_rechts");
                        } else {
                            setImage("kreuz");
                        }
                        ft.hide(currentSlide);
                        //set the right slideNumber
                        slideNumber = Integer.parseInt(lection.getBackSlide());
                        //remove the last slide from the slideBackStack
                        lection.slideBackStack.remove(lection.slideBackStack.size() - 1);
                    }
                    //if the slide currently open has no backSlide, hide the backButton
                    if (lection.getBackSlide().equals("noBack") && lection.slideHashMap.get(Integer.toString(slideNumber)).back() == null) {
                        backButton.setVisibility(View.GONE);
                    }
                    ft.commit();
                }
            }
        });

        //defines the exact same behavior as the nextButton for the whole slide except that quizSlides are not evaluated
        RelativeLayout lectionFrame = (RelativeLayout) findViewById(R.id.lection_frame);
        lectionFrame.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if ((!lection.getLectionName().contains(":")) || !(lection.getLectionName().contains("timeEval") || lection.getLectionName().contains("appEval"))) {
                    String nextSlideNumber = lection.slideHashMap.get(Integer.toString(slideNumber)).next();

                    Slide slide = lection.slideHashMap.get(Integer.toString(slideNumber));
                    if (!(didEvaluationStart) && (!(slide instanceof QuizSlide) || ((QuizSlide) slide).getEvaluated())) {
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        int slideToJumpTo; //aka the next SlideNumber
                        //if the next slide is a quizSlide show the question mark
                        if (nextSlideNumber != null && nextSlideNumber.equals("quiz")) {
                            ((QuizSlide) lection.slideHashMap.get(Integer.toString(slideNumber))).evaluation();
                            //if there is no next slide after the next show the cross
                            if (lection.lectionInfoHashMap.get(Integer.toString(slideNumber + 1)) == null) {
                                setImage("kreuz");
                                //if there is a next slide after the next slide set the arrow
                            } else {
                                setImage("pfeil_rechts");
                            }
                        } else {
                            //if the current slide defines a next slide that's the slideNumberToJumpTo
                            if (nextSlideNumber != null) {
                                slideToJumpTo = Integer.parseInt(nextSlideNumber);
                            } else {
                                //otherwise it's the slide with the next higher number
                                slideToJumpTo = slideNumber + 1;
                            }
                            //if the slide with the retrieved number exists, jump to it
                            if (lection.lectionInfoHashMap.get(Integer.toString(slideToJumpTo)) != null) {
                                jumpToSlide(Integer.toString(slideToJumpTo));
                            } else {
                                //otherwise close the Activity
                                goBackToLectionList(slideNumber);
                            }
                        }
                        ft.commit();
                    }
                }
            }
        });
    }

    /**
     * set the right icon for the right button
     *
     * @param image kreuz, pfeil_rechts, question
     * @author Tim
     */
    public void setImage(String image) {
        ImageButton nextButton = (ImageButton) findViewById(R.id.next_button);
        Context context = getApplicationContext();
        switch (image) {
            case ("kreuz"):
                nextButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_close));
                break;
            case ("pfeil_rechts"):
                nextButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_arrow_forward_red_100_48dp));
                break;
            case ("check"):
                nextButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_check));
                break;
            default:
                nextButton.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.kreuz));
        }
    }

    /**
     * jump to a specific slide
     *
     * @param next the slide Number of the slide to switch to
     * @author Tim
     */
    public void jumpToSlide(String next) {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ImageButton backButton = (ImageButton) findViewById(R.id.back_button);
        //if the nextSlide to be called exists and is not already created, create it out of its description
        if (lection.lectionInfoHashMap.containsKey(next) && !lection.slideHashMap.containsKey(next)) {
            String slideDescription = lection.lectionInfoHashMap.get(next);
            Slide newSlide = Slide.createSlide(slideDescription);
            Bundle slideInfos = new Bundle();
            slideInfos.putString("parameters", slideDescription);
            newSlide.setArguments(slideInfos);
            //add the new slide to the lectionObject
            lection.slideHashMap.put(next, newSlide);

            //and display the newly created slide
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();

            transaction.add(R.id.lection_frame, newSlide, "slide");
            transaction.commit();
        } else {
            ft.show(lection.slideHashMap.get(next));

        }
        if (lection.slideHashMap.get(next) != null) {
            Slide slide = lection.slideHashMap.get(next);
            //hide the current Slide
            ft.hide(lection.slideHashMap.get((Integer.toString(slideNumber))));

            //add the current Slide to the slideBackStack
            lection.slideBackStack.add(Integer.toString(slideNumber));
            slideNumber = Integer.parseInt(next);
            //if the new Slide has no last slide hide the backButton
            if (slide.back() == null) {
                backButton.setVisibility(View.GONE);
            }
            //if it has a last slide show the backButton
            if (!lection.getBackSlide().equals("noBack") || slide.back() != null) {
                backButton.setVisibility(View.VISIBLE);
            }
            //if the next slide is a quizSlide set the questionMark
            if (slide instanceof QuizSlide && !((QuizSlide) slide).getEvaluated()) {
                setImage("check");
            } else {
                //if the nextSlide has no slide thereafter show the cross
                if ((slide.next() == null) && (lection.lectionInfoHashMap.get(Integer.toString(Integer.parseInt(next) + 1)) == null)) {
                    setImage("kreuz");
                } else {
                    setImage("pfeil_rechts");
                }
            }
        }
        ft.commit();
    }

    /**
     * wrap up the lection, count points and return to lection overview
     *
     * @param currentSlide
     */
    private void goBackToLectionList(int currentSlide) {
        didEvaluationStart = true;
        ImageView button = (ImageView) findViewById(R.id.next_button);
        button.setVisibility(View.GONE);
        button = (ImageView) findViewById(R.id.back_button);
        button.setVisibility(View.GONE);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.lection_frame);
        layout.setEnabled(true);

        ValueKeeper v = ValueKeeper.getInstance();
        Boolean isEval = false;
        if (lection.getLectionName().contains(":")) {
            isEval = true;
            String switc = lection.getLectionName().substring(0, lection.getLectionName().indexOf(":"));
            Log.d("MyApp", switc);
            switch (switc) {
                case "timeEval":


                    v.setIsEvaluationOutstandingFalse();
                    v.setEvaluationResults(evaluationResults);
                    v.increaseCurrentEvaluation();
                    break;
                case "appEval":

                    v.removeFirstFromAppList();
                    v.setEvaluationResults(evaluationResults);
                    v.increaseCurrentEvaluation();
                    break;
                default:
                    isEval = false;
            }


        }
        if (lection.slideHashMap.get(Integer.toString(currentSlide)).isLectionSolved() && (lection.getProcessingStatus() != 3 && !isEval)) {
            DBHandler db = new DBHandler(this, null, null, 1);

            if (lection.getProcessingStatus() != -99) {
                db.changeLectionToSolved(lection.getLectionName());
            }

            MethodFactory factory = new MethodFactory(this);
            if (className != null && !className.equals("nothing") && !className.equals("Daily Lessons")) {
                Method method = factory.createMethod("changeTokenCount");
                method.callClassMethod("1");
            }

            //raise the acornCount on success
            Method method2 = factory.createMethod("changeAcornCount");
            method2.callClassMethod(Integer.toString(lection.getReward()));
            db.close();


            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    onBackPressed();
                }
            }, 4250);
        } else if (lection.getProcessingStatus() != 3) {

            long nextFreeTime = (System.currentTimeMillis() % Long.MAX_VALUE) + lection.getDelaytime();
            Log.d("getbacktolist", "nextfreetime: " + nextFreeTime);
            Log.d("getbacktolist", "delaytime: " + lection.getDelaytime());
            DBHandler db = new DBHandler(this, null, null, 1);
            db.setLectionNextFreeTime(lection.getLectionName(), nextFreeTime);
            db.close();
            onBackPressed();
        } else onBackPressed();
    }


    public void addEvaluationResult(String question, String answer) {

        String prefix = lection.getLectionName().substring(lection.getLectionName().indexOf(":") + 1, lection.getLectionName().length());
        evaluationResults.put(prefix + ":" + question, answer);

    }

    @Override
    public void onPause() {
        super.onPause();
        DBHandler db = new DBHandler(this, null, null, 3);
        db.clearDAppsFromVK();
        db.close();
        handler.removeCallbacksAndMessages(null);
    }
}