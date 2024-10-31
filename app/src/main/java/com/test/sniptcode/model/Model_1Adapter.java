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

public class Model_1Adapter extends ArrayAdapter<Model_1> {
    private Context context;
    private List<Model_1> model_1List;
    private OnModel_1ClickListener listener;

    public Model_1Adapter(Context context, List<Model_1> model_1List, OnModel_1ClickListener listener) {
        super(context, 0, model_1List);
        this.context = context;
        this.model_1List = model_1List;
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.small_item, parent, false);
        }

        Model_1 model_1 = model_1List.get(position);

        TextView tvModel_1_IDsach     = convertView.findViewById(R.id.edText1_small);
        TextView tvModel_1_NgayXB     = convertView.findViewById(R.id.edText2_small);
        TextView tvModel_1_Theloai    = convertView.findViewById(R.id.edText3_small);
        TextView tvModel_1_Tensach    = convertView.findViewById(R.id.edText4_small);
        TextView tvModel_1_IdTacgia   = convertView.findViewById(R.id.edText5_small1);


        tvModel_1_IDsach.setText(model_1.getIDsach() + "");
        tvModel_1_NgayXB.setText(model_1.getNgayXB());
        tvModel_1_Theloai.setText(model_1.getTheloai());
        tvModel_1_Tensach.setText(model_1.getTensach());
        tvModel_1_IdTacgia.setText(model_1.getIdTacgia() + "");


        convertView.setOnClickListener(v -> listener.onModel_1Click(model_1));

        return convertView;
    }

    public interface OnModel_1ClickListener {
        void onModel_1Click(Model_1 model_1);
    }
}

    


