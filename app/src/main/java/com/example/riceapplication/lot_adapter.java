package com.example.riceapplication;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class lot_adapter extends RecyclerView.Adapter<lot_adapter.ViewHolder>{
    private static final String TAG = "LOM";

    private Context mcontext;
    private String muser,mfragment;
    private ArrayList<String> mlotnumber = new ArrayList<String>();
    Bundle bundle;
    View view;

    public lot_adapter(Context context, ArrayList<String> lot_number,String user,String from_fg) {
        this.mcontext   = context;
        this.mlotnumber = lot_number;
        this.muser      = user;
        this.mfragment  = from_fg;
    }

    @Override
    public int getItemViewType(int position){
        //Log.d(TAG, "getItemViewType: position : "+position);
        if (position == 0){
            return 2;
        }else{
            return 1;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Log.d(TAG, "onCreateViewHolder:  called : "+viewType);
        if(viewType == 2){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lot_list_last_order,parent,false);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lot_list_layout,parent,false);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(lot_adapter.ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        holder.text.setText(mlotnumber.get(position));
        holder.parentLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d(TAG, "onClick: clicked on "+mlotnumber.get(position));

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Bundle bundle = new Bundle();
                bundle.putString("username",muser);
                bundle.putString("LOT_NO",mlotnumber.get(position));

                switch (position){
                    case 0 :
                        Toast.makeText(mcontext,mlotnumber.get(position),Toast.LENGTH_SHORT).show();
                        Fragment1 newform = new Fragment1();
                        bundle.putBoolean("new_lot",true);
                        newform.setArguments(bundle);
                        activity.getSupportActionBar().setTitle(activity.getResources().getString(R.string.menu1));
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, newform).addToBackStack(null).commit();
                        break;

                    default:
                        Log.d(TAG, "onClick: Fragment : " + mfragment);
                        Toast.makeText(mcontext,"Lot number : "+mlotnumber.get(position),Toast.LENGTH_SHORT).show();
                        bundle.putBoolean("new_lot",false);

                        if(mfragment.equals("1")){
                            Fragment1 fragment1 = new Fragment1();
                            fragment1.setArguments(bundle);
                            activity.getSupportActionBar().setTitle(activity.getResources().getString(R.string.menu1));
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment1).addToBackStack(null).commit();
                        }else if(mfragment.equals("2")) {
                            Fragment2 fragment2 = new Fragment2();
                            fragment2.setArguments(bundle);
                            activity.getSupportActionBar().setTitle(activity.getResources().getString(R.string.menu2));
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment2).addToBackStack(null).commit();
                        }else if(mfragment.equals("3")){
                            Fragment3 fragment3 = new Fragment3();
                            fragment3.setArguments(bundle);
                            activity.getSupportActionBar().setTitle(activity.getResources().getString(R.string.menu3));
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment3).addToBackStack(null).commit();
                        }else if(mfragment.equals("4")){
                            Fragment4 fragment4 = new Fragment4();
                            fragment4.setArguments(bundle);
                            activity.getSupportActionBar().setTitle(activity.getResources().getString(R.string.menu4));
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment4).addToBackStack(null).commit();
                        }
                }
            }
        });

    }

    @Override
    public int getItemCount(){
        return mlotnumber.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView text;
        CardView parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.lot_text_view);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

}
