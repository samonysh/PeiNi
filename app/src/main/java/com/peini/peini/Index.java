package com.peini.peini;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Index extends AppCompatActivity {


    private EditText nameView;
    private EditText passwordView;
    private Button loginView;
    private Button registerView;

    private String name = null;
    private String password = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();   //去掉标题栏
        setContentView(R.layout.activity_index);

        initViews();

        loginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameView.getText().toString();
                password = passwordView.getText().toString();

                User testUser = new User(name,password);

                new LoginTask().execute(testUser);

            }
        });

        registerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Index.this,Register.class);
                startActivity(intent);
            }
        });

    }

    public void initViews() {
        nameView = (EditText) findViewById(R.id.name);

        passwordView = (EditText) findViewById(R.id.password);

        loginView = (Button) findViewById(R.id.login);

        registerView = (Button) findViewById(R.id.register);

    }


    public class LoginTask extends AsyncTask<User,Void,Integer>{

        @Override
        protected Integer doInBackground(User... params) {

            Integer result = UserService.login(params[0]);

            return result;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            switch (integer) {
                case 1:
                    Intent oldIntent = new Intent(Index.this,OldVoice.class);
                    startActivity(oldIntent);
                    finish();
                    break;
                case 2:
                    Intent youngIntent = new Intent(Index.this,YoungVoice.class);
                    startActivity(youngIntent);
                    finish();
                    break;
                default:
                    Toast.makeText(Index.this, "登录错误，请检查用户名与密码", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
