package com.nnk.springboot.unit.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CurvePointTests {

	@Autowired
	private CurvePointRepository curvePointRepository;
	private CurvePoint curvePoint;

	@BeforeEach
	public void setup() {

		curvePoint = new CurvePoint(10, 10, 10d, 10d);
	}

	@Test
	public void bidListSavTest() {
		// Save
		curvePoint = curvePointRepository.save(curvePoint);
		assertThat(curvePoint.getCurveID()).isNotNull();
		assertThat(curvePoint.getTerm()).isEqualTo(10d);
	}

	@Test
	public void bidListUpdateTest() {
		// Update
		curvePoint.setTerm(20d);
		curvePoint = curvePointRepository.save(curvePoint);
		assertThat(curvePoint.getTerm()).isEqualTo(20d);
	}

	@Test
	public void bidListFindTest() {
		// Find
		curvePoint = curvePointRepository.save(curvePoint);
		List<CurvePoint> listResult = curvePointRepository.findAll();
		assertThat(listResult).hasSizeGreaterThan(0);
	}

	@Test
	public void bidListDeleteTest() {
		// Delete
		curvePoint = curvePointRepository.save(curvePoint);
		Integer id = curvePoint.getCurveID();
		curvePointRepository.delete(curvePoint);
		Optional<CurvePoint> bidList = curvePointRepository.findById(id);
		assertThat(bidList).isEmpty();
	}
}
