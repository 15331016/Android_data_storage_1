package com.example.yc.lab7;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText NewPassword;
    private EditText ConfirmPassword;
    private Button ok;
    private Button clear;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        init();
        myClick();
    }

    void findView() {
        //获取实例对象
        NewPassword =(EditText) findViewById(R.id.NewPassword);
        ConfirmPassword = (EditText) findViewById(R.id.ConfirmPassword);
        ok = (Button) findViewById(R.id.ok);
        clear = (Button) findViewById(R.id.clear);
        sp = this.getSharedPreferences("MY_PREFERENCE",this.MODE_PRIVATE);
    }

    void init() {
        //判断是否已存入密码，若已存入，则界面改变
        boolean isRemember = sp.getBoolean("rememberPassword",false);
        if (isRemember) {
            NewPassword.setVisibility(View.GONE);
            ConfirmPassword.setHint("Password");
        }
    }

    void myClick() {
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //首先判断是否已经存入密码，从而设置不同的点击事件
                String psw = sp.getString("Password","");
                String psw1 = NewPassword.getText().toString();
                String psw2 = ConfirmPassword.getText().toString();
                if (psw.equals("")) {
                    if (psw1.equals("") || psw2.equals("")) {
                        Toast.makeText(getApplicationContext(), "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (!psw1.equals(psw2)) {
                        Toast.makeText(getApplicationContext(), "Password Mismatch", Toast.LENGTH_SHORT).show();
                    } else {//密码不为空且互相匹配，创建成功，转入文件编辑界面
                        //存储密码
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putBoolean("rememberPassword", true);
                        editor.putString("Password", psw1);
                        editor.commit();
                        Intent intent = new Intent(MainActivity.this, FileActivity.class);
                        startActivity(intent);
                    }
                } else {
                    if (psw2.equals("")) {
                        Toast.makeText(getApplicationContext(), "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (!psw2.equals(psw)) {
                        Toast.makeText(getApplicationContext(), "Password Mismatch", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(MainActivity.this, FileActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NewPassword.setText("");
                ConfirmPassword.setText("");
            }
        });
    }
}
