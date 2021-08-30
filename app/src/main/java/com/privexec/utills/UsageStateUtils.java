package com.privexec.utills;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class UsageStateUtils {
          private static final SimpleDateFormat dateFormat = new SimpleDateFormat("M-d-yyyy HH:mm:ss");
        public static final String TAG = "UsageStateUtils ";
        @SuppressWarnings("ResourceType")
        public static void getStats(Context context) {
            UsageStatsManager usm = (UsageStatsManager) context.getSystemService("usagestats");
            int interval = UsageStatsManager.INTERVAL_YEARLY;
            Calendar calendar = Calendar.getInstance();
            long endTime = calendar.getTimeInMillis();
            calendar.add(Calendar.YEAR, -1);
            long startTime = calendar.getTimeInMillis();
            Log.d(TAG, "Range start:" + dateFormat.format(startTime));
            Log.d(TAG, "Range end:" + dateFormat.format(endTime));
            UsageEvents uEvents = usm.queryEvents(startTime, endTime);
            while (uEvents.hasNextEvent()) {
                UsageEvents.Event e = new UsageEvents.Event();
                uEvents.getNextEvent(e);
                if (e != null) {
                    Log.d(TAG, "Event: " + e.getPackageName() + "\t" + e.getTimeStamp());
                }
            }
        }
        public static List<UsageStats> getUsageStatsList(Context context) {
            UsageStatsManager usm = getUsageStatsManager(context);
            Calendar calendar = Calendar.getInstance();
            long endTime = calendar.getTimeInMillis();
            calendar.add(Calendar.YEAR, -1);
            long startTime = calendar.getTimeInMillis();
            Log.d(TAG, "Range start:" + dateFormat.format(startTime));
            Log.d(TAG, "Range end:" + dateFormat.format(endTime));
            List<UsageStats> usageStatsList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime);
            return usageStatsList;
        }
        public void printUsageStats(List<UsageStats> usageStatsList) {
            String value = null;
            for (UsageStats u : usageStatsList) {
                Log.d(TAG, "printUsageStats Pkg: " + u.getPackageName() + "\t" + "getLastTimeUsed: "
                        + u.getLastTimeUsed());
                value = "Pkg: " + u.getPackageName() + "\t" + "getLastTimeUsed: " + u.getLastTimeUsed();
            }
        }
        public void printCurrentUsageStatus(Context context) {
            printUsageStats(getUsageStatsList(context));
        }
        public static String getLastLaunchedTime(Context context,String packageName) {
            return printLastLaunchedTime(getUsageStatsList(context ),packageName);
        }
    public static String getInstalledTime(Context context,String packageName) {
        return printInstalledTime(getUsageStatsList(context ),packageName);
    }
        @SuppressWarnings("ResourceType")
        private static UsageStatsManager getUsageStatsManager(Context context) {
            UsageStatsManager usm = (UsageStatsManager) context.getSystemService("usagestats");
            return usm;
        }
        public static String printLastLaunchedTime(List<UsageStats> usageStatsList, String packageName) {
            String value = null;
            for (UsageStats u : usageStatsList) {
             if(u.getPackageName().toString().equalsIgnoreCase(packageName)) {


              if(!(""+u.getLastTimeUsed()).equals("0")  && !(""+u.getLastTimeUsed()).equals("null")  && !(""+u.getLastTimeUsed()).equals("") )
                value = "" + u.getLastTimeUsed();
                 Log.d(TAG, "<<>>printLastLaunchedTime u.getPackageName : (" + u.getPackageName() + ")   ==old ("+packageName+") \t" + "getLastTimeUsed: "
                         + u.getLastTimeUsed());
           }
            }
            return value;
        }

     public static String printInstalledTime(List<UsageStats> usageStatsList, String packageName) {
        String value = null;
        for (UsageStats u : usageStatsList) {
            if(u.getPackageName().toString().equalsIgnoreCase(packageName)) {
                Log.d(TAG, "printInstalledTime u.getPackageName : (" + u.getPackageName() + ")   ==old ("+packageName+") \t" + "getFirstTimeStamp(): "
                        + u.getFirstTimeStamp());
                value = "" + u.getFirstTimeStamp();
            }
        }
        return value;
    }
}
