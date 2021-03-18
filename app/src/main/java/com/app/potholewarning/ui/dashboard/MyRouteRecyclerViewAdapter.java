package com.app.potholewarning.ui.dashboard;

import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.potholewarning.Models.Route;
import com.app.potholewarning.R;
import com.app.potholewarning.ui.dashboard.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Route}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyRouteRecyclerViewAdapter extends RecyclerView.Adapter<MyRouteRecyclerViewAdapter.ViewHolder> {

    private final List<Route> mValues;
    private final View praView;

    public MyRouteRecyclerViewAdapter(View v,List<Route> items) {
        this.praView =v;
        this.mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_route, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).getName());
        holder.subContenViewt.setText(mValues.get(position).getDescription());
        holder.numContentView.setText(String.valueOf(mValues.get(position).getPointCount()));
        holder.mView.setOnClickListener(v -> {
            Log.e("Show",mValues.get(position).getId());
            Bundle bundle = new Bundle();
            bundle.putString("mapID", mValues.get(position).getId());
            bundle.putString("mapName",mValues.get(position).getName());
            Navigation.findNavController(praView).navigate(R.id.action_listRouteFragment_to_navigation_home,bundle);
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public TextView subContenViewt;
        public TextView numContentView;
        public Route mItem;


        public ViewHolder(View view) {
            super(view);
            mView = view;

            subContenViewt = (TextView) view.findViewById(R.id.contentSub);
            numContentView =(TextView) view.findViewById(R.id.num);
            mContentView = (TextView) view.findViewById(R.id.content);
            setTextViewDrawableColor(mContentView, R.color.gray1);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
    private void setTextViewDrawableColor(TextView textView, int color) {
        for (Drawable drawable : textView.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(textView.getContext(), color), PorterDuff.Mode.SRC_IN));
            }
        }
    }
}