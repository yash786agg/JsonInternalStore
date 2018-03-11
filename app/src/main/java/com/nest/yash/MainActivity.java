package com.nest.yash;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.nest.adapter.RecyclerViewAdapter;
import com.nest.extras.EmptyRcyclv;
import com.nest.extras.RcylcVItemClick;
import com.nest.extras.Utility;
import com.nest.model.VisitorsData;
import com.nest.viewmodel.VisitorsViewModel;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,RcylcVItemClick
{
    //Application supports the latest Android Architecture Component
    //Code Structure: MVVM (Model View View Controller) with Live Data and View Model are Used.
    //Application support both Potrait and Landsacpe mode.

    static
    {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @BindView(R.id.addFab) FloatingActionButton addFab;
    @BindView(R.id.mainRclv) EmptyRcyclv mainRclv;
    @BindView(R.id.rclvEmptyView) TextView rclvEmptyView;
    @BindView(R.id.backBtn) ImageView backBtn;
    private ArrayList<VisitorsData> visitorsArrayList;
    private RecyclerViewAdapter rclvAdapter;
    private boolean isData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*  Intialization of ButterKnife */
        ButterKnife.bind(this);

        backBtn.setVisibility(View.GONE);

        /*  Intialization of ArrayList */
        visitorsArrayList = new ArrayList<>();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        visitorsArrayList.clear();

        // Observe Visitors data

        VisitorsViewModel viewModel = ViewModelProviders.of(this).get(VisitorsViewModel.class);
        viewModel.getVisitors().observe(this, new Observer<List<VisitorsData>>()
        {
            @Override
            public void onChanged(@Nullable List<VisitorsData> visitorsData)
            {
                visitorsArrayList = (ArrayList<VisitorsData>) visitorsData;
                isData = true;

                mainRclv.setEmptyView(rclvEmptyView);

                /*  Intialization of RecyclerView Adapter */
                rclvAdapter = new RecyclerViewAdapter(MainActivity.this,visitorsArrayList);

                if(visitorsArrayList.size() >= 1)
                {
                    isData = true;
                    ArrayList<String> dates = new ArrayList<>();

                    for (int i = 0; i < visitorsArrayList.size(); i++)
                    {
                        dates.add(visitorsArrayList.get(i).getDate());
                    }

                    if(Utility.containsSubString(dates,Utility.getTodaysDate()))
                    {
                        /*We used LinearLayoutManager to show Data in List Form */

                        mainRclv.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                        rclvAdapter.setOnItemClickListener(MainActivity.this);

                        mainRclv.setAdapter(rclvAdapter);
                    }
                    else
                    {
                        rclvEmptyView.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    @Override//Click listener for FAB Icon
    @OnClick({R.id.addFab})
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.addFab:

                Intent svIntent = new Intent(this,SiteVisit.class);
                svIntent.putExtra(getString(R.string.newSV),getString(R.string.trueValue));
                svIntent.putExtra(getString(R.string.isData),isData);
                svIntent.putParcelableArrayListExtra(getString(R.string.visitorsArrayList),visitorsArrayList);
                startActivity(svIntent);

              break;
        }
    }

    @Override//Item Click listener for recyclerview item
    public void onItemClick(View view, int position)
    {
        Intent svIntent = new Intent(this,SiteVisit.class);
        svIntent.putParcelableArrayListExtra(getString(R.string.visitorsArrayList),visitorsArrayList);
        svIntent.putExtra(getString(R.string.indexPosition),position);
        svIntent.putExtra(getString(R.string.newSV),getString(R.string.falseValue));
        svIntent.putExtra(getString(R.string.isData),isData);
        startActivity(svIntent);
    }
}
