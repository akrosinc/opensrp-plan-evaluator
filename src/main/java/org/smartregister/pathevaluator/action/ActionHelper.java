/**
 * 
 */
package org.smartregister.pathevaluator.action;

import java.util.List;

import org.smartregister.domain.Action;
import org.smartregister.domain.Condition;
import org.smartregister.domain.Jurisdiction;
import org.smartregister.pathevaluator.PathEvaluatorLibrary;
import org.smartregister.pathevaluator.ResourceType;
import org.smartregister.pathevaluator.dao.ClientDao;
import org.smartregister.pathevaluator.dao.LocationDao;

import com.ibm.fhir.model.resource.Resource;

/**
 * @author Samuel Githengi created on 06/15/20
 */
public class ActionHelper {
	
	/**
	 * Gets the resource type for the action
	 * 
	 * @param action the action
	 * @return the ResourceType for the action
	 */
	public static ResourceType getResourceType(Action action) {
		
		if (action.getSubjectCodableConcept() == null) {
			throw new IllegalArgumentException("getSubjectCodableConcept is null ");
		}
		return ResourceType.from(action.getSubjectCodableConcept().getText());
	}
	
	/**
	 * Gets the subject resources in the jurisdiction that tasks should be generated against
	 * 
	 * @param action
	 * @param jurisdiction
	 * @return resources that tasks should be generated against
	 */
	public List<? extends Resource> getSubjectResources(Action action, Jurisdiction jurisdiction) {
		ResourceType resourceType = getResourceType(action);
		LocationDao locationDao = PathEvaluatorLibrary.getInstance().getLocationProvider().getLocationDao();
		ClientDao clientDao = PathEvaluatorLibrary.getInstance().getClientProvider().getClientDao();
		switch (resourceType) {
			case JURISDICTION:
				
				return locationDao.findJurisdictionsById(jurisdiction.getCode());
			
			case LOCATION:
				return locationDao.findLocationByJurisdiction(jurisdiction.getCode());
			
			case FAMILY:
				return clientDao.findFamilyByJurisdiction(jurisdiction.getCode());
			
			case FAMILY_MEMBER:
				return clientDao.findFamilyMemberyByJurisdiction(jurisdiction.getCode());
			
			default:
				return null;
		}
	}
	
	/**
	 * Gets the subject resources of the resource id that tasks should be generated against
	 *
	 * @param condition
	 * @param action
	 * @param id the resource id
	 * @return resources that tasks should be generated against
	 */
	public List<? extends Resource> getConditionSubjectResources(Condition condition, Action action, Resource resource,
	        String planIdentifier) {
		ResourceType conditionResourceType = ResourceType.from(condition.getExpression().getSubjectConcept());
		ResourceType actionResourceType = ResourceType.from(action.getSubjectCodableConcept());
		switch (conditionResourceType) {
			case JURISDICTION:
				return PathEvaluatorLibrary.getInstance().getLocationProvider().getJurisdictions(resource,
				    actionResourceType);
			
			case LOCATION:
				return PathEvaluatorLibrary.getInstance().getLocationProvider().getLocations(resource, actionResourceType);
			
			case FAMILY:
				return PathEvaluatorLibrary.getInstance().getClientProvider().getFamilies(resource, actionResourceType);
			
			case FAMILY_MEMBER:
				return PathEvaluatorLibrary.getInstance().getClientProvider().getFamilyMembers(resource, actionResourceType);
			
			case TASK:
				return PathEvaluatorLibrary.getInstance().getTaskProvider().getTasks(resource, actionResourceType,
				    planIdentifier);
			
			default:
				return null;
		}
	}
	
}