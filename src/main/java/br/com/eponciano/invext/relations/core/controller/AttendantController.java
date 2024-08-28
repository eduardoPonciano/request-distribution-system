package br.com.eponciano.invext.relations.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.eponciano.invext.relations.core.model.Attendant;
import br.com.eponciano.invext.relations.core.model.SectorEnum;
import br.com.eponciano.invext.relations.core.service.AttendantService;

@RestController
@RequestMapping("/api/attendant")
public class AttendantController {

    @Autowired
    private AttendantService service;

    @GetMapping
    public ResponseEntity<?>  getAll(@RequestParam(required = false) SectorEnum setor) {
    	
        return ResponseEntity.ok(service.getAll(setor));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?>  getById(@PathVariable String id) {
        Attendant ret = service.getById(id);
        if (ret != null) {
            return ResponseEntity.ok(ret);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Attendant attendant) {
		service.save(attendant);
		return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRequest(@PathVariable String id, @RequestBody Attendant updatedAttendant) {
        boolean updated = service.update(id, updatedAttendant);
        if (updated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable String id) {
        boolean deleted = service.deleteById(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
}
