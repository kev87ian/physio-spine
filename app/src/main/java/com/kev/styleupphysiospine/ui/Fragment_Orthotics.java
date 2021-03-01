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
import com.kev.styleupphysiospine.data.OrthoticsAdapter;
import com.kev.styleupphysiospine.data.OrthoticsModel;

import org.json.JSONArray;

import java.util.ArrayList;


public class Fragment_Orthotics extends Fragment {
    Context context;
    private static final String fraagment_URL = "https://styleupapi.herokuapp.com/orthotics";
    private RecyclerView orthoticsRv;
    OrthoticsModel orthoticsModel;
    ArrayList<OrthoticsModel> orthoticsModelArrayList;
    OrthoticsAdapter orthoticsAdapter;


    public Fragment_Orthotics() {
        // Required empty public constructor
    }
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__orthotics, container, false);
        orthoticsRv = view.findViewById(R.id.orthotics_recycler_view);
        loadOrthotics();
        return view;
    }

    private void loadOrthotics() {
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
        orthoticsModelArrayList = new ArrayList<>();
        orthoticsModelArrayList.clear();

        try {
            //converting json object
            JSONArray array = new JSONArray(response);

//            JSONArray jsonArray = new JSONArray(response);
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();

            //actual fetching data

            for (int i = 0; i<array.length(); i++){
                OrthoticsModel orthoticsModel = gson.fromJson(array.getJSONObject(i).toString(), OrthoticsModel.class);
                orthoticsModelArrayList.add(orthoticsModel);
            }

            //setup adapter
//            adapterGG = new AdapterGG(context, goalGoalArrayList);
//            goalGoalRv.setAdapter(adapterGG);
//            progressBar.setVisibility(View.GONE);
//
            orthoticsAdapter = new OrthoticsAdapter(context, orthoticsModelArrayList);
            orthoticsRv.setAdapter(orthoticsAdapter);

        }
        catch (Exception e){
            Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}