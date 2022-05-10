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

        String title = null;

        InputStream response = null;
        try {
            response = new URL(url).openStream();

            Scanner scanner = new Scanner(response);
            String responseBody = scanner.useDelimiter("\\A").next();
            title = (responseBody.substring(responseBody.indexOf("<title>") + 7, responseBody.indexOf("</title>")));
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
