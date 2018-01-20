package com.plaidman1701.lcarsserver03.jpa;

import java.util.List;
import java.util.Set;

import javax.ejb.Local;
import javax.validation.ConstraintViolationException;

import com.plaidman1701.lcarsserver03.entity.Starship;
import com.plaidman1701.lcarsserver03.entity.StarshipException;

@Local
public interface StarshipFacade {
	
	List<Starship> getStarships();
	Starship findStarship(String stringToFind);
	int updateStarship(Starship starshipToUpdate);
	void removeStarship(String idToRemove) throws StarshipException;
	Set<String> addStarship(Starship starshipToAdd) throws ConstraintViolationException;
}