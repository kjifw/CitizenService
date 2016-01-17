package com.citizen.service.citizenservice.helpers;

import android.content.Context;
import android.net.Uri;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.HttpURLConnection;

public class PicassoBuilder {

    // FIXME: If we use different contexes we need dictionary with picassos

    private static Picasso picasso = null;

    public static Picasso getInstance(Context context) {
        if(picasso == null) {
            Picasso.Builder builder = new Picasso.Builder(context);
            picasso = builder.downloader(new OkHttpDownloader(context) {
                @Override
                protected HttpURLConnection openConnection(Uri uri) throws IOException {
                    HttpURLConnection connection = super.openConnection(uri);
                    connection.setRequestProperty("Host", "localhost");
                    return connection;
                }
            }).build();
        }
        return picasso;
    }
}
