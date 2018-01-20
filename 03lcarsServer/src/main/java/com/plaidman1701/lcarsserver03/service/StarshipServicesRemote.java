package com.plaidman1701.lcarsserver03.service;

import java.util.List;

import javax.ejb.Remote;

import com.plaidman1701.lcarsserver03.entity.ResponseCode;
import com.plaidman1701.lcarsserver03.entity.Starship;

@Remote
public interface StarshipServicesRemote {
	
	
	List<Starship> getStarshipList();
	ResponseCode findStarship(String stringToFind);
	List<ResponseCode> addStarship(Starship starshipToAdd);
	ResponseCode removeStarship(String registryToRemove);
}
