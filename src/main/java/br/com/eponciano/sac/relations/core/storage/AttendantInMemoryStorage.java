package br.com.eponciano.sac.relations.core.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import br.com.eponciano.sac.relations.core.model.Attendant;
import br.com.eponciano.sac.relations.core.model.SectorEnum;

public class AttendantInMemoryStorage {
	
	private AttendantInMemoryStorage() {
		Attendant attendant = Attendant.builder()
				.nome("Usuaroi Padrao")
				.setor(SectorEnum.CARD)
				.build();
		AttendantInMemoryStorage.add(attendant);
	}
	
	private static List<Attendant> attendantSingleton = new ArrayList<Attendant>();

	public static void add(Attendant request) {
		attendantSingleton.add(request);
	}
	
	public static Attendant getById( UUID id) {
		return attendantSingleton.stream()
			.filter(request -> (id == null || request.getId().equals(id)))
			.findFirst()
			.orElse(null);
	}
	public static List<Attendant> getByFilter( SectorEnum sectorEnum) {
		return attendantSingleton.stream()
			.filter(request -> (sectorEnum == null || request.getSetor() == sectorEnum))
			.collect(Collectors.toList());
	}
	
	public static boolean remove(UUID id) {
		return attendantSingleton.removeIf(request -> request.getId().equals(id));
	}
	
	public static boolean update(UUID id, Attendant updatedAttendant) {
		for (int i = 0; i < attendantSingleton.size(); i++) {
			Attendant existingAttendant = attendantSingleton.get(i);
			if (existingAttendant.getId().equals(id)) {
				attendantSingleton.set(i, updatedAttendant);
				return true;
			}
		}
		return false;
	}
}
