package com.joao.manager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileManager {
    private static String FILE_NAME = "scores.txt";

    private static String readScoreFile() throws IOException {
        Path path = Paths.get(FileManager.FILE_NAME);

        if(!Files.exists(path)) {
            Files.createFile(path);
        }

        return Files.readString(path);
    }

    public static Map<String, Integer> getSavedScores() {
        Map<String, Integer> scores = new HashMap<>();

        try {
            List<String> fileContent = List.of(FileManager.readScoreFile().split("\n"));
            
            fileContent.forEach(line -> {
                String[] splitedLine = line.split("=");
                int value = -1;

                try {
                    value = Integer.parseInt(splitedLine[1]);
                } catch(NumberFormatException nfe) {
                    value = -1;
                }

                scores.put(splitedLine[0], value);
            });
        } catch (IOException ioe) {
            return scores;
        }

        return scores;
    }

    public static boolean addScore(String name, int score) {
        Path path = Paths.get(FILE_NAME);

        try {
            if(!Files.exists(path)) {
                Files.createFile(path);
            }

            String content = FileManager.readScoreFile();
            content += String.format("\n%s=%d",name, score);
            Files.writeString(path, content);
            return true;
        } catch(IOException ie) {
            return false;
        }
    }

}
