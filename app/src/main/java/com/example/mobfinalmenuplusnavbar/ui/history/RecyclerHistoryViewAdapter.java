package com.example.mobfinalmenuplusnavbar.ui.history;

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

public class RecyclerHistoryViewAdapter extends RecyclerView.Adapter<RecyclerHistoryViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerHistoryAdapter";
    private ArrayList<Integer> cardRecyclerIds = new ArrayList<>();
    private ArrayList<Integer> cardRecyclerLogos = new ArrayList<>();
    private ArrayList<String> cardRecyclerBankNames = new ArrayList<>();
    private ArrayList<String> cardRecyclerCashes = new ArrayList<>();
    private ArrayList<String> cardRecyclerDates = new ArrayList<>();
    private Context cardRecyclerContext;

    public RecyclerHistoryViewAdapter(
            Context cardRecyclerContext,
            ArrayList<Integer> cardRecyclerIds,
            ArrayList<Integer> cardRecyclerLogos,
            ArrayList<String> cardRecyclerBankNames,
            ArrayList<String> cardRecyclerCashes,
            ArrayList<String> cardRecyclerDates
    ) {
        this.cardRecyclerIds = cardRecyclerIds;
        this.cardRecyclerContext = cardRecyclerContext;
        this.cardRecyclerLogos = cardRecyclerLogos;
        this.cardRecyclerBankNames = cardRecyclerBankNames;
        this.cardRecyclerCashes = cardRecyclerCashes;
        this.cardRecyclerDates = cardRecyclerDates;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_history_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.cardRecyclerLogo.setImageResource(cardRecyclerLogos.get(position));
        holder.cardRecyclerBankName.setText(cardRecyclerBankNames.get(position));
        holder.cardRecyclerDate.setText(cardRecyclerDates.get(position));
        holder.cardRecyclerCash.setText(cardRecyclerCashes.get(position));

        if (cardRecyclerCashes.get(position).contains("+")) {
            holder.cardRecyclerCash.setTextColor(0x9944DE46);
        } else if (cardRecyclerCashes.get(position).contains("-")) {
            holder.cardRecyclerCash.setTextColor(0xBBFF0000);
        }

        holder.cardRecyclerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "LAST RECORDS: clicked on: " + String.valueOf(cardRecyclerIds.get(position)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardRecyclerCashes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout cardRecyclerLayout;
        ImageView cardRecyclerLogo;
        TextView cardRecyclerBankName;
        TextView cardRecyclerCash;
        TextView cardRecyclerDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardRecyclerLayout = itemView.findViewById(R.id.cardHistoryRecyclerLayout);
            cardRecyclerLogo = itemView.findViewById(R.id.cardHistoryRecyclerLogo);
            cardRecyclerBankName = itemView.findViewById(R.id.cardHistoryRecyclerBankName);
            cardRecyclerCash = itemView.findViewById(R.id.cardHistoryRecyclerCash);
            cardRecyclerDate = itemView.findViewById(R.id.cardHistoryRecyclerDate);
        }
    }

}
