package ru.netology;

import java.io.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {

    final static String HOME_DIR = "Games\\";
    static StringBuilder stringBuilder = new StringBuilder();

    public static void main(String[] args) {
        File src = createDir(HOME_DIR, "src");
        File res = createDir(HOME_DIR, "res");
        File savegames = createDir(HOME_DIR, "savegames");
        File temp = createDir(HOME_DIR, "temp");

        File main = createDir(src.getPath(), "main");
        File test = createDir(src.getPath(), "test");

        File mainF = createFile(main.getPath(), "Main.java");
        File utilsF = createFile(main.getPath(), "Utils.java");

        File drawables = createDir(res.getPath(), "drawables");
        File vectors = createDir(res.getPath(), "vectors");
        File icons = createDir(res.getPath(), "icons");

        File tempF = createFile(temp.getPath(), "temp.txt");

        try (FileWriter fileWriter = new FileWriter(tempF);) {
            fileWriter.write(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        GameProgress progress1 = new GameProgress(1, 2, 3, 4.0);
        GameProgress progress2 = new GameProgress(5, 6, 7, 8.0);
        GameProgress progress3 = new GameProgress(9, 10, 11, 12.0);

        saveGame("Games\\savegames\\save1.dat", progress1);
        saveGame("Games\\savegames\\save2.dat", progress2);
        saveGame("Games\\savegames\\save3.dat", progress3);

        /*zipFiles("Games\\savegames\\zip1.zip", "Games\\savegames\\save1.dat");
        zipFiles("Games\\savegames\\zip2.zip", "Games\\savegames\\save2.dat");
        zipFiles("Games\\savegames\\zip3.zip", "Games\\savegames\\save3.dat");

        openZip("Games\\savegames\\zip1.zip", "Games\\");
        openZip("Games\\savegames\\zip2.zip", "Games\\");
        openZip("Games\\savegames\\zip3.zip", "Games\\");

        System.out.println(openProgress("Games\\save1.dat"));
        System.out.println(openProgress("Games\\save2.dat"));
        System.out.println(openProgress("Games\\save3.dat"));*/

    }

    public static File createDir(String home, String path) {
        Timestamp now = new Timestamp(new Date().getTime());
        File file = new File(home + "\\" + path);
        boolean mk = file.mkdir();
        if (mk) {
            stringBuilder.append(now).append(" Create dir: ").append(file.getAbsolutePath()).append("\n");
        }

        return file;
    }

    public static File createFile(String path, String fileName) {
        Timestamp now = new Timestamp(new Date().getTime());
        File file = new File(path, fileName);
        try {
            boolean mk = file.createNewFile();
            if (mk) {
                stringBuilder.append(now).append(" Create file: ").append(file.getAbsolutePath()).append("\n");
            }
        } catch (IOException e) {
            stringBuilder.append(now).append(" File create file: ").append(file.getAbsolutePath()).append(" ").append(e.getMessage()).append("\n");
        }
        return file;
    }

    public static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream ous = new ObjectOutputStream(fos)) {
            ous.writeObject(gameProgress);
            ous.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameProgress openProgress(String path) {
        GameProgress gameProgress = null;
        try (FileInputStream fis = new FileInputStream(path); ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return gameProgress;
    }

    public static void zipFiles(String zipName, String savePath) {
        try (FileOutputStream fos = new FileOutputStream(zipName);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            FileInputStream fis = new FileInputStream(savePath);
            ZipEntry zipEntry = new ZipEntry(savePath);
            zos.putNextEntry(zipEntry);

            byte[] bytes = fis.readAllBytes();

            zos.write(bytes);
            zos.closeEntry();
            fis.close();

            File file = new File(savePath);
            if (file.delete()) {
                System.out.println("File " + file.getName() + "was delete");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openZip(String zipName, String unzipPath) {
        try (FileInputStream fis = new FileInputStream(zipName);
             ZipInputStream zis = new ZipInputStream(fis)) {
            String fileName;
            ZipEntry zipEntry;

            while ((zipEntry = zis.getNextEntry()) != null) {
                fileName = zipEntry.getName();

                FileOutputStream fos = new FileOutputStream(unzipPath + new File(fileName).getName());
                for (int i = zis.read(); i != -1; i = zis.read()) {
                    fos.write(i);
                }
                fos.flush();
                zis.closeEntry();
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
