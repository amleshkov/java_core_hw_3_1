import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    //      mkdir /home/anonymous/Games
    private static String BASEDIR = "/home/anonymous/Games/";
    private static boolean FsObjectCreate(String name, FsObjectType type) {
        boolean result;
        File fsObject = new File(name);

        if (type == FsObjectType.DIRECTORY) {
            result = fsObject.mkdir();
        } else {
            try {
                result = fsObject.createNewFile();
            } catch (IOException e) {
                result = false;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        ArrayList<LogEntry> log = new ArrayList<>();
        String[] topLevel = {"src", "res", "savegames", "temp"};
        Arrays.stream(topLevel).map(f -> BASEDIR + f).forEach(f -> log.add(
                new LogEntry(f, FsObjectCreate(f, FsObjectType.DIRECTORY))));
        String[] srcLevel = {"main", "test"};
        Arrays.stream(srcLevel).map(f -> BASEDIR + "src/" + f).forEach(f -> log.add(
                new LogEntry(f, FsObjectCreate(f, FsObjectType.DIRECTORY))));
        String[] mainLevel = {"Main.java, Utils.java"};
        Arrays.stream(mainLevel).map(f -> BASEDIR + "src/main/" + f).forEach(f -> log.add(
                new LogEntry(f, FsObjectCreate(f, FsObjectType.FILE))));
        String[] resLevel = {"drawables", "vectors", "icons"};
        Arrays.stream(resLevel).map(f -> BASEDIR + "res/" + f).forEach(f -> log.add(
                new LogEntry(f, FsObjectCreate(f, FsObjectType.DIRECTORY))));
        String tempPath = BASEDIR + "temp/temp.txt";

        if (FsObjectCreate(tempPath, FsObjectType.FILE)) {
            log.add(new LogEntry(tempPath, true));
            StringBuilder sb = new StringBuilder();
            log.forEach(le -> sb.append(le.toString() + '\n'));
            String logText = sb.toString();
            try (FileWriter fw = new FileWriter(tempPath, true)) {
                fw.write(logText);
            } catch (IOException e) {
                System.out.println("Невозможно записать в файл журнала");
                e.getStackTrace();
            }
        } else {
            System.out.println("Не удалось создать файл журнала");
            System.out.println("Результат установки:");
            log.forEach(System.out::println);
        }
    }
}