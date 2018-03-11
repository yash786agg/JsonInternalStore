package com.nest.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.nest.extras.RcylcVItemClick;
import com.nest.extras.Utility;
import com.nest.model.VisitorsData;
import com.nest.yash.R;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

/*
 * Created by Yash on 11/3/18.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>
{
    private ArrayList<VisitorsData> visitorsArrayList;
    private LayoutInflater layoutInflater;
    private Context context;
    private RcylcVItemClick clickListener;

    public RecyclerViewAdapter(Context context, ArrayList<VisitorsData> visitorsArrayList)
    {
        /*
         * RecyclerViewAdapter Constructor to Initialize Data which we get from MainActivity
         */

        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.visitorsArrayList = visitorsArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        /*
         * LayoutInflater is used to Inflate the view
         * from adapter_layout
         * for showing data in RecyclerView
         */

        View view = layoutInflater.inflate(R.layout.adapter_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.MyViewHolder holder, final int position)
    {
        /*
         * onBindViewHolder is used to Set all the respective data
         * to Textview form visitors ArrayList.
         */

        VisitorsData visitorsData = visitorsArrayList.get(position);

        if(Utility.getTodaysDate().equalsIgnoreCase(visitorsData.getDate()))
        {
            holder.svLL.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(visitorsData.getName()))
            {
                String name = context.getResources().getString(R.string.name) +" "+
                        context.getResources().getString(R.string.colon)+" "+visitorsData.getName();
                holder.nameTxt.setText(name);
            }

            if (!TextUtils.isEmpty(visitorsData.getEmail()))
            {
                String email = context.getResources().getString(R.string.email) +" "+
                        context.getResources().getString(R.string.colon)+" "+visitorsData.getEmail();
                
                holder.emailTxt.setText(email);
            }

            if (!TextUtils.isEmpty(visitorsData.getPhone()))
            {
                String phone = context.getResources().getString(R.string.phone) +" "+
                        context.getResources().getString(R.string.colon)+" "+visitorsData.getPhone();
                holder.phonetxt.setText(phone);
            }

            if (!TextUtils.isEmpty(visitorsData.getTime()))
            {
                String time = context.getResources().getString(R.string.time)+" "+
                        context.getResources().getString(R.string.colon) +" "+visitorsData.getTime();
                holder.timeTxt.setText(time);
            }

            if (!TextUtils.isEmpty(visitorsData.getVisitStatus()))
            {
                String status = context.getResources().getString(R.string.status) +" "+
                        context.getResources().getString(R.string.colon)+" "+visitorsData.getVisitStatus();
                holder.statusTxt.setText(status);
            }

            if (!TextUtils.isEmpty(visitorsData.getVisitStatus()))
            {
                if(visitorsData.getVisitStatus().equalsIgnoreCase(context.getResources().getString(R.string.notVisited)))
                {
                    holder.nameTxt.setTextColor(ContextCompat.getColor(context,R.color.circleColor));
                    holder.emailTxt.setTextColor(ContextCompat.getColor(context,R.color.circleColor));
                    holder.phonetxt.setTextColor(ContextCompat.getColor(context,R.color.circleColor));
                    holder.statusTxt.setTextColor(ContextCompat.getColor(context,R.color.circleColor));
                    holder.timeTxt.setTextColor(ContextCompat.getColor(context,R.color.circleColor));
                    holder.svLLCell1.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
                    holder.svLLCell2.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
                }
                else if(visitorsData.getVisitStatus().equalsIgnoreCase(context.getResources().getString(R.string.visited)))
                {
                    holder.nameTxt.setTextColor(ContextCompat.getColor(context,R.color.lightBlueColor));
                    holder.emailTxt.setTextColor(ContextCompat.getColor(context,R.color.lightBlueColor));
                    holder.phonetxt.setTextColor(ContextCompat.getColor(context,R.color.lightBlueColor));
                    holder.statusTxt.setTextColor(ContextCompat.getColor(context,R.color.lightBlueColor));
                    holder.timeTxt.setTextColor(ContextCompat.getColor(context,R.color.lightBlueColor));
                    holder.svLLCell1.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
                    holder.svLLCell2.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
                }
                else if(visitorsData.getVisitStatus().equalsIgnoreCase(context.getResources().getString(R.string.pending)))
                {
                    holder.nameTxt.setTextColor(ContextCompat.getColor(context,R.color.white));
                    holder.emailTxt.setTextColor(ContextCompat.getColor(context,R.color.white));
                    holder.phonetxt.setTextColor(ContextCompat.getColor(context,R.color.white));
                    holder.statusTxt.setTextColor(ContextCompat.getColor(context,R.color.white));
                    holder.timeTxt.setTextColor(ContextCompat.getColor(context,R.color.white));
                    holder.svLLCell1.setBackgroundColor(ContextCompat.getColor(context,R.color.darkBlueColor));
                    holder.svLLCell2.setBackgroundColor(ContextCompat.getColor(context,R.color.darkBlueColor));
                }
                else if(visitorsData.getVisitStatus().equalsIgnoreCase(context.getResources().getString(R.string.cancelled)))
                {
                    holder.nameTxt.setTextColor(ContextCompat.getColor(context,R.color.lightBlueColor));
                    holder.emailTxt.setTextColor(ContextCompat.getColor(context,R.color.lightBlueColor));
                    holder.phonetxt.setTextColor(ContextCompat.getColor(context,R.color.darkBlueColor));
                    holder.statusTxt.setTextColor(ContextCompat.getColor(context,R.color.darkBlueColor));
                    holder.timeTxt.setTextColor(ContextCompat.getColor(context,R.color.darkBlueColor));
                    holder.svLLCell1.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
                    holder.svLLCell2.setBackgroundColor(ContextCompat.getColor(context,R.color.lightOrangeColor));
                }
            }
        }
        else
        {
            holder.svLL.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount()
    {
        /*
         * getItemCount is used to get the size of respective visitorsArrayList
         */

        return visitorsArrayList.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        /*
         * Return the view type of the item at position for the purposes of view recycling.
         */

        return position;
    }

    public void setOnItemClickListener(final RcylcVItemClick mItemClickListener)
    {
        this.clickListener = mItemClickListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        @BindView(R.id.nameTxt) TextView nameTxt;
        @BindView(R.id.emailTxt) TextView emailTxt;
        @BindView(R.id.phonetxt) TextView phonetxt;
        @BindView(R.id.timeTxt) TextView timeTxt;
        @BindView(R.id.statusTxt) TextView statusTxt;
        @BindView(R.id.svLL) LinearLayout svLL;
        @BindView(R.id.svLLCell1) LinearLayout svLLCell1;
        @BindView(R.id.svLLCell2) LinearLayout svLLCell2;

        /*
         * MyViewHolder is used to Initializing the view.
         */

        MyViewHolder(View itemView)
        {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            if (clickListener != null)
            {
                clickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }
}