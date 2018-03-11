package com.nest.model;

import android.os.Parcel;
import android.os.Parcelable;

/*
 * Created by Yash on 11/3/18.
 */

public class VisitorsData implements Parcelable
{
    private String name;
    private String email;
    private String phone;
    private String time;
    private String date;
    private String visitStatus;

    public VisitorsData()
    {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVisitStatus() {
        return visitStatus;
    }

    public void setVisitStatus(String visitStatus) {
        this.visitStatus = visitStatus;
    }

    @Override
    public String toString()
    {
        return "VisitorsData{" +
                "name='" + name + '\'' +
                "email='" + email + '\'' +
                "phone='" + phone + '\'' +
                "time='" + time + '\'' +
                "date='" + date + '\'' +
                ", visitStatus='" + visitStatus + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(time);
        dest.writeString(date);
        dest.writeString(visitStatus);
    }

    public VisitorsData(Parcel in)
    {
        name = in.readString();
        email = in.readString();
        phone = in.readString();
        time = in.readString();
        date = in.readString();
        visitStatus = in.readString();
    }

    public static final Parcelable.Creator<VisitorsData> CREATOR = new Parcelable.Creator<VisitorsData>() {
        @Override
        public VisitorsData createFromParcel(Parcel in) {
            return new VisitorsData(in);
        }

        @Override
        public VisitorsData[] newArray(int size) {
            return new VisitorsData[size];
        }
    };
}
