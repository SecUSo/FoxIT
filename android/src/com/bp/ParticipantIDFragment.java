package com.bp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Tim on 25.06.2016.
 */
public class ParticipantIDFragment extends Fragment {
    View view; //the fragments view, useful for usages outside of onCreateView

    Fragment thisFragment; //this fragment useful to be called in another callClassMethod

    /**
     * @author Tim
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    /**
     * defines the layout of this fragment and provides the yes and no button behavior
     * @author Tim
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        view = inflater.inflate(R.layout.fragment_participant_id, container, false);
        thisFragment = this;

        final EditText participantID =(EditText) view.findViewById(R.id.participant_id_input_text);

        //remove this fragment if the no button is pressed
        ImageButton continueButton = (ImageButton) view.findViewById(R.id.continue_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(participantID.getText().toString().matches("[A-Za-z][A-Za-z]\\d\\d[A-Za-z][A-Za-z]")) {
                    ValueKeeper vc=ValueKeeper.getInstance();
                    vc.setVpnCode(participantID.getText().toString());
                    getActivity().getFragmentManager().beginTransaction().remove(thisFragment).commit();

                } else{
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Dies ist kein valider VPN-Code.", Toast.LENGTH_LONG).show();

                }   }

        });

        return view;
    }




}
