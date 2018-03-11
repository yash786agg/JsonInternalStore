package com.nest.extras;

import android.content.Context;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/*
 * Created by Yash on 11/3/18.
 */

public class IOHelper
{
    public static String databaseName = "visitorsData.txt";

    public static void writeToFile(Context context, String str)
    {
        try
        {
            FileOutputStream fos = context.openFileOutput(databaseName, Context.MODE_PRIVATE);
            fos.write(str.getBytes(), 0, str.length());
            fos.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static String stringFromStream(InputStream is)
    {
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null)
                sb.append(line).append("\n");
            reader.close();
            return sb.toString();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
