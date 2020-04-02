package com.nnk.springboot.services;


import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@Slf4j
public class CurvePointService {

    @Autowired
    private CurvePointRepository curvePointRepository;

    public List<CurvePoint> loadAllCurvePoint(){
        List<CurvePoint> curvePoints = curvePointRepository.findAll();
        return curvePoints;
    }
    public CurvePoint findCurvePointbyID(Integer id){
        if (curvePointRepository.findById(id).isPresent()) {
            CurvePoint curvePoint = curvePointRepository.findById(id).get();
            return curvePoint;
        } else
            log.error("No CurvePoint found with this id");
        return null;
    }
    public void updateCurvePoint(CurvePoint curvePoint) {
        curvePointRepository.save(curvePoint);
    }

    public void deleteCurvePoint(Integer id){
        curvePointRepository.delete(curvePointRepository.findById(id).get());
    }
}
