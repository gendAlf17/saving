import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
        GameProgress gameProgress1 = new GameProgress(95, 5, 10, 7);
        GameProgress gameProgress2 = new GameProgress(83, 5, 11, 13);
        GameProgress gameProgress3 = new GameProgress(55, 1, 15, 22);
        List<String> listGameProgress = Arrays.asList(
                "gameProgress1.dat",
                "gameProgress2.dat",
                "gameProgress3.dat"
        );

        saveGame("gameProgress1.dat", gameProgress1);
        saveGame("gameProgress2.dat", gameProgress2);
        saveGame("gameProgress3.dat", gameProgress3);
        zipFiles("zip_output.zip", listGameProgress);

        for (String gameProgress : listGameProgress) {
            new File(gameProgress).delete();
        }
    }

    static void saveGame(String pathFile, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(pathFile);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    static void zipFiles(String pathZipFile, List<String> list) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(pathZipFile))) {
            for (String s : list) {
                try (FileInputStream fis = new FileInputStream(s)) {
                    ZipEntry entry = new ZipEntry("packed_" + s);
                    zout.putNextEntry(entry);
                    byte[] bytes = new byte[fis.available()];
                    fis.read(bytes);
                    zout.write(bytes);
                    zout.closeEntry();
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}


