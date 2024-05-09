package org.usco.uscoia.municipio;
//https://spring.io/guides/gs/rest-service/

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/municipio/api/v1")
public class MunicipioController {

	@Autowired
	MunicipioRepository municipioRepository;

	@PostMapping("/")
	public ResponseEntity<String> createMunicipio(@RequestBody Municipio municipio) {
		try {
			municipioRepository.create(
					new Municipio(municipio.getDepartamento(), municipio.getNombre(), municipio.getAcronimo()));
			return new ResponseEntity<>("Municipio was created successfully.", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/")
	public ResponseEntity<List<Municipio>> read() {
		try {
			List<Municipio> municipio = new ArrayList<Municipio>();

			municipioRepository.read().forEach(municipio::add);

			if (municipio.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(municipio, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	@PutMapping("/{id}")
	public ResponseEntity<String> update(@PathVariable("id") int id, @RequestBody Municipio municipio) {
		Municipio _municipio = municipioRepository.findById(id);
		
		if (_municipio != null) {
			_municipio.setId(municipio.getId());
			_municipio.setDepartamento(municipio.getDepartamento());
			_municipio.setNombre(municipio.getNombre());
			_municipio.setAcronimo(municipio.getAcronimo());

			municipioRepository.update(id, municipio);
			return new ResponseEntity<>("Municipio was updated successfully.", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Cannot find Municipio with id=" + id, HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") int id) {
		try {
			int result = municipioRepository.delete(id);
			if (result == 0) {
				return new ResponseEntity<>("Cannot find municipio with id=" + id, HttpStatus.OK);
			}
			return new ResponseEntity<>("municipio was deleted successfully.", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Cannot delete municipio.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


}