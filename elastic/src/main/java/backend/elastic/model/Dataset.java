package backend.elastic.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

import static backend.elastic.utils.Utils.INDEX;
import static backend.elastic.utils.Utils.TYPE;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Document(indexName = INDEX, type = TYPE, shards = 5)
public class Dataset {
    @Id
    private String id;
    private String name;
    private String author;
    private String organization;
    private String url;
    private String creatorUserId;
    private String licenseTitle;
    private String licenseUrl;
    private String notesRendered;
    @Field(type = FieldType.Nested)
    private List<Resource> resources;

}
