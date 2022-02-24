package org.isi.bank.account.domain.transfer.repository;


import lombok.extern.slf4j.Slf4j;
import org.isi.bank.account.domain.transfer.Transfer;
import org.isi.bank.account.port.out.TransferPort;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.OptionalInt;
/*
   Class Repository that contain method to access to Transfer Repository
   Using @Component to Make class as singleton
*/

@Component
@Slf4j
public class TransferRepository implements TransferPort {

    private Map<Integer, Transfer> transferMap;

    public TransferRepository() {
        log.info("init map transfer repo");
        this.transferMap = new HashMap<>();
    }

    @Override
    public Transfer createTransfer(Transfer transfer) {
        log.info("Create Transfer Entity with info {}", transfer);
        transfer.setId(getGeneratedId());
        transferMap.put(transfer.getIdTransfer(), transfer);
        return transferMap.get(transfer.getIdTransfer());
    }

    private int getGeneratedId() {
        log.info("generate id");
        OptionalInt generatedIdOpt = this.transferMap.keySet().stream().mapToInt(v -> v).max();
        int generatedId = 1;
        if (generatedIdOpt.isPresent()) {
            generatedId = generatedIdOpt.getAsInt() + 1;
        }
        return generatedId;
    }
}
