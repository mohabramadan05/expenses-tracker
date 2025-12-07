package com.expense.server.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    @Test
    void testConnection() {
        assertTrue(Database.testConnection());
    }
}