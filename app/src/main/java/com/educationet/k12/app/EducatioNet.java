package com.educationet.k12.app;
import android.app.Application;

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
    @Override
    public void onCreate() {
        super.onCreate();

        // The following line triggers the initialization of ACRA
        ACRA.init(this);
    }
}
