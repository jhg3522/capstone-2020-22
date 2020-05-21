package com.capstone.navigatAR;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import static com.mapbox.mapboxsdk.LibraryLoader.load;

public class Login_Activity extends AppCompatActivity implements View.OnClickListener {

    Button Button_btn; // 코드를 전달하는 Button
    EditText EditText_Email;
    EditText EditText_Password;
    CheckBox CheckBox_auto_login;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String Login_Code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText_Email = findViewById(R.id.EditText_Email);
        Button_btn = findViewById(R.id.Button_btn);
        CheckBox_auto_login = findViewById(R.id.CheckBox_auto_login);
        sharedPreferences = getSharedPreferences("Login_Code",0);
        Login_Code = sharedPreferences.getString("Login_Code",null);


        if (sharedPreferences.getBoolean("CheckBox_auto_login",false)){
            EditText_Email.setText(sharedPreferences.getString("Login_Code",""));
            CheckBox_auto_login.setChecked(true);
        }

        if(Login_Code != null){
            if(Login_Code.equals("AR001")){
                Toast.makeText(Login_Activity.this,Login_Code+"사용 가능한 코드입니다.",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }else {
            Button_btn.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v){
        sharedPreferences = getSharedPreferences("Login_Code",0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(CheckBox_auto_login.isChecked()) {
            if(EditText_Email.getText().toString().equals("AR001")){
                editor.putString("Login_Code", EditText_Email.getText().toString());
                editor.putBoolean("CheckBox_auto_login", true);
                editor.commit();
                Toast.makeText(Login_Activity.this,Login_Code+" 사용 가능한 코드입니다.",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(Login_Activity.this,EditText_Email.getText().toString()+" 사용 불가능한 코드입니다.",Toast.LENGTH_SHORT).show();
            }
        }
        else if(!CheckBox_auto_login.isChecked() ){
            if(Login_Code !=null) {
                editor.clear();
                editor.commit();
            }
            if(EditText_Email.getText().toString().equals("AR001")){
                Toast.makeText(Login_Activity.this,EditText_Email.getText().toString()+" 사용 가능한 코드입니다.",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            else{
                if(Login_Code !=null) {
                    Toast.makeText(Login_Activity.this, Login_Code + " 사용 불가능한 코드입니다.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Login_Activity.this, EditText_Email.getText().toString() + " 사용 불가능한 코드입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}


