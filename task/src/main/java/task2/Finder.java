package task2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;


public class Finder {

    private final String fileName;

    private String dir = "";

    private final boolean recursive;

    public Finder(boolean recursive, String dir, String fileName) {
        this.recursive = recursive;
        this.dir = dir;
        this.fileName = fileName;
    }

    public Set<String> find() throws IOException {
        List<File> fileList = new ArrayList<>();
        TreeSet<String> result = new TreeSet<>();
        // создаётся упорядоченное можество, потому что в системе, которая используется на гитхабе файлы находятся в обратном порядке, значит нужно их строго упорядочить
        if (dir.equals("")) {dir = System.getProperty("user.dir");}
        search(new File(dir), fileList);
        for(File file : fileList) {
            result.add(file.getCanonicalPath());
        }
        if (result.isEmpty()) {
            System.err.println("Not found");
        }
        return result;
    }

    private void search(File root, List<File> fileList) {
        if (root.isDirectory()){
            File[] files = root.listFiles();
            Pattern p = Pattern.compile(fileName, Pattern.LITERAL + Pattern.CASE_INSENSITIVE);
            // флаги LITERAL - указывает чтобы метасимволы не воспринимиались, CASE_INSENSITIVE - понятно по названию
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        if (recursive) {
                            search(file, fileList);
                        }
                    }
                    else {
                        if (p.matcher(file.getName()).find()) {
                            fileList.add(file);
                        }
                    }
                }
            }
        }
    }
}
