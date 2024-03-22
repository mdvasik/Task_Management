package com.snm.techcraft.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.snm.techcraft.model.Client;
import com.snm.techcraft.repository.ClientRepository;
import com.snm.techcraft.service.ClientService;
import com.snm.techcraft.util.SNMResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientRepository clientRepository;

	@Override
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUBADMIN')")
	public SNMResponse addClient(Client client) {
		

		log.info("Inside AddClient Service Method");

		client.setStatus(true);
		clientRepository.save(client);
		return new SNMResponse("Client addded Successfully", 200, null);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUBADMIN') or hasRole('ROLE_USER')")
	public SNMResponse getAllClient(String searchValue) {

		log.info("Inside AllClient Service Method");
		List<Client> clientList = clientRepository.findBySearchValue(searchValue);
		return new SNMResponse("All Client List", 200, clientList);
	}

	@Override
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUBADMIN')")
	public SNMResponse editClient(Integer id, Client updatedClient) {
		Optional<Client> optionalClient = clientRepository.findById(id);

		log.info("Inside EditClient Service Method");

		if (optionalClient.isEmpty()) {
			return new SNMResponse("Client with ID " + id + " not found", HttpStatus.NOT_FOUND.value(), null);
		}

		// Check authorization
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean isAdminOrSubadmin = authentication.getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN")
						|| authority.getAuthority().equals("ROLE_SUBADMIN"));
		if (!isAdminOrSubadmin) {
			throw new AccessDeniedException("You do not have authority to perform this action");
		}

		Client client = optionalClient.get();

		if (updatedClient.getName() != null) {
			client.setName(updatedClient.getName());
		}

		if (updatedClient.getMobile() != 0) {
			client.setMobile(updatedClient.getMobile());
		}

		if (updatedClient.getReference() != null) {
			client.setReference(updatedClient.getReference());
		}

		clientRepository.save(client);

		return new SNMResponse("Client with ID " + id + " updated successfully", HttpStatus.OK.value(), null);
	}

	@Override
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUBADMIN')")
	public SNMResponse deleteClient(Integer id) {

		log.info("Inside DeleteClient Service Method");
		Optional<Client> optionalClient = clientRepository.findById(id);

		if (optionalClient.isEmpty()) {
			return new SNMResponse("Client with ID " + id + " not found", HttpStatus.NOT_FOUND.value(), null);
		}

		Client client = optionalClient.get();
		clientRepository.delete(client);

		return new SNMResponse("Client with ID " + id + " deleted successfully", HttpStatus.OK.value(), null);
	}

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public SNMResponse statusChangeOfClient(Integer id) {
		
		log.info("Inside statusChange Service Method");
		Optional<Client> optionalClient = clientRepository.findById(id);

		if (optionalClient.isEmpty()) {
			return new SNMResponse("Client with ID " + id + " not found", HttpStatus.NOT_FOUND.value(), null);
		}

		Client client = optionalClient.get();
		boolean isActive = client.isStatus();
		client.setStatus(!isActive);

		clientRepository.save(client);

		String message = isActive ? "Client with ID " + id + " deactivated successfully"
				: "Client with ID " + id + " activated successfully";
		return new SNMResponse(message, HttpStatus.OK.value(), null);
	}

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public SNMResponse getInactiveClient() {
		log.info("Inside getInactive Client Service Method");
		List<Client> inactiveClients = clientRepository.findByStatus(false);
		return new SNMResponse("Inactive Clients", HttpStatus.OK.value(), inactiveClients);
	}

}
