package backend.elastic.controller;

import backend.elastic.configuration.ElasticsearchConfiguration;
import backend.elastic.model.Dataset;
import backend.elastic.model.Resource;
import backend.elastic.repository.DatasetsRepository;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/rest/search")
public class SearchResource {

    @Autowired
    DatasetsRepository datasetsRepository;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @GetMapping(value = "/name/{text}")
    public Dataset searchName(@PathVariable final String text)
    {
        return datasetsRepository.findByName(text).get(0);
    }

    @GetMapping(value = "/id/{text}")
    public Optional<Dataset> searchbyID(@PathVariable final String text)
    {
        return datasetsRepository.findById(text);
    }

    @GetMapping(value = "/dataset/{datasetID}/resource/{resourceID}")
    public Resource searchResourcebyIDk(@PathVariable final String datasetID,
                                                 @PathVariable final String resourceID)
    {
        Resource resource_result = new Resource();
        Dataset dataset = datasetsRepository.findById(datasetID).get();
        for (Resource resource :dataset.getResources()){
            System.out.println("resource.getId()="+resource.getId());
            if (resource.getId().equals(resourceID)) {
                resource_result = resource;
                break;
            }
        }
        return resource_result;
    }

    @GetMapping(value = "/all")
    public List<Dataset> searchAll() {
        List<Dataset> usersList = new ArrayList<>();
        Iterable<Dataset> users = datasetsRepository.findAll();
        users.forEach(usersList::add);
        return usersList;
    }

    @PreDestroy
    @GetMapping(value = "/delete")
    public boolean delete() {
        return elasticsearchTemplate.deleteIndex(Dataset.class);
    }

    @GetMapping("/view/{id}")
    public Map<String, Object> view(@PathVariable final String id) throws IOException {
        GetResponse getResponse = ElasticsearchConfiguration.client().prepareGet("name", "id", id).get();
        System.out.println(getResponse.getSource());


        return getResponse.getSource();
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable final String id) throws IOException {

        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("employee")
                .type("id")
                .id(id)
                .doc(jsonBuilder()
                        .startObject()
                        .field("gender", "male")
                        .endObject());
        try {
            UpdateResponse updateResponse = ElasticsearchConfiguration.client().update(updateRequest).get();
            System.out.println(updateResponse.status());
            return updateResponse.status().toString();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e);
        }
        return "Exception";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable final String id) throws IOException {

        DeleteResponse deleteResponse = ElasticsearchConfiguration.client().prepareDelete("employee", "id", id).get();

        System.out.println(deleteResponse.getResult().toString());
        return deleteResponse.getResult().toString();
    }

}
