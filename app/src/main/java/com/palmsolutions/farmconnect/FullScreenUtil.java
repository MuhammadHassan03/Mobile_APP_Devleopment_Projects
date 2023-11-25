package com.palmsolutions.farmconnect;

import android.os.Build;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;

public class FullScreenUtil {

    public static void hideSystemUI(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            hideSystemUINew(view);
        } else {
            hideSystemUILegacy(view);
        }
    }

    @SuppressWarnings("deprecation")
    private static void hideSystemUILegacy(View view) {
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        view.setSystemUiVisibility(uiOptions);
    }

    private static void hideSystemUINew(View view) {
        WindowInsetsController insetsController = view.getWindowInsetsController();
        if (insetsController != null) {
            insetsController.hide(WindowInsets.Type.navigationBars());
        }
    }
}

