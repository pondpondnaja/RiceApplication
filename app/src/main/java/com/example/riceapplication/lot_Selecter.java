package com.example.riceapplication;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class lot_Selecter extends Fragment {
    private static final String TAG = "lotSeletor";
    private static final String URL = "http://10.0.2.2:8084/kak/read_lot_no.jsp";
    private ArrayList<String> lot_number = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    RecyclerView recyclerView;
    String name,lot_no,user,from_fragment;
    Bundle bundle;
    OnMyFragmentListener mListener;

    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view =  inflater.inflate(R.layout.lot_selecter_fragment,container,false);

        //*** Session Login
        final LoginHelper usrHelper = new LoginHelper(getActivity().getApplicationContext());
        context = view.getContext();
        user    = usrHelper.getUserName();
        
        inidata(view);
        //initRecycleView(view);
        bundle = getArguments();
        if(bundle != null){
            from_fragment = bundle.getString("from_fragment");
            Log.d(TAG, "onCreateView: From fragment : "+from_fragment);
        }

        return view;
    }

    public void setOnMyFragmentListener(OnMyFragmentListener listener) {
        this.mListener = listener;
    }

    public interface OnMyFragmentListener {
        public void onChangeToolbarTitle(String title);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMyFragmentListener) {
            mListener = (OnMyFragmentListener) context;
            mListener.onChangeToolbarTitle("เลือก LOT NO"); // Call this in `onResume()`
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onStart(){
        Log.d(TAG, "onStart: Initial data");
        //inidata();
        super.onStart();
    }

    @Override
    public void onResume(){
        super.onResume();
        mListener.onChangeToolbarTitle("เลือก LOT NO");
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onPause: Clear data");
        lot_number.clear();
        super.onStop();
    }

    public void inidata(final View view){

        Log.d(TAG, "inidata: addData");
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.GET,URL , new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                lot_number.add("สร้างรายการใหม่");
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        name   = item.getString("Product_Type");
                        lot_no = item.getString("LOT_NO");

                        Log.d(TAG, "onResponse: Product  : "+name);
                        Log.d(TAG, "onResponse: LOT_NO   : "+lot_no);

                        lot_number.add(lot_no);

                        Log.d(TAG, "onResponse: Array    : "+lot_number);
                    }
                    initRecycleView(view);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("onError",error.toString());
                Toast.makeText(getActivity(),"เกิดข้อผิดพลาดโปรดลองอีกครั้ง",Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }

    private void initRecycleView(View view) {
        Log.d(TAG, "initRecycleView: Called init");
        recyclerView = view.findViewById(R.id.lot_recycleview);
        layoutManager = new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        lot_adapter lot_adapter = new lot_adapter(context,lot_number,user,from_fragment);
        recyclerView.setAdapter(lot_adapter);
    }
}
