//package com.foxyourprivacy.f0x1t.slides;
//
///**
// * A slide with 2 buttons that have actions
// * Created by Tim on 25.06.2016.
// */
//public class QuestionSlide extends Slide {
//    View view;
//    //callClassMethod for the left button
//    Method methodLeft;
//    //callClassMethod for the right button
//    Method methodRight;
//
//
//    /**
//     * fills the layout by calling fillLayout
//     *
//     * @author Tim
//    */
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
//        view = inflater.inflate(R.layout.layout_slide_question, container, false);
//        //generic callClassMethod of all slides to fill the slideDescription into the layout
//        fillLayout();
//
//        return view;
//    }
//
//
//
//
//    /** fills the slide's layout
//     * @author Tim
//    */
//    @Override
//    public void fillLayout() {
//        //fetches the methods for the buttons
//        MethodFactory methodFactory = new MethodFactory(getActivity());
//        methodLeft = methodFactory.createMethod(parameter.get("method"));
//        methodRight = methodFactory.createMethod(parameter.get("method2"));
//
//        //sets the text displayed on a slide
//        TextView text = (TextView) view.findViewById(R.id.question_text);
//        text.setText(parameter.get("text"));
//
//        //sets the text displayed on the left button
//        final Button buttonLeft = (Button) view.findViewById((R.id.button_left));
//        buttonLeft.setText(parameter.get("buttonText"));
//        //sets the text displayed on the left button
//        final Button buttonRight = (Button) view.findViewById((R.id.button_right));
//        buttonRight.setText(parameter.get("buttonText2"));
//        //sets the callClassMethod for the left button
//        buttonLeft.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                buttonLeft.setVisibility(View.INVISIBLE);
//                buttonRight.setVisibility(View.INVISIBLE);
//                methodLeft.callClassMethod(parameter.get("methodParameter"));
//            }
//        });
//
//
//        //sets the callClassMethod for the right button
//        buttonRight.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                buttonLeft.setVisibility(View.INVISIBLE);
//                buttonRight.setVisibility(View.INVISIBLE);
//                methodRight.callClassMethod(parameter.get("methodParameter2"));
//            }
//        });
//
//    }
//
//}
