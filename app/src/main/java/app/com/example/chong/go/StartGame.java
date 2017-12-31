package app.com.example.chong.go;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import app.com.example.chong.go.GameBuild.DrawGame;
import app.com.example.chong.go.GameBuild.Game;

/**
 * This class implements the main game.
 */

public class StartGame extends AppCompatActivity {
    /* Create new game object */
    public static Game game = new Game();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawGame(this));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        game = new Game();
        return;
    }
}
