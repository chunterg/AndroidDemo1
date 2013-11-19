package com.example.helloworld.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.json.JSONObject;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class FileUtil {
	private static String DIR = "HistoryApp";

	public static String readSDFile(Context context,String FILE_NAME) {
		String result="";
		if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){	//check if the sd card exists.
			//创建目录
			String foldername = Environment.getExternalStorageDirectory().getPath()+ "/"+DIR;
		    File folder = new File(foldername);
		    
		    if (folder == null || !folder.exists()) {
		    	folder.mkdir();
		    }
		    
		    //读取文件
			File file = new File(foldername + "/" + FILE_NAME);
			Log.v("App", "app->readSDFile.filePath = " + file.getAbsolutePath());
			try {
				if(file.exists()){
					 InputStream in = new BufferedInputStream(new FileInputStream(file));
					 BufferedReader br= new BufferedReader(new InputStreamReader(in, "UTF-8"));
					 String tmp;
					 
					 while((tmp=br.readLine())!=null){
						 result+=tmp;
					 }
					 br.close();
					 in.close();

				}
			} catch (Exception e) {
				// TODO: handle exception
				Log.e("App", "File read error");
			}

		}else {
			result =  "";
			Toast.makeText(context, "There is no sd card.", Toast.LENGTH_SHORT).show();
		}
		return result;
	}
	
	public static Boolean writeSDFile(Context context,String FILE_NAME, String content) {
		Boolean result = false;
		if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){	//check if the sd card exists.
			//创建目录
			String foldername = Environment.getExternalStorageDirectory().getPath()+ "/"+DIR;
		    File folder = new File(foldername);
		    
		    if (folder == null || !folder.exists()) {
		    	folder.mkdir();
		    }
		    
		    File file = new File(foldername + "/" + FILE_NAME);
			OutputStreamWriter osw;
			Log.d("app", "app->writeSDFile.filePath = " + file.getAbsolutePath());
			try {
				if(file.exists()){
					 file.delete(); 
				}
//				覆写文件
				file.createNewFile(); 
				osw = new OutputStreamWriter(new FileOutputStream(file),"utf-8");
                osw.write(content); 
                osw.close();
				result = true;
			} catch (Exception e) {
				// TODO: handle exception
			}

		}else {
			Toast.makeText(context, "There is no sd card.", Toast.LENGTH_SHORT).show();
		}
		return result;
	}
}
