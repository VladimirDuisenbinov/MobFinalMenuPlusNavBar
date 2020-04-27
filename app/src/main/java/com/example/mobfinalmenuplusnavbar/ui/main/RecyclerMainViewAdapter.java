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

public class RecyclerMainViewAdapter extends RecyclerView.Adapter<RecyclerMainViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<Integer> cardRecyclerLogos = new ArrayList<>();
    private ArrayList<String> cardRecyclerBankNames = new ArrayList<>();
    private ArrayList<String> cardRecyclerCashes = new ArrayList<>();
    private Context cardRecyclerContext;

    public RecyclerMainViewAdapter(
            Context cardRecyclerContext,
            ArrayList<Integer> cardRecyclerLogos,
            ArrayList<String> cardRecyclerBankNames,
            ArrayList<String> cardRecyclerCashes
    ) {
        this.cardRecyclerContext = cardRecyclerContext;
        this.cardRecyclerLogos = cardRecyclerLogos;
        this.cardRecyclerBankNames = cardRecyclerBankNames;
        this.cardRecyclerCashes = cardRecyclerCashes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_main_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.cardRecyclerLogo.setImageResource(cardRecyclerLogos.get(position));
        holder.cardRecyclerBankName.setText(cardRecyclerBankNames.get(position));
        holder.cardRecyclerCash.setText(cardRecyclerCashes.get(position));

        holder.cardRecyclerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + cardRecyclerBankNames.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return cardRecyclerBankNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout cardRecyclerLayout;
        ImageView cardRecyclerLogo;
        TextView cardRecyclerBankName;
        TextView cardRecyclerCash;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardRecyclerLayout = itemView.findViewById(R.id.cardRecyclerLayout);
            cardRecyclerLogo = itemView.findViewById(R.id.cardRecyclerLogo);
            cardRecyclerBankName = itemView.findViewById(R.id.cardRecyclerBankName);
            cardRecyclerCash = itemView.findViewById(R.id.cardRecyclerCash);
        }
    }

}
