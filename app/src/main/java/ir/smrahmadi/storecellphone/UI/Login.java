package ir.smrahmadi.storecellphone.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ir.smrahmadi.storecellphone.R;

public class Login extends AppCompatActivity {

    Button Btn_Login ;
    EditText Login_Edt_UserName;
    EditText Login_Edt_Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setTitle("Login");

        Btn_Login = (Button) findViewById(R.id.Login_Btn_Login);
        Login_Edt_UserName = (EditText) findViewById(R.id.Login_Edt_UserName);
        Login_Edt_Password = (EditText) findViewById(R.id.Login_Edt_Password);



        Btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = Login_Edt_UserName.getText().toString();
                String password = Login_Edt_Password.getText().toString();

                if(TextUtils.equals(username,"admin") && TextUtils.equals(password,"admin"))
                startActivity(new Intent(Login.this,ManagerHome.class));
            }
        });

    }
}
