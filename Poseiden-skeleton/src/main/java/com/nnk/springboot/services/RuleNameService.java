package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RuleNameService {

    @Autowired
    private RuleNameRepository ruleNameRepository;
    
    /**
     * @return all the RuleName objects registered in the database
     */
    public List<RuleName> loadAllRating(){
        return ruleNameRepository.findAll();
    }
    public RuleName findRuleNameByID(Integer id) {
        if (ruleNameRepository.findById(id).isPresent()) {
            return ruleNameRepository.findById(id).get();
        } else
            log.error("No RuleName found with id "+id);
        return null;
    }
    
    /**
     * @return a specify RuleName object according to the Id
     */
    public void addRuleName(RuleName ruleName){
        if (!ruleNameRepository.findById(ruleName.getId()).isPresent()) {
            ruleNameRepository.save(ruleName);
        } else
            log.error("A RuleName already exist with this id: "+ ruleName.getId());
    }
    
    /**
     * update a RuleName in the database
     */
    public void updateRuleName(RuleName ruleName){
        if (ruleNameRepository.findById(ruleName.getId()).isPresent()) {
            ruleNameRepository.save(ruleName);
        } else
            log.error("No RuleName found with id "+ruleName.getId());
    }

    /**
     * delete a RuleName in the database
     */
    public void deleteRuleName(Integer id){
        Optional<RuleName> optionalRuleName = ruleNameRepository.findById(id);
        if (optionalRuleName.isPresent()) {
            ruleNameRepository.delete(optionalRuleName.get());
            log.error("Rulename "+id+" has been deleted");
        }else
            log.error("No RuleName found with id "+id);
    }
}
