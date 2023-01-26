package com.example.PMD_Assignment2;

public class Weather {
    String title;
    String woeid;
    String weather_state_name; // https://www.metaweather.com/static/img/weather/sn.svg
    String applicable_date;
    String weather_state_abbr;
    float min_temp;
    float max_temp;
    float the_temp;

    public String getWeather_state_abbr() {
        return weather_state_abbr;
    }

    public void setWeather_state_abbr(String weather_state_abbr) {
        this.weather_state_abbr = weather_state_abbr;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWoeid() {
        return woeid;
    }

    public void setWoeid(String woeid) {
        this.woeid = woeid;
    }

    public String getWeather_state_name() {
        return weather_state_name;
    }

    public void setWeather_state_name(String weather_state_name) {
        this.weather_state_name = weather_state_name;
    }
    public String getApplicable_date() {
        return applicable_date;
    }

    public void setApplicable_date(String created) {
        this.applicable_date = applicable_date;
    }
    public float getMin_temp() {
        return min_temp;
    }

    public void setMin_temp(float min_temp) {
        this.min_temp = min_temp;
    }

    public float getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(float max_temp) {
        this.max_temp = max_temp;
    }

    public float getThe_temp() {
        return the_temp;
    }

    public void setThe_temp(float the_temp) {
        this.the_temp = the_temp;
    }


    @Override
    public String toString() {
        return "Weather{" +
                "title='" + title + '\'' +
                ", woeid='" + woeid + '\'' +
                ", weather_state_name='" + weather_state_name + '\'' +
                ", applicable_date='" + applicable_date + '\'' +
                ", min_temp=" + min_temp +
                ", max_temp=" + max_temp +
                ", the_temp=" + the_temp +
                '}';
    }
}

//how do i get outer json, sure, but still