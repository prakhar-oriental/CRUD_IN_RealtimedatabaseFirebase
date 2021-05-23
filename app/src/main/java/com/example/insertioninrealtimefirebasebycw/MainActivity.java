package com.example.insertioninrealtimefirebasebycw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
EditText name,age,sal;
TextView textView;
Button save;
Button read;
DatabaseReference post =  FirebaseDatabase.getInstance().getReference().child("Post");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.edittext1);
        age = findViewById(R.id.edittext2);
        sal = findViewById(R.id.edittext3);
        save = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        read = findViewById(R.id.button2);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,Object> map = new HashMap<>();
                map.put("name",name.getText().toString());
                map.put("age",age.getText().toString());
                map.put("sal",sal.getText().toString());
                post.push().setValue(map)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task!=null)
                                {
                                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();

                                }
                                                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "failure"+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                read();
            }
        });
    }

    private void read() {
        post.child("-M_oyARIoKCWnUnX6Gzl").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String post = "Name:"+snapshot.child("name").getValue(String.class)+"\n"+ "Age:"
                        +snapshot.child("age").getValue(String.class)+"\n"+ "Salary:"+snapshot.child("sal").getValue(String.class);
                textView.setText(post);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}