package com.nicolas.proyectoif.repository;

import com.nicolas.proyectoif.entity.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

    @Query(value = "select t1.* from transaction t1 inner join ( select MAX(time_stamp) max_date, card_number from transaction group by card_number )t2 on t1.card_number = t2.card_number and t1.time_stamp = t2.max_date", nativeQuery = true)
    List<Transaction> findMostRecentTransactions();

    @Query(value = "select distinct t.card_number, ifnull(recargas.total, 0) - ifnull(usos.total, 0) saldoAnio\n" +
            "from `transaction` t\n" +
            "left join (\n" +
            "\tselect SUM(balance) total, card_number\n" +
            "\tfrom `transaction` t \n" +
            "\twhere t.`type` = 'R'\n" +
            "\tgroup by card_number) recargas\n" +
            "on t.card_number  = recargas.card_number\n" +
            "left join (\n" +
            "\tselect SUM(balance) total, card_number\n" +
            "\tfrom `transaction` t \n" +
            "\twhere t.`type` = 'U'\n" +
            "\tgroup by card_number) usos\n" +
            "on t.card_number  = usos.card_number" , nativeQuery = true)
    List<Object> getYearBalance();


    @Query(value = "select\n" +
            "\tall_t.card_number,\n" +
            "\tbalance - saldoAnio\n" +
            "from\n" +
            "\t(\n" +
            "\tselect\n" +
            "\t\tdistinct t.card_number,\n" +
            "\t\tifnull(recargas.total, 0) - ifnull(usos.total, 0) saldoAnio\n" +
            "\tfrom\n" +
            "\t\t`transaction` t\n" +
            "\tleft join (\n" +
            "\t\tselect\n" +
            "\t\t\tSUM(balance) total,\n" +
            "\t\t\tcard_number\n" +
            "\t\tfrom\n" +
            "\t\t\t`transaction` t\n" +
            "\t\twhere\n" +
            "\t\t\tt.`type` = 'R'\n" +
            "\t\tgroup by\n" +
            "\t\t\tcard_number) recargas on\n" +
            "\t\tt.card_number = recargas.card_number\n" +
            "\tleft join (\n" +
            "\t\tselect\n" +
            "\t\t\tSUM(balance) total,\n" +
            "\t\t\tcard_number\n" +
            "\t\tfrom\n" +
            "\t\t\t`transaction` t\n" +
            "\t\twhere\n" +
            "\t\t\tt.`type` = 'U'\n" +
            "\t\tgroup by\n" +
            "\t\t\tcard_number) usos on\n" +
            "\t\tt.card_number = usos.card_number ) all_t\n" +
            "inner join (\n" +
            "\tselect\n" +
            "\t\tt1.card_number,\n" +
            "\t\tt1.balance\n" +
            "\tfrom\n" +
            "\t\ttransaction t1\n" +
            "\tinner join (\n" +
            "\t\tselect\n" +
            "\t\t\tMAX(time_stamp) max_date,\n" +
            "\t\t\tcard_number\n" +
            "\t\tfrom\n" +
            "\t\t\ttransaction\n" +
            "\t\tgroup by\n" +
            "\t\t\tcard_number )t2 on\n" +
            "\t\tt1.card_number = t2.card_number\n" +
            "\t\tand t1.time_stamp = t2.max_date ) ultimos_saldos on\n" +
            "\tall_t.card_number = ultimos_saldos.card_number\n", nativeQuery = true)
    List<Object> getRealBalance();


    @Query(value = "select mes, sum(balance)\n" +
            "from (\n" +
            "\tselect month(t.time_stamp) mes, t.card_number, t.balance, max(t.time_stamp)\n" +
            "\tfrom transaction t\n" +
            "\tgroup by month(t.time_stamp), t.card_number\n" +
            ") ultimas_tx\n" +
            "group by mes", nativeQuery = true)
    List<Object> findLastTxMonthBalance();

    @Query(value = "select parciales.mes, sum(parciales.balance), sum(parciales.cuenta)\n" +
            "from (\n" +
            "\tselect month(time_stamp) mes, sum(balance) balance , count(1) cuenta\n" +
            "\tfrom transaction\n" +
            "\tgroup by month(time_stamp), card_number\n" +
            ") parciales\n" +
            "group by parciales.mes", nativeQuery = true)
    List<Object> findMonthBalance();

    @Query(value = "select COUNT(*), t.card_number \n" +
            "from transaction t\n" +
            "group by t.card_number", nativeQuery = true)
    List<Object> getTxYearCount();

    @Query(value = "select month(t.time_stamp), COUNT(t.balance)\n" +
            "from transaction t\n" +
            "group by month(T.time_stamp)\n", nativeQuery = true)
    List<Object> getTxMonthCount();
}
