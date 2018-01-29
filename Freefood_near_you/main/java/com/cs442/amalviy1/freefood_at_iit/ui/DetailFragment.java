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

package com.cs442.amalviy1.freefood_at_iit.ui;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cs442.amalviy1.freefood_at_iit.Constants;
import com.cs442.amalviy1.freefood_at_iit.Pojo.DisplayPojo;
import com.cs442.amalviy1.freefood_at_iit.Pojo.ListPojo;
import com.cs442.amalviy1.freefood_at_iit.R;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class DetailFragment extends Fragment {

    private static final

    String EXTRA_ATTRACTION = "attraction";
    private DisplayPojo mAttraction;

    public static DetailFragment createInstance(String attractionName) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_ATTRACTION, attractionName);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    public DetailFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        String attractionName = getArguments().getString(EXTRA_ATTRACTION);
        Log.d( "Print "+attractionName," Check" );
        mAttraction = findAttraction(attractionName);

        if (mAttraction == null) {
            getActivity().finish();
            return null;
        }

        TextView nameTextView = (TextView) view.findViewById( R.id.nameTextView);
        TextView descTextView = (TextView) view.findViewById(R.id.descriptionTextView);
        TextView distanceTextView = (TextView) view.findViewById(R.id.distanceTextView);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        FloatingActionButton mapFab = (FloatingActionButton) view.findViewById(R.id.mapFab);




        nameTextView.setText(attractionName);

        descTextView.setText(mAttraction.getFood_description());

        int imageSize = getResources().getDimensionPixelSize(R.dimen.image_size)
                * Constants.IMAGE_ANIM_MULTIPLIER;
        Glide.with(getActivity())
                .load(mAttraction.getImage())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
              //  .placeholder(R.color.lighter_gray)
                .override(imageSize, imageSize)
                .into(imageView);

        mapFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(Constants.MAPS_INTENT_URI +
                        Uri.encode(mAttraction.getLocation())));
                startActivity(intent);
            }
        });


        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d( "Option selected","" );

        switch (item.getItemId()) {
            case android.R.id.home:
                // Some small additions to handle "up" navigation correctly
                Intent upIntent = NavUtils.getParentActivityIntent( getActivity() );
                upIntent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK );

                // Check if up activity needs to be created (usually when
                // detail screen is opened from a notification or from the
                // Wearable app
                if (NavUtils.shouldUpRecreateTask( getActivity(), upIntent )
                        || getActivity().isTaskRoot()) {

            //         Synthesize parent stack
                    TaskStackBuilder.create(getActivity())
                            .addNextIntentWithParentStack(upIntent)
                            .startActivities();
               }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // On Lollipop+ we finish so to run the nice animation
                        getActivity().finishAfterTransition();
                        return true;
                    }

                    // Otherwise let the system handle navigating "up"
                    return false;
                }
                return super.onOptionsItemSelected( item );
        }


    /**
     * Really hacky loop for finding attraction in our static content provider.
     * Obviously would not be used in a production app.
     */
    private DisplayPojo findAttraction(String attractionName) {
        for (int i = 0; i < ListPojo.arrayItem.size(); i++) {
            if(ListPojo.arrayItem.get( i ).getItem_name().equals( attractionName ))
            {
                return ListPojo.arrayItem.get( i );
            }
        }
        return null;
    }
}
