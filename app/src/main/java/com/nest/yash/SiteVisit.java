package com.nest.yash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.nest.extras.AlertDialogCallback;
import com.nest.extras.IOHelper;
import com.nest.extras.Utility;
import com.nest.model.VisitorsData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

/*
 * Created by Yash on 11/3/18.
 */

public class SiteVisit extends AppCompatActivity implements View.OnClickListener,AlertDialogCallback<String>
{
    @BindView(R.id.edtxtName) EditText edtxtName;
    @BindView(R.id.edtxtEmail) EditText edtxtEmail;
    @BindView(R.id.edtxtMobileNumber) EditText edtxtMobileNumber;
    @BindView(R.id.svStatusSpinner) Spinner svStatusSpinner;
    @BindView(R.id.dateSpinner) Spinner dateSpinner;
    @BindView(R.id.timeSpinner) Spinner timeSpinner;
    @BindView(R.id.svCordLyt) CoordinatorLayout svCordLyt;
    private ArrayAdapter<CharSequence> svAdapter,dateAdapter,timeAdapter;
    private String svStatus,svDate,svTime,svValue;
    private boolean isData = false,isDataExist = false;
    private ArrayList<VisitorsData> visitorsArrayList;
    private int indexPosition;
    private JSONObject jsonObjOld;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.site_visit_layout);

        /*Intialization of ButterKnife*/
        ButterKnife.bind(this);

        // Get data through Intent

        svValue = getIntent().getStringExtra(getString(R.string.newSV));
        isData = getIntent().getBooleanExtra(getString(R.string.isData),false);
        visitorsArrayList = getIntent().getParcelableArrayListExtra(getString(R.string.visitorsArrayList));

        /* Checking the New SiteVisit or existing
        * @Params svValue -> If Existing then false else true*/
        if(svValue.equalsIgnoreCase(getString(R.string.falseValue)))
        {
            indexPosition = getIntent().getIntExtra(getString(R.string.indexPosition), 0);

            isDataExist = true;

            setData(indexPosition);
        }

        /* Checking the data exist or not
        * @Params isDataExist -> If True the set SiteVisit date else Today's date*/
        if(isDataExist)
            svDate = visitorsArrayList.get(indexPosition).getDate();
        else
            svDate = Utility.getTodaysDate();

        availableTimeSlot(svDate);

        /* Intialization of Site Visit Status Adapter for Spinner */
        svAdapter = ArrayAdapter.createFromResource(this, R.array.svStatusArray, android.R.layout.simple_spinner_item);
        svAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        svStatusSpinner.setAdapter(svAdapter);

        /* Intialization of Site Visit date Adapter for Spinner */
        dateAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, Utility.nextSixDays(this));
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        dateSpinner.setAdapter(dateAdapter);

    }

    // slots which are already booked is removed for the timeSlots Arraylist
    private void availableTimeSlot(String svDate)
    {
        ArrayList<String> timeArrayList;

        // timeSlots -> will return the time slots available for site visit
        timeArrayList = Utility.timeSlots();

        if(visitorsArrayList != null && visitorsArrayList.size() >=1)
        {
            for(int i = 0; i < visitorsArrayList.size();i++)
            {
                if(svDate.equalsIgnoreCase(visitorsArrayList.get(i).getDate()))
                {
                    if(svValue.equalsIgnoreCase(getString(R.string.trueValue)))
                    {
                        if(timeArrayList.contains(visitorsArrayList.get(i).getTime()))
                        {
                            // Removal of already booked Time Slots in new Site Visit
                            timeArrayList.remove(visitorsArrayList.get(i).getTime());
                        }
                    }
                    else
                    {
                        // Removal of already booked Time Slots in existing Site Visit except the time solt that already booked
                        // for that paticular site visit
                        if(!visitorsArrayList.get(indexPosition).getTime().equalsIgnoreCase(visitorsArrayList.get(i).getTime()))
                        {
                            timeArrayList.remove(visitorsArrayList.get(i).getTime());
                        }
                    }
                }
            }
        }

        String[] timeSoltsArray = new String[timeArrayList.size()];

        for(int i = 0; i < timeArrayList.size();i++)
        {
            timeSoltsArray[i] = timeArrayList.get(i);
        }

        /* Intialization of Site Visit Time Adapter for Spinner */
        timeAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, timeSoltsArray);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        timeSpinner.setAdapter(timeAdapter);
    }

    //Pre-filled data of Tenant if SiteVisit was already scheduled
    private void setData(int position)
    {
        edtxtName.setEnabled(false);
        edtxtEmail.requestFocus();

        VisitorsData visitorsData = visitorsArrayList.get(position);

        if (!TextUtils.isEmpty(visitorsData.getName()))
            edtxtName.setText(visitorsData.getName());

        if (!TextUtils.isEmpty(visitorsData.getEmail()))
            edtxtEmail.setText(visitorsData.getEmail());

        if (!TextUtils.isEmpty(visitorsData.getPhone()))
            edtxtMobileNumber.setText(visitorsData.getPhone());
    }

    //Click listener on Status, date and Time Spinner
    @OnItemSelected({R.id.svStatusSpinner,R.id.dateSpinner,R.id.timeSpinner})
    public void spinnerItemSelected(Spinner spinner, int position)
    {
        Utility.hideKeyboard(this);
        if(spinner.getId() == R.id.svStatusSpinner)
        {
            if(isDataExist)// If sitevisit is already scheduled then set the existing data
            {
                int spinnerPosition = svAdapter.getPosition(visitorsArrayList.get(indexPosition).getVisitStatus());
                svStatusSpinner.setSelection(spinnerPosition);
                svStatus = visitorsArrayList.get(indexPosition).getVisitStatus();
            }
            else // set the default 1st position of spinner Array
            {
                svStatus = String.valueOf(spinner.getItemAtPosition(position));
            }

        }
        else if(spinner.getId() == R.id.dateSpinner)
        {
            if(isDataExist)
            {
                int spinnerPosition = dateAdapter.getPosition(visitorsArrayList.get(indexPosition).getDate());
                dateSpinner.setSelection(spinnerPosition);
                svDate = visitorsArrayList.get(indexPosition).getDate();
            }
            else
            {
                svDate = String.valueOf(spinner.getItemAtPosition(position));

                if(svDate.equalsIgnoreCase(getString(R.string.Today)))
                {
                    availableTimeSlot(Utility.getTodaysDate());
                }
                else if(svDate.equalsIgnoreCase(getString(R.string.Tomorrow)))
                {
                    availableTimeSlot(Utility.getNextDayDate());
                }
                else
                {
                    availableTimeSlot(svDate);
                }
            }
        }
        else if(spinner.getId() == R.id.timeSpinner)
        {
            if(isDataExist)
            {
                int spinnerPosition = timeAdapter.getPosition(visitorsArrayList.get(indexPosition).getTime());
                timeSpinner.setSelection(spinnerPosition);
                isDataExist = false;
                svTime = visitorsArrayList.get(indexPosition).getTime();
            }
            else
            {
                svTime = String.valueOf(spinner.getItemAtPosition(position));
            }
        }
    }

    @Override//Click listener on Sechedule a visit and back button
    @OnClick({R.id.svBtn,R.id.backBtn})
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.svBtn:

                Utility.hideKeyboard(this);

                if(edtxtName.getText().toString().length() == 0)
                    Utility.SnackbarPopup(this,getString(R.string.nameBlank),1,svCordLyt);

                else if(edtxtEmail.getText().toString().length() == 0)
                    Utility.SnackbarPopup(this,getString(R.string.emailBlank),1,svCordLyt);

                else if(edtxtMobileNumber.getText().toString().length() == 0)
                    Utility.SnackbarPopup(this,getString(R.string.phoneBlank),2,svCordLyt);

                else if(edtxtMobileNumber.getText().toString().length() <= 9)
                    Utility.SnackbarPopup(this,getString(R.string.phoneShort),3,svCordLyt);

                else if(edtxtMobileNumber.getText().toString().length() > 10)
                    Utility.SnackbarPopup(this,getString(R.string.phoneLong),2,svCordLyt);

                else
                    addData();

                break;

            case R.id.backBtn:

                moveToPerviousScreen();

                break;
        }
    }

    private void addData()
    {
        JSONObject obj = new JSONObject();

        JSONArray list = new JSONArray();

        JSONObject obj1 = new JSONObject();

        try
        {
            obj1.put(getString(R.string.nameParam), edtxtName.getText().toString());
            obj1.put(getString(R.string.emailParam), edtxtEmail.getText().toString());
            obj1.put(getString(R.string.phoneParam), edtxtMobileNumber.getText().toString());
            obj1.put(getString(R.string.timeParam), svTime);
            if(svDate.equalsIgnoreCase(getString(R.string.Today)))
            {
                obj1.put(getString(R.string.dateParam),Utility.getTodaysDate());
            }
            else if(svDate.equalsIgnoreCase(getString(R.string.Tomorrow)))
            {
                obj1.put(getString(R.string.dateParam),Utility.getNextDayDate());
            }
            else
            {
                obj1.put(getString(R.string.dateParam), svDate);
            }
            obj1.put(getString(R.string.visitStatusParam), svStatus);

            list.put(obj1);

            if(visitorsArrayList != null && visitorsArrayList.size() >=1)
            {
                for(int i = 0; i < visitorsArrayList.size();i++)
                {
                    JSONObject jsonObj = new JSONObject();

                    jsonObj.put(getString(R.string.nameParam), visitorsArrayList.get(i).getName());
                    jsonObj.put(getString(R.string.emailParam), visitorsArrayList.get(i).getEmail());
                    jsonObj.put(getString(R.string.phoneParam), visitorsArrayList.get(i).getPhone());
                    jsonObj.put(getString(R.string.timeParam), visitorsArrayList.get(i).getTime());
                    jsonObj.put(getString(R.string.dateParam), visitorsArrayList.get(i).getDate());
                    jsonObj.put(getString(R.string.visitStatusParam), visitorsArrayList.get(i).getVisitStatus());

                    list.put(jsonObj);
                }
            }

            obj.put(getString(R.string.slotBookingParam), list);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        if(isData)
        {
            updateData(obj);// If sitevisit is already scheduled then update the existing data
        }
        else
        {   // Scheduled the new sitevisit.
            IOHelper.writeToFile(this, obj.toString());
            Toast.makeText(this,R.string.dataAdded,Toast.LENGTH_SHORT).show();

            moveToPerviousScreen();
        }
    }

    private void updateData(JSONObject obj)
    {
        // Link --> https://stackoverflow.com/questions/38041893/json-file-java-editing-updating-fields-values

        //Read from file
        try
        {
            FileInputStream is = openFileInput(IOHelper.databaseName);
            String result = IOHelper.stringFromStream(is);

            try
            {
                jsonObjOld = new JSONObject(result);

                JSONArray jsonArrayOld = jsonObjOld.getJSONArray(getString(R.string.slotBookingParam));

                ArrayList<String> keyName = new ArrayList<>();

                for (int i = 0; i < jsonArrayOld.length(); i++)
                {   // Add all the value of tenant name as a key to update the list
                    keyName.add(jsonArrayOld.getJSONObject(i).getString(getString(R.string.nameParam)));
                }

                // Check whether the current tenant name is present in the keyName ArrayList
                if(keyName.contains(edtxtName.getText().toString()))
                {
                    int position =  keyName.indexOf(edtxtName.getText().toString());

                    JSONObject jsonObjectOld = jsonArrayOld.getJSONObject(position);

                    jsonObjectOld.put(getString(R.string.emailParam), edtxtEmail.getText().toString());
                    jsonObjectOld.put(getString(R.string.phoneParam), edtxtMobileNumber.getText().toString());
                    jsonObjectOld.put(getString(R.string.timeParam), svTime);

                    if(svDate.equalsIgnoreCase(getString(R.string.Today)))// If date value is set to today then replace today with date
                    {
                        jsonObjectOld.put(getString(R.string.dateParam),Utility.getTodaysDate());
                    }
                    else if(svDate.equalsIgnoreCase(getString(R.string.Tomorrow)))// If date value is set to Tomorrow then replace today with date
                    {
                        jsonObjectOld.put(getString(R.string.dateParam),Utility.getNextDayDate());
                    }
                    else
                    {
                        jsonObjectOld.put(getString(R.string.dateParam), svDate);
                    }

                    jsonObjectOld.put(getString(R.string.visitStatusParam), svStatus);

                    if(svValue.equalsIgnoreCase(getString(R.string.falseValue)))
                    {
                        updateUserData();// If sitevisit is already scheduled then update the existing data
                    }
                    else
                    {
                        alertDataUpdate();// If sitevisit is new but Tenant name is already exists then alert will be Pop-Up
                    }
                }
                else
                {
                    IOHelper.writeToFile(this, obj.toString());
                    Toast.makeText(this,R.string.dataAdded,Toast.LENGTH_SHORT).show();

                    moveToPerviousScreen();
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            is.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    // Alert will show up that Are you sure you want to Update?
    private void alertDataUpdate()
    {
        Utility.showDialog(this,this.getResources().getString(R.string.updateMsg),this);
    }

    //Updating the existing Tenant data.
    private void updateUserData()
    {
        //Write into the file
        IOHelper.writeToFile(this, jsonObjOld.toString());

        Toast.makeText(this,R.string.dataUpdated,Toast.LENGTH_SHORT).show();

        moveToPerviousScreen();
    }

    private void moveToPerviousScreen()
    {
        finish();
    }

    @Override
    public void onBackPressed()
    {
        moveToPerviousScreen();
    }

    @Override
    public void alertDialogCallback(String ret)
    {
        if(ret.equalsIgnoreCase(this.getResources().getString(R.string.Yes)))
        {
            updateUserData();
        }
    }
}
