package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Загружаем ResourceBundle на основе системного языка
        ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
        loader.setResources(bundle);

        // -Duser.language=en -Duser.country=US
        primaryStage.setScene(new Scene(loader.load(), 900, 700));
        primaryStage.setTitle(bundle.getString("app.title")); // Заголовок окна из ресурсов
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}