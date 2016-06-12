package com.xiaoqi.enca_android_prototype;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gc.materialdesign.views.ButtonRectangle;

public class SampleFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private FloatingActionButton fab;
    private int position;

    public static SampleFragment newInstance(int position) {
        SampleFragment f = new SampleFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        position = getArguments().getInt(ARG_POSITION);
        View rootView = inflater.inflate(R.layout.page_kitchen, container, false);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fabButton);
        fab.setDrawableIcon(getResources().getDrawable(R.drawable.plus));
        fab.setBackgroundColor(getResources().getColor(R.color.material_deep_teal_500));
//        int layoutId =0;
        switch (position) {
            case 0:
                fab.setBackgroundColor(getResources().getColor(R.color.material_deep_teal_500));
//                layoutId = R.layout.page_kitchen;
                ButtonRectangle btn_kitchen_cooker = (ButtonRectangle) rootView.findViewById(R.id.btn_kitchen_cooker);
                ButtonRectangle btn_kitchen_dish = (ButtonRectangle) rootView.findViewById(R.id.btn_kitchen_dish);
//                btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(container.getContext(),"Cooker",Toast.LENGTH_SHORT).show();
//                    }
//                });
                btn_kitchen_cooker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.android.search");
                        intent.putExtra("Kitchen", "Cooker & Oven");
                        startActivity(intent);
                    }
                });
                btn_kitchen_dish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.android.search");
                        intent.putExtra("Kitchen", "Dish, Bowl & Cup");
                        startActivity(intent);
                    }
                });
                break;
            case 1:
                rootView = inflater.inflate(R.layout.page_bathroom, container, false);
                fab = (FloatingActionButton) rootView.findViewById(R.id.fabButton);
                fab.setDrawableIcon(getResources().getDrawable(R.drawable.plus));
                fab.setBackgroundColor(getResources().getColor(R.color.material_deep_teal_500));
//                layoutId = R.layout.page_bathroom;
                ButtonRectangle btn_bathroom_bath = (ButtonRectangle) rootView.findViewById(R.id.btn_bathroom_Bath);
                btn_bathroom_bath.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.android.search");
                        intent.putExtra("Bathroom", "Bath");
                        startActivity(intent);
                    }
                });
                break;
            case 2:
                rootView = inflater.inflate(R.layout.page_bedroom, container, false);
                fab = (FloatingActionButton) rootView.findViewById(R.id.fabButton);
                fab.setDrawableIcon(getResources().getDrawable(R.drawable.plus));
                fab.setBackgroundColor(getResources().getColor(R.color.material_deep_teal_500));
//                layoutId = R.layout.page_bedroom;
                break;
            case 3:
                rootView = inflater.inflate(R.layout.page_livingroom, container, false);
                fab = (FloatingActionButton) rootView.findViewById(R.id.fabButton);
                fab.setDrawableIcon(getResources().getDrawable(R.drawable.plus));
                fab.setBackgroundColor(getResources().getColor(R.color.material_deep_teal_500));
//                layoutId = R.layout.page_livingroom;
                break;
            case 4:
                rootView = inflater.inflate(R.layout.page_office, container, false);
                fab = (FloatingActionButton) rootView.findViewById(R.id.fabButton);
                fab.setDrawableIcon(getResources().getDrawable(R.drawable.plus));
                fab.setBackgroundColor(getResources().getColor(R.color.material_deep_teal_500));
//                layoutId = R.layout.page_office;
                break;
            case 5:
                rootView = inflater.inflate(R.layout.page_general, container, false);
                fab = (FloatingActionButton) rootView.findViewById(R.id.fabButton);
                fab.setDrawableIcon(getResources().getDrawable(R.drawable.plus));
                fab.setBackgroundColor(getResources().getColor(R.color.material_deep_teal_500));
//                layoutId = R.layout.page_general;
                break;
        }
//        rootView = inflater.inflate(layoutId, container, false);
        return rootView;
    }

}