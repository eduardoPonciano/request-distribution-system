package br.com.eponciano.sac.relations.core.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.eponciano.sac.relations.core.model.Attendant;
import br.com.eponciano.sac.relations.core.model.SectorEnum;
import br.com.eponciano.sac.relations.core.service.AttendantService;
import br.com.eponciano.sac.relations.core.storage.AttendantInMemoryStorage;

@Service
public class AttendantServiceImpl implements AttendantService {

	public List<Attendant> getAll(SectorEnum setor) {
		return AttendantInMemoryStorage.getByFilter(setor);
	}

	public Attendant getById(String id) {
		return AttendantInMemoryStorage.getById(UUID.fromString(id));
	}

	public void save(Attendant attendant) {
		AttendantInMemoryStorage.add(attendant);
	}
	
	public boolean update(String id, Attendant attendant) {
		return AttendantInMemoryStorage.update(UUID.fromString(id), attendant);
	}

	public boolean deleteById(String id) {
		return AttendantInMemoryStorage.remove(UUID.fromString(id));
	}
}
