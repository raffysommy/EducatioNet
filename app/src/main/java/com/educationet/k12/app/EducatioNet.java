package com.educationet.k12.app;
import android.app.Application;

import org.acra.*;
import org.acra.annotation.*;

@ReportsCrashes(formUri = "https://collector.tracepot.com/0149abb9")
public class EducatioNet extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // The following line triggers the initialization of ACRA
        ACRA.init(this);
    }
}
