package com.soundking.flodlabelsview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private FoldLabelView mFoldLabelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFoldLabelView = findViewById(R.id.foldlabelsview);

        List<String> list = new ArrayList<>();
        list.add("灭霸");
        list.add("美国队长");
        list.add("雷神1");
        list.add("美国队长");
        list.add("鹰眼");
        list.add("惊奇队长");
        list.add("鹰眼");
        list.add("鹰眼鹰");
        list.add("奇异博士");
        list.add("奇异博士11");
        list.add("奇异博士22");
        list.add("奇异博士12dsa");

        mFoldLabelView.setLabels(list);
        mFoldLabelView.setOnLabelClickListener(new LabelsView.OnLabelClickListener() {
            @Override
            public void onLabelClick(TextView label, Object data, int position) {
                Toast.makeText(MainActivity.this, data.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
