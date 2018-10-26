package cz.metacentrum.perun.finder.persistence.data;

import cz.metacentrum.perun.finder.persistence.models.entities.PerunEntity;
import cz.metacentrum.perun.finder.persistence.models.Query;

import java.util.List;

public interface FinderDAO {

	List<PerunEntity> executeQuery(Query query);

	List<Integer> executeQueryForIds(Query query);

}
