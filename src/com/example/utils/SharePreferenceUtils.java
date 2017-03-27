package com.example.utils;
/**
 * 缓存各种页面标记
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharePreferenceUtils {
	private String FILE_NAME="config";
	private SharedPreferences sp;
	public SharePreferenceUtils(Context context){
		sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
	}
	/**
	 * 放入一个int值
	 * @param i
	 */
	public void putInt(String key,int i){
		Editor editor = sp.edit();
		editor.putInt(key, i);
		editor.commit();
	}
	/**
	 * 拿取一个int值
	 */
	public int getInt(String key){
		int i=sp.getInt(key, 0);
		return i; 
	}
}
