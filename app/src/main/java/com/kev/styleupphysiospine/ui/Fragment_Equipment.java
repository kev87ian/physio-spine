package com.kev.styleupphysiospine.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kev.styleupphysiospine.R;
import com.kev.styleupphysiospine.data.EquipmentAdapter;
import com.kev.styleupphysiospine.data.EquipmentModel;

import org.json.JSONArray;

import java.util.ArrayList;

public class Fragment_Equipment extends Fragment {
    Context context;
    private static final String fraagment_URL = "https://styleupapi.herokuapp.com/equipment";
    private EditText searchEt;
    private RecyclerView equipmentRv;
    ArrayList<EquipmentModel> equipmentModelArrayList;
    EquipmentAdapter equipmentAdapter;


    public Fragment_Equipment() {
        // Required empty public constructor
    }

    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__equipment, container, false);
        equipmentRv = view.findViewById(R.id.equipment_recycler_view);
        loadEquipment();
        return view;

    }

    private void loadEquipment() {

          StringRequest stringRequest = new StringRequest(Request.Method.GET, fraagment_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                handleResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //on failed response
              //  progressBar.setVisibility(View.GONE);
                Toast.makeText(context, ""+ error, Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    private void handleResponse(String response) {

        equipmentModelArrayList = new ArrayList<>();
        equipmentModelArrayList.clear();

        try {
            //converting json object
            JSONArray array = new JSONArray(response);

//            JSONArray jsonArray = new JSONArray(response);
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();

            //actual fetching data

            for (int i = 0; i<array.length(); i++){
                EquipmentModel equipmentModel = gson.fromJson(array.getJSONObject(i).toString(), EquipmentModel.class);
                equipmentModelArrayList.add(equipmentModel);
            }

            //setup adapter
//            adapterGG = new AdapterGG(context, goalGoalArrayList);
//            goalGoalRv.setAdapter(adapterGG);
//            progressBar.setVisibility(View.GONE);
//
            equipmentAdapter = new EquipmentAdapter(context, equipmentModelArrayList);
            equipmentRv.setAdapter(equipmentAdapter);

        }
        catch (Exception e){
            Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}