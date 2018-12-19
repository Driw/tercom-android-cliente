package br.com.tercom.Boundary.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import br.com.tercom.R;
import butterknife.BindView;
import butterknife.OnClick;

public class MenuActivity extends AppCompatActivity {

    @BindView(R.id.cvContato)
    CardView cvContato;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        cvContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent messageList = new Intent(MenuActivity.this, MessageList.class);
                startActivity(messageList);
            }
        });
    }
}
