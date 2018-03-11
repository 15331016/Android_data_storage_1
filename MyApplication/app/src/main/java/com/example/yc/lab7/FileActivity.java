package com.example.yc.lab7;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by yc on 2017/11/30.
 */

public class FileActivity extends AppCompatActivity {
    private EditText topic;
    private EditText content;
    private Button save;
    private Button load;
    private Button clear2;
    private Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        findView();
        myClick();
    }

    void findView() {
        topic =(EditText) findViewById(R.id.topic);
        content = (EditText) findViewById(R.id.content);
        save = (Button) findViewById(R.id.save);
        load = (Button) findViewById(R.id.load);
        clear2 = (Button) findViewById(R.id.clear2);
        delete = (Button) findViewById(R.id.delete);
    }

    void myClick() {
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String text1 = topic.getText().toString();
                String text2 = content.getText().toString();
                if (text1.equals("")) {
                    Toast.makeText(getApplicationContext(),"Fail to save file",Toast.LENGTH_SHORT).show();
                } else {
                    String FILE_NAME = text1+".txt";
                    try (FileOutputStream fileOutputStream = openFileOutput(FILE_NAME, MODE_PRIVATE)) {
                        fileOutputStream.write(text2.getBytes());
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        Toast.makeText(getApplicationContext(),"Save successfully",Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(),"Fail to save file",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        load.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String text1 = topic.getText().toString();
                if (text1.equals("")) {
                    content.setText("");
                    Toast.makeText(getApplicationContext(),"Fail to load file",Toast.LENGTH_SHORT).show();
                } else {
                    String FILE_NAME = text1+".txt";
                    try (FileInputStream fileInputStream = openFileInput(FILE_NAME)) {
                        byte[] contents = new byte[fileInputStream.available()];
                        fileInputStream.read(contents);
                        String text = new String(contents);
                        content.setText(text);
                        Toast.makeText(getApplicationContext(),"Load successfully",Toast.LENGTH_SHORT).show();
                        fileInputStream.close();
                    } catch (IOException e) {
                        content.setText("");
                        Toast.makeText(getApplicationContext(),"Fail to load file",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        clear2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                content.setText("");
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String text1 = topic.getText().toString();
                if (text1.equals("")) {
                    Toast.makeText(getApplicationContext(),"Fail to delete file",Toast.LENGTH_SHORT).show();
                } else {
                    String FILE_NAME = text1+".txt";
                    boolean flag = deleteFile(FILE_NAME);
                    if (flag) {
                        Toast.makeText(getApplicationContext(),"Delete successfully",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),"Fail to delete file",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
