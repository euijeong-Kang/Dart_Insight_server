package dart.insight.domain.chromeExtension.service;

import dart.insight.domain.chromeExtension.domain.Content;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

@Service
public class ChatBotService {

    public Content getContent(Content requestDTO) {
        requestDTO.setTitle(getTileFromURL(requestDTO.getUrl()));
        return requestDTO;
    }

    private String getTileFromURL(String url) {

        String title = "오늘의 뉴스 트랜드를 확인하세요";

        InputStream response = null;
        try {
            response = new URL(url).openStream();

            Scanner scanner = new Scanner(response);
            String responseBody = scanner.useDelimiter("\\A").next();
            try {title = (responseBody.substring(responseBody.indexOf("<title>") + 7, responseBody.indexOf("</title>")));} catch(Exception exception) {}
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return title.split("-|:|\\|")[0];
    }
}
