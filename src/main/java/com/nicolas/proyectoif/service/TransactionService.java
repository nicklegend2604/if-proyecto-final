package com.nicolas.proyectoif.service;

import com.nicolas.proyectoif.factory.TransactionFactory;
import com.nicolas.proyectoif.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class TransactionService {

    private static final String HEADERS_RECHARGE = "NUMERO TARJETA,ULTIMA CARGA,SALDO FINAL";
    private static final String HEADERS_USE = "NUMERO TARJETA,ULTIMO USO,SALDO FINAL";
    private static final String DEFAULT_DIRECTORY = "C:/proyecto_if/data/";
    private static final String FILE_NAME_RECHARGE = "recargas";
    private static final String FILE_NAME_USE = "usos";
    private static final String FILE_EXT = ".csv";


    @Autowired
    private TransactionFactory factory;


    public void generateRandomToFile(int ammount, boolean padded, Integer month) throws IOException {
        File fout = new File(DEFAULT_DIRECTORY + (padded ? FILE_NAME_RECHARGE : FILE_NAME_USE) + month + FILE_EXT);
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        bw.write(padded ? HEADERS_RECHARGE : HEADERS_USE);
        bw.newLine();
        for (int i = 0; i < ammount; i++) {
            Transaction transaction = padded ? factory.createRandomZeroPadding() : factory.createRandomNoPadding();
            bw.write(transaction.toString());
            bw.newLine();
        }
        bw.close();
    }
}
