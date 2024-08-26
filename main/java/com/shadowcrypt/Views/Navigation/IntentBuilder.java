package com.shadowcrypt.Views.Navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class IntentBuilder {
    private final Intent intent;
    private final Bundle extras;

    public IntentBuilder(Context context, Class<?> targetActivity) {
        this.intent = new Intent(context, targetActivity);
        this.extras = new Bundle();
    }

    public IntentBuilder putExtras(String key, int value){
        extras.putInt(key, value);
        return this;
    }

    public IntentBuilder putExtras(String key, boolean value){
        extras.putBoolean(key, value);
        return this;
    }

    public IntentBuilder putExtras(String key, String value){
        extras.putString(key, value);
        return this;
    }

    public IntentBuilder addFlags(int flags){
        intent.addFlags(flags);
        return this;
    }

    public Intent build(){
        intent.putExtras(extras);
        return intent;
    }
}