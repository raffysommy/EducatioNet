package com.educationet.k12.app;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.ibm.mobile.services.core.IBMBluemix;
import com.ibm.mobile.services.data.IBMData;
import com.ibm.mobile.services.push.IBMPush;
import com.ibm.mobile.services.push.IBMPushNotificationListener;
import com.ibm.mobile.services.push.IBMSimplePushNotification;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import bolts.Continuation;
import bolts.Task;


import org.acra.*;
import org.acra.annotation.*;


//@ReportsCrashes(formUri = "https://collector.tracepot.com/0149abb9") //(old Tracepot Api)
@ReportsCrashes(
        //formKey = "",
        formUri = "https://d2932ab6-82db-4e45-8cd1-0a07601e249c-bluemix.cloudant.com/acra-educationet/_design/acra-storage/_update/report",
        reportType = org.acra.sender.HttpSender.Type.JSON,
        httpMethod = org.acra.sender.HttpSender.Method.PUT,
        formUriBasicAuthLogin="merculeheneadomrsiessirc",
        formUriBasicAuthPassword="TBxgqQ0u0YoLADcM03P0Qe1C",
        // Your usual ACRA configuration
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.crash_report
)
/**
 * Application Main Class
 * @author  K12-Dev-Team
 */
public class EducatioNet extends Application {
    public static final int EDIT_ACTIVITY_RC = 1;
    public static IBMPush push = null;
    private Activity mActivity;
    private static final String deviceAlias = "Nexus";
    private static final String consumerID = "r4ffy";
    private static final String CLASS_NAME = EducatioNet.class.getSimpleName();
    private static final String APP_ID = "applicationID";
    private static final String APP_SECRET = "applicationSecret";
    private static final String APP_ROUTE = "applicationRoute";
    private static final String PROPS_FILE = "educationet.properties";

    private IBMPushNotificationListener notificationListener = null;
    @Override
    public void onCreate() {
        super.onCreate();

        // The following line triggers the initialization of ACRA
        ACRA.init(this);
        // Read from properties file.
        Properties props = new java.util.Properties();
        Context context = getApplicationContext();
        try {
            AssetManager assetManager = context.getAssets();
            props.load(assetManager.open(PROPS_FILE));
            Log.i(CLASS_NAME, "Found configuration file: " + PROPS_FILE);
        } catch (FileNotFoundException e) {
            Log.e(CLASS_NAME, "The bluelist.properties file was not found.", e);
        } catch (IOException e) {
            Log.e(CLASS_NAME, "The bluelist.properties file could not be read properly.", e);
        }
        Log.i(CLASS_NAME, "Application ID is: " + props.getProperty(APP_ID));

        // Initialize the IBM core backend-as-a-service.
        IBMBluemix.initialize(this, props.getProperty(APP_ID), props.getProperty(APP_SECRET), props.getProperty(APP_ROUTE));
        // Initialize the IBM Data Service.
        IBMData.initializeService();
        // Initialize IBM Push service.
        IBMPush.initializeService();
        // Retrieve instance of the IBM Push service.
        push = IBMPush.getService();
        // Register the device with the IBM Push service.


        push.register(deviceAlias, consumerID).continueWith(new Continuation<String, Void>() {

            @Override
            public Void then(Task<String> task) throws Exception {
                if (task.isCancelled()) {
                    Log.e(CLASS_NAME, "Exception : Task " + task.toString() + " was cancelled.");
                } else if (task.isFaulted()) {
                    Log.e(CLASS_NAME, "Exception : " + task.getError().getMessage());
                } else {
                    Log.d(CLASS_NAME, "Device Successfully Registered");
                }

                return null;
            }

        });
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public EducatioNet() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.d(CLASS_NAME, "Activity created: " + activity.getLocalClassName());
                mActivity = activity;

                // Define IBMPushNotificationListener behavior on push notifications.
                notificationListener = new IBMPushNotificationListener() {
                    @Override
                    public void onReceive(final IBMSimplePushNotification message) {
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Class<? extends Activity> actClass = mActivity.getClass();
                                    Log.e(CLASS_NAME, "Notification message received: " + message.toString());
                                    // Present the message when sent from Push notification console.
                                        mActivity.runOnUiThread(new Runnable() {
                                            public void run() {
                                                new AlertDialog.Builder(mActivity)
                                                        .setTitle("Push notification received")
                                                        .setMessage(message.getAlert())
                                                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                            }
                                                        })
                                                        .show();
                                            }
                                        });
                            }
                        });
                    }
                };
            }
            @Override
            public void onActivityStarted(Activity activity) {
                mActivity = activity;
                Log.d(CLASS_NAME, "Activity started: " + activity.getLocalClassName());
            }
            @Override
            public void onActivityResumed(Activity activity) {
                mActivity = activity;
                Log.d(CLASS_NAME, "Activity resumed: " + activity.getLocalClassName());
                if (push != null) {
                    push.listen(notificationListener);
                }
            }
            @Override
            public void onActivitySaveInstanceState(Activity activity,Bundle outState) {
                Log.d(CLASS_NAME, "Activity saved instance state: " + activity.getLocalClassName());
            }
            @Override
            public void onActivityPaused(Activity activity) {
                if (push != null) {
                    push.hold();
                }
                Log.d(CLASS_NAME, "Activity paused: " + activity.getLocalClassName());
                if (activity != null && activity.equals(mActivity))
                    mActivity = null;
            }
            @Override
            public void onActivityStopped(Activity activity) {
                Log.d(CLASS_NAME, "Activity stopped: " + activity.getLocalClassName());
            }
            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.d(CLASS_NAME, "Activity destroyed: " + activity.getLocalClassName());
            }
        });
    }
}
