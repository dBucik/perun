package cz.metacentrum.perun.finder.service;

import cz.metacentrum.perun.finder.persistence.data.FinderDAO;
import cz.metacentrum.perun.finder.persistence.exceptions.AttributeTypeException;
import cz.metacentrum.perun.finder.persistence.exceptions.IllegalRelationException;
import cz.metacentrum.perun.finder.persistence.exceptions.IncorrectCoreAttributeTypeException;
import cz.metacentrum.perun.finder.persistence.models.Query;
import cz.metacentrum.perun.finder.persistence.models.entities.PerunEntity;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.InputEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinderManager {

	private final FinderDAO dao;

	public FinderManager(FinderDAO finderDAO) {
		this.dao = finderDAO;
	}

	public List<PerunEntity> performSearch(String input) throws IllegalRelationException, InputParseException, IncorrectCoreAttributeTypeException, AttributeTypeException, IncorrectSourceEntityException {
		InputEntity parsedInput = JsonToInputParser.parseInput(input);
		if (parsedInput == null) {
			throw new InputParseException("Could not parse input");
		}
		Query query = parsedInput.toQuery(null);
		return dao.executeQuery(query);
	}

}
