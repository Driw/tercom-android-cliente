package br.com.tercom.Boundary.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import br.com.tercom.Application.AppTercom;
import br.com.tercom.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuActivity extends AppCompatActivity {

    @BindView(R.id.cvContato)
    CardView cvContato;

    @BindView(R.id.textView4)
    TextView txtWelcome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        cvContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent messageList = new Intent(MenuActivity.this, MessageList.class);
                startActivity(messageList);
            }
        });
        txtWelcome.setText("Bem vindo, " + AppTercom.USER_STATIC.getCustomerEmployee().getName() + "!");
    }
}
