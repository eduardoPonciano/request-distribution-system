package br.com.eponciano.sac.relations.core.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eponciano.sac.relations.core.model.Request;
import br.com.eponciano.sac.relations.core.model.RequestUpdate;
import br.com.eponciano.sac.relations.core.model.SectorEnum;
import br.com.eponciano.sac.relations.core.model.StatusRequestEnum;
import br.com.eponciano.sac.relations.core.service.AttendantService;
import br.com.eponciano.sac.relations.core.service.RequestService;
import br.com.eponciano.sac.relations.core.storage.RequestsInMemoryStorage;

@Service
public class RequestServiceImpl implements RequestService {
	
	@Autowired
	private AttendantService attendantService;

    public List<Request> getAll(SectorEnum setor,String idAtendente, StatusRequestEnum status) {
		UUID id = idAtendente == null ? null : UUID.fromString(idAtendente);
        return RequestsInMemoryStorage.getByFilter(setor, id, status);
    }

    public Request getById(String id) {    	
        return RequestsInMemoryStorage.getById(UUID.fromString(id));
    }

    public void save(Request request) {
    	request.setStatus(StatusRequestEnum.PENDENTE);
        RequestsInMemoryStorage.add(request);
    }
    
	public boolean update(String id, RequestUpdate request) throws Exception {
		boolean confirmUpdate = false;
		int size = RequestsInMemoryStorage.getByFilter(request.getTipo(), request.getIdAtendente(), StatusRequestEnum.EM_ANALISE).size();
		if(request !=null && size < 3 && StatusRequestEnum.EM_ANALISE ==request.getStatus() || StatusRequestEnum.EM_ANALISE != request.getStatus()) {
			Request requestUpdate = getById(id);
			if(requestUpdate != null) {
				requestUpdate.setIdAtendente(request.getIdAtendente());
				requestUpdate.setStatus(request.getStatus());
			} else {
				return confirmUpdate;
			}
			confirmUpdate = RequestsInMemoryStorage.update(UUID.fromString(id), requestUpdate);
		}else {
			throw new Exception("Limites de tarefas em atendimento excedidas para o Usuario"); 
		}
		return confirmUpdate;
	}

    public boolean deleteById(String id) {
    	return RequestsInMemoryStorage.remove(UUID.fromString(id));
    }
}
