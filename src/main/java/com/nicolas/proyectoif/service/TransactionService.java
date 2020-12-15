package com.nicolas.proyectoif.service;

import com.nicolas.proyectoif.factory.TransactionFactory;
import com.nicolas.proyectoif.model.Transaction;
import com.nicolas.proyectoif.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {

    private static final String HEADERS_RECHARGE = "NUMERO TARJETA,ULTIMA CARGA,SALDO FINAL";
    private static final String HEADERS_USE = "NUMERO TARJETA,ULTIMO USO,SALDO FINAL";
    private static final String DEFAULT_DIRECTORY = "C:/proyecto_if/data/";
    private static final String FILE_NAME_RECHARGE = "recargas";
    private static final String FILE_NAME_USE = "usos";
    private static final String FILE_EXT = ".csv";
    private static final String PADDING_MASK = "00000000000000000";


    @Autowired
    private TransactionFactory factory;

    @Autowired
    private TransactionRepository repository;


    public void generateRandomToFile(int ammount, boolean padded, Integer month) throws IOException {
        File fout = new File(DEFAULT_DIRECTORY + (padded ? FILE_NAME_RECHARGE : FILE_NAME_USE) + month + FILE_EXT);
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        bw.write(padded ? HEADERS_RECHARGE : HEADERS_USE);
        bw.newLine();
        for (int i = 0; i < ammount; i++) {
            Transaction transaction = padded ? factory.createRandomZeroPadding(month) : factory.createRandomNoPadding(month);
            bw.write(transaction.toString());
            bw.newLine();
        }
        bw.close();
    }

    public void loadFiles() throws IOException, ParseException {
        File dir = new File(DEFAULT_DIRECTORY);
        File[] directoryListing = dir.listFiles();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
        for (File child : directoryListing) {
            List<com.nicolas.proyectoif.entity.Transaction> transactions = new ArrayList<>();
            FileReader fr = new FileReader(child);
            boolean padded = child.getName().contains(FILE_NAME_RECHARGE);
            BufferedReader br = new BufferedReader(fr);
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                com.nicolas.proyectoif.entity.Transaction t = new com.nicolas.proyectoif.entity.Transaction();
                t.setCardNumber(padded ? values[0]: PADDING_MASK.substring(values[0].length()) + values[0]);
                t.setTimeStamp(fmt.parse(values[1]));
                t.setBalance(Integer.parseInt(values[2]));
                t.setType(padded ? "R": "U");
                transactions.add(t);
            }
            fr.close();
            repository.saveAll(transactions);
        }
    }

    public List<com.nicolas.proyectoif.entity.Transaction> findMostRecentTransactions() {
        return repository.findMostRecentTransactions();
    }

    public List<Object> getYearBalance() {
        return repository.getYearBalance();
    }

    public List<Object> getRealBalance() {
        return repository.getRealBalance();
    }

    public List<Object> findLastTxMonthBalance() {
        return  repository.findLastTxMonthBalance();
    }

    public List<Object> findMonthBalance() {
        return repository.findMonthBalance();
    }

    public List<Object> getTxYearCount() {
        return repository.getTxYearCount();
    }

    public List<Object> getTxMonthCount(){
        return repository.getTxMonthCount();
    }
}
