package com.example.helloworld.Fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.helloworld.R;
import com.example.helloworld.utils.FileUtil;
import com.example.helloworld.utils.HttpClientUtil;

/**
 * Created by geng.cheng on 13-10-15.
 */

@SuppressLint("ValidFragment")
public class ContentFragment extends Fragment {
	private int uid = 1;
	private static String urlString = "http://ali-062323w.hz.ali.com:88/ajax/getHistoryData";
    private static final int MSG_SUCCESS = 0;//请求成功的标识
    private static final int MSG_FAILURE = 1;//请求失败的标识
    private Thread mThread;
    private TextView textView;
    private String content = null; 
    public ContentFragment() {
    	
    }

    public ContentFragment(int uid) {
	     this.uid = uid;
   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
        View view = inflater.inflate(R.layout.fragment_text, null);
        textView =(TextView)view.findViewById(R.id.textView);        
  
//       检测有无之前保存的内容
        String historyData= FileUtil.readSDFile(getActivity(),"historyData"+uid+".json");
        
        if(historyData!=null && historyData.equals("")){
//  		  比较文件有无更新        
            if(mThread == null) {
                mThread = new Thread(runnable);
                mThread.start();//线程启动
            }
        }else{
        	try {
    			JSONObject jsonTemp = new JSONObject(historyData);
    			textView.setText(Html.fromHtml(jsonTemp.getString("data").toString()));
    		} catch (JSONException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	
        }

        
        return view;
    }
    
//    主线程Handler
    private class DataHandler extends Handler {
    	public DataHandler(Looper looper){
            super (looper);
    	}
    	@Override
        public void handleMessage (Message msg) {//此方法在ui线程运行
    		Log.d("App","Message recieved");
            switch(msg.what) {
            case MSG_SUCCESS:  
            	JSONObject handerContentJsonObject = (JSONObject)msg.obj;
            	try {
					content = handerContentJsonObject.getString("data").toString();
					Log.d("App",handerContentJsonObject.toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	if(!TextUtils.isEmpty(content)) {
                    textView.setText(Html.fromHtml(content));
                    
                }
                break;

            case MSG_FAILURE:
                break;
            }
        }
    };
    
    private Runnable runnable = new Runnable() {
    	JSONObject respJsonObject = null;
        @Override
        public void run() {//run()在新的线程中运行
        	
        	Log.d("App","Sub thread start");
            JSONObject paramList = new JSONObject();
            try {
    			paramList.put("uid", uid);
    		} catch (JSONException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            
    		
            respJsonObject = HttpClientUtil.getJsonResault(urlString, paramList);
            
            //获得主线程的Looper
            Looper looper = Looper.getMainLooper();
            DataHandler dataHandler = new DataHandler(looper);
            //向ui线程发送MSG_SUCCESS标识和respJsonObject
            Message m = dataHandler.obtainMessage(MSG_SUCCESS,respJsonObject);
            dataHandler.sendMessage(m);
            
            
            //保存数据
            //写入SD做缓存
            Boolean writeFileFlag = FileUtil.writeSDFile(getActivity(),"historyData"+uid+".json",respJsonObject.toString());
            if(writeFileFlag){
            	Log.v("App","file cached");
            }

        }
    };
       

}
