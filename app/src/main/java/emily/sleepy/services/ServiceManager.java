package emily.sleepy.services;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import emily.sleepy.constants.Constants;

/**
 * Created by kawo123 on 12/9/2016.
 * Copyright to cs390mb
 */
public class ServiceManager {
    @SuppressWarnings("unused")
    /** used for debugging purposes */
    private static final String TAG = ServiceManager.class.getName();

    /** Singleton instance of the remote sensor manager */
    private static ServiceManager instance;

    /** The application context is used to bind the service manager to the application. **/
    private final Context context;

    /** Returns the singleton instance of the remote sensor manager, instantiating it if necessary. */
    public static synchronized ServiceManager getInstance(Context context) {
        if (instance == null) {
            instance = new ServiceManager(context.getApplicationContext());
        }

        return instance;
    }

    private ServiceManager(Context context) {
        this.context = context;
    }

    /**
     * Starts the given sensor service.
     * @param serviceClass the reference to the sensor service class.
     * @see SensorService
     */
    public void startSensorService(Class<? extends SensorService> serviceClass){
        Log.d(TAG, "startSensorService");
        Intent startServiceIntent = new Intent(context, serviceClass);
        startServiceIntent.setAction(Constants.ACTION.START_SERVICE);
        context.startService(startServiceIntent);
    }

    /**
     * Stops the given sensor service.
     * @param serviceClass the reference to the sensor service class.
     * @see SensorService
     */
    public void stopSensorService(Class<? extends SensorService> serviceClass){
        Log.d(TAG, "stopSensorService");
        Intent startServiceIntent = new Intent(context, serviceClass);
        startServiceIntent.setAction(Constants.ACTION.STOP_SERVICE);
        context.startService(startServiceIntent);
    }

    /**
     * Returns whether the given service is running
     * @param serviceClass a reference to a service class
     * @return true if the service is running, false otherwise
     */
    public boolean isServiceRunning(Class<? extends Service> serviceClass){
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
