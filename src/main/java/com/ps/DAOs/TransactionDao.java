package com.ps.DAOs;

import org.apache.commons.dbcp2.BasicDataSource;

public class TransactionDao {
    private BasicDataSource basicDataSource;
    
    public TransactionDao(BasicDataSource basicDataSource) {
        this.basicDataSource = basicDataSource;
    }
}
