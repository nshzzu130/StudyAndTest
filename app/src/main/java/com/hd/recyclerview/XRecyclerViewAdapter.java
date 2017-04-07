package com.hd.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hudong on 2017/3/31.
 */
abstract public class XRecyclerViewAdapter<T,V extends XRecyclerViewAdapter.XViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private int layoutid;
    private List<T> mDatas=new ArrayList<>();
    public XRecyclerViewAdapter(Context context, int layoutid, List<T> mDatas){
        this.context=context;
        this.layoutid=layoutid;
        this.mDatas=mDatas;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(layoutid,parent,false);
        final XViewHolder viewHolder=getHolder(view);
        viewHolder.mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                if(mDatas!=null&&mDatas.size()>0&&position<mDatas.size()){//修复monkeybug
                    onItemClick(parent, v, mDatas.get(position), position);
                }
            }
        });
        return viewHolder;
    }
    public V getHolder(View view){
        Class <V> vclass = (Class <V>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        if(vclass==null){
            Log.e("123", "getHolder: vclass==null");
        }else{
            Log.e("123", "getHolder: vclass!=null vclassname="+vclass.getName());
        }
        try {
            Class[] classes = new Class[] {View.class };
            Constructor<V> method=vclass.getConstructor(classes);
          return method.newInstance(view);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
    public abstract void BindData(V holder,T t);
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.e("123", "onBindViewHolder: position="+position );
        BindData((V)holder,mDatas.get(position));
    }
    @Override
    public int getItemCount() {
        Log.e("123", "getItemCount: size="+mDatas.size() );
        return mDatas.size();
    }
    static class XViewHolder extends RecyclerView.ViewHolder{
        View mContentView;
        public XViewHolder(View itemView) {
            super(itemView);
            mContentView=itemView;
        }
        public void setText(int widgetid,String text){
            View view=mContentView.findViewById(widgetid);
            if(view instanceof TextView){
                ((TextView)view).setText(text);
            }
        }
    }
    abstract public void onItemClick(ViewGroup parent, View v, T t, int position);
}
