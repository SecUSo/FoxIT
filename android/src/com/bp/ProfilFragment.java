package com.bp;

import android.app.ListFragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Tim on 11.06.2016.
 */
public class ProfilFragment extends ListFragment implements AdapterView.OnItemClickListener {

    ProfilListObject[] profilList={new ProfilListObject("Name","Das ist dein name"),new ProfilListObject("Name","Das ist dein name"),new ProfilListObject("Name","Das ist dein name")};
    String[] lengthArray;
    Context context;
    private MyListAdapter_permission adapter;
    int markedProfil=-1;
    View view;
    EditText inputText;
    @Override
    /**fills the fragments layout and provides behavior for the permissionHeadline
     * @author Tim
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        lengthArray =new String[profilList.length];

        context = getActivity().getApplicationContext();
        view = getActivity().getLayoutInflater().inflate(R.layout.fragment_profil, container, false);

        EditText inputField= (EditText) view.findViewById(R.id.input_input);
        inputField.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            profilList[markedProfil].setInput(inputText.getText().toString());
                            adapter.notifyDataSetChanged();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });




        RelativeLayout answerFrame =(RelativeLayout) view.findViewById(R.id.answer_frame);
        answerFrame.setVisibility(View.GONE);



        return view;
    }


    @Override
    /** defines the listView
     * @author Tim
     */
    public void onActivityCreated(Bundle savedInstanceState) {
        //filling the listView with data
        super.onActivityCreated(savedInstanceState);
        //creates the listView
        adapter = new MyListAdapter_permission();   //new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,permissionArray);//new MyListAdapter_permission();
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    /**
     * on Item Click the permissionDescriptionFragment is created
     * @author Tim
     */
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    markedProfil=position;

    RelativeLayout answerFrame =(RelativeLayout) this.view.findViewById(R.id.answer_frame);
        answerFrame.setVisibility(View.VISIBLE);

    TextView inputDescription=(TextView) this.view.findViewById(R.id.text_input_description);
        inputDescription.setText(profilList[position].getInputDescription());
    EditText input=(EditText) this.view.findViewById(R.id.input_input);
        input.setText(profilList[position].getInput());
     adapter.notifyDataSetChanged();
    }



    /**
     * class to define the way the settings are displayed in the listView
     *
     * @author Tim
     */
    private class MyListAdapter_permission extends ArrayAdapter<String> {
        public MyListAdapter_permission() {
            //defining the listView's layout for single entries
            super(context, R.layout.layout_profil, lengthArray);
        }

        /**
         * Here ist defined how the XML-Layout is filed  by the data stored in the array.
         *
         * @param position    position in the array used
         * @param convertView
         * @param parent
         * @return
         * @author Tim
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //convertView has to be filled with layout_app if it's null
            View itemView = convertView;
            if (itemView == null) {
                itemView = getActivity().getLayoutInflater().inflate(R.layout.layout_profil, parent, false);
            }
            TextView inputType=(TextView) itemView.findViewById(R.id.text_input_type);
            inputType.setText(profilList[position].getInputType());

            TextView input=(TextView) itemView.findViewById(R.id.text_input_input);
            input.setText(profilList[position].getInput());

            RelativeLayout wholeEntry=(RelativeLayout) itemView.findViewById(R.id.profil_frame);
            if(position==markedProfil){
             wholeEntry.setBackgroundColor(Color.LTGRAY);
            }else{
                wholeEntry.setBackgroundColor(Color.WHITE);
            }

            return itemView;
        }

}

}






