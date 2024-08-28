package br.com.eponciano.invext.relations.core.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import br.com.eponciano.invext.relations.core.model.Request;
import br.com.eponciano.invext.relations.core.model.SectorEnum;
import br.com.eponciano.invext.relations.core.model.StatusRequestEnum;

public class RequestsInMemoryStorage {
	
	private RequestsInMemoryStorage() {
		
	}
	
	private static List<Request> requestSingleton = new ArrayList<Request>();

	public static void add(Request request) {
		requestSingleton.add(request);
	}
	
	public static Request getById( UUID id) {
		return requestSingleton.stream()
			.filter(request -> (id == null || request.getId().equals(id)))
			.findFirst()
			.orElse(null);
	}
	public static List<Request> getByFilter( SectorEnum sectorEnum, UUID idAtendente, StatusRequestEnum status) {
		return requestSingleton.stream()
			.filter(request -> (sectorEnum == null || request.getTipo() == sectorEnum))
			.filter(request -> (idAtendente == null || (request.getIdAtendente()!=null && request.getIdAtendente().equals(idAtendente))))
			.filter(request -> (status == null || request.getStatus() == status))
			.collect(Collectors.toList());
	}
	
	public static boolean remove(UUID id) {
		return requestSingleton.removeIf(request -> request.getId().equals(id));
	}
	
	public static boolean update(UUID id, Request updatedRequest) {
	    for (int i = 0; i < requestSingleton.size(); i++) {
	        Request existingRequest = requestSingleton.get(i);
	        
	        if (existingRequest.getId().equals(id)) {
	            boolean canUpdateStatus = false;

	            if (updatedRequest.getStatus() != null) {
	                if (updatedRequest.getStatus() == StatusRequestEnum.PENDENTE && existingRequest.getStatus() == StatusRequestEnum.EM_ANALISE) {
	                    canUpdateStatus = true;
	                } else if (updatedRequest.getStatus() == StatusRequestEnum.EM_ANALISE && existingRequest.getStatus() != StatusRequestEnum.PENDENTE) {
	                    canUpdateStatus = true;
	                }
	            }

	            // Atualiza os campos permitidos
	            if (canUpdateStatus) {
	                existingRequest.setStatus(updatedRequest.getStatus());
	                existingRequest.setIdAtendente(updatedRequest.getIdAtendente());
	            }

	            // Se alguma atualização foi feita, retorna true
	            requestSingleton.set(i, existingRequest);
	            return true;
	        }
	    }
	    return false;
	}

}
