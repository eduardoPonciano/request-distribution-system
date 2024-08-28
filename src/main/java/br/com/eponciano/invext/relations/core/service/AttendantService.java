package br.com.eponciano.invext.relations.core.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.eponciano.invext.relations.core.model.Attendant;
import br.com.eponciano.invext.relations.core.model.SectorEnum;

@Service
public interface AttendantService {

    public List<Attendant> getAll(SectorEnum setor);

    public Attendant getById(String id);

    public void save(Attendant request);

    public boolean update(String id, Attendant request);

    public boolean deleteById(String id);
}

