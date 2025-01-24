package com.example.secondassignment;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<DataModel> dataSet;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private PlayerAdapter adapter;
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mediaPlayer = MediaPlayer.create(this, R.raw.backgroundsong);

        mediaPlayer.start();
        setContentView(R.layout.activity_main);

        dataSet = new ArrayList<>();
        recyclerView = findViewById(R.id.resView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        for (int i = 0; i < MyData.namePlayerArray.length; i++) {
            dataSet.add(new DataModel(
                    MyData.namePlayerArray[i],
                    MyData.descriptionArray[i],
                    MyData.photosArray[i],
                    MyData.id_[i]
            ));
        }

        adapter = new PlayerAdapter(dataSet);
        recyclerView.setAdapter(adapter);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    public void musicfunc(View view)
    {
        Button playButton = (Button) view;

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        // Pause the audio
                        mediaPlayer.pause();
                        playButton.setBackgroundResource(R.drawable.ic_headset_off);
                    } else {
                        // Start playing the audio
                        mediaPlayer.start();
                        playButton.setBackgroundResource(R.drawable.ic_headset_on);
                    }
                }
            }
        });
    }
    public void funcPopUp(View view) {
        // Get the player's data from tags
        String playerName = (String) view.getTag(R.id.NameOfThePlayer);
        String playerDescription = (String) view.getTag(R.id.DesriptionPlayer);
        int playerImage = (int) view.getTag(R.id.imageView); // Assuming image is stored as a resource ID

        // Create and show the Dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.player_card_layout);
        dialog.setCancelable(true);

        // Find components within the Dialog
        TextView title = dialog.findViewById(R.id.title);
        TextView message = dialog.findViewById(R.id.message);
        ImageView imageView = dialog.findViewById(R.id.imageViewPop); // Find ImageView
        Button closeBtn = dialog.findViewById(R.id.btn_close);

        // Set the player's name and description
        title.setText(playerName);
        message.setText(playerDescription);
        imageView.setImageResource(playerImage); // Set the player's image dynamically

        // Handle click on the close button
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // Close the dialog
            }
        });

        // Show the dialog
        dialog.show();
    }
    public void searchFunc(View view) {
        TextView nameTextView = findViewById(R.id.editTextNamePlayer);

        String searchText = nameTextView.getText().toString();

        // Clear previous results
        dataSet.clear();

        if (!searchText.isEmpty()) {
            for (int i = 0; i < MyData.namePlayerArray.length; i++) {
                if (MyData.namePlayerArray[i].contains(searchText) ||
                        MyData.descriptionArray[i].contains(searchText)) {
                    dataSet.add(new DataModel(
                            MyData.namePlayerArray[i],
                            MyData.descriptionArray[i],
                            MyData.photosArray[i],
                            MyData.id_[i]
                    ));
                }
            }
            if (adapter == null) {
                adapter = new PlayerAdapter(dataSet);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
            if (dataSet.isEmpty()) {
                Toast.makeText(view.getContext(), "לא נמצאו תוצאות לחיפוש", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(view.getContext(), "אנא הזן טקסט לחיפוש", Toast.LENGTH_SHORT).show();
        }
    }
}