package com.example;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.List;

public class Controller {

    @FXML private ImageView imageView;
    @FXML private Button startButton;
    @FXML private Button stopButton;
    @FXML private Button chooseDirButton;

    private ConcreteAggregate slides;
    private List<File> imageFiles;
    private int currentIndex = 0;

    private Timeline timeline;
    private Stage stage;

    @FXML
    public void initialize() {

        /* === ФИКСИРУЕМ РАЗМЕР ВСЕХ ФОТО === */
        imageView.setFitWidth(400);
        imageView.setFitHeight(300);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);

        /* === Получаем Stage === */
        imageView.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.windowProperty().addListener((o, oldW, newW) -> {
                    if (newW instanceof Stage s) {
                        stage = s;
                    }
                });
            }
        });

        /* === Создание агрегата через фабрику === */
        AbstractAggregateFactory factory = new ImageAggregateFactory();
        slides = (ConcreteAggregate) factory.createAggregate("jpg,png");

        imageFiles = slides.getImageFiles();

        if (!imageFiles.isEmpty()) {
            showImageAt(0);
        }

        /* === Бесконечный цикл === */
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(3), e -> nextImage())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void showImageAt(int index) {
        if (imageFiles.isEmpty()) {
            imageView.setImage(null);
            return;
        }

        currentIndex = index;
        File file = imageFiles.get(index);

        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);

        FadeTransition fade = new FadeTransition(Duration.millis(500), imageView);
        fade.setFromValue(0.4);
        fade.setToValue(1.0);
        fade.play();
    }

    @FXML
    private void nextImage() {
        if (imageFiles.isEmpty()) return;
        currentIndex = (currentIndex + 1) % imageFiles.size();
        showImageAt(currentIndex);
    }

    @FXML
    private void previousImage() {
        if (imageFiles.isEmpty()) return;
        currentIndex = (currentIndex - 1 + imageFiles.size()) % imageFiles.size();
        showImageAt(currentIndex);
    }

    @FXML
    private void startSlideshow() {
        timeline.play();
    }

    @FXML
    private void stopSlideshow() {
        timeline.stop();
    }

    @FXML
    private void chooseDirectory() {
        DirectoryChooser chooser = new DirectoryChooser();
        File dir = chooser.showDialog(stage);

        if (dir != null) {
            slides.setDirectory(dir.getAbsolutePath());
            imageFiles = slides.getImageFiles();
            currentIndex = 0;

            if (!imageFiles.isEmpty()) {
                showImageAt(0);
            } else {
                imageView.setImage(null);
            }
        }
    }
}
