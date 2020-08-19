package com.slaask.sdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.UUID;

import com.slaask.sdk.ui.SlaaskActivity;

public class Slaask {

    private static String packageName = "com.slaask.sdk";
    private static Slaask instance;
    private SlaaskIdentity identity;
    private Boolean identified;
    private String apiKey;
    private String randomToken;
    private String locale;
    private String color = "#e01865";
    private Context context;

    private Slaask(Context context, String apiKey) {
        this.context = context;
        getOrCreateRandomToken();
        this.identity = new SlaaskIdentity();
        this.identity.setId(randomToken);
        this.identified = false;
        this.apiKey = apiKey;
    }

    private Slaask(Context context, String apiKey, SlaaskIdentity identity) {
        this.context = context;
        this.apiKey = apiKey;
        this.identity = identity;
        this.identified = true;
    }

    public static void initialize(Context context, String apiKey) {
        instance = new Slaask(context, apiKey);
    }

    public static void initialize(Context context, String apiKey, SlaaskIdentity identity) {
        instance = new Slaask(context, apiKey, identity);
    }

    private static void deprecationAlert() {
        Log.e(packageName, "The secretKey argument is deprecated. Please see https://xenoapp.help/hc/developers-documentation/android-sdk/automatically-identify-your-users-on-your-android-apps");
    }

    // DEPRECATED
    private Slaask(Context context, String apiKey, String secretKey) {
        deprecationAlert();
    }

    private Slaask(Context context, String apiKey, SlaaskIdentity identity, String secretKey) {
        deprecationAlert();
    }

    public static void initialize(Context context, String apiKey, String secretKey) {
        deprecationAlert();
    }

    public static void initialize(Context context, String apiKey, String secretKey, SlaaskIdentity identity) {
        deprecationAlert();
    }

    public static Slaask getInstance() {
        if (instance == null) {
            Log.e(packageName, "You need to call Slaask.initialize(this, \"apiKey\", \"secretKey\") first");
        }
        return instance;
    }

    public static void show() {
        Intent intent = new Intent(getInstance().context, SlaaskActivity.class);
        intent.putExtra("LOADER_COLOR", getInstance().color);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getInstance().context.startActivity(intent);
        ((Activity) getInstance().context).overridePendingTransition(R.anim.slide_in_up, R.anim.nothing);
    }


    private void getOrCreateRandomToken() {
        randomToken = context.getSharedPreferences(packageName, Context.MODE_PRIVATE).getString("slaasktoken", null);
        if (randomToken != null) {
            return;
        }
        SharedPreferences.Editor editor = context.getSharedPreferences(packageName, Context.MODE_PRIVATE).edit();

        setRandomToken(UUID.randomUUID().toString());

        editor.putString("slaasktoken", randomToken);

        editor.apply();
    }

    public static void setLoaderColor(String color) {
        getInstance().color = color;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setRandomToken(String tokenId) {
        this.randomToken = randomToken;
    }

    public String getRandomToken() {
        return randomToken;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getLocale() {
        return locale;
    }

    public Context getContext() {
        return context;
    }

    public static SlaaskIdentity getIdentity() {
        return getInstance().identity;
    }

    public static void setIdentity(SlaaskIdentity identity) {
        getInstance().identity = identity;
    }

    public Boolean isIdentified() {
        return identified;
    }
}