package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RuleNameService {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    public List<RuleName> loadAllRating(){
        List<RuleName> ruleNameList = ruleNameRepository.findAll();
        return ruleNameList;
    }
    public RuleName findRuleNameByID(Integer id) {
        if (ruleNameRepository.findById(id).isPresent()) {
            RuleName ruleName = ruleNameRepository.findById(id).get();
            return ruleName;
        } else
            log.error("No RuleName found with this id");
        return null;
    }
    public void updateRuleName(RuleName ruleName){
        ruleNameRepository.save(ruleName);
    }
    public void deleteRuleName(Integer id){
        ruleNameRepository.delete(ruleNameRepository.findById(id).get());
    }
}
