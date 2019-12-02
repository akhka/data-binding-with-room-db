package com.gcs.testroomdb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

public class Handlers {

    private Context context;
    private EditText et;
    private UserViewModel userViewModel;
    private int id;

    public Handlers(Context context, EditText et){
        this.context = context;
        this.et = et;
        userViewModel = ViewModelProviders.of((MainActivity)context).get(UserViewModel.class);
    }

    public Handlers(Context context, int id){
        this.context = context;
        this.id = id;
    }

    public void onClickButton(View view){
        if (TextUtils.isEmpty(et.getText())){
            Toast.makeText(context, "Field is Empty!", Toast.LENGTH_SHORT).show();
        }
        else{
            String text = et.getText().toString();
            if (!text.contains(" ")){
                Toast.makeText(context, "Provide First and Last Name, separate with space!", Toast.LENGTH_SHORT).show();
                return;
            }
            String[] strarr = text.split(" ");
            String first = strarr[0];
            String last = strarr[1];
            int id = userViewModel.getAllUsers().getValue().size();
            userViewModel.insert(new User(id+1, first, last));
            et.setText("");
        }
    }

    public void onClickItem(View view){
        Intent intent = new Intent(context, SecondActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);

    }

}
