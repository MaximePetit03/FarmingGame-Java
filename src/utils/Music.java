package utils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Music {
    private MediaPlayer mediaPlayer;
    private final Map<String, Media> cache = new HashMap<>();

    public void play(String fileName, double volume) {
        stop();

        try {
            Media media = cache.get(fileName);

            if (media == null) {
                URL resource = getClass().getResource("/soundtracks/" + fileName);
                if (resource == null) {
                    System.err.println("Fichier audio introuvable : " + fileName);
                    return;
                }
                media = new Media(resource.toExternalForm());
                cache.put(fileName, media);
            }

            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(volume);

            mediaPlayer.setOnReady(() -> mediaPlayer.play());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
    }
}