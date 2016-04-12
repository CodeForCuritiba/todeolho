package br.com.todeolho.todeolho;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

/**
 * Created by gustavomagalhaes on 4/10/16.
 */
public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    // Clicks dos botões
    public void logarFacebook(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void cadastrarNovoUsuario(View view){
        Toast.makeText(this, "cadastrarNovoUsuario", Toast.LENGTH_SHORT).show();
    }

    public void ajudarUsuario(View view){
        Toast.makeText(this, "ajudarUsuario", Toast.LENGTH_SHORT).show();
    }
}
