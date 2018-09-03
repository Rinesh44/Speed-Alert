package com.example.darknight.tootlespeedalert.util;

/**
 * Created by bpn on 12/9/16.
 */
public class DateConverter {

    public static String convertDateInCustomFormat(String date) {

        date = removeYearAndDash(date);
        String dateAndMonth[] = date.split(" ");
        String month = dateAndMonth[0];
        month = getMonthName(month);
        String day = dateAndMonth[1];

        return month + " " + day;

    }

    private static String removeYearAndDash(String date) {

        String year = date.substring(0, 4);
        date = date.replace(year, "");
        date = date.replaceAll("-", " ");
        date = date.trim();
        return date;

    }


    private static String getMonthName(String monthName) {

        int month = Integer.parseInt(monthName);

        switch (month) {

            case 1:
                return "Jan";
            case 2:
                return "Feb";
            case 3:
                return "Mar";
            case 4:
                return "Apr";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "Aug";
            case 9:
                return "Sept";
            case 10:
                return "Oct";
            case 11:
                return "Nov";
            case 12:
                return "Dec";
        }
        return "";
    }
}
