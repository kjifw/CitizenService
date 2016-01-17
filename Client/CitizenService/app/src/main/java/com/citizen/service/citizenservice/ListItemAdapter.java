package com.citizen.service.citizenservice;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.provider.SyncStateContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.citizen.service.citizenservice.helpers.PicassoBuilder;
import com.citizen.service.citizenservice.models.IssueListItemModel;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

public class ListItemAdapter extends BaseAdapter{
    Context context;
    List<IssueListItemModel> items;

    public ListItemAdapter(Context context, List<IssueListItemModel> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(
                    Activity.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.list_view_item, null);
        }

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.titleText);
        TextView txtVotes = (TextView) convertView.findViewById(R.id.positiveVotesCount);

        IssueListItemModel listItem = (IssueListItemModel)getItem(position);

        Picasso picasso = PicassoBuilder.getInstance(this.context);

        picasso.load(listItem.getImage()).fit().into(imgIcon);
        txtTitle.setText(listItem.getTitle());
        txtVotes.setText(listItem.getVotesCount());

        return convertView;
    }

}
