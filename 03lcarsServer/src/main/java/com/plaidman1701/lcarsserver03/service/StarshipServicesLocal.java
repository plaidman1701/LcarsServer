package com.plaidman1701.lcarsserver03.service;

import java.util.List;

import javax.ejb.Local;

import com.plaidman1701.lcarsserver03.entity.ResponseCode;
import com.plaidman1701.lcarsserver03.entity.Starship;

@Local
public interface StarshipServicesLocal {
	
	List<Starship> getStarshipList();
	ResponseCode findStarship(String stringToFind);
	List<ResponseCode> addStarship(Starship starshipToAdd);
	ResponseCode removeStarship(String registryToRemove);

}
