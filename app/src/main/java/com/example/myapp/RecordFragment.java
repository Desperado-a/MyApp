package com.example.myapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;


public class RecordFragment extends Fragment implements  AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        return inflater.inflate(R.layout.frame_record,container);

    }
    private ListView list;
    private ArrayList<HashMap<String,String>> Listitems;
    private SimpleAdapter sadapter;
    private DBManager manager;
    public void  onActivityCreated(Bundle saveInstanceState){
        super.onActivityCreated(saveInstanceState);

        manager=new DBManager(getContext());

        Log.i("aaa","on RecordFragment");
        list = getView().findViewById(R.id.record_list);
        list.setEmptyView(getView().findViewById(R.id.nodata));
        list.setOnItemClickListener(this);
        list.setOnItemLongClickListener(this);

        Listitems=new ArrayList<HashMap<String,String>>();;


        for(RecordItem i:manager.listAll()){
            String k="",mm="";
            HashMap<String, String> map = new HashMap<String, String>();
            if(i.getKind()==1) {
                k="支出："+i.getMoney();
                mm="-"+i.getMoney();
            }
            else if(i.getKind()==2) {
                k="收入："+i.getMoney();
                mm="+"+i.getMoney();
            }

            map.put("kind", k);
            map.put("id", String.valueOf(i.getId()));
            map.put("money", mm);
            map.put("detail",i.getDetail());
            map.put("date", i.getDate());
            Listitems.add(map);
        }
        Log.i("aaa","获取成功");
        sadapter = new SimpleAdapter(getContext(),Listitems,R.layout.list_item,
                new String[]{"kind","money","detail","date"},
                new int[]{R.id.item_kind,R.id.item_money,R.id.item_detail,R.id.item_date});
        list.setAdapter(sadapter);
        Log.i("aaa","显示成功");

        TextView out_sum=getView().findViewById(R.id.out_sum);
        TextView in_sum=getView().findViewById(R.id.in_sum);
        out_sum.setText("-"+manager.getOut_sum());
        in_sum.setText("+"+manager.getIn_sum());

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        HashMap<String,String> map=(HashMap<String,String>)list.getItemAtPosition(i);
        String kind = map.get("kind");
        String money = map.get("money");
        String detail = map.get("detail");
        String date = map.get("date");
        String id =map.get("id");
        Log.i("aaa","on click id:"+map.get("id"));
        Intent intent = new Intent(getContext(),show.class);
        intent.putExtra("date",date);
        intent.putExtra("mon",money);
        intent.putExtra("kind",kind);
        intent.putExtra("detail",detail);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
        builder.setTitle("确认删除").setMessage("删除后不可恢复！")
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        HashMap<String,String> map=(HashMap<String,String>)list.getItemAtPosition(i);
                        Listitems.remove(map);
                        Log.i("aaa","on long click id:"+map.get("id"));
                        int id= Integer.parseInt(map.get("id"));
                        String kind= map.get("id").substring(0,2);
                        String  money= map.get("id").substring(1);
                        float m = Float.parseFloat(money);
                        int k=0;
                        if(kind.equals("支出")) {
                            k=1;
                        }
                        else if(kind.equals("收入")){
                            k=2;
                        }

                        manager.delete(id);
                        Intent intent = new Intent(getContext(),FrameActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }
                }).setNegativeButton("取消",null);
        builder.create().show();
        return true;
    }

}
