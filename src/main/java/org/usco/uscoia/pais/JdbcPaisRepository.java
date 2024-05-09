package org.usco.uscoia.pais;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class JdbcPaisRepository implements PaisRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	String CREATE_SQL = "INSERT INTO public.pais (\"nombre\", \"acronimo\") VALUES (?,?)";
	String READ_SQL = "SELECT id, nombre, acronimo FROM public.pais";
	String UPDATE_SQL = "UPDATE public.pais SET nombre=?, acronimo=? WHERE id=?";
	String DELETE_SQL = "DELETE FROM public.pais WHERE id=?";
	String FINDBYID_SQL = "SELECT * FROM pais WHERE id=?";

	@Override
	public int create(Pais pais) {
		System.out.println(CREATE_SQL);
		System.out.println(pais);
		return jdbcTemplate.update(CREATE_SQL, new Object[] { pais.getNombre(), pais.getAcronimo() });
	}

	@Override
	public List<Pais> read() {
		return jdbcTemplate.query(READ_SQL, BeanPropertyRowMapper.newInstance(Pais.class));

	}

	@Override
	public int update(int id, Pais pais) {
		return jdbcTemplate.update(UPDATE_SQL, new Object[] { pais.getNombre(), pais.getAcronimo(), id });

	}

	@Override
	public int delete(int id) {
		return jdbcTemplate.update(DELETE_SQL, id);


	}

	@Override
	public Pais findById(int id) {
		try {
			Pais pais = jdbcTemplate.queryForObject(FINDBYID_SQL,
					BeanPropertyRowMapper.newInstance(Pais.class), id);

			return pais;
		} catch (IncorrectResultSizeDataAccessException e) {
			return null;
		}
	}

}
