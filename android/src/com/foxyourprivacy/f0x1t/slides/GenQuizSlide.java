package com.foxyourprivacy.f0x1t.slides;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.foxyourprivacy.f0x1t.R;
import com.foxyourprivacy.f0x1t.lessonmethods.MethodFactory;

import java.util.Arrays;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Created by noah on 04.06.17.
 */

public class GenQuizSlide extends Slide {

    protected boolean evaluated = false;
    View view;
    String[] answers;
    Boolean[] correctness;
    int[] ids;
    String rightAnswer;// = getString(R.string.genericQuizCorrect);
    String wrongAnswer;// = getString(R.string.genericQuizIncorrect);
    String question;
    int points = 0;


    @Override
    public void setArguments(Bundle arg) {
        super.setArguments(arg);
        type = "quiz";
        slideInfo = slideInfo.replaceFirst(Pattern.quote("[QUIZ]"), "");
        Log.d("Quiz-content", slideInfo);

        if (slideInfo.contains("response+:")) {
            String correct = slideInfo.substring(slideInfo.indexOf("response+:"));
            correct = correct.substring(0, correct.indexOf(";") + 1);
            rightAnswer = correct.substring(10, correct.indexOf(";"));
            slideInfo = slideInfo.replace(correct, "");
        } else {
            rightAnswer = "Richtig.";//getString(R.string.genericQuizCorrect);

        }
        if (slideInfo.contains("response-:")) {
            String wrong = slideInfo.substring(slideInfo.indexOf("response-:"));
            wrong = wrong.substring(0, wrong.indexOf(";") + 1);
            wrongAnswer = wrong.substring(10, wrong.indexOf(";"));
            slideInfo = slideInfo.replace(wrong, "");
        } else {
            wrongAnswer = "Falsch.";//getString(R.string.genericQuizIncorrect);

        }


        String[] slideparts = slideInfo.split(";");
        question = slideparts[0];
        correctness = new Boolean[slideparts.length - 1];
        answers = new String[slideparts.length - 1];
        String[] onlyanswers = shuffleAnswers(Arrays.copyOfRange(slideparts, 1, slideparts.length));
        for (String s : onlyanswers) Log.d("shuffledonlyanswers", s);

        for (int i = 0; i < onlyanswers.length; i++) {
            correctness[i] = (onlyanswers[i].substring(0, 1)).equals("1");
            answers[i] = onlyanswers[i].substring(1);
            Log.d(onlyanswers[i], correctness[i] + "  " + answers[i]);
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_slide_quiz, container, false);
        fillLayout();
        return view;
    }

    @Override
    public void fillLayout() {
        TextView questionText = (TextView) view.findViewById(R.id.quiz_text);
        questionText.setText(question);
        GridLayout boxcontainer = (GridLayout) view.findViewById(R.id.quiz_boxes);

        ids = new int[answers.length];
        int k = 0;
        for (String str : answers) {
            CheckBox box = new CheckBox(getContext());
            box.setText(str);
            box.setId(k + 9999);
            ids[k] = box.getId();
            k++;
            boxcontainer.addView(box);
        }
    }

    private String[] shuffleAnswers(String[] answers) {
        int index;
        String[] randAnswers = new String[answers.length];
        Random random = new Random();
        for (int i = 0; i < answers.length; i++) {
            printArray(randAnswers);
            index = random.nextInt(answers.length - 1);
            while (randAnswers[index] != null) {
                Log.d(index + "", randAnswers[index]);
                index = random.nextInt(answers.length);
            }
            randAnswers[index] = answers[i];
        }
        return randAnswers;

    }

    private void printArray(String[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) Log.d("array", array[i]);
        }
    }

    public boolean gotEvaluated() {
        return evaluated;
    }

    public boolean evaluation() {
        int i = 0;
        for (int id : ids) {
            CheckBox box = (CheckBox) view.findViewById(id);
            box.setEnabled(false);
            if (box.isChecked() && correctness[i] || !box.isChecked() && !correctness[i]) {
                box.setBackgroundResource(R.color.rightAnswer);
                points++;
            } else {
                box.setBackgroundResource(R.color.wrongAnswer);
            }
            i++;
        }
        evaluated = true;
        if (points >= answers.length) {
            Toast toast = Toast.makeText(getActivity(), rightAnswer, Toast.LENGTH_LONG);
            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
            v.setBackgroundColor(Color.GREEN);
            toast.show();
            MethodFactory factory = new MethodFactory(getActivity());
            factory.createMethod("scoreAdd").callClassMethod(String.valueOf(points));
            return true;
        } else {
            Toast toast = Toast.makeText(getActivity(), wrongAnswer, Toast.LENGTH_LONG);
            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
            v.setBackgroundColor(Color.RED);
            toast.show();
            return false;
        }
    }

}
