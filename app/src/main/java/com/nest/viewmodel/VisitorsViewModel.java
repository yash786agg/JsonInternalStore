package com.nest.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.widget.Toast;
import com.nest.extras.IOHelper;
import com.nest.model.VisitorsData;
import com.nest.yash.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * Created by Yash on 11/3/18.
 */

public class VisitorsViewModel extends AndroidViewModel
{
    private MutableLiveData<List<VisitorsData>> visitors;
    private ArrayList<VisitorsData> visitorsArrayList;

    public VisitorsViewModel(@NonNull Application application)
    {
        super(application);
    }

    public LiveData<List<VisitorsData>> getVisitors()
    {
        if (visitors == null)
        {
            visitors = new MutableLiveData<>();
        }

        visitorsArrayList = new ArrayList<>();

        loadVisitors();

        return visitors;
    }

    private void loadVisitors()
    {
        try
        {
            FileInputStream is = this.getApplication().openFileInput(IOHelper.databaseName);
            String result = IOHelper.stringFromStream(is);

            try
            {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray(this.getApplication().getString(R.string.slotBookingParam));

                for (int i = 0; i < jsonArray.length(); i++)
                {
                    VisitorsData visitorsData = new VisitorsData();
                    JSONObject object = jsonArray.getJSONObject(i);

                    visitorsData.setName(object.getString(this.getApplication().getString(R.string.nameParam)));
                    visitorsData.setEmail(object.getString(this.getApplication().getString(R.string.emailParam)));
                    visitorsData.setPhone(object.getString(this.getApplication().getString(R.string.phoneParam)));
                    visitorsData.setTime(object.getString(this.getApplication().getString(R.string.timeParam)));
                    visitorsData.setDate(object.getString(this.getApplication().getString(R.string.dateParam)));
                    visitorsData.setVisitStatus(object.getString(this.getApplication().getString(R.string.visitStatusParam)));

                    visitorsArrayList.add(visitorsData);
                }
                visitors.setValue(visitorsArrayList);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
                Toast.makeText(this.getApplication(),R.string.noSVToday,Toast.LENGTH_SHORT).show();
            }

            is.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();

            Toast.makeText(this.getApplication(),R.string.noSVToday,Toast.LENGTH_SHORT).show();
        }
    }

}
