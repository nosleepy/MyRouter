package com.grandstream.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.grandstream.app.databinding.ActivityMainBinding;
import com.grandstream.common.CommonUtil;
import com.grandstream.myrouter.api.MyRouter;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toast.makeText(this, "1 + 2 = " + CommonUtil.add(1, 2), Toast.LENGTH_SHORT).show();
        binding.btn.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("name", "apt");
            MyRouter.getInstance().navigation("/launcher", bundle);
        });
    }
}