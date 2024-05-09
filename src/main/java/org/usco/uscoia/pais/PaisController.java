package org.usco.uscoia.pais;

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
import org.usco.uscoia.municipio.Municipio;
import org.usco.uscoia.municipio.MunicipioRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/pais/api/v1")
public class PaisController {
	@Autowired
	PaisRepository paisRepository;

	@PostMapping("/")
	public ResponseEntity<String> createPais(@RequestBody Pais pais) {
		try {
			paisRepository.create(
					new Pais( pais.getNombre(), pais.getAcronimo()));
			return new ResponseEntity<>("Pais was created successfully.", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("El pais no fue creado", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/")
	public ResponseEntity<List<Pais>> read() {
		try {
			List<Pais> pais = new ArrayList<Pais>();

			paisRepository.read().forEach(pais::add);

			if (pais.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(pais, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	

	@PutMapping("/{id}")
	public ResponseEntity<String> update(@PathVariable("id") int id, @RequestBody Pais pais) {
		Pais _pais = paisRepository.findById(id);
		
		if (_pais != null) {
			_pais.setId(pais.getId());
			_pais.setNombre(pais.getNombre());
			_pais.setAcronimo(pais.getAcronimo());
			

			paisRepository.update(id, pais);
			return new ResponseEntity<>("Pais was updated successfully.", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Cannot find Pais with id=" + id, HttpStatus.NOT_FOUND);
		}

	}
	
	

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") int id) {
		try {
			int result = paisRepository.delete(id);
			if (result == 0) {
				return new ResponseEntity<>("Cannot find pais with id=" + id, HttpStatus.OK);
			}
			return new ResponseEntity<>("Pais was deleted successfully.", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Cannot delete Pais.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Pais> findById(@PathVariable("id") int id) {
		Pais pais = paisRepository.findById(id);

		if (pais != null) {
			return new ResponseEntity<>(pais, HttpStatus.OK);
		} else {
			System.out.println("Id no existe");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}


}
