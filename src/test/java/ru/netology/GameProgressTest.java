package ru.netology;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

public class GameProgressTest {

    @Test
    @DisplayName("Can be saved")
    public void testCheckGameProgress() {
        String path = "Games\\savegames\\save1.dat";
        GameProgress progress = new GameProgress(1, 2, 3, 4.0);

        String progressString = progress.toString();

        Assertions.assertNotNull(path);
        Assertions.assertNotNull(progressString);
    }

    @Test
    @DisplayName("File exist")
    public void testIsFileExist() {
        String fileName = "Games\\savegames\\save1.dat";

        File file = new File(fileName);

        Assertions.assertNotNull(file);
    }

    @Test
    @DisplayName("Is save game")
    public void testIsSaveGameFile() {
        String fileName = "Games\\savegames\\save1.dat";

        try (FileInputStream fis = new FileInputStream(fileName); ObjectInputStream ois = new ObjectInputStream(fis)) {

            GameProgress gameProgress = (GameProgress) ois.readObject();

            Assertions.assertEquals(GameProgress.class, gameProgress.getClass());

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIsSaveGameFileHamcrest() {
        String fileName = "Games\\savegames\\save1.dat";

        try (FileInputStream fis = new FileInputStream(fileName); ObjectInputStream ois = new ObjectInputStream(fis)) {

            GameProgress gameProgress = (GameProgress) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
