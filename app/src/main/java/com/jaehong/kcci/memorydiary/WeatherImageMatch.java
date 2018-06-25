package com.jaehong.kcci.memorydiary;

public class WeatherImageMatch {

    public int WeatherImage(String weather){
        int image = 0;

        switch (weather){
            case "맑음" :
                image = R.drawable.if_cloudy;
                break;
            case "흐림":
                image = R.drawable.if_cloud;
                break;
            case "비" :
                image = R.drawable.if_rain_cloud;
                break;
            case "눈":
                image = R.drawable.if_snow_cloud;
                break;
            case "번개":
                image = R.drawable.if_flash_cloud;
                break;
                default:
                    image = 0;
        }

        return image;
    }

    public String WeatherToString(int position){
        String weather = "맑음";

        switch (position) {
            case 0:
                weather = "맑음";
                break;
            case 1:
                weather = "흐림";
                break;
            case 2:
                weather = "비";
                break;
            case 3:
                weather = "눈";
                break;
            case 4:
                weather = "번개";
                break;
            default:
                weather = null;
        }
        
        return weather;
    }

    public int WeatherToPosition(String weather){
        int position = 0;

        switch (weather) {
            case "맑음":
                position = 0;
                break;
            case "흐림":
                position = 1;
                break;
            case "비":
                position = 2;
                break;
            case "눈":
                position = 3;
                break;
            case "번개":
                position = 4;
                break;
            default:
                position = 0;
        }

        return position;
    }
}
