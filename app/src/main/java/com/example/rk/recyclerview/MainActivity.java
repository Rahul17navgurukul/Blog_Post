package com.example.rk.recyclerview;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
   private RecyclerView itemList;
   private Button add;

   private DatabaseReference databaseReference;

   FirebaseAuth mAuth;
   FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         mAuth = FirebaseAuth.getInstance();
         mAuthListener = new FirebaseAuth.AuthStateListener() {
             @Override
             public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                 if (firebaseAuth .getCurrentUser()==null){

                     Intent RegIntent = new Intent(MainActivity.this,Reg_Activity.class);
                     RegIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                     startActivity(RegIntent);
                 }

             }
         };

        itemList=findViewById(R.id.list_item);
        itemList.setLayoutManager(new LinearLayoutManager(this));

        add=findViewById(R.id.add);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Blog");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Add_Item.class);
                startActivity(intent);
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.logout){

            logOut();
        }


        return super.onOptionsItemSelected(item);
    }

    private void logOut() {

        mAuth.signOut();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);

        FirebaseRecyclerAdapter<Adapter, AdapterViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Adapter, AdapterViewHolder>(

                Adapter.class,
                R.layout.card_item,
                AdapterViewHolder.class,
                databaseReference
        ) {
            @Override
            protected void populateViewHolder(AdapterViewHolder viewHolder, Adapter model, int position) {

                viewHolder.setTitle(model.title);
                viewHolder.setDesc(model.description);
                viewHolder.setimage(model.image);

            }
        };

        itemList.setAdapter(firebaseRecyclerAdapter);

    }

    public static class AdapterViewHolder extends RecyclerView.ViewHolder{

        View mview;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            mview = itemView;
        }

        public void setTitle(String title){

            TextView card_title = mview.findViewById(R.id.title);
            card_title.setText(title);
        }

        public void setDesc(String description){

            TextView card_description = mview.findViewById(R.id.description);
            card_description.setText(description);
        }


        public void setimage(String image) {
            ImageView card_image = mview.findViewById(R.id.image);
            Picasso.get().load(image).into(card_image);


        }
    }

}
