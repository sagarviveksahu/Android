/*
 * Copyright 2015 Google Inc. All rights reserved.
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

package com.cs442.amalviy1.freefood_at_iit;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Display;


import org.w3c.dom.Node;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.HashSet;

/**
 * This class contains shared static utility methods that both the mobile and
 * wearable apps can use.
 */
public class Utils {
    private static final String TAG = Utils.class.getSimpleName();

    private static final String PREFERENCES_LAT = "lat";
    private static final String PREFERENCES_LNG = "lng";
    private static final String PREFERENCES_GEOFENCE_ENABLED = "geofence";
    private static final String DISTANCE_KM_POSTFIX = "km";
    private static final String DISTANCE_M_POSTFIX = "m";

    /**
     * Check if the app has access to fine location permission. On pre-M
     * devices this will always return true.
     */
    public static boolean checkFineLocationPermission(Context context) {
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    /**
     * Calculate distance between two LatLng points and format it nicely for
     * display. As this is a sample, it only statically supports metric units.
     * A production app should check locale and support the correct units.
     */

    /**
     * Store the location in the app preferences.
     */


    /**
     * Fetch the location from app preferences.
     */


    /**
     * Store if geofencing triggers will show a notification in app preferences.
     */
    public static void storeGeofenceEnabled(Context context, boolean enable) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(PREFERENCES_GEOFENCE_ENABLED, enable);
        editor.apply();
    }

    /**
     * Retrieve if geofencing triggers should show a notification from app preferences.
     */
    public static boolean getGeofenceEnabled(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(PREFERENCES_GEOFENCE_ENABLED, true);
    }

    /**
     * Convert an asset into a bitmap object synchronously. Only call this
     * method from a background thread (it should never be called from the
     * main/UI thread as it blocks).
     */
//    public static Bitmap loadBitmapFromAsset(GoogleApiClient googleApiClient, Asset asset) {
//        if (asset == null) {
//            throw new IllegalArgumentException("Asset must be non-null");
//        }
//        // convert asset into a file descriptor and block until it's ready
//        InputStream assetInputStream = Wearable.DataApi.getFdForAsset(
//                googleApiClient, asset).await().getInputStream();
//
//        if (assetInputStream == null) {
//            Log.w(TAG, "Requested an unknown Asset.");
//            return null;
//        }
//        // decode the stream into a bitmap
//        return BitmapFactory.decodeStream(assetInputStream);
//    }
//
//    /**
//     * Create a wearable asset from a bitmap.
//     */
//    public static Asset createAssetFromBitmap(Bitmap bitmap) {
//        if (bitmap != null) {
//            final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteStream);
//            return Asset.createFromBytes(byteStream.toByteArray());
//        }
//        return null;
//    }
//
//    /**
//     * Get a list of all wearable nodes that are connected synchronously.
//     * Only call this method from a background thread (it should never be
//     * called from the main/UI thread as it blocks).
//     */
//    public static Collection<String> getNodes(GoogleApiClient client) {
//        Collection<String> results= new HashSet<String>();
//        NodeApi.GetConnectedNodesResult nodes =
//                Wearable.NodeApi.getConnectedNodes(client).await();
//        for (Node node : nodes.getNodes()) {
//            results.add(node.getId());
//        }
//        return results;
//    }

    /**
     * Calculates the square insets on a round device. If the system insets are not set
     * (set to 0) then the inner square of the circle is applied instead.
     *
     * @param display device default display
     * @param systemInsets the system insets
     * @return adjusted square insets for use on a round device
     */
    public static Rect calculateBottomInsetsOnRoundDevice(Display display, Rect systemInsets) {
        Point size = new Point();
        display.getSize(size);
        int width = size.x + systemInsets.left + systemInsets.right;
        int height = size.y + systemInsets.top + systemInsets.bottom;

        // Minimum inset to use on a round screen, calculated as a fixed percent of screen height
        int minInset = (int) (height * Constants.WEAR_ROUND_MIN_INSET_PERCENT);

        // Use system inset if it is larger than min inset, otherwise use min inset
        int bottomInset = systemInsets.bottom > minInset ? systemInsets.bottom : minInset;

        // Calculate left and right insets based on bottom inset
        double radius = width / 2;
        double apothem = radius - bottomInset;
        double chord = Math.sqrt(Math.pow(radius, 2) - Math.pow(apothem, 2)) * 2;
        int leftRightInset = (int) ((width - chord) / 2);

        Log.d(TAG, "calculateBottomInsetsOnRoundDevice: " + bottomInset + ", " + leftRightInset);

        return new Rect(leftRightInset, 0, leftRightInset, bottomInset);
    }
}
