package com.jaehong.kcci.memorydiary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PasswordActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView txtPassword1 , txtPassword2, txtPassword3, txtPassword4;
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        txtPassword1 = findViewById(R.id.txt_password_1);
        txtPassword2 = findViewById(R.id.txt_password_2);
        txtPassword3 = findViewById(R.id.txt_password_3);
        txtPassword4 = findViewById(R.id.txt_password_4);

        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        btn4 = findViewById(R.id.btn_4);
        btn5 = findViewById(R.id.btn_5);
        btn6 = findViewById(R.id.btn_6);
        btn7 = findViewById(R.id.btn_7);
        btn8 = findViewById(R.id.btn_8);
        btn9 = findViewById(R.id.btn_9);
        btn0 = findViewById(R.id.btn_0);


        btn1.setOnClickListener( this );
        btn2.setOnClickListener( this );
        btn3.setOnClickListener( this );
        btn4.setOnClickListener( this );
        btn5.setOnClickListener( this );
        btn6.setOnClickListener( this );
        btn7.setOnClickListener( this );
        btn8.setOnClickListener( this );
        btn9.setOnClickListener( this );
        btn0.setOnClickListener( this );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_0:
                setTextPassword(btn0.getText().toString());
                break;
            case R.id.btn_1:
                setTextPassword(btn1.getText().toString());
                break;
            case R.id.btn_2:
                setTextPassword(btn2.getText().toString());
                break;
            case R.id.btn_3:
                setTextPassword(btn3.getText().toString());
                break;
            case R.id.btn_4:
                setTextPassword(btn4.getText().toString());
                break;
            case R.id.btn_5:
                setTextPassword(btn5.getText().toString());
                break;
            case R.id.btn_6:
                setTextPassword(btn6.getText().toString());
                break;
            case R.id.btn_7:
                setTextPassword(btn7.getText().toString());
                break;
            case R.id.btn_8:
                setTextPassword(btn8.getText().toString());
                break;
            case R.id.btn_9:
                setTextPassword(btn9.getText().toString());
                break;
        }
    }

    private void setTextPassword(String num){
        if(txtPassword1.getText().toString().equals("")){
            txtPassword1.setText(num);
            return;
        }


        if (!txtPassword1.getText().toString().equals("") && txtPassword2.getText().toString().equals("")){
            txtPassword2.setText(num);
            return;
        }


        if (!txtPassword1.getText().toString().equals("") && !txtPassword2.getText().toString().equals("")
                && txtPassword3.getText().toString().equals("")){
            txtPassword3.setText(num);
            return;
        }


        if (!txtPassword1.getText().toString().equals("") && !txtPassword2.getText().toString().equals("")
                && !txtPassword3.getText().toString().equals("") && txtPassword4.getText().toString().equals("")){
            txtPassword4.setText(num);

            StringBuffer text = new StringBuffer();

            text.append(txtPassword1.getText().toString());
            text.append(txtPassword2.getText().toString());
            text.append(txtPassword3.getText().toString());
            text.append(txtPassword4.getText().toString());

            Intent intent = getIntent();

            if (intent != null && intent.getStringExtra("type").toString().equals("비밀번호 저장")) {
                editor.putString("password", text.toString());
                editor.commit();
                Toast.makeText(getApplicationContext(), "암호 등록 완료", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
            }else {
                if(prefs.getString("password",null).toString().equals(text.toString())){
                    Toast.makeText(getApplicationContext(),"암호 확인 완료",Toast.LENGTH_SHORT).show();
                    intent = new Intent(this, MainActivity.class);
                    finish();
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"암호를 확인 해주세요.",Toast.LENGTH_SHORT).show();
                    txtPassword1.setText("");
                    txtPassword2.setText("");
                    txtPassword3.setText("");
                    txtPassword4.setText("");
                }
            }
        }
    }

}
