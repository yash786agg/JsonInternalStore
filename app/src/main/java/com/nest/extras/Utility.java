package com.nest.extras;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import com.nest.yash.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/*
 * Created by Yash on 11/3/18.
 */

public class Utility
{
    public static void SnackbarPopup(Context context, String message, int line_count, CoordinatorLayout crdLyt)
    {
        Snackbar snackbar = Snackbar.make(crdLyt, message, Snackbar.LENGTH_LONG)
                .setAction(context.getResources().getString(R.string.ok), new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {}
                });

        snackbar.setActionTextColor(context.getResources().getColor(R.color.pinkColor));
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setMaxLines(line_count);
        snackbar.show();
    }

    public static void hideKeyboard(Context context)
    {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    // get current date and return in String format
    public static String getTodaysDate()
    {
        Calendar c = Calendar.getInstance();

        String datePattern = "dd-MMM-yyyy";

        SimpleDateFormat df = new SimpleDateFormat(datePattern, Locale.US);

        return df.format(c.getTime());
    }

    // get current date and return in String format
    public static String getNextDayDate()
    {
        String datePattern = "dd-MMM-yyyy";

        SimpleDateFormat df = new SimpleDateFormat(datePattern, Locale.US);

        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DATE, 1);

        return df.format(calendar.getTime());
    }

    public static String[] nextSixDays(Context context)
    {
        String[] array= new String[6];

        String datePattern = "dd-MMM-yyyy";

        array[0] = context.getResources().getString(R.string.Today);
        array[1] = context.getResources().getString(R.string.Tomorrow);

        SimpleDateFormat sdf = new SimpleDateFormat(datePattern, Locale.US);
        for (int i = 2; i < 6; i++)
        {
            Calendar calendar = new GregorianCalendar();
            calendar.add(Calendar.DATE, i);
            String day = sdf.format(calendar.getTime());
            array[i] = day;
        }
        return array;
    }

    public static boolean containsSubString(ArrayList<String> stringArray, String substring)
    {
        for (String string : stringArray)
        {
            if (string.contains(substring)) return true;
        }
        return false;
    }

    public static ArrayList<String> timeSlots()
    {
        String timFormat = "hh:mm a";
        String startTime = "09:30 AM";

        ArrayList<String> array = new ArrayList<>();

        Calendar instance = Calendar.getInstance();
        try
        {
            instance.setTime(new SimpleDateFormat(timFormat,Locale.US).parse(startTime));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        int i = -1;
        while(i++ < 20)
        {
            instance.add(Calendar.MINUTE, 30);
            SimpleDateFormat sdf = new SimpleDateFormat(timFormat,Locale.US);
            String time = sdf.format(instance.getTime());
            array.add(time);
        }
        return array;
    }

    // showDialog for Alerting the user that User is already present by this name
    // To ask user whether the user is sure that he want to update.
    public static void showDialog(final Context context, String message,final AlertDialogCallback<String> callback)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.app_name));
        builder.setMessage(message);
        builder.setPositiveButton(context.getResources().getString(R.string.Yes),
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        callback.alertDialogCallback(context.getResources().getString(R.string.Yes));
                    }
                });
        builder.setNegativeButton(context.getResources().getString(R.string.No),
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        callback.alertDialogCallback(context.getResources().getString(R.string.No));
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
