package org.usco.uscoia.municipio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
@Repository
public class JdbcMunicipioRepository implements MunicipioRepository {
	@Autowired
	JdbcTemplate jdbcTemplate;
	String CREATE_SQL = "INSERT INTO public.municipio(\n" + " departamento, nombre, acronimo)\n" + "VALUES (?, ?, ?)";
	String READ_SQL = "SELECT id, departamento, nombre, acronimo\n" + "	FROM public.municipio";
	String UPDATE_SQL = "UPDATE public.municipio\n" + "	SET  departamento=?, nombre=?, acronimo=?\n" + " WHERE id=?";
	String DELETE_SQL = "DELETE FROM public.municipio\n" + "	WHERE id=?";
	String FINDBYID_SQL = "SELECT * FROM municipio WHERE id=?";

	@Override
	public int create(Municipio municipio) {
		return jdbcTemplate.update(CREATE_SQL,
				new Object[] { municipio.getDepartamento(), municipio.getNombre(), municipio.getAcronimo() });
	}

	@Override
	public List<Municipio> read() {
		return jdbcTemplate.query(READ_SQL, BeanPropertyRowMapper.newInstance(Municipio.class));

	}

	@Override
	public int update(int id, Municipio municipio) {
		return jdbcTemplate.update(UPDATE_SQL,
				new Object[] { municipio.getDepartamento(), municipio.getNombre(), municipio.getAcronimo(), id });

	}

	@Override
	public int delete(int id) {
		return jdbcTemplate.update(DELETE_SQL, id);

	}

	@Override
	public Municipio findById(int id) {
		try {
			Municipio municipio = jdbcTemplate.queryForObject(FINDBYID_SQL,
					BeanPropertyRowMapper.newInstance(Municipio.class), id);

			return municipio;
		} catch (IncorrectResultSizeDataAccessException e) {
			return null;
		}
	}

}
