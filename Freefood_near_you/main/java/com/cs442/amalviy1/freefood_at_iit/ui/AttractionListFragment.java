

package com.cs442.amalviy1.freefood_at_iit.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cs442.amalviy1.freefood_at_iit.Pojo.Current_User_Pojo;
import com.cs442.amalviy1.freefood_at_iit.Pojo.DisplayPojo;
import com.cs442.amalviy1.freefood_at_iit.Pojo.ListPojo;
import com.cs442.amalviy1.freefood_at_iit.R;
import com.cs442.amalviy1.freefood_at_iit.asynctask.Database_New_Post;
import com.cs442.amalviy1.freefood_at_iit.asynctask.Database_store_entry;

import java.util.ArrayList;




public class AttractionListFragment extends Fragment {
    public static final int IMAGE_ANIM_MULTIPLIER = 2;
    private AttractionAdapter mAdapter;
   // private LatLng mLatestLocation;
    private int mImageSize;
    private boolean mItemClicked;
    TextView mLike;

    public AttractionListFragment() {}
    private SwipeRefreshLayout swipeContainer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Load a larger size image to make the activity transition to the detail screen smooth
        mImageSize = getResources().getDimensionPixelSize( R.dimen.image_size)
                * IMAGE_ANIM_MULTIPLIER;





        ArrayList<DisplayPojo> attractions= new ArrayList<DisplayPojo>();

        mAdapter = new AttractionAdapter(getActivity(), ListPojo.arrayItem);
        Current_User_Pojo current_user_pojo= new Current_User_Pojo();
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        TextView session= (TextView) view.findViewById(R.id.displaysession);
        session.setText("Hello, "+current_user_pojo.getName());
        AttractionsRecyclerView recyclerView =
                (AttractionsRecyclerView) view.findViewById(android.R.id.list);
        recyclerView.setEmptyView(view.findViewById(android.R.id.empty));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);



        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);
        //Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(() -> {
            // Your code to refresh the list here.
            // Make sure you call swipeContainer.setRefreshing(false)
            // once the network request has completed successfully.
            Intent intent = new Intent(getActivity().getApplicationContext(), RetriveData.class);
            startActivity(intent);
            //        swipeContainer.setRefreshing(true);

        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        mItemClicked = false;

    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mBroadcastReceiver);
    }
//
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


                mAdapter.notifyDataSetChanged();

        }
    };



    private class AttractionAdapter extends RecyclerView.Adapter<ViewHolder>
            implements ItemClickListener {

        public ArrayList<DisplayPojo> mAttractionList;
        private Context mContext;

        public AttractionAdapter(Context context, ArrayList<DisplayPojo> attractions) {
            super();
            mContext = context;
            mAttractionList = attractions;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.list_row, parent, false);
            return new ViewHolder(view, this);
        }

        @Override
        public void onViewDetachedFromWindow(ViewHolder holder)
        {

        }
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            DisplayPojo attraction = mAttractionList.get(position);

            holder.mTitleTextView.setText(attraction.getItem_name());
            holder.mDescriptionTextView.setText(attraction.getFood_description());
            holder.mTime.setText(attraction.getDate());
            holder.mLike.setText( attraction.getLikes()+" likes");
            Glide.with(mContext)
                    .load(attraction.getImage())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .override(mImageSize, mImageSize)
                    .into(holder.mImageView);

            holder.LikeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = attraction.getLikes();
                    i++;
                    attraction.setLikes( i);
                    holder.mLike.setText( attraction.getLikes()+" likes");

                    Database_store_entry database_store_entry= new Database_store_entry();
                    database_store_entry.execute(attraction.getId(),attraction.getLikes());
                }});




        }


        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return mAttractionList == null ? 0 : mAttractionList.size();
        }

        @Override
        public void onItemClick(View view, int position) {
            if (!mItemClicked) {
                mItemClicked = true;
                View heroView = view.findViewById(android.R.id.icon);
                DetailActivity.launch(
                        getActivity(), mAdapter.mAttractionList.get(position).getItem_name(), heroView);
            }
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        TextView mTitleTextView;
        TextView mDescriptionTextView;
        TextView mOverlayTextView;
        TextView mTime;
        Button LikeButton;
        TextView mLike;
        TextView mComments;
        ImageView mImageView;
        ItemClickListener mItemClickListener;

        public ViewHolder(View view, ItemClickListener itemClickListener) {
            super(view);
            mTitleTextView = (TextView) view.findViewById(android.R.id.text1);
            mDescriptionTextView = (TextView) view.findViewById(android.R.id.text2);
            mOverlayTextView = (TextView) view.findViewById(R.id.overlaytext);
            mImageView = (ImageView) view.findViewById(android.R.id.icon);
            mTime= (TextView) view.findViewById(R.id.timeing);
            mLike= (TextView) view.findViewById(R.id.likecount);
            LikeButton= (Button)view.findViewById(R.id.buttonofLike);
            mItemClickListener = itemClickListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
