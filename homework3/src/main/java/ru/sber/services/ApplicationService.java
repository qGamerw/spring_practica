package ru.sber.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.exception.BankClientException;
import ru.sber.exception.TransferByPhoneException;
import ru.sber.model.Client;
import ru.sber.proxies.BankClientsAppProxy;
import ru.sber.proxies.BankClientsInterfaceProxy;
import ru.sber.proxies.TransferByPhoneInterfaceProxy;
import ru.sber.repositories.DBTranslationHistoryRepository;

import java.math.BigDecimal;

@Service
public class ApplicationService {
    private BankClientsInterfaceProxy bankClientsInterfaceProxy;
    private TransferByPhoneInterfaceProxy transferByPhoneInterfaceProxy;
    private DBTranslationHistoryRepository dbTranslationHistoryRepository;

    @Autowired
    public ApplicationService(BankClientsAppProxy bankClientsAppProxy,
                              TransferByPhoneInterfaceProxy transferByPhoneInterfaceProxy,
                              DBTranslationHistoryRepository dbTranslationHistoryRepository) {
        this.bankClientsInterfaceProxy = bankClientsAppProxy;
        this.transferByPhoneInterfaceProxy = transferByPhoneInterfaceProxy;
        this.dbTranslationHistoryRepository = dbTranslationHistoryRepository;
    }

    public void checkClient(Client client) throws BankClientException {
        if (bankClientsInterfaceProxy.getBankClient(client)) {
            System.out.println("Пользователь " + client.getName() + " является клиентом банка.");
        } else {
            throw new BankClientException("Пользователь не является клиентом банка.");
        }
    }

    public void sendMoney(String phone, BigDecimal bigDecimal) {
        try {
            transferByPhoneInterfaceProxy.TransferByPhone(phone, bigDecimal);
            dbTranslationHistoryRepository.addTranslationHistory(phone, bigDecimal);
        } catch (TransferByPhoneException transferByPhoneException) {
            transferByPhoneException.printStackTrace();
        }
    }

    public void printHistory() {
        System.out.println("История платежей");
        dbTranslationHistoryRepository.getTranslationHistory().forEach(System.out::println);
    }
}