package backend.elastic.load;

import backend.elastic.configuration.ElasticsearchConfiguration;
import backend.elastic.model.Dataset;
import backend.elastic.model.Resource;
import backend.elastic.repository.DatasetsRepository;
import eu.trentorise.opendata.jackan.CkanClient;
import eu.trentorise.opendata.jackan.model.CkanDataset;
import eu.trentorise.opendata.jackan.model.CkanResource;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class Loaders {

    @Autowired
    ElasticsearchOperations operations;

    @Autowired
    DatasetsRepository datasetsRepository;

    @PostConstruct
    @Transactional
    public void loadAll() throws IOException{

        operations.putMapping(Dataset.class);
        System.out.println("Starting loading Data");

        Client client = ElasticsearchConfiguration.client();
        String catalogURL ="http://dati.trentino.it";
        CkanClient ckanClient = new CkanClient(catalogURL);
        //CKAN configuration
        List<String> datasetList = ckanClient.getDatasetList(4, 0);
        for (String datasetItem : datasetList) {
            List<Resource> resources = new ArrayList<Resource>();
            CkanDataset dataset = ckanClient.getDataset(datasetItem);
            for (CkanResource resource : dataset.getResources()) {
                Resource resourceInstance = new Resource(
                        resource.getId(),
                        resource.getFormat(),
                        resource.getUrlType(),
                        resource.getOwner(),
                        resource.getState().toString());

                resources.add(resourceInstance);
            }
            datasetsRepository.save(
                    new Dataset(dataset.getId(), dataset.getName(),
                            dataset.getAuthor(), dataset.getOrganization().toString(),
                            dataset.getUrl(), dataset.getCreatorUserId(),
                            dataset.getLicenseTitle(), dataset.getLicenseUrl(),
                            dataset.getNotesRendered(), resources
                    ));
            }
            client.close();
        System.out.println("Loading Completed");
    }
}