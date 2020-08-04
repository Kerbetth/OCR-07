package com.nnk.springboot.controllers;

import com.nnk.springboot.controllers.exceptions.IdAlreadyExistException;
import com.nnk.springboot.controllers.exceptions.IdDoesntExistException;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.CurvePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * These controllers manage CRUD operations with the CurvePoint object
 * and generate a model for listing, adding and updating
 */
@Controller
public class CurveController {

    // TODO: Inject Curve Point service
    @Autowired
    CurvePointService curvePointService;

    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        // TODO: find all Curve Point, add to model
        model.addAttribute("curvePoints", curvePointService.loadAllCurvePoint());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(Model model) {
        model.addAttribute("curvePoint", new CurvePoint());
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result) {
        // TODO: check data valid and save to db, after saving return Curve list
        if (curvePointService.findCurvePointbyID(curvePoint.getCurveID()) == null) {
            if (result.hasErrors()) {
                return "curvePoint/add";
            }
            curvePointService.updateCurvePoint(curvePoint);
            return "redirect:/curvePoint/list";
        }
        throw new IdAlreadyExistException(curvePoint.getId());
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get CurvePoint by Id and to model then show to the form
        CurvePoint curvePoint = curvePointService.findCurvePointbyID(id);
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                            BindingResult result) {
        // TODO: check required fields, if valid call service to update Curve and return Curve list
        if (curvePointService.findCurvePointbyID(id) != null) {
            if (result.hasErrors()) {
                return "curvePoint/update";
            }
            curvePointService.updateCurvePoint(curvePoint);
            return "redirect:/curvePoint/list";
        }
        throw new IdDoesntExistException(id);
    }


    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id) {
        // TODO: Find Curve by Id and delete the Curve, return to Curve list
        CurvePoint curvePoint = curvePointService.findCurvePointbyID(id);
        if (curvePoint == null) {
            throw new IdDoesntExistException(id);
        }
        curvePointService.deleteCurvePoint(curvePoint);
        return "redirect:/curvePoint/list";
    }
}
