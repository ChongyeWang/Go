package app.com.example.chong.go;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import app.com.example.chong.go.TutorialGuide.Guide1;
import app.com.example.chong.go.TutorialGuide.Guide2;
import app.com.example.chong.go.TutorialGuide.Guide3;
import app.com.example.chong.go.TutorialGuide.Guide4;
import app.com.example.chong.go.TutorialGuide.Guide5;
import app.com.example.chong.go.TutorialGuide.Guide6;

public class Guide extends AppCompatActivity {

    private Button guide1_button;
    private Button guide2_button;
    private Button guide3_button;
    private Button guide4_button;
    private Button guide5_button;
    private Button guide6_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        //Button guide1
        guide1_button = (Button) findViewById(R.id.guide1);
        guide1_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent guide1Intent = new Intent(Guide.this, Guide1.class);
                startActivity(guide1Intent);
            }
        });

        //Button guide2
        guide2_button = (Button) findViewById(R.id.guide2);
        guide2_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent guide2Intent = new Intent(Guide.this, Guide2.class);
                startActivity(guide2Intent);
            }
        });

        //Button guide3
        guide3_button = (Button) findViewById(R.id.guide3);
        guide3_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent guide3Intent = new Intent(Guide.this, Guide3.class);
                startActivity(guide3Intent);
            }
        });

        //Button guide4
        guide4_button = (Button) findViewById(R.id.guide4);
        guide4_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent guide4Intent = new Intent(Guide.this, Guide4.class);
                startActivity(guide4Intent);
            }
        });

        //Button guide5
        guide5_button = (Button) findViewById(R.id.guide5);
        guide5_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent guide5Intent = new Intent(Guide.this, Guide5.class);
                startActivity(guide5Intent);
            }
        });

        //Button guide6
        guide6_button = (Button) findViewById(R.id.guide6);
        guide6_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent guide6Intent = new Intent(Guide.this, Guide6.class);
                startActivity(guide6Intent);
            }
        });

    }

}
