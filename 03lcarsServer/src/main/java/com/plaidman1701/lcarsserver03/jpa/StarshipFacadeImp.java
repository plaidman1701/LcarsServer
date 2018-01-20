package com.plaidman1701.lcarsserver03.jpa;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;

import com.plaidman1701.lcarsserver03.entity.Starship;
import com.plaidman1701.lcarsserver03.entity.StarshipException;

@Stateless
public class StarshipFacadeImp implements StarshipFacade {
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Starship> getStarships() {
		TypedQuery<Starship> getQuery = entityManager.createNamedQuery("getAllStarships", Starship.class);
		return getQuery.getResultList();
	}

	@Override
	public Starship findStarship(String stringToFind) {
		
		try {
			return entityManager.createNamedQuery("findStarship", Starship.class).setParameter("stringToFind", stringToFind.toLowerCase()).getSingleResult();
		}
		catch (NoResultException e)
		{
			return null;
		}
	}

	@Override
	// doesn't exist yet - in the backlog
	public int updateStarship(Starship starshipToUpdate) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeStarship(String idToRemove) throws StarshipException {
		
		// find starship first
		Starship starshipToRemove = entityManager.find(Starship.class, idToRemove);
		
		// ship not found
		if (starshipToRemove == null)
		{
			throw new StarshipException();
		}
		
		entityManager.remove(starshipToRemove);
		entityManager.flush();
	}

	@Override
	public Set<String> addStarship(Starship starshipToAdd) throws PersistenceException {
		
		Set<String> returnMessages = validate(starshipToAdd);
		// if returnMessages has at least 1 entry, validation failed
		if ((returnMessages != null) && (returnMessages.size() > 0))
		{
			return returnMessages;
		}
		entityManager.persist(starshipToAdd);
		entityManager.flush();
		return returnMessages;
	}
	
	// before adding a new ship, check their fields against entity constraints
	private Set<String> validate(Starship shipToValidate)
	{
		Set<String> errorMessages = new HashSet<String>();
		
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Starship>> constraintViolations = validator.validate(shipToValidate);
		
		if (constraintViolations.size() > 0) // true if errors found
		{
			for (ConstraintViolation<Starship> constraintViolation : constraintViolations)
			{
				 errorMessages.add(constraintViolation.getPropertyPath() + " : "  + constraintViolation.getMessage());
				 System.err.println(constraintViolation.getPropertyPath() + " : "  + constraintViolation.getMessage());				
			}
		}
		
		return errorMessages;
	}
}