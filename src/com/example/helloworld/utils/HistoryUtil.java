package com.example.helloworld.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.net.Uri;
import android.util.Log;

import com.example.helloworld.R;
import com.example.helloworld.pojo.Roadmap;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geng.cheng on 13-10-15.
 */
public class HistoryUtil {
    private static List historyCategory = null;
    private static List roadMapList = null;

    private static boolean          isInited    = false;



    public static List<Roadmap> getRoadMapList(Context context) {
    	roadMapList = parseFromXml(context);
        return roadMapList;
    }



	private static List<Roadmap> parseFromXml(Context context) {

        try {
            XmlResourceParser xml = context.getResources().getXml(R.xml.category);
            List<Roadmap> historyCategory = null;
            Roadmap roadmap = null;
            int eventType = xml.next();
            while (eventType != XmlPullParser.END_DOCUMENT) {
            	
                switch (eventType) {
                	case XmlPullParser.START_DOCUMENT://判断当前事件是否是文档开始事件  
                		historyCategory = new ArrayList<Roadmap>();//初始化集合  
                    break; 
                    case XmlPullParser.START_TAG:
                    	if("roadmap".equals(xml.getName())) {
                    		roadmap = new Roadmap();
                    	}
                    	else if ("uid".equals(xml.getName())) {
                            roadmap.setUid(Integer.parseInt(xml.nextText()));
                        } else if ("name".equals(xml.getName())) {
                            roadmap.setName(xml.nextText());
                        } else if ("time".equals(xml.getName())) {
                            roadmap.setTime(xml.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("roadmap".equals(xml.getName())) {
                            historyCategory.add(roadmap);
                        }
                        break;
                }
                eventType = xml.next();
            }
            return historyCategory;
        } catch (Exception e) {
        	e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

//	url跳转
	public static void goToUrl(Context context,String url) {
		Uri uriUrl = Uri.parse(url);
		Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl); 
		context.startActivity(launchBrowser);
	}

}
