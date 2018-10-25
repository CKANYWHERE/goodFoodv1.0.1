package com.example.vustk.goodfoodv101;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.vustk.goodfoodv101.network.NetworkUtil;
import com.example.vustk.goodfoodv101.util.Config;
import com.kakao.auth.ISessionCallback;

import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import org.json.JSONException;
import org.json.JSONObject;

//intent 수정 필요함
public class LoginActivity extends AppCompatActivity {

    private EditText etId;
    private EditText etPw;
    private Button btnSignUp;
    private Button btnSignIn;

    private boolean serverAuth;
    private String serverMsg;
    private String userId;

    private String userName;
    private String oid;

    private SharedPreferences settings;
    SharedPreferences.Editor editor;


    private NetworkUtil networkUtil;
    private SessionCallback callback;

    @Override //뒤로가기 버튼 구현해야함
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        settings = getSharedPreferences("user", MODE_PRIVATE);
        editor = settings.edit();

        networkUtil = new NetworkUtil(getApplicationContext());


        etId = (EditText) findViewById(R.id.etId);
        etPw = (EditText) findViewById(R.id.etPassword);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignIn = (Button) findViewById(R.id.btnSignin);
        serverAuth = false;

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                requestPostLogin();
            }
        });

        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().checkAndImplicitOpen();
    }

    public void requestPostLogin() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", etId.getText().toString());
            jsonObject.put("password", etPw.getText().toString());
            Log.e("json", jsonObject.toString());
            networkUtil.requestServer(Request.Method.POST, Config.MAIN_URL + Config.POST_SIGNIN, jsonObject, networkSuccessListener(), networkErrorListener());

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
                    userName = response.getString("username");
                    oid = response.getString("oid");
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                } finally {
                    Toast.makeText(getApplicationContext(), serverMsg.toString(), Toast.LENGTH_LONG).show();
                }
                if (serverAuth) {
                    userId = etId.getText().toString();
                    editor.putString("userId", userId);
                    editor.putString("userName", userName);
                    editor.putString("oid", oid);
                    editor.commit();
                    setResult(RESULT_OK);//////////////////////////////////////////////////////
                    finish(); //LOGIN VIEW 종료
                }
            }
        };
    }

    private Response.ErrorListener networkErrorListener() {
        return new Response.ErrorListener() {

            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            requestMeForKaKao();
            setResult(RESULT_OK);
            finish();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Logger.e(exception);
            }
        }
    }


    public void requestMeForKaKao() {
        UserManagement.getInstance().requestMe(new MeResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e("SessionCallback :: ", "onSessionClosed : " + errorResult.getErrorMessage());
            }

            @Override
            public void onNotSignedUp() {
                Log.e("SessionCallback :: ", "onNotSignedUp");
            }

            @Override
            public void onSuccess(UserProfile userProfile) {
                Log.e("SessionCallback :: ", "onSuccess");

                String nickname = userProfile.getNickname();
                String email = userProfile.getEmail();
                long number = userProfile.getId();
                String id = String.valueOf(number);
                if (email == null) {
                    email = "did_not_accpet_kakao_email";
                }
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", nickname);
                    jsonObject.put("age", "");
                    jsonObject.put("id", email);
                    jsonObject.put("password", "");
                    jsonObject.put("number", id);
                    networkUtil.requestServer(Request.Method.POST,
                            Config.MAIN_URL + Config.POST_SIGNUP,
                            jsonObject,
                            networkSuccessListener(),
                            networkErrorListener()
                    );

                    editor.putString("kakaoNick", nickname);
                    editor.putString("kakao", id);
                    editor.commit();
                } catch (JSONException e) {
                    throw new IllegalStateException("Failed to convert the object to JSON");
                }

            }
        });
    }


}