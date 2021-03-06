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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobfinalmenuplusnavbar.R;
import com.example.mobfinalmenuplusnavbar.add_data_fragments.AddAccountFragment;
import com.example.mobfinalmenuplusnavbar.add_data_fragments.AddRecordFragment;
import com.example.mobfinalmenuplusnavbar.db.Account;
import com.example.mobfinalmenuplusnavbar.db.Record;

import java.util.ArrayList;

public class RecyclerMainViewAdapter extends RecyclerView.Adapter<RecyclerMainViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<Long> cardRecyclerIds = new ArrayList<>();
    private ArrayList<Integer> cardRecyclerLogos = new ArrayList<>();
    private ArrayList<String> cardRecyclerBankNames = new ArrayList<>();
    private ArrayList<String> cardRecyclerCashes = new ArrayList<>();
    private Context cardRecyclerContext;

    public RecyclerMainViewAdapter(
            Context cardRecyclerContext,
            ArrayList<Long> cardRecyclerIds,
            ArrayList<Integer> cardRecyclerLogos,
            ArrayList<String> cardRecyclerBankNames,
            ArrayList<String> cardRecyclerCashes
    ) {
        this.cardRecyclerIds = cardRecyclerIds;
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
                Log.d(TAG, "MAIN ACCOUNTS: clicked on: " + String.valueOf(cardRecyclerIds.get(position)));

                Account account = Account.get(cardRecyclerIds.get(position));

                AddAccountFragment addAccount = new AddAccountFragment(account);
                FragmentTransaction transaction = ((AppCompatActivity)cardRecyclerContext).getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.main_container, addAccount, "AddAccFragment");
                transaction.addToBackStack("MainAcc");

                transaction.commit();
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
