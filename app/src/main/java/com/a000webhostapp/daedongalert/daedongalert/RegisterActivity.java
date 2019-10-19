package com.a000webhostapp.daedongalert.daedongalert;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterActivity extends AppCompatActivity {

    public static final String TAG = RegisterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final EditText passwordConfirm = (EditText) findViewById(R.id.passwordConfirm);
        final EditText nameText = (EditText) findViewById(R.id.nameText);
        final CheckBox studentCheck = (CheckBox) findViewById(R.id.studentCheck);
        final CheckBox teacherCheck = (CheckBox) findViewById(R.id.teacherCheck);
        final CheckBox parentCheck = (CheckBox) findViewById(R.id.parentCheck);

        Button RegisterButton = (Button) findViewById(R.id.RegisterButton);

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();
                String userPasswordC = passwordConfirm.getText().toString();
                String userName = nameText.getText().toString();
                RegisterCheckBox checkBox = new RegisterCheckBox(studentCheck, teacherCheck, parentCheck);

                if (!(userPassword.equals(userPasswordC))) {
                    DialogPasswordDiff();
                    Log.d(TAG, "Password Different!");
                }
                else if(checkBox.errorCheck() == 0) {
                    CheckBoxNone();
                    Log.d(TAG, "Checkbox none");
                }
                else if(checkBox.errorCheck() > 1) {
                    CheckBoxOver();
                    Log.d(TAG, "Checkbox over");
                }
                else {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                boolean success = jsonObject.getBoolean("success");
                                if(success) {
                                    RegisterSuccess();
                                } else {
                                    RegisterFailure();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    Log.d(TAG, "RegisterRequest queued!");

                    String encrypt_userPassword = encryptSHA512(userPassword);

                    RegisterRequest registerRequest = new RegisterRequest(userID, encrypt_userPassword, userName, checkBox.getType(), responseListener);
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    queue.add(registerRequest);
                }
            }
        });
    }

    void CheckBoxNone() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("경고");
        builder.setMessage("회원 유형을 선택하십시오.");
        builder.setNeutralButton("다시시도", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    void CheckBoxOver() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("경고");
        builder.setMessage("회원 유형을 하나만 선택하십시오.");
        builder.setNeutralButton("다시시도", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    void RegisterFailure() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("오류");
        builder.setMessage("회원가입에 실패하였습니다.");
        builder.setNegativeButton("다시시도", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    void RegisterSuccess() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("성공");
        builder.setMessage("회원가입에 성공하였습니다.");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        builder.show();
    }

    void DialogPasswordDiff() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("경고");
        builder.setMessage("입력된 패스워드가 다릅니다.");
        builder.setNeutralButton("다시시도", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    public static String encryptSHA512(String target) {
        try {
            MessageDigest sh = MessageDigest.getInstance("SHA-512");
            sh.update(target.getBytes());

            StringBuffer stringBuffer = new StringBuffer();

            for(byte b : sh.digest()) {
                stringBuffer.append(Integer.toHexString(0xff & b));
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }
}
