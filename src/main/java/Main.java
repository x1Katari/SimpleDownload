import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;

public class Main {
    private static final String folderName = "C:/Users/Liphy/Desktop/images";
    private static final String url = "https://toonily.com/webtoon/beginning-after-end/chapter-77/";
    public static void main(String[] args) throws Exception  {
        ArrayList<String> links = new ArrayList();
        Document document = Jsoup.connect(url).maxBodySize(0).get();
        Elements linksArray = document.getElementsByTag("img");
        for (Element element : linksArray)
            if (element.attr("data-src").contains(".jpg"))
                links.add(element.attr("data-src").trim());
        for (String link : links)
            getImage(link);
    }

    private static void getImage(String src) throws Exception  {
        File theDir = new File(folderName);
        if (!theDir.exists())
            theDir.mkdir();
        URL url = new URL(src);
        String fileName = url.getFile();
        String destName = folderName + fileName.substring(fileName.lastIndexOf("/"));

        try (InputStream is = url.openStream(); OutputStream fos = new FileOutputStream(destName)) {
            byte[] b = new byte[2048];
            int length;
            while ((length = is.read(b)) != -1)
                fos.write(b, 0, length);
        }
    }

}
