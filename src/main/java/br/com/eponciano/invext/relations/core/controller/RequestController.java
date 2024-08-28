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

import br.com.eponciano.invext.relations.core.model.Request;
import br.com.eponciano.invext.relations.core.model.RequestUpdate;
import br.com.eponciano.invext.relations.core.model.SectorEnum;
import br.com.eponciano.invext.relations.core.model.StatusRequestEnum;
import br.com.eponciano.invext.relations.core.service.RequestService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/request")
public class RequestController {

    @Autowired
    private RequestService service;

    @GetMapping
    public ResponseEntity<?>  getAll(
            @RequestParam(required = false) SectorEnum setor,
            @RequestParam(required = false) String idAtendente,
            @RequestParam(required = false) StatusRequestEnum status) {
    	
        return ResponseEntity.ok(service.getAll(setor,idAtendente,status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Void>  getById(@PathVariable String id) {
       Request ret = service.getById(id);
        if (ret!=null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<?>  create(@Valid  @RequestBody Request request) {
    	service.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    //regra para não deixar o atendente ter mais de 3 solitações em atendimento
    @PutMapping("/{id}")
    public ResponseEntity<String> updateRequest(@PathVariable String id, @Valid @RequestBody RequestUpdate updatedRequest) {
		try {
			boolean updated = service.update(id, updatedRequest);
			if (updated) {
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} catch (Exception e) {
			return  new ResponseEntity<String>("Unauthorized: "+e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable String id) {
        boolean deleted =service.deleteById(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
