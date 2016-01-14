package com.citizen.service.citizenservice.http;

import android.os.AsyncTask;
import android.util.Config;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ImageUpload {

    private String boundary = "-------------" + System.currentTimeMillis();
    private static final String LINE_FEED = "\r\n";
    private static final String TWO_HYPHENS = "--";

    private StringBuilder url;
    private String protocol;
    private String imagePath;
    private String authorizationToken;
    private int count = 0;
    private DataOutputStream dos;

    public ImageUpload(StringBuilder url, String protocol, String authorizationToken) {
        super();
        this.url = url;
        this.protocol = protocol;
        this.authorizationToken = authorizationToken;
    }

    public ImageUpload(StringBuilder url, String protocol, String imagePath, String authorizationToken) {
        super();
        this.url = url;
        this.protocol = protocol;
        this.imagePath = imagePath;
        this.authorizationToken = authorizationToken;
    }

    public void addFormField(String fieldName, String value) {
        try {
            dos.writeBytes(TWO_HYPHENS + boundary + LINE_FEED);
            dos.writeBytes("Content-Disposition: form-data; name=\"" + fieldName + "\"" + LINE_FEED + LINE_FEED/*+ value + LINE_FEED*/);
            /*dos.writeBytes("Content-Type: text/plain; charset=UTF-8" + LINE_FEED);*/
            dos.writeBytes(value + LINE_FEED);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addFilePart(String fieldName, File uploadFile) {
        try {
            dos.writeBytes(TWO_HYPHENS + boundary + LINE_FEED);
            dos.writeBytes("Content-Disposition: form-data; name=\"" + fieldName + "\";filename=\"" + uploadFile.getName() + "\"" + LINE_FEED);
            dos.writeBytes(LINE_FEED);

            FileInputStream fStream = new FileInputStream(uploadFile);
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = -1;

            while ((length = fStream.read(buffer)) != -1) {
                dos.write(buffer, 0, length);
            }
            dos.writeBytes(LINE_FEED);
            dos.writeBytes(TWO_HYPHENS + boundary + TWO_HYPHENS + LINE_FEED);
        /* close streams */
            fStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addHeaderField(String name, String value) {
        try {
            dos.writeBytes(name + ": " + value + LINE_FEED);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void build() {
        try {
            dos.writeBytes(LINE_FEED);
            dos.flush();
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readStream(InputStream in) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String nextLine = "";
            while ((nextLine = reader.readLine()) != null) {
                sb.append(nextLine);
            }
        /* Close Stream */
            if (null != in) {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public Boolean upload() {

        StringBuilder stringBuilder = new StringBuilder();

        String strResponse = "";
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

        try {
            urlConnection = (HttpURLConnection) new URL(url.toString()).openConnection();
            urlConnection.setRequestProperty("Host", "localhost");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Connection", "close");
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ");
            urlConnection.setRequestProperty("Authorization", "Bearer " + this.authorizationToken);
            urlConnection.setRequestProperty("Content-type", "multipart/form-data; boundary=" + boundary);

            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setChunkedStreamingMode(1024);
            urlConnection.setRequestMethod("POST");
            dos = new DataOutputStream(urlConnection.getOutputStream());

            File file = new File(this.imagePath);
            if (file != null) {
                addFilePart("photos[" + 1 + "][image]", file);
            }

            build();
            urlConnection.connect();
            int statusCode = 0;
            try {
                urlConnection.connect();
                statusCode = urlConnection.getResponseCode();
            } catch (EOFException e1) {
                if (count < 5) {
                    urlConnection.disconnect();
                    count++;
                    Boolean result = upload();
                    if (result == true) {
                        return result;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 200 represents HTTP OK
            if (statusCode == HttpURLConnection.HTTP_OK) {
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                strResponse = readStream(inputStream);
            } else {
                System.out.println(urlConnection.getResponseMessage());
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                strResponse = readStream(inputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != inputStream)
                    inputStream.close();
            } catch (IOException e) {
            }
        }

        Log.d("Response", strResponse);

        return true;
    }
}
