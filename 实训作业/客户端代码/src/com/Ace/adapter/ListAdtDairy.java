package com.Ace.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.Ace.bean.DairyBean;
import com.Ace.constant.Constant;
import com.Ace.net.Transfer;
import com.Ace.resp.BaseResp;
import com.Ace.zuoye.DairyContent;
import com.Ace.zuoye.MainActivity;
import com.Ace.zuoye.R;
import com.android.volley.Response;
import com.android.volley.VolleyError;

public class ListAdtDairy extends BaseAdapter {

	DairyBean dairy;

	private float x, ux;

	private Button curDel_btn;

	ArrayList<DairyBean> dairyArray = new ArrayList<DairyBean>();

	LayoutInflater inflate;

	Context context;

	// flag用来判断是日记本还是记账本使用的这个adt 因为日记本可以有点击查看详情 1为日记 0为记账本
	int flag;

	public ListAdtDairy(Context context, ArrayList<DairyBean> dairyArray, int flag) {
		this.dairyArray = dairyArray;
		inflate = LayoutInflater.from(context);
		this.context = context;
		this.flag = flag;
	}

	@Override
	public int getCount() {
		return dairyArray.size();
	}

	@Override
	public DairyBean getItem(int position) {
		return dairyArray.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View view, ViewGroup parent) {

		ViewHolder holder = new ViewHolder();

		dairy = getItem(position);

		if (view == null) {
			view = inflate.inflate(R.layout.lv_dairy_content, null);
			holder.time = (TextView) view.findViewById(R.id.date);
			holder.content = (TextView) view.findViewById(R.id.content);
			holder.delete = (Button) view.findViewById(R.id.delete_button);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		// 替换掉onItemclick方法
		view.setOnClickListener(new OnClickListener() {
			DairyBean dairy = new DairyBean();

			@Override
			public void onClick(View v) {
				if (flag == 1) {
					dairy = dairyArray.get(position);
					Intent intent = new Intent();
					intent.setClass(context, DairyContent.class);
					intent.putExtra("dairy", dairy);
					context.startActivity(intent);
				}
			}
		});

		// 为每一个view项设置触控监听
		view.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				final ViewHolder holder = (ViewHolder) v.getTag();

				// 当按下时处理
				if (event.getAction() == MotionEvent.ACTION_DOWN) {

					// 获取按下时的x轴坐标
					x = event.getX();
					// 判断之前是否出现了删除按钮如果存在就隐藏
					if (curDel_btn != null) {
						if (curDel_btn.getVisibility() == View.VISIBLE) {

							// ！！！！！！设置成View.INVISIBLE会有奇怪的事情发生！！！！！！！划出来删除按钮
							// 但是点击还是进去了！！！！Tmd!!!!
							curDel_btn.setVisibility(View.GONE);
							return true;
						}
					}

				} else if (event.getAction() == MotionEvent.ACTION_UP) {// 松开处理

					// 获取松开时的x坐标
					ux = event.getX();

					// 判断当前项中按钮控件不为空时
					if (holder.delete != null) {
						// 按下和松开绝对值差当大于20时显示删除按钮，否则不显示

						if (Math.abs(ux - x) > 20) {
							holder.delete.setVisibility(View.VISIBLE);
							curDel_btn = holder.delete;
							return true;
						}
					}
				}
				return false;
			}

		});

		holder.time.setText(dairy.getDate());
		holder.content.setText(dairy.getContent());

		// 为删除按钮添加监听事件，实现点击删除按钮时删除该项
		holder.delete.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (curDel_btn != null)
					curDel_btn.setVisibility(View.GONE);

				String url = Constant.DeleteDairy;
				String date = dairy.getDate().substring(0, 20);
				System.out.println(date);
				if (flag == 0) {
					url = Constant.DeleteTally;
				}

				HashMap<String, String> params = new HashMap<String, String>();
				params.put("name", MainActivity.accountStr);
				params.put("date", date);

				Transfer.doTransfer(params, BaseResp.class, url, null, new Response.Listener<BaseResp>() {

					@Override
					public void onResponse(BaseResp response) {
						String status = response.getStatus();
						if (status.equals("1")) {
							Toast.makeText(context, "删除成功！", 1000).show();
							dairyArray.remove(position);
							notifyDataSetChanged();
						} else {
							Toast.makeText(context, "删除失败！", 1000).show();
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(context, "请检查网络", 1000);
					}
				});
			}
		});

		return view;
	}

	public static class ViewHolder {
		TextView time;
		TextView content;
		Button delete;
	}

}
