package com.example.insertioninrealtimefirebasebycw;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class PostAdapter extends FirebaseRecyclerAdapter<Post,PostAdapter.PostViewHolder> {
private Context context;
    public PostAdapter(@NonNull FirebaseRecyclerOptions<Post> options,Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull Post post) {
       holder.name.setText(post.getName());
       holder.age.setText(post.getAge());
       holder.sal.setText(post.getSal());
       holder.delete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               FirebaseDatabase.getInstance().getReference()
                       .child("Post")
                       .child(getRef(position).getKey())
                       .removeValue()
                       .addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {

                           }
                       });
           }
       });
       holder.edit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               DialogPlus dialog = DialogPlus.newDialog(context)
                       .setGravity(Gravity.CENTER)
                       .setMargin(50,0,50,0)
                       .setContentHolder(new ViewHolder(R.layout.content))
                       .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                       .create();
               View  holderView = dialog.getHolderView();
               EditText name = holderView.findViewById(R.id.name);
               EditText age = holderView.findViewById(R.id.age);
               EditText sal = holderView.findViewById(R.id.salary);

               name.setText(post.getName());
               age.setText(post.getAge());
               sal.setText(post.getSal());

               Button update = holderView.findViewById(R.id.update);
               update.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Map<String,Object> map = new HashMap<>();
                       map.put("name",name.getText().toString());
                       map.put("age",age.getText().toString());
                       map.put("sal",sal.getText().toString());
                       FirebaseDatabase.getInstance().getReference()
                               .child("Post")
                               .child(getRef(position).getKey())
                               .updateChildren(map)
                               .addOnCompleteListener(new OnCompleteListener<Void>() {
                                   @Override
                                   public void onComplete(@NonNull Task<Void> task) {
                                      dialog.dismiss();
                                   }
                               });
                   }
               });
               dialog.show();
           }
       });
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post, parent, false);

        return new PostViewHolder(view);
    }

    class PostViewHolder extends RecyclerView.ViewHolder{
      TextView name,age,sal;
      ImageView edit,delete;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            age = itemView.findViewById(R.id.age);
            sal = itemView.findViewById(R.id.sal);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
        }

    }

    }

