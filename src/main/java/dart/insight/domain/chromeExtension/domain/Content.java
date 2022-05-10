package dart.insight.domain.chromeExtension.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Content {
    @Id @GeneratedValue
    private Long id;

    private String title;

    private String url;

    private String keywords;

    private String author;

    private Date createdDate;

//    @Builder
//    public Content(String title, String url, String description) {
//        this.title = title;
//        this.url = url;
//        this.description = description;
//    }
}
