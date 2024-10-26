package com.log.yh.Utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Log husy on 2024/10/19.
 *
 * @link https://github.com/Log08
 * @Gdescription:
 * 更新
 */
public class Update {
    public static String message;
    private static String version="1.3";
    private static Handler handler=new Handler();
    public static void Updated_version(Context context){
         new Thread(()->{
             try {
                 URL url=new URL("http链接");
                 HttpURLConnection connection=(HttpURLConnection) url.openConnection();
                 connection.setRequestMethod("GET");
                 connection.setRequestProperty("Accept", "*/*");
                 connection.setRequestProperty("User-Agent", "Profile/MIDP-2.0 Configuration/CLDC-1.0");
                 connection.setRequestProperty("Content-Language", "en-US");
                 connection.setRequestProperty("Accept-Language","zh-CN");
                 connection.setRequestProperty("Content-type","text/html");
                 connection.connect();
                 InputStreamReader ir  = new InputStreamReader(connection.getInputStream());
                 BufferedReader br = new BufferedReader(ir);
                 StringBuffer buffer=new StringBuffer();
                 String line;
                 while ((line = br.readLine()) != null) {
                     buffer.append(line);
                 }
                 br.close();
                 ir.close();
                 connection.disconnect();
                 JSONObject json=new JSONObject(buffer.toString());
                 boolean isMandatory=json.getBoolean("Mandatory_updates")? false:true;
                 String json_version=json.getString("version");
                 String Update_http=json.getString("Update_http");
                 message=json.getString("message");
                 if (version.equals(json_version)) {
                     return;
                 }
                 handler.post(()->{
                     AlertDialog builder=new MaterialAlertDialogBuilder(context).setTitle("有新版本需要更新").setMessage(message).setCancelable(isMandatory).setPositiveButton("前往更新",null).create();
                     builder.show();
                     builder.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener((view -> {
                      Intent intent=new Intent();
                      intent.setData(Uri.parse(Update_http));
                      context.startActivity(intent);
                     }));
                 });
             } catch (Exception e) {
                 throw new RuntimeException(e);
             }
         }).start();
    }
}
