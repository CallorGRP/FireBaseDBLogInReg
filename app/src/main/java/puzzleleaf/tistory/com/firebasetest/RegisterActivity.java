package puzzleleaf.tistory.com.firebasetest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.util.Iterator;

public class RegisterActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private EditText editEmail;
    private ValueEventListener checkRegister = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();
            while (child.hasNext()) {
                if (editEmail.getText().toString().equals(child.next().getKey())) {
                    Toast.makeText(getApplicationContext(), "존재하는 아이디 입니다.", Toast.LENGTH_LONG).show();
                    databaseReference.removeEventListener(this);
                    return;
                }
            }
            makeNewId();
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    };
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        databaseReference  = FirebaseDatabase.getInstance().getReference("users");

        editEmail = (EditText)findViewById(R.id.email);

        Button check = (Button)findViewById(R.id.submit);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addListenerForSingleValueEvent(checkRegister);
            }
        });
    }
    void makeNewId()
    {
        Date date = new Date(System.currentTimeMillis());
        databaseReference.child(editEmail.getText().toString()).child("가입일").setValue(date.toString());
        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
    }
}
