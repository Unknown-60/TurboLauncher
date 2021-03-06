/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.phonemetra.turbo.launcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

public class PreloadReceiver extends BroadcastReceiver {
    private static final String TAG = "Launcher.PreloadReceiver";
    private static final boolean LOGD = false;

    public static final String EXTRA_WORKSPACE_NAME =
            "com.phonemetra.turbo.launcher.action.EXTRA_WORKSPACE_NAME";

    @Override
    public void onReceive(Context context, Intent intent) {
        final LauncherProvider provider = LauncherAppState.getLauncherProvider();
        if (provider != null) {
            String name = intent.getStringExtra(EXTRA_WORKSPACE_NAME);
            final int workspaceResId = !TextUtils.isEmpty(name)
                    ? context.getResources().getIdentifier(name, "xml", "com.phonemetra.turbo.launcher") : 0;
            if (LOGD) {
                Log.d(TAG, "workspace name: " + name + " id: " + workspaceResId);
            }
            new AsyncTask<Void, Void, Void>() {
                public Void doInBackground(Void ... args) {
                    provider.loadDefaultFavoritesIfNecessary(workspaceResId);
                    return null;
                }
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void) null);
        }
    }
}
