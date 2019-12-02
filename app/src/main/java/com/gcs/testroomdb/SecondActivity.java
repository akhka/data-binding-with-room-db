package com.gcs.testroomdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.gcs.testroomdb.databinding.ActivitySecondBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SecondActivity extends AppCompatActivity {

    ActivitySecondBinding binding;
    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_second);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        if (id != 0){
            List<User> users = userViewModel.getUsersByIds(id);
            User user = users.get(0);
            binding.setUser(user);
        }

    }
}
