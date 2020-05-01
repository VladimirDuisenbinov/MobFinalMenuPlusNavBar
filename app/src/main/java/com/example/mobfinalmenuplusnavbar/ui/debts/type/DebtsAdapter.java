package com.example.mobfinalmenuplusnavbar.ui.debts.type;

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
import com.example.mobfinalmenuplusnavbar.db.Debt;
import com.example.mobfinalmenuplusnavbar.ui.main.RecyclerLastRecordsAdapter;

import java.util.List;

import static android.content.ContentValues.TAG;

public class DebtsAdapter extends RecyclerView.Adapter<DebtsAdapter.MyViewHolder> {
    private List<Debt> debtList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout activeDebtsLayout;
        TextView activeDebtsName;
        TextView activeDebtsAmount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            activeDebtsLayout = itemView.findViewById(R.id.activeDebtsRecV);
            activeDebtsName = itemView.findViewById(R.id.actDebtsName);
            activeDebtsAmount = itemView.findViewById(R.id.actDebtsAmount);
        }
    }

    public DebtsAdapter( List<Debt> debtList) {
        this.debtList = debtList;
    }

    @NonNull
    @Override
    public DebtsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_active_debts_recv, parent, false);

        return new DebtsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DebtsAdapter.MyViewHolder holder, final int position) {


        holder.activeDebtsName.setText(debtList.get(position).getName());
        holder.activeDebtsAmount.setText(debtList.get(position).getAmount().toString());
         if (debtList.get(position).getAmount().toString().contains("-")) {
             holder.activeDebtsAmount.setTextColor(0xBBFF0000);
         }else {
            holder.activeDebtsAmount.setTextColor(0x9944DE46);
        }


        holder.activeDebtsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + debtList.get(position).getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.debtList.size();
    }
}
