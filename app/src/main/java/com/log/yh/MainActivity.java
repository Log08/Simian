package com.log.yh;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.log.yh.Utils.Sp;
import com.log.yh.Utils.Update;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    ImageFilterView github_dump;
    LinearLayout layout_activation, answer_layout, mode_layout, title_layout,main_layout,vip_layout;
    RadioGroup mode_group;
    RadioButton mode_first, mode_second, mode_third;
    ImageView StatusIcon,qqqun_image;
    TextView activation_title, activation_meesage,title_text;
    TextInputEditText edit_title, edit_answer,practice_answers;
    Button upload_button;
    Switch vip_switch;


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Update.Updated_version(this);
        initview();
    }

    private void initview() {
        github_dump = (ImageFilterView) findViewById(R.id.github_dump);
        main_layout=(LinearLayout) findViewById(R.id.main_layout);
        layout_activation = (LinearLayout) findViewById(R.id.layout_activation);
        answer_layout = (LinearLayout) findViewById(R.id.answer_layout);
        mode_layout = (LinearLayout) findViewById(R.id.mode_layout);
        title_layout = (LinearLayout) findViewById(R.id.title_layout);
        vip_layout=(LinearLayout) findViewById(R.id.vip_layout);
        mode_group = (RadioGroup) findViewById(R.id.mode_group);
        mode_first = (RadioButton) findViewById(R.id.mode_first);
        mode_second = (RadioButton) findViewById(R.id.mode_second);
        mode_third = (RadioButton) findViewById(R.id.mode_third);
        StatusIcon=(ImageView) findViewById(R.id.StatusIcon);
        qqqun_image=(ImageView) findViewById(R.id.qqqun_image);
        title_text=(TextView) findViewById(R.id.title_text);
        activation_title = (TextView) findViewById(R.id.activation_title);
        activation_meesage = (TextView) findViewById(R.id.activation_meesage);
        edit_title = (TextInputEditText) findViewById(R.id.edit_title);
        edit_answer = (TextInputEditText) findViewById(R.id.edit_answer);
        practice_answers=(TextInputEditText) findViewById(R.id.practice_answers);
        upload_button=(Button) findViewById(R.id.upload_button);
        vip_switch=(Switch) findViewById(R.id.vip_switch);
        init();
    }

    private void init() {
        mode_third.setVisibility(View.GONE);
        mode_first.setText(R.string.mode_1);
        mode_second.setText(R.string.mode_2);
        qqqun_image.setOnClickListener((view -> {
            joinQQGroup("rnqZIwayhgddTBF62ufYb1d25tGnSwZo");
        }));
        github_dump.setOnClickListener((view -> {
            Intent intent = new Intent();
            intent.setData(Uri.parse("https://github.com/Log08"));
            startActivity(intent);
        }));
        if (!isModuleActive()) {
            title_layout.setVisibility(View.GONE);
            mode_layout.setVisibility(View.GONE);
            answer_layout.setVisibility(View.GONE);
            activation_title.setText("未激活模块");
            activation_meesage.setText("请先激活模块，才能正常加载该界面");
            layout_activation.setBackgroundResource(R.drawable.bg_red_solid);
            upload_button.setText("退出");
            upload_button.setOnClickListener(view -> {
                System.exit(0);
            });
            return;
        }
        StatusIcon.setImageResource(R.mipmap.ic_yes);
        activation_title.setText("已激活模块");
        activation_meesage.setText("可以正常使用以下功能");
        Sp.SP_init(this);
        ismode();
        set_answer_title();
        agreement_show();
        mode_group.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == mode_first.getId()) {
                Sp.putInt("mode", 0);
                title_layout.setVisibility(View.GONE);
            } else if (i == mode_second.getId()) {
                Sp.putInt("mode", 1);
                title_layout.setVisibility(View.VISIBLE);
            }
        });
        upload_button.setOnClickListener((view -> {
            if (title_layout.getVisibility()== View.VISIBLE){
                Sp.putString("title",edit_title.getText().toString());
            }
        Sp.putString("answer",edit_answer.getText().toString());
            Sp.putString("practice_answer",practice_answers.getText().toString());
        }));
       vip_layout.setVisibility(Sp.getBoolean("vip_layout")?View.VISIBLE:View.GONE);
        title_text.setOnLongClickListener((view -> {
            vip_layout.setVisibility(View.VISIBLE);
            Sp.putBoolen("vip_layout",true);
            return true;
        }));
        vip_switch.setText("是否开启破解Vip功能");
        vip_switch.setChecked(Sp.getBoolean("vip")?true:false);
        vip_switch.setOnCheckedChangeListener((compoundButton,b)->{
            Sp.putBoolen("vip",b);
        });
    }
    public void agreement_show(){
        try {
            if (Sp.getString("first").equals("1")) {
                return;
            }
            new MaterialAlertDialogBuilder(this).setTitle("使用协议").setMessage(readtxt()).setCancelable(false).setPositiveButton("同意", (dialogInterface, i) -> {
                Sp.putString("first", "1");
                dialogInterface.dismiss();
            }).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private boolean isModuleActive() {
        return false;
    }

    private void ismode() {
        switch (Sp.getInt("mode")) {
            case 0 -> {
                mode_first.setChecked(true);
                title_layout.setVisibility(View.GONE);
            }
            case 1 -> mode_second.setChecked(true);
            case 2 -> mode_third.setChecked(true);
        }
    }

    private void set_answer_title() {
        String title = Sp.getString("title");
        String answer = Sp.getString("answer");
        String practice_answer=Sp.getString("practice_answer");
        edit_title.setText(title);
        edit_answer.setText(answer);
        practice_answers.setText(practice_answer);
    }
    public String readtxt()throws Exception{
        AssetManager manager=getAssets();
        InputStream open = manager.open("agreement.txt");
        byte b[] = new byte[1024];
        int len = 0;
        StringBuffer sb = new StringBuffer();
        //字符串为-1 表示为空
        while((len = open.read(b)) != -1){
            sb.append(new String(b,0,len));
        }
        return sb.toString();
    }
    private void joinQQGroup(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26jump_from%3Dwebapi%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "请先安装QQ", Toast.LENGTH_SHORT).show();
        }
    }
}