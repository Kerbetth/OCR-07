package com.nnk.springboot.services;


import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Rating;
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
        return curvePointRepository.findAll();
    }

    /**
     * @return a specify BidList object according to the Id
     */
    public CurvePoint findCurvePointbyID(Integer id){
        if (id != null) {
        if (curvePointRepository.findById(id).isPresent()) {
            return curvePointRepository.findById(id).get();
        } }else
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
     * @param curvePoint
     */
    public void deleteCurvePoint(CurvePoint curvePoint){
        curvePointRepository.delete(curvePoint);
    }
}
