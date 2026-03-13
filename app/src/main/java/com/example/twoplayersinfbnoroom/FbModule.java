package com.example.twoplayersinfbnoroom;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FbModule {

    private DatabaseReference reference;

    public interface GameTurnListener {
        void onGameTurnChanged(GameTurn gameTurn);
    }

    public FbModule() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        reference = database.getReference("game");
    }


    public void listenToGameTurn(final GameTurnListener listener)
    {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GameTurn gameTurn = dataSnapshot.getValue(GameTurn.class);
                if (gameTurn != null) {
                    listener.onGameTurnChanged(gameTurn);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    public void writeGameTurn(GameTurn gameTurn) {
        reference.setValue(gameTurn);
    }

    public void deleteGame() {
        reference.removeValue();
    }
}