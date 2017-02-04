package katur1j.cps596a.cmich.edu.chipix;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreen extends AppCompatActivity {
    private Button signIn;
    private TextView newUser;
    private EditText email, pText;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        signIn = (Button) findViewById(R.id.signin);
        newUser = (TextView) findViewById(R.id.newUser);
        email = (EditText) findViewById(R.id.email);
        pText = (EditText) findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("TAG", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("TAG", "onAuthStateChanged:signed_out");
                }
                // [START_EXCLUDE]
                // [END_EXCLUDE]
            }
        };

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailId = email.getText().toString().trim();
                String password = pText.getText().toString().trim();
                if (emailId.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginScreen.this, "Fields are empty.", Toast.LENGTH_SHORT).show();
                } else {
                    signIn(emailId,password);
                }
            }
        });

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginScreen.this, NewUser.class);
                startActivity(intent);

            }
        });
    }
    private void signIn(final String email, String password) {

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TAG", "signInWithEmail:onComplete:" + task.isSuccessful());
                        String jack = email;
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "signInWithEmail:failed", task.getException());
                            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Intent intent = new Intent(LoginScreen.this, NaviDrawer.class);
                            intent.putExtra("email", jack);
                            startActivity(intent);
                            finish();
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Failed2", Toast.LENGTH_LONG).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }
}
