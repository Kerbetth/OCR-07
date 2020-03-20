package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleNameService {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    public List<RuleName> loadAllRating() throws UsernameNotFoundException {
        List<RuleName> ruleNameList = ruleNameRepository.findAll();
        return ruleNameList;
    }
    public RuleName findBidListbyID(Integer integer) throws UsernameNotFoundException {
        RuleName ruleName = ruleNameRepository.findFirstByid(integer);
        return ruleName;
    }
    public void updateBidlist(RuleName ruleName) throws UsernameNotFoundException {
        ruleNameRepository.save(ruleName);
    }
    public void deleteBidlist(Integer id) throws UsernameNotFoundException {
        ruleNameRepository.delete(ruleNameRepository.findFirstByid(id));
    }
}
