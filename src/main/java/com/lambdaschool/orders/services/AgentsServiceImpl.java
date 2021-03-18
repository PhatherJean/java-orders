package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Agent;
import com.lambdaschool.orders.repositories.AgentsRepository;
import com.lambdaschool.orders.repositories.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service(value = "agentsService")
public class AgentsServiceImpl implements AgentsService
{
    @Autowired
    private AgentsRepository agentsrepos;

    @Autowired
    private CustomersRepository custrepos;

    @Override
    public Agent findAgentById(long id) throws
            EntityNotFoundException
    {
        return agentsrepos.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Agent Id "+ id + " Not Found"));
    }

    @Transactional
    @Override
    public void deleteUnassigned(long agentid)
    {
        if (agentsrepos.findById(agentid).isPresent())
        {
            if (custrepos.findFirstByAgent_Agentcode(agentid) == null)
            {
                agentsrepos.deleteById(agentid);
            } else
            {
                throw new EntityNotFoundException("Agent " + agentid + " Not Found!");
            }
        }
    }

}
