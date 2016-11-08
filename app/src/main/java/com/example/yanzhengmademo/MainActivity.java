package com.example.yanzhengmademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.View;
public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private VerificationCode code;
    private String verificationCode;
    private EditText editText;
    private String inputCode;
    String TAG = "duanlian====";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.image);
        editText = (EditText) findViewById(R.id.edittext);
        inputCode = editText.getText().toString().trim();//拿到用户输入的验证码
        Log.w(TAG,"inputCode"+inputCode);
        //调用方法拿到对象,传入2个参数,参数1:要绘制的验证码个数,参数2:要绘制的干扰线条的条数
        code = VerificationCode.getInstance(5,4);
        imageView.setImageBitmap(code.getBitmap());
        verificationCode = code.getCode();//拿到验证码
        //点击imageView后刷新验证码
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageBitmap(code.getBitmap());
                verificationCode = code.getCode();//拿到刷新之后的验证码
                Log.w(TAG,"verificationCode==="+verificationCode);
            }
        });
    }

    /**
     * 验证按钮的监听
     */
    public void virification(View view){
        inputCode = editText.getText().toString().trim();//拿到用户输入的验证码
        Log.w(TAG,"inputCode"+inputCode);
        if (verificationCode.equals(inputCode)) {
            Toast.makeText(MainActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(inputCode)) {
            Toast.makeText(MainActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "您输入的验证码有误", Toast.LENGTH_SHORT).show();
        }

    }
}
