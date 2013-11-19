package com.example.helloworld.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.integer;
import android.annotation.TargetApi;
import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.helloworld.R;
import com.example.helloworld.pojo.Roadmap;
import com.example.helloworld.utils.HistoryUtil;
import com.example.helloworld.MainActivity;

/**
 * Created by geng.cheng on 13-10-15.
 */

public class MenuFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.list, container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<Roadmap> roadmapList = HistoryUtil.getRoadMapList(getActivity());
        
        
        RoadmapAdapter adapter = new RoadmapAdapter(getActivity(),R.layout.category_list);
        adapter.setData(roadmapList);
        this.setListAdapter(adapter);
        
    }
    
   //菜单列表点击事件
    @Override
	public void onListItemClick(ListView parent, View v, int position, long id) {
    	super.onListItemClick(parent, v, position, id);
		Fragment newContent = null;
		
		//获取当前ListView关联的adapter
		Adapter adapter = getListAdapter();
		
		int val  =((Roadmap)adapter.getItem(position)).getUid();
		//传uid到ContentFragement
		newContent = new ContentFragment(val);

		if (newContent != null){
			MainActivity fca = (MainActivity) getActivity();
			fca.switchContent(newContent);
		}
			
	}

/*
 * 设置自定义适配器
 */
    public class RoadmapAdapter extends ArrayAdapter<Roadmap> {
    	private Context context;
    	//private List<Roadmap> listItems;
    	private int resource;
        public RoadmapAdapter(Context context, int resourceId) {
        	
        	super(context, resourceId); 
        	
        	//绑定数据和上下文
            //this.listItems = listItems;
            this.context = context;
            this.resource = resourceId;
        }
        public void setData(List<Roadmap> list) {
    		synchronized (list) {
    			for (Roadmap item : list) {
    				add(item);
    			}
    		}
    	}
        
        //自定义控件集合     
        public final class ListItemView{  
            public TextView name;     
            public TextView time; 
            public int uid;
	     } 
        
           
        public View getView(int position, View convertView, ViewGroup parent) {
        	ListItemView  listItemView = null;
        	final Roadmap listItem = getItem(position);
            if (convertView == null) {
            	listItemView = new ListItemView(); 
            	
            	//获取容器视图
                convertView = LayoutInflater.from(context).inflate(resource, null);
                listItemView.name = (TextView) convertView.findViewById(R.id.roadmapname);
                listItemView.time = (TextView) convertView.findViewById(R.id.roadmaptime);
                listItemView.uid =(int) listItem.getUid();
                //设置控件集到convertView   
                convertView.setTag(listItemView); 
                
                Log.d("Adapter", "convertView is null now");
            }else {   
                listItemView = (ListItemView)convertView.getTag();   
                Log.d("Adapter", "convertView is not null now"); 
            } 

           
            listItemView.name.setText((String) listItem.getName());
            listItemView.time.setText((String) listItem.getTime());

            return convertView;
        }

    }





}