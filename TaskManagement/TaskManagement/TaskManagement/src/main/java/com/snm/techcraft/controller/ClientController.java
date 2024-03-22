package com.snm.techcraft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.snm.techcraft.model.Client;
import com.snm.techcraft.service.ClientService;
import com.snm.techcraft.util.SNMResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/client")
@Slf4j
public class ClientController {
	
	@Autowired
	private ClientService clientService;
	
	@PostMapping("/addclient")
	public SNMResponse addClient(@RequestBody Client client) {
		return clientService.addClient(client);
	}
	
	
	@GetMapping("/allclient")
	public SNMResponse getAllClient(@RequestParam(name = "searchValue", required = false) String searchValue) {
	    return clientService.getAllClient(searchValue);
	}

	
	
	
	@PutMapping("/editclient/{id}")
	public SNMResponse editClient(@RequestBody Client updatedClient, @PathVariable Integer  id) {
		return clientService.editClient(id,updatedClient);
	}
	
	
	@DeleteMapping("deleteclient/{id}")
	public SNMResponse deleteClient(@PathVariable Integer  id) {
		return clientService.deleteClient(id);
	}
	
	@PutMapping("statuschange/{id}")
	public SNMResponse statusChangeOfClient(@PathVariable Integer  id) {
		return clientService.statusChangeOfClient(id);
	}
	
	@GetMapping("/showinactiveclient")
		public SNMResponse getInactiveClient() {
		return clientService.getInactiveClient();
	}
}