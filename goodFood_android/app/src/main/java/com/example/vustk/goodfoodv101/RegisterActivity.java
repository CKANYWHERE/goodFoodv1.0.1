package com.example.vustk.goodfoodv101;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.vustk.goodfoodv101.network.NetworkUtil;
import com.example.vustk.goodfoodv101.util.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vustk on 2018-03-31.
 */

public class RegisterActivity extends AppCompatActivity {
    private EditText etUserName;
    private EditText etPhone;
    private EditText etId;
    private EditText etPassword;
    private EditText etPasswordConfirm;
    private Button btnDone;
    private Button btnCancel;
    private RadioButton rbtnMan;
    private RadioButton rbtnWoman;
    private RadioGroup radioGroup;

    private String serverMsg;
    private Boolean serverAuth;

    private NetworkUtil networkUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);

        networkUtil = new NetworkUtil(getApplicationContext());

        etUserName = (EditText) findViewById(R.id.etUserName);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etId = (EditText) findViewById(R.id.etId);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPasswordConfirm = (EditText) findViewById(R.id.etPasswordConfirm);
        btnDone = (Button) findViewById(R.id.btnDone);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        rbtnMan = (RadioButton) findViewById(R.id.rbtnMan);
        rbtnWoman = (RadioButton) findViewById(R.id.rbtnWoman);

        // 비밀번호 일치 검사
        etPasswordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = etPassword.getText().toString();
                String confirm = etPasswordConfirm.getText().toString();

                if (password.equals(confirm)) {
                    etPassword.setBackgroundColor(Color.GREEN);
                    etPasswordConfirm.setBackgroundColor(Color.GREEN);
                } else {
                    etPassword.setBackgroundColor(Color.RED);
                    etPasswordConfirm.setBackgroundColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbtnMan.setOnClickListener(this);
                rbtnWoman.setOnClickListener(this);
                rbtnMan.setChecked(true);

                if (checkSignUp()) {
                    requestPostRegister();

                    Intent result = new Intent();
                    result.putExtra("Id", etId.getText().toString());   //rbtn botton 처리 필요함. // 서버에 데이터 보내야한다.

                    // 자신을 호출한 Acstivity로 데이터를 보낸다.
                    setResult(RESULT_OK, result);
                    finish();
                } else
                    Toast.makeText(getApplicationContext(), "회원가입 정보를 해주세요", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public boolean checkSignUp() {
        //유저이름 입력 확인
        if (etUserName.getText().toString().length() == 0) {
            Toast.makeText(RegisterActivity.this, "이름을 입력하세요!", Toast.LENGTH_SHORT).show();
            etUserName.requestFocus();
            return false;
        }

        //생년월일 입력 확인
        if (etPhone.getText().toString().length() == 0) {
            Toast.makeText(RegisterActivity.this, "휴대번호을 입력하세요!", Toast.LENGTH_SHORT).show();
            etPhone.requestFocus();
            return false;
        }

        // 아이디 입력 확인
        if (etId.getText().toString().length() == 0) {
            Toast.makeText(RegisterActivity.this, "아이디를 입력하세요!", Toast.LENGTH_SHORT).show();
            etId.requestFocus();
            return false;
        }

        // 비밀번호 입력 확인
        if (etPassword.getText().toString().length() == 0) {
            Toast.makeText(RegisterActivity.this, "비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
            etPassword.requestFocus();
            return false;
        }

        // 비밀번호 확인 입력 확인
        if (etPasswordConfirm.getText().toString().length() == 0) {
            Toast.makeText(RegisterActivity.this, "비밀번호 확인을 입력하세요!", Toast.LENGTH_SHORT).show();
            etPasswordConfirm.requestFocus();
            return false;
        }

        // 비밀번호 일치 확인
        if (!etPassword.getText().toString().equals(etPasswordConfirm.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
            etPassword.setText("");
            etPasswordConfirm.setText("");
            etPassword.requestFocus();
            return false;
        }

        return true;
    }

    public void requestPostRegister() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", etUserName.getText().toString());
            jsonObject.put("age", Integer.parseInt(etPhone.getText().toString()));
            jsonObject.put("id", etId.getText().toString());
            jsonObject.put("password", etPasswordConfirm.getText().toString());
            if (rbtnMan.isChecked()) {
                jsonObject.put("sex", "M");
            } else {
                jsonObject.put("sex", "F");
            }
            networkUtil.requestServer(Request.Method.POST,
                    Config.MAIN_URL + Config.POST_SIGNUP,
                    jsonObject,
                    networkSuccessListener(),
                    networkErrorListener());
        } catch (JSONException e) {
            throw new IllegalStateException("Failed to convert the object to JSON");
        }
    }

    private Response.Listener<JSONObject> networkSuccessListener() {

        return new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                try {
                    serverMsg = response.getString("message");
                    serverAuth = response.getBoolean("success");

                } catch (JSONException e) {
                    throw new IllegalArgumentException("Failed to parse the String: " + serverMsg);
                } finally {
                    Toast.makeText(getApplicationContext(), serverMsg, Toast.LENGTH_LONG).show();
                }
            }
        };

    }

    private Response.ErrorListener networkErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage() != null) {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        };
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rbtnMan: {

                break;
            }
            case R.id.rbtnWoman: {
                break;
            }
        }
    }
}
