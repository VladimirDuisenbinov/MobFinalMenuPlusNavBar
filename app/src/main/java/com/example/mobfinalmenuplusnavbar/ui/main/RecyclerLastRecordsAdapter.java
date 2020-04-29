package com.example.mobfinalmenuplusnavbar.ui.main;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobfinalmenuplusnavbar.R;

import java.util.ArrayList;

public class RecyclerLastRecordsAdapter extends RecyclerView.Adapter<RecyclerLastRecordsAdapter.ViewHolder>{

    private static final String TAG = "RecyclerLastRecordsAdapter";
    private ArrayList<Integer> lastRecordsRecyclerIds = new ArrayList<>();
    private ArrayList<Integer> lastRecordsRecyclerLogos = new ArrayList<>();
    private ArrayList<String> lastRecordsRecyclerCategories = new ArrayList<>();
    private ArrayList<String> lastRecordsRecyclerCashes = new ArrayList<>();
    private Context lastRecordsRecyclerContext;

    public RecyclerLastRecordsAdapter(
            Context lastRecordsRecyclerContext,
            ArrayList<Integer> lastRecordsRecyclerIds,
            ArrayList<Integer> lastRecordsRecyclerLogos,
            ArrayList<String> lastRecordsRecyclerCategories,
            ArrayList<String> lastRecordsRecyclerCashes
    ) {
        this.lastRecordsRecyclerIds = lastRecordsRecyclerIds;
        this.lastRecordsRecyclerContext = lastRecordsRecyclerContext;
        this.lastRecordsRecyclerLogos = lastRecordsRecyclerLogos;
        this.lastRecordsRecyclerCategories = lastRecordsRecyclerCategories;
        this.lastRecordsRecyclerCashes = lastRecordsRecyclerCashes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_last_records_card, parent, false);

        return new RecyclerLastRecordsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.lastRecordsRecyclerLogo.setImageResource(lastRecordsRecyclerLogos.get(position));
        holder.lastRecordsRecyclerCategory.setText(lastRecordsRecyclerCategories.get(position));
        holder.lastRecordsRecyclerCash.setText(lastRecordsRecyclerCashes.get(position));

        if (lastRecordsRecyclerCashes.get(position).contains("+")) {
            holder.lastRecordsRecyclerCash.setTextColor(0x9944DE46);
        } else if (lastRecordsRecyclerCashes.get(position).contains("-")) {
            holder.lastRecordsRecyclerCash.setTextColor(0xBBFF0000);
        }

        holder.lastRecordsRecyclerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "LAST RECORDS: clicked on: " + String.valueOf(lastRecordsRecyclerIds.get(position)));
            }
        });
    }

    @Override
    public int getItemCount() { return lastRecordsRecyclerCashes.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout lastRecordsRecyclerLayout;
        ImageView lastRecordsRecyclerLogo;
        TextView lastRecordsRecyclerCategory;
        TextView lastRecordsRecyclerCash;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            lastRecordsRecyclerLayout = itemView.findViewById(R.id.lastRecordsRecyclerLayout);
            lastRecordsRecyclerLogo = itemView.findViewById(R.id.lastRecordsRecyclerLogo);
            lastRecordsRecyclerCategory = itemView.findViewById(R.id.lastRecordsRecyclerCategory);
            lastRecordsRecyclerCash = itemView.findViewById(R.id.lastRecordsRecyclerCash);
        }
    }
}
