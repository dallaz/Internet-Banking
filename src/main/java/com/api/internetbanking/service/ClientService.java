package com.api.internetbanking.service;

import com.api.internetbanking.dto.ClientDTO;
import com.api.internetbanking.dto.TransactionRequestDTO;
import com.api.internetbanking.mapper.ClientMapper;
import com.api.internetbanking.models.Client;
import com.api.internetbanking.models.Historic;
import com.api.internetbanking.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private HistoricService historicService;

    @Autowired
    private ClientMapper clientMapper;

    public ClientDTO findByNumeroConta(String numeroConta) {

        if (clientRepository.findByNumeroConta(numeroConta) != null) {
            return clientMapper.mapToDto(clientRepository.findByNumeroConta(numeroConta));
        }
        return null;
    }

    public void saveClient(ClientDTO client) {
        clientRepository.save(clientMapper.mapToEntity(client));
    }

    public List<ClientDTO> findAllClients() {
        return clientMapper.mapToDTOList(clientRepository.findAll().stream().toList());
    }

    public Client deposit(String numeroConta, TransactionRequestDTO transactionRequestDTO) {
        BigDecimal valor = transactionRequestDTO.getValor();

        Client client = new Client();
        client = clientRepository.findByNumeroConta(numeroConta);

        BigDecimal saldo = client.getSaldo();

        BigDecimal depositResult = saldo.add(valor);

        client.setSaldo(depositResult);

        saveHistoric(client, "ISENTO", valor, "DEPOSITO", bigDecimalMonetaryFormat(depositResult));
        return clientRepository.save(client);
    }

    public Client withdraw(String numeroConta, TransactionRequestDTO transactionRequestDTO) {

        return clientRepository.save(withdrawRules(getClient(numeroConta), transactionRequestDTO.getValor()));
    }

    public Client withdrawRules(Client client, BigDecimal valor) {

        if (checkBalance(client.getSaldo(), valor)) {
            if (!client.getPlanoExclusive()) {
                if (valor.compareTo(new BigDecimal("100")) >= 0 && valor.compareTo(new BigDecimal("300")) <= 0) {

                    BigDecimal rate = valor.multiply(new BigDecimal(0.04).divide(new BigDecimal("100")));
                    BigDecimal rateAndWithdraw = bigDecimalMonetaryFormat(valor.add(rate));

                    if (checkWithdrawWithRate(client.getSaldo(), rateAndWithdraw)) {

                        client.setSaldo(bigDecimalMonetaryFormat(client.getSaldo().subtract(rateAndWithdraw)));

                        saveHistoric(client, String.valueOf(bigDecimalMonetaryFormat(rate)), valor, "SAQUE", bigDecimalMonetaryFormat(client.getSaldo().subtract(rateAndWithdraw)));
                        return client;

                    } else {
                        return null;
                    }
                }
                if (valor.compareTo(new BigDecimal("300")) > 0) {

                    BigDecimal rate = valor.multiply(new BigDecimal(0.1).divide(new BigDecimal("100")));
                    BigDecimal rateAndWithdraw = bigDecimalMonetaryFormat(valor.add(rate));

                    if (checkWithdrawWithRate(client.getSaldo(), rateAndWithdraw)) {

                        saveHistoric(client, String.valueOf(bigDecimalMonetaryFormat(rate)), valor, "SAQUE", bigDecimalMonetaryFormat(client.getSaldo().subtract(rateAndWithdraw)));
                        return client;

                    } else {
                        return null;
                    }
                } else {
                    if (checkWithdrawWithRate(client.getSaldo(), new BigDecimal(0))) {
                        client.setSaldo(bigDecimalMonetaryFormat(client.getSaldo().subtract(valor)));
                        saveHistoric(client, "ISENTO", valor, "SAQUE", bigDecimalMonetaryFormat(client.getSaldo().subtract(valor)));
                        return client;
                    }
                }
            } else {
                if (checkWithdrawWithRate(client.getSaldo(), new BigDecimal(0))) {
                    client.setSaldo(bigDecimalMonetaryFormat(client.getSaldo().subtract(valor)));
                    saveHistoric(client, "ISENTO", valor, "SAQUE", bigDecimalMonetaryFormat(client.getSaldo()));
                    return client;
                }
            }
        }
        return client;
    }

    public Client getClient(String numeroConta) {
        Client client = new Client();
        return clientRepository.findByNumeroConta(numeroConta);
    }

    public Boolean checkBalance(BigDecimal balance, BigDecimal valor) {
        if (balance.compareTo(valor) >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkWithdrawWithRate(BigDecimal balance, BigDecimal rateAndWithdraw) {
        if (rateAndWithdraw.compareTo(balance) < 0) {
            return true;
        } else {
            return false;
        }
    }

    public BigDecimal bigDecimalMonetaryFormat(BigDecimal valor) {
        return valor.setScale(2, RoundingMode.DOWN);
    }

    public void saveHistoric(Client client, String taxa, BigDecimal valor, String tipoTranscao, BigDecimal saldoAtual) {
        DateTimeFormatter novoFormato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate actualDate = LocalDate.now();
        actualDate.format(novoFormato);

        Historic historic = new Historic();
        historic.setNome(client.getNome());
        historic.setNumeroConta(client.getNumeroConta());
        historic.setSaldoAnterior(client.getSaldo());
        historic.setSaldoAtual(saldoAtual);
        historic.setTaxa(taxa);
        historic.setValor(valor);
        historic.setTipoTransacao(tipoTranscao);
        historic.setData(actualDate);

        historicService.saveHistoric(historic);

    }

}
