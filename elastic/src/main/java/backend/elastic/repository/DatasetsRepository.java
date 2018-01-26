package backend.elastic.repository;

import backend.elastic.model.Dataset;
import backend.elastic.model.Resource;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;

public interface DatasetsRepository extends ElasticsearchRepository<Dataset, String> {
    //@Query("SELECT D FROM Dataset D WHERE D.author= 'Servizio Statistica' ")
    List<Dataset> findByName(String text);
    Dataset findByIdAndResourcesId(String datasetID, String resourceId);
    Optional<Dataset> findById(String text);
}
