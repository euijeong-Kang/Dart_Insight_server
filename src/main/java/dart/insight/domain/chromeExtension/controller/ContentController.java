package dart.insight.domain.chromeExtension.controller;

import dart.insight.domain.chromeExtension.domain.Content;
import dart.insight.domain.chromeExtension.service.ChatBotService;
import dart.insight.domain.chromeExtension.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class ContentController {

    @Autowired
    ContentService contentService;

    @Autowired
    ChatBotService chatBotService;

    @PostMapping("api/v1/post")
    public void postNewsContent(@RequestBody Content requestDto) {

        requestDto.setCreatedDate(Date.valueOf(LocalDate.now()));

        contentService.postNews(requestDto);
    }

    @PostMapping("api/v1/content")
    public void getUrlFromBot(@RequestBody Content requestDto) {
        Content content = chatBotService.getContent(requestDto);
        content.setCreatedDate(Date.valueOf(LocalDate.now()));
        contentService.postNews(content);
    }
}
