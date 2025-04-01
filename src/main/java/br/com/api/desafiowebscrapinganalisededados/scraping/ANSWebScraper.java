package br.com.api.desafiowebscrapinganalisededados.scraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.nio.file.*;
import java.net.URL;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.Channels;

public class ANSWebScraper {
    private static final String URL_SITE = "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos";
    private static final String DOWNLOAD_DIR = "C:/Users/Alexandro/Downloads/";

    public static void main(String[] args) {
        try {
            // Criar diretório se não existir
            Files.createDirectories(Paths.get(DOWNLOAD_DIR));

            // Conectar ao site e buscar os links dos PDFs
            Document doc = Jsoup.connect(URL_SITE).get();
            Elements links = doc.select("a[href$=.pdf]");

            for (Element link : links) {
                String pdfUrl = link.absUrl("href");
                String fileName = pdfUrl.substring(pdfUrl.lastIndexOf("/") + 1);

                // Baixar os PDFs usando NIO
                downloadFile(pdfUrl, DOWNLOAD_DIR + fileName);
                System.out.println("Download concluído: " + fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void downloadFile(String fileURL, String savePath) throws Exception {
        URL url = new URL(fileURL);
        try (ReadableByteChannel rbc = Channels.newChannel(url.openStream())) {
            Files.copy(Channels.newInputStream(rbc), Paths.get(savePath), StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
