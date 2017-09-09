package com.peini.peini;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    private EditText nameText;
    private EditText realNameText;
    private EditText mobileText;
    private RadioGroup typeRadio;
    private EditText passwordText;
    private Button registerButton;

    private String name=null;
    private String realName=null;
    private String mobile=null;
    private String type="old";
    private String password=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();

        typeRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton radioButton = (RadioButton) typeRadio.findViewById(checkedId);
                String typename = radioButton.getText().toString();
                if(typename.equals("我是老人")){
                    type="old";
                }else{
                    type="young";
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameText.getText().toString();
                realName = realNameText.getText().toString();
                mobile = mobileText.getText().toString();
                password = passwordText.getText().toString();

                User registerUser = new User(name,realName,mobile,type,password);

                new RegisterTask().execute(registerUser);
            }
        });

    }

    private void initViews(){
        nameText = (EditText) findViewById(R.id.registerName);
        realNameText = (EditText) findViewById(R.id.registerRealName);
        mobileText = (EditText) findViewById(R.id.registerMobile);
        typeRadio = (RadioGroup) findViewById(R.id.registerType);
        passwordText = (EditText) findViewById(R.id.registerPassword);
        registerButton = (Button) findViewById(R.id.button);
    }

    public class RegisterTask extends AsyncTask<User,Void,Boolean>{

        @Override
        protected Boolean doInBackground(User... params) {
            Boolean result = UserService.register(params[0]);
            return result;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                Intent intent = new Intent(Register.this,Index.class);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(Register.this, "0", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
