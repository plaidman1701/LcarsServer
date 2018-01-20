package com.plaidman1701.lcarsserver03.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import com.plaidman1701.lcarsserver03.entity.ResponseCode;
import com.plaidman1701.lcarsserver03.entity.Starship;
import com.plaidman1701.lcarsserver03.entity.StarshipException;
import com.plaidman1701.lcarsserver03.jpa.StarshipFacade;
import com.plaidman1701.lcarsserver03.presentation.PresentationUtils;

@Stateless(mappedName="StarshipServicesImp")
@Local(StarshipServicesLocal.class)
@Remote(StarshipServicesRemote.class)
public class StarshipServicesImp implements StarshipServicesLocal, StarshipServicesRemote {
	
	@EJB(mappedName="java:app/03lcarsServer/StarshipFacadeImp")
	private StarshipFacade starshipFacade;
	
	// default ctor
	public StarshipServicesImp()
	{
		System.err.println("StarshipServicesImp called from server");
		
	}

	@Override
	public List<Starship> getStarshipList() {
		
		return starshipFacade.getStarships();
	}

	@Override
	public ResponseCode findStarship(String stringToFind) {
		
		ResponseCode findRC = new ResponseCode();
		Starship starshipToFind = starshipFacade.findStarship(stringToFind);
		
		if (starshipToFind != null)
		{
			findRC.setCode(PresentationUtils.getString("success.find.starship.code"));
			Object[] descArgs = {starshipToFind.getName().toUpperCase(), starshipToFind.getRegistry().toUpperCase(), starshipToFind.getVesselclass().toUpperCase()};
			findRC.setDesc(PresentationUtils.getString("success.find.starship.desc", descArgs));
		}
		else
		{
			findRC.setCode(PresentationUtils.getString("error.find.starship.code"));
			Object[] descArgs = {stringToFind};
			findRC.setCode(PresentationUtils.getString("error.find.starship.desc", descArgs));
		}
		
		return findRC;

	}

	@Override
	public List<ResponseCode> addStarship(Starship starshipToAdd) {


		List<ResponseCode> responseCodes = new ArrayList<ResponseCode>();
		
		try
		{
			// if responseMsgs has at least 1 element, add was unsuccessful
			Set<String> responseMsgs = starshipFacade.addStarship(starshipToAdd);
			responseCodes = buildResponseCode(responseMsgs, "add", starshipToAdd);
		}
		catch (PersistenceException | EJBTransactionRolledbackException e)
		{
			System.err.println("caught exception " + e.getClass());

			ResponseCode errorRc = new ResponseCode();
			errorRc.setCode(PresentationUtils.getString("error.add.starship.duplicate.code"));
			errorRc.setDesc(PresentationUtils.getString("error.add.starship.duplicate.desc"));
			responseCodes.add(errorRc);			
		}
		
		return responseCodes;
	}

	private List<ResponseCode> buildResponseCode(Set<String> errorMsgs, String operationName, Starship starshipToAdd) {
		
		List<ResponseCode> responses = new ArrayList<ResponseCode>();
		if ((errorMsgs != null) && (errorMsgs.size() == 0))
		{
			Object[] args = { starshipToAdd.getName().toUpperCase(), starshipToAdd.getRegistry().toUpperCase() };
			
			ResponseCode rs = new ResponseCode();
			rs.setCode(PresentationUtils.getString("success." + operationName + ".starship.code"));
			rs.setDesc(PresentationUtils.getString("success." + operationName + ".starship.desc", args));
			responses.add(rs);
		}
		
		if ((errorMsgs!=null) && (errorMsgs.size() > 0))
		{
			for (String errMsg : errorMsgs)
			{
				ResponseCode rc = new ResponseCode();
				System.out.println(errMsg + " from StarshipServicesImp");
				String [] msgParts = errMsg.split("registry : "); //TODO lame way of getting rid of the Bean Validation prefix 
				rc.setCode(PresentationUtils.getString(msgParts[1] + ".code"));
				rc.setDesc(PresentationUtils.getString(msgParts[1] + ".desc"));
				responses.add(rc);
			}
		}
		
		return responses;
	}

	@Override
	public ResponseCode removeStarship(String registryToRemove) {
		
		ResponseCode removeRs = new ResponseCode();
		
		try
		{
			starshipFacade.removeStarship(registryToRemove);
		}
		catch (StarshipException e)
		{
			removeRs.setCode(PresentationUtils.getString("error.remove.starship.code"));
			removeRs.setDesc(PresentationUtils.getString("error.remove.starship.desc"));
			return removeRs;
		}
		
		removeRs.setCode(PresentationUtils.getString("success.remove.starship.code"));
		Object[] args = {registryToRemove.toUpperCase()};
		removeRs.setDesc(PresentationUtils.getString("success.remove.starship.desc", args));
		
		return removeRs;
	}
}