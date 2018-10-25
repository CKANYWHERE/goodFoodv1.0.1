package com.example.vustk.goodfoodv101.botpager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.vustk.goodfoodv101.LoginActivity;
import com.example.vustk.goodfoodv101.R;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

public class NaviFragment4 extends Fragment {
    private Button btnLogin;
    private boolean checkSession = false;
    private SharedPreferences getUser;
    private SharedPreferences.Editor editor;

    public NaviFragment4() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.bucket, container, false);
        btnLogin = (Button) v.findViewById(R.id.btnLogin);

        getUser = this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = getUser.edit();


        checkSession = checkSessionIsOpen();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkSession) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                        onClickLogoutForKaKao();
                }
            }
        });

        return v;
    }


    public boolean checkSessionIsOpen() {
        if (getUser.contains("kakao")) {
            btnLogin.setText( getUser.getString("kakao", "")+ "님 환영합니다 \n로그아웃");
            return true;
        } else if (getUser.contains("userId")) {
            btnLogin.setText( getUser.getString("userId", "")+ "님 환영합니다 \n로그아웃");
            return true;
        }
        return false;
    }


    private void onClickLogoutForKaKao() {
        checkSession = false;
        UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                editor.clear();
                editor.commit();
            }
        });

        editor.clear();
        editor.commit();
        btnLogin.setText("Login");
    }
}
