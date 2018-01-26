package backend.elastic.repository;

import backend.elastic.model.Dataset;
import backend.elastic.model.Resource;

import java.util.List;

public interface CustomDatasetsRepository {

	//List<Dataset> searchDatasets(String query);

	Resource searchResourcebyID( final String datasetID,
								final String resourceID);
}