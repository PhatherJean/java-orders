package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Agent;
import com.lambdaschool.orders.services.AgentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agents")
public class AgentController
{
    @Autowired
    AgentsService agentsService;

    @GetMapping(value="/agent/{agentid}")
    public ResponseEntity<?> getAgentById(@PathVariable long agentid)
    {
        Agent a = agentsService.findAgentById(agentid);
        return new ResponseEntity<>(a, HttpStatus.OK);
    }

    @DeleteMapping(value = "/unassigned/{agentid}")
    public ResponseEntity<?> deleteAgentById(@PathVariable long agentid)
    {
        agentsService.deleteUnassigned(agentid);
        return  new ResponseEntity<>(HttpStatus.OK);
    }
}
