package app.com.example.chong.go;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button rules;
    private Button startGame;
    private Button guide;
    private Button AI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Button rules
        rules = (Button) findViewById(R.id.rules);
        rules.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent rulesIntent = new Intent(MainActivity.this, Rules.class);
                startActivity(rulesIntent);
            }
        });

        //Button startGame
        startGame = (Button) findViewById(R.id.startGame);
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent starGameIntent = new Intent(MainActivity.this, StartGame.class);
                startActivity(starGameIntent);
            }
        });

        //Button guide
        guide = (Button) findViewById(R.id.guide);
        guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent guideIntent = new Intent(MainActivity.this, Guide.class);
                startActivity(guideIntent);
            }
        });

        //Button play with AI
        AI = (Button) findViewById(R.id.AI);
        AI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AIIntent = new Intent(MainActivity.this, AI.class);
                startActivity(AIIntent);
            }
        });


    }
}
