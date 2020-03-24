package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleNameService {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    public List<RuleName> loadAllRating(){
        List<RuleName> ruleNameList = ruleNameRepository.findAll();
        return ruleNameList;
    }
    public RuleName findBidListbyID(Integer integer) {
        RuleName ruleName = ruleNameRepository.findById(integer).get();
        return ruleName;
    }
    public void updateRuleName(RuleName ruleName){
        ruleNameRepository.save(ruleName);
    }
    public void deleteRuleName(Integer id){
        ruleNameRepository.delete(ruleNameRepository.findById(id).get());
    }
}
