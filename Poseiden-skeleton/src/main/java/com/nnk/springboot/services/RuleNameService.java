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
    public List<RuleName> loadAllRating() {
        return ruleNameRepository.findAll();
    }

    /**
     * @return a specify RuleName object according to the Id
     */
    public RuleName findRuleNameByID(Integer id) {
        if (id != null) {
            Optional<RuleName> ruleName = ruleNameRepository.findById(id);
            if (ruleName.isPresent()) {
                return ruleName.get();
            }
        } else
            log.error("No RuleName found with id " + id);
        return null;
    }


    /**
     * update a RuleName in the database
     */
    public void updateRuleName(RuleName ruleName) {
        ruleNameRepository.save(ruleName);
    }

    /**
     * delete a RuleName in the database
     * @param ruleName
     */
    public void deleteRuleName(RuleName ruleName) {
        ruleNameRepository.delete(ruleName);
    }
}
