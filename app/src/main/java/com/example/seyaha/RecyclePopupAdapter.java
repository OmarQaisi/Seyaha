package com.example.seyaha;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclePopupAdapter extends  RecyclerView.Adapter<RecyclePopupAdapter.ViewHolder>
{

    List<String> cost_type;
    List<Integer> cost_value;
    Context context;
    int []counter;

   static Map<Integer,Integer> positions;

    public RecyclePopupAdapter(Context context, List<String> cost_type, List<Integer>cost_value)
    {

        this.context=context;
        this.cost_type=cost_type;
        this.cost_value = cost_value;
        counter=new int[cost_type.size()];
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.custom_cost, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        positions=new HashMap <>();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position)
    {
    holder.cost_const.setText(cost_type.get(position));
    holder.cost_value.setText(" "+cost_value.get(position).toString());
    for(int i=0;i<cost_value.size();i++)
    {
        positions.put(position,cost_value.get(i));
    }
    holder.add_remove_but.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view)
        {
            if(counter[position]%2==0)
            {
               // holder.cost_const.setBackground(new ColorDrawable(Color.GRAY));
               // holder.cost_value.setBackground(new ColorDrawable(Color.GRAY));
                holder.cost_const.setPaintFlags(holder.cost_const.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.cost_value.setPaintFlags(holder.cost_value.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.add_remove_but.setImageResource(R.drawable.ic_add);
                positions.remove(positions);

            }
            else
            {
               // holder.cost_const.setBackground(new ColorDrawable(Color.WHITE));
              //  holder.cost_value.setBackground(new ColorDrawable(Color.WHITE));

                holder.cost_value.setPaintFlags(0);
                holder.cost_const.setPaintFlags(0);
                positions.put(position,cost_value.get(position));
                holder.add_remove_but.setImageResource(R.drawable.ic_remove);
            }
            counter[position]++;
        }
    });
    }

    @Override
    public int getItemCount() {
        return cost_type.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView cost_const,cost_value;
        CircleImageView add_remove_but;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cost_const=itemView.findViewById(R.id.const_cost);
            cost_value=itemView.findViewById(R.id.changable_cost);
            add_remove_but=itemView.findViewById(R.id.add_remove);
        }
    }
}
