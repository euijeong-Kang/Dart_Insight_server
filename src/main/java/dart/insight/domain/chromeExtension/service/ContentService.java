package dart.insight.domain.chromeExtension.service;

import com.microsoft.graph.models.*;
import com.microsoft.graph.requests.GraphServiceClient;
import dart.insight.domain.chromeExtension.domain.Content;
import dart.insight.domain.chromeExtension.repository.ContentRepository;
import dart.insight.global.Emoji;
import dart.insight.global.azure.AuthHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

@Service
//@Transactional
public class ContentService {



    @Value("${TEAM_ID}")
    private String[] teamId;

    @Value("${CHANNEL_ID}")
    private String[] channelId;

    @Value("${azure.activedirectory.client-id}")
    private String[] clientIdArray;

    @Value("${ID}")
    private String[] userNameArray;

    @Value("${PASSWORD}")
    private String[] userPasswordArray;

    @Autowired
    ContentRepository contentRepository;

    @Autowired
    AuthHelper authHelper;

    private GraphServiceClient graphClient = null;

    // DB
//    public Long create(Content content) {
//        contentRepository.save(content);
//        return content.getId();
//    }

    private void setGraphClient(String clientId, String userName, String userPassword) {
        this.graphClient = authHelper.getGraphClient(clientId, userName, userPassword);
    }

    public void postNews(Content content)
    {
        String emoji = Emoji.getEmoji();

        final String[] companies = {"Dart", "Hiper", "OPMS"};
        final String title = content.getTitle();
        final String url = content.getUrl();

        String tags = "";

        if (content.getKeywords() != null) {
            String[] list = content.getKeywords().split(",");

            for (int i= 0; i < list.length; i++) {
                if(list[i].length() < 1) {
                    break;
                }
                tags += "#"+list[i]+"  ";
            }
        }

        for (int i = 0; i < 3; i++) {
            // setting graph client
            this.setGraphClient(clientIdArray[i], userNameArray[i], userPasswordArray[i]);

            try {
                ChatMessage chatMessage = new ChatMessage();
                ItemBody body = new ItemBody();
                body.contentType = BodyType.HTML;
                body.content = String.format("<div>\n" +
                        "<p style=\"font-weight: bold; font-size: 1.15em\">\uD83D\uDD14<span style=\"color: #42b983\">%s Insight</span> Now\uD83D\uDD14</p><h4>%s<a href=\"%s\" style=\"color: #2f4f4f; text-decoration: none\">%s</a></h4>\n" +
                        "<p style=\"color: #87cefa\">%s</p>\n" +
                        "</div>", companies[i],emoji,url,title,tags);
                chatMessage.body = body;

                graphClient.teams(teamId[i]).channels(channelId[i]).messages()
                        .buildRequest()
                        .post(chatMessage);
            } catch(Exception ex) {}
        }

    }
}
