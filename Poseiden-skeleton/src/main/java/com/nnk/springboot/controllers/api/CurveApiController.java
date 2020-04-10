package com.nnk.springboot.controllers.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
public class CurveApiController {

    @Autowired
    CurvePointService curveService;


    @PostMapping("/curveAPI")
    public String validateApi(@Valid @RequestBody CurvePoint curve, BindingResult result) {
        if (result.hasErrors()) {
            return result.toString();
        }
        curveService.updateCurvePoint(curve);
        return "A new CurvePoint has been saved";
    }

    @GetMapping("/curveAPI/{id}")
    public String readCurve(@PathVariable("id") Integer id) throws JsonProcessingException {
        if (curveService.findCurvePointbyID(id) == null) {
            return "No CurvePoint with this ID";
        }
        ObjectMapper Obj = new ObjectMapper();
        return Obj.writeValueAsString(curveService.findCurvePointbyID(id));
    }

    @PutMapping("/curveAPI/{id}")
    public String updateCurveApi(@PathVariable("id") Integer id, @Valid @RequestBody CurvePoint curve, BindingResult result) {
        if (curveService.findCurvePointbyID(id) == null) {
            return "No CurvePoint with this ID";
        } else if (result.hasErrors()) {
            return result.toString();
        } else {
            curve.setId(id);
            curveService.updateCurvePoint(curve);
            return "Ok";
        }
    }

    @DeleteMapping("/curveAPI/{id}")
    public String deleteCurve(@PathVariable("id") Integer id) {
        if (curveService.findCurvePointbyID(id) == null) {
            return "No CurvePoint with this ID";
        }
        curveService.deleteCurvePoint(id);
        return "CurvePoint " + id + " has been deleted";
    }
}
