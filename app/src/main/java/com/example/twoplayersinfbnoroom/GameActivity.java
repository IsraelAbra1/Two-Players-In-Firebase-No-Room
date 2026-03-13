package com.example.twoplayersinfbnoroom;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private String player;
    private ArrayList<Card> player1Cards;
    private ArrayList<Card> player2Cards;
    private FbModule fbModule;
    private GameTurn currentGameTurn;

    private TextView tvMyCards;
    private TextView tvOtherCards;

    private final String[] colors = {"Red", "Blue", "Green", "Yellow"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fbModule = new FbModule();
        player = getIntent().getStringExtra("player");

        TextView tvPlayerName = findViewById(R.id.tvPlayerName);
        tvMyCards = findViewById(R.id.tvMyCards);
        tvOtherCards = findViewById(R.id.tvOtherCards);

        if (player != null) {
            tvPlayerName.setText(player);
        }

        if (player != null && player.equals("player1")) {
            player1Cards = generateRandomCards(5);
            player2Cards = new ArrayList<>();
        } else if (player != null && player.equals("player2")) {
            player2Cards = generateRandomCards(5);
            player1Cards = new ArrayList<>();
        } else {
            finish();
        }

        updateUI();

        fbModule.listenToGameTurn(new FbModule.GameTurnListener() {
            @Override
            public void onGameTurnChanged(GameTurn gameTurn) {
                currentGameTurn = gameTurn;
                if ("player1".equals(player)) {
                    if (gameTurn.getPlayer2() != null && !gameTurn.getPlayer2().isEmpty()) {
                        player2Cards = gameTurn.getPlayer2();
                    }
                } else if ("player2".equals(player)) {
                    if (gameTurn.getPlayer1() != null && !gameTurn.getPlayer1().isEmpty()) {
                        player1Cards = gameTurn.getPlayer1();
                    }
                }
                updateUI();
            }
        });

        Button btnSendCards = findViewById(R.id.btnSendCards);
        btnSendCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameTurn turnToSend = new GameTurn(player1Cards, player2Cards);
                fbModule.writeGameTurn(turnToSend);
            }
        });
    }

    private void updateUI() {
        if ("player1".equals(player)) {
            tvMyCards.setText(formatCards(player1Cards));
            tvOtherCards.setText(formatCards(player2Cards));
        } else {
            tvMyCards.setText(formatCards(player2Cards));
            tvOtherCards.setText(formatCards(player1Cards));
        }
    }

    private String formatCards(ArrayList<Card> cards) {
        if (cards == null || cards.isEmpty()) return "None";
        StringBuilder sb = new StringBuilder();
        for (Card card : cards) {
            sb.append(card.getColor()).append(" ").append(card.getNum()).append("\n");
        }
        return sb.toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fbModule != null) {
            fbModule.deleteGame();
        }
    }

    private ArrayList<Card> generateRandomCards(int count) {
        ArrayList<Card> randomCards = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            int num = random.nextInt(13) + 1;
            String color = colors[random.nextInt(colors.length)];
            randomCards.add(new Card(num, color));
        }
        return randomCards;
    }
}