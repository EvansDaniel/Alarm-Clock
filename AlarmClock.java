package com.company;

import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class AlarmClock extends Application {

    private JPanel panel;
    private ComboBox<Integer> hourSet;
    private ComboBox<Integer> minSet;
    private Timer newTimer;
    private Sound alarmSound;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {

        // Handles the time computation
        Clock clock = new Clock();

        window.setTitle("Alarm Clock");

        BorderPane layout = new BorderPane();

        // Hour label
        Label hour = new Label("Hour");

        // Minute Label
        Label min = new Label("Minute");

        // Adds min and hour label to HBox
        HBox minAndHour = new HBox(30);
        minAndHour.getChildren().addAll(min,hour);

        // User can select what hour to wake up at
        minSet = new ComboBox<>();
        minSet.getItems().addAll
        (
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
            20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42,
            43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59
        );
        minSet.setPromptText("Choose Min");

        // User can select what min to wake up at
        hourSet = new ComboBox<>();
        hourSet.getItems().addAll
        (
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
            20, 21, 22, 23
        );
        hourSet.setPromptText("Choose Hour");

        // Adds comboBoxes to the HBox
        HBox hourMinCont = new HBox();
        hourMinCont.setPadding(new Insets(0, 12, 15, 750/2-190));
        hourMinCont.getChildren().addAll(hourSet,minSet);

        layout.setCenter(hourMinCont);

        Button stopAlarm = new Button("Stop Alarm");
        HBox box = new HBox();
        box.getChildren().add(stopAlarm);
        layout.setRight(box);

        Button resetAlarm = new Button("Reset Alarm");
        VBox resetBox = new VBox();
        resetBox.getChildren().add(resetAlarm);
        layout.setLeft(resetBox);
        resetAlarm.setVisible(false);

        // Adds a set Alarm button
        Button setAlarm = new Button("Set Alarm");
        setAlarm.setOnAction(e -> {
            // Make sure the user set a time to wake up at
            if(hourSet.getValue() != null && minSet.getValue() != null) {
                setAlarm.setDisable(true);
                resetAlarm.setVisible(true);
                minSet.setDisable(true);
                hourSet.setDisable(true);
                 // Alarm is accurate within 1 seconds
                // dictates when the alarm will go of
                newTimer = new Timer(1000, h -> {
                    if ((alarmTimeInMin() == clockTimeInMin())) {
                        resetAlarm.setVisible(false);
                        newTimer.stop();
                        alarmSound = null;
                        try {
                            alarmSound = new Sound("C:/NewSounds/Sirens.wav");
                        } catch (IOException
                                | UnsupportedAudioFileException
                                | LineUnavailableException e1) {
                            e1.printStackTrace();
                        }
                        try {
                            if (alarmSound != null) alarmSound.playAudio();
                        } catch (IOException | LineUnavailableException e1) {
                            e1.printStackTrace();
                        }
                        stopAlarm.setVisible(true);
                    }
                });
                newTimer.setRepeats(true);
                newTimer.setInitialDelay(0);
                newTimer.start();
            }
        });

        // Allows you to set the alarm to a different time
        resetAlarm.setOnAction(e -> {
            setAlarm.setDisable(false);
            resetAlarm.setVisible(false);
            hourSet.setDisable(false);
            minSet.setDisable(false);
            // No need to use 'if (newTimer.isRunning())' because resetAlarm button
            // is visible only if setAlarm has been pressed, which sets timer running
            newTimer.stop();
        });
        // Stops the alarm once it is going off
        stopAlarm.setOnAction(e -> {
            // Once the alarm is set it cannot be changed
            // except by the reset alarm button
            hourSet.setDisable(false);
            minSet.setDisable(false);
            setAlarm.setDisable(false);
            stopAlarm.setVisible(false);
            resetAlarm.setVisible(false);
            try { alarmSound.stopAudio();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        stopAlarm.setVisible(false);

        layout.setBottom(setAlarm);
        BorderPane.setAlignment(setAlarm, Pos.BOTTOM_CENTER);

        // panel added to the swingNode
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // clock.getTime returns a JComponent(JLabel)
        panel.add(clock.getTime());
        panel.setVisible(true);

        // adds panel into the HBox
        SwingNode swingNode = new SwingNode();
        setSwingContent(swingNode);

        // Used to add the swingNode (contains a panel) to the scene
        HBox timeBox = new HBox();

        // Centers the date and time
        timeBox.setPadding(new Insets(0, 12, 15, 57));
        timeBox.getChildren().add(swingNode);

        layout.setTop(timeBox);

        Scene scene = new Scene(layout, 750, 500);
        window.setScene(scene);
        window.show();
        // Stops the audio on closing the window
        window.setOnCloseRequest(e -> {
            try {
                if(alarmSound != null)
                    alarmSound.stopAudio();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }
    // Sets the content contained in the swingNode
    private void setSwingContent(final SwingNode swingNode) {
        SwingUtilities.invokeLater(() -> swingNode.setContent(panel));
    }
    // alarmTimeinMin() and clockTimeinMin() are used to convert the alarm time set by user
    // to the minutes passed since 12AM; the same happens in the clockTimeInMin method.
    // When they are equal, the alarm goes off.
    private int alarmTimeInMin() {
       int hours = hourSet.getValue();
       return minSet.getValue() + hours * 60;
    }
    private int clockTimeInMin() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        // if it's after noon
        if(cal.get(Calendar.AM_PM) == Calendar.PM) {
            int clockHour = cal.get(Calendar.HOUR);
            int clockTotalMin = (clockHour + 12)*60 + cal.get(Calendar.MINUTE);
            // 1440 minutes indicates that it is 12AM. Thus, the current time is
            // made up of only minutes
            if(clockTotalMin >= 1440)
                clockTotalMin = cal.get(Calendar.MINUTE);
            return clockTotalMin;
        }
        else {
            // if it's before noon
            int clockHour = cal.get(Calendar.HOUR);
            return clockHour*60+cal.get(Calendar.MINUTE);
        }
    }
}