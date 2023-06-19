package com.api.internetbanking.mapper;

import com.api.internetbanking.dto.ClientDTO;
import com.api.internetbanking.models.Client;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientMapper {

    public Client mapToEntity(ClientDTO dto) {
        Client client = new Client();
        client.setNome(dto.getNome());
        client.setNumeroConta(dto.getNumeroConta());
        client.setPlanoExclusive(dto.getPlanoExclusive());
        client.setSaldo(dto.getSaldo());
        client.setDataNascimento(dto.getDataNascimento());
        return client;
    }

    public ClientDTO mapToDto(Client client){
        ClientDTO clientDto = new ClientDTO();
        clientDto.setNome(client.getNome());
        clientDto.setNumeroConta(client.getNumeroConta());
        clientDto.setPlanoExclusive(client.getPlanoExclusive());
        clientDto.setSaldo(client.getSaldo());
        clientDto.setDataNascimento(client.getDataNascimento());
        return clientDto;
    }

    public List<ClientDTO> mapToDTOList(List<Client> clients) {
        return clients.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<Client> mapToEntityList(List<ClientDTO> produtoDTOs) {
        return produtoDTOs.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }
}
