package br.com.api.desafiowebscrapinganalisededados.scraping;

import java.io.*;
import java.nio.file.*;
import java.util.zip.*;

public class ZipFiles {
    private static final String DOWNLOAD_DIR = "C:/Users/Alexandro/Downloads/";
    private static final String ZIP_FILE = DOWNLOAD_DIR + "anexos.zip";

    public static void main(String[] args) {
        try {
            // Criar o arquivo ZIP
            FileOutputStream fos = new FileOutputStream(ZIP_FILE);
            ZipOutputStream zipOut = new ZipOutputStream(fos);

            // Obter todos os PDFs da pasta
            Files.list(Paths.get(DOWNLOAD_DIR))
                    .filter(path -> path.toString().endsWith(".pdf"))
                    .forEach(path -> {
                        try {
                            addFileToZip(path, zipOut);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

            zipOut.close();
            fos.close();
            System.out.println("Compactação concluída: " + ZIP_FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addFileToZip(Path filePath, ZipOutputStream zipOut) throws IOException {
        File file = filePath.toFile();
        try (FileInputStream fis = new FileInputStream(file)) {
            ZipEntry zipEntry = new ZipEntry(file.getName());
            zipOut.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                zipOut.write(buffer, 0, bytesRead);
            }
            zipOut.closeEntry();
            System.out.println("Arquivo adicionado ao ZIP: " + file.getName());
        }
    }
}

