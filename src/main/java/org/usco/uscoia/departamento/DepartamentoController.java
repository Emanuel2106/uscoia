package org.usco.uscoia.departamento;
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

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/departamento/api/v1")
public class DepartamentoController {

	@Autowired
	DepartamentoRepository departamentoRepository;

	@PostMapping("/")
	public ResponseEntity<String> createDepartamento(@RequestBody Departamento departamento) {
		try {
			departamentoRepository.create(
					new Departamento(departamento.getPais(), departamento.getNombre(), departamento.getAcronimo()));
			return new ResponseEntity<>("DEpartamento was created successfully.", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/")
	public ResponseEntity<List<Departamento>> read() {
		try {
			List<Departamento> departamentos = new ArrayList<Departamento>();

			departamentoRepository.read().forEach(departamentos::add);

			if (departamentos.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(departamentos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	
	

	@PutMapping("/{id}")
	public ResponseEntity<String> update(@PathVariable("id") int id, @RequestBody Departamento departamento) {
		Departamento _departamento = departamentoRepository.findById(id);
		
		if (_departamento != null) {
			_departamento.setId(departamento.getId());
			_departamento.setPais(departamento.getPais());
			_departamento.setNombre(departamento.getNombre());
			_departamento.setAcronimo(departamento.getAcronimo());

			departamentoRepository.update(id, departamento);
			return new ResponseEntity<>("departamento was updated successfully.", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Cannot find departamento with id=" + id, HttpStatus.NOT_FOUND);
		}

	}
	

	
//	@DeleteMapping("/{id}")
//	public ResponseEntity<String> deleteDepartamento(@PathVariable("id") int id) {
//		try {
//			departamentoRepository.delete(id);
//			return new ResponseEntity<>("Departamento eliminado con exito", HttpStatus.CREATED);
//		} catch (Exception e) {
//			return new ResponseEntity<>("Cannot delete Departamento.", HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
//	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") int id) {
		try {
			int result = departamentoRepository.delete(id);
			
			if (result == 0) {
				return new ResponseEntity<>("Cannot find Departamento with id=" + id, HttpStatus.OK);
			}
			return new ResponseEntity<>("Departamento was deleted successfully.", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Cannot delete Departamento.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
}
