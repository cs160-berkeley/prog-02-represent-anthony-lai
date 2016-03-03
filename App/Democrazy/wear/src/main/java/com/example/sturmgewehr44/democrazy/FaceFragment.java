package com.example.sturmgewehr44.democrazy;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FaceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FaceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FaceFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";

    // TODO: Rename and change types of parameters
    private String mParam1 = "fail";
    private String mParam2;
    private int mParam3;
    private int mParam4;

    private OnFragmentInteractionListener mListener;

    public FaceFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FaceFragment newInstance(String param1, String param2, int color, int order) {
        FaceFragment fragment = new FaceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putInt(ARG_PARAM3, color);
        args.putInt(ARG_PARAM4, order);
        System.out.println(param1 + Integer.toString(order) + param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getInt(ARG_PARAM3);
            mParam4 = getArguments().getInt(ARG_PARAM4);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        if (view != null) {
            ((TextView) view.findViewById(R.id.sen)).setText(mParam1);
            ((TextView) view.findViewById(R.id.par)).setText(mParam2);
            ImageButton portrait = (ImageButton) view.findViewById(R.id.Face);
            portrait.setBackground((Drawable) new ColorDrawable(mParam3));
            if (mParam4 > 2) {
                ((TextView) view.findViewById(R.id.textView)).setText("Representative");
            }
            switch (mParam4) {
                case 0: portrait.setBackgroundColor(0xFF3c3b6e);
                        ((LinearLayout) view.findViewById(R.id.container)).setVisibility(View.GONE);
                        break;
                case 1: portrait.setImageResource(R.drawable.fdr);
                        ((TextView) view.findViewById(R.id.title)).setVisibility(View.GONE);
                        break;
                case 2: portrait.setImageResource(R.drawable.stalin);
                        ((TextView) view.findViewById(R.id.title)).setVisibility(View.GONE);
                        break;
                case 3: portrait.setImageResource(R.drawable.wendell);
                        ((TextView) view.findViewById(R.id.title)).setVisibility(View.GONE);
                        break;
                case 4: portrait.setImageResource(R.drawable.hitler);
                        ((TextView) view.findViewById(R.id.title)).setVisibility(View.GONE);
                        break;
                case 5: portrait.setImageResource(R.drawable.churchill);
                        ((TextView) view.findViewById(R.id.title)).setVisibility(View.GONE);
                        break;
            }

            portrait.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mParam4 == 0) {
                        return;
                    }
                    Intent sendIntent = new Intent(getActivity().getBaseContext(), WatchToPhoneService.class);
                    sendIntent.putExtra("/CASE", "FACE");
                    sendIntent.putExtra("VALUE", Integer.toString(mParam4));
                    sendIntent.putExtra("sen", mParam1);
                    sendIntent.putExtra("par", mParam2);
                    getActivity().startService(sendIntent);
                }
            });

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_face, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
