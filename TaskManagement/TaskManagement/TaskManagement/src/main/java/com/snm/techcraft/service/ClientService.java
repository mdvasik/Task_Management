package com.snm.techcraft.service;

import com.snm.techcraft.model.Client;
import com.snm.techcraft.util.SNMResponse;

public interface ClientService {

	SNMResponse addClient(Client client);

	SNMResponse getAllClient(String searchValue);


	SNMResponse editClient(Integer id, Client updatedClient);

	SNMResponse deleteClient(Integer id);

	SNMResponse statusChangeOfClient(Integer id);

	SNMResponse getInactiveClient();


}
