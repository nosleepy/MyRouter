package com.grandstream.launcher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.grandstream.myrouter.annotation.Route;

@Route(path = "/launcher")
public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        Bundle bundle = getIntent().getExtras();
        String name = (String) bundle.getString("name");
        Toast.makeText(this, "name = " + name, Toast.LENGTH_SHORT).show();
//        MyRouter.getInstance().navigation(this, "/settings");
    }
}