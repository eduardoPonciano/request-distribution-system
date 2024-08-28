package br.com.eponciano.invext.relations.core.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.eponciano.invext.relations.core.model.Request;
import br.com.eponciano.invext.relations.core.model.RequestUpdate;
import br.com.eponciano.invext.relations.core.model.SectorEnum;
import br.com.eponciano.invext.relations.core.model.StatusRequestEnum;

@Service
public interface RequestService {

	public List<Request> getAll(SectorEnum setor,String idAtendente,StatusRequestEnum status);

    public Request getById(String id);

    public void save(Request request);
    
    public boolean update(String id, RequestUpdate request) throws Exception ;

    public boolean deleteById(String id);
}

