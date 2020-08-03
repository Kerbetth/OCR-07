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

    /**
     * @return all the BidList objects registered in the database
     */
    public List<CurvePoint> loadAllCurvePoint(){
        List<CurvePoint> curvePoints = curvePointRepository.findAll();
        return curvePoints;
    }

    /**
     * @return a specify BidList object according to the Id
     */
    public CurvePoint findCurvePointbyID(Integer id){
        if (curvePointRepository.findById(id).isPresent()) {
            CurvePoint curvePoint = curvePointRepository.findById(id).get();
            return curvePoint;
        } else
            log.error("No CurvePoint found with this id");
        return null;
    }

    /**
     * update a bidList in the database
     */
    public void updateCurvePoint(CurvePoint curvePoint) {
        curvePointRepository.save(curvePoint);
    }

    /**
     * delete a bidList in the database
     */
    public void deleteCurvePoint(Integer id){
        curvePointRepository.delete(curvePointRepository.findById(id).get());
    }
}
