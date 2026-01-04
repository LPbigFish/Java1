package lab.score;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ScoreRepository {
    public static void save(List<ScoreData> scoreDataList) throws IOException {
        File saveFile = new File("saves.csv");
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveFile), StandardCharsets.UTF_8))) {
            for (ScoreData scoreData : scoreDataList) {
                bw.write(scoreData.toString());
                bw.newLine();
            }
        } catch (Exception e) {
            if (e.getClass().equals(IOException.class)) {
                throw e;
            } else {
                System.err.println("Unknown Error: " + e.getMessage());
            }
        }
    }

    public static List<ScoreData> load() throws IOException {
        List<ScoreData> scoreDataList = new ArrayList<>();
        File saveFile = new File("saves.csv");
        if (!saveFile.exists()) {
            return scoreDataList;
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(saveFile), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                scoreDataList.add(new ScoreData(data[0], Integer.parseInt(data[1])));
            }
        }  catch (Exception e) {
            if (e.getClass().equals(IOException.class)) {
                throw e;
            } else if (e.getClass().equals(ParseException.class)) {
                System.err.println("Save file is corrupted: " + e.getMessage());
            } else {
                System.err.println("Unknown Error: " + e.getMessage());
            }
        }
        return scoreDataList;
    }

    public static void deleteEverything() throws IOException {
        File saveFile = new File("saves.csv");
        if (!saveFile.exists()) {
            return;
        }
        boolean result = saveFile.delete();
        if (!result) {
            throw new IOException("Failed to delete saves.csv");
        }
    }
}
