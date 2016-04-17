package br.com.todeolho.todeolho;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import br.com.todeolho.todeolho.model.Questionario;

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
