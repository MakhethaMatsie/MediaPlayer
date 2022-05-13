package com.example.assignment2;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class HelloController {
    @FXML
    private MediaView mediaView;
    @FXML
    private MediaPlayer player;
    @FXML
    private Slider timer;
    @FXML
    private Slider sound;

    @FXML
    public void initialize() {
        String video = getClass().getResource("See you again.mp4").toExternalForm();
        Media media = new Media(video);
        player = new MediaPlayer(media);

        player.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                timer.setValue(newValue.toSeconds());
            }
        });

        timer.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                player.seek(Duration.seconds(timer.getValue()));
            }
        });

        player.setOnReady(new Runnable() {
            @Override
            public void run() {
                Duration total = media.getDuration();
                timer.setMax(total.toSeconds());
            }
        });

        sound.setValue(player.getVolume() * 100);
        sound.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                player.setVolume(sound.getValue()/100);
            }
        });

        mediaView.setMediaPlayer(player);
    }

    @FXML
    void playVideo(MouseEvent event) {player.play();}

    @FXML
    void stopVideo(MouseEvent event) {
        player.stop();
    }

    @FXML
    void pauseVideo(MouseEvent event) {
        player.pause();
    }
}

