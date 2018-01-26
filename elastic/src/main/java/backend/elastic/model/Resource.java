package backend.elastic.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Resource {

    @Id
    private String id;
    private String format;
    private String UrlType;
    private String Owner;
    private String State;

}
