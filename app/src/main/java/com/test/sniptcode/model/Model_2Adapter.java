package com.test.sniptcode.model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.test.sniptcode.R;

import java.util.List;

public class Model_2Adapter extends ArrayAdapter<Model_2> {
    private Context context;
    private List<Model_2> model_2List;
    private Model_2Adapter.OnModel_2ClickListener listener;

    public Model_2Adapter(Context context, List<Model_2> model_2List, Model_2Adapter.OnModel_2ClickListener listener) {
        super(context, 0, model_2List);
        this.context = context;
        this.model_2List = model_2List;
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.small_item2, parent, false);
        }

        Model_2 model_2 = model_2List.get(position);

        TextView tvModel_2_IDTacgia     = convertView.findViewById(R.id.edText5_small);
        TextView tvModel_2_dienthoai    = convertView.findViewById(R.id.edText6_small);
        TextView tvModel_2_Email        = convertView.findViewById(R.id.edText7_small);
        TextView tvModel_2_Diachi       = convertView.findViewById(R.id.edText8_small);
        TextView tvModel_2_TenTacgia    = convertView.findViewById(R.id.edText9_small);

        tvModel_2_IDTacgia.setText(model_2.getIDTacgia() + "");
        tvModel_2_dienthoai.setText(model_2.getDienthoai());
        tvModel_2_Email.setText(model_2.getEmail());
        tvModel_2_Diachi.setText(model_2.getDiachi());
        tvModel_2_TenTacgia.setText(model_2.getTenTacgia());

        convertView.setOnClickListener(v -> listener.onModel_2Click(model_2));

        return convertView;
    }

    public interface OnModel_2ClickListener {
        void onModel_2Click(Model_2 model_2);
    }
}
