//package com.example.cjlam.borrowe;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by cjlam on 5/27/2016.
 */
public class Cases {
    static Vector<Transactions> transactionsArray;
    static Vector<String> borrowers;
    static HashMap<String, Double> totals;
    static double transtotal = 0;
    //ResultSet tSet;
    //static int transFocus;

    static void addTransaction(String from, double amt, String desc) throws SQLException {
        Shortcuts.inSingleQuotes("INSERT INTO (to,from,amt,desc) transactions VALUES (" +
                Shortcuts.inSingleQuotes(Shortcuts.username) + "," + Shortcuts.inSingleQuotes(from) + "," + amt + Shortcuts.inSingleQuotes(desc) + ")");
        transactionsArray.addElement(new Transactions(-1,from, amt, desc));
        if (!totals.containsKey(from)) {
            addBorrower(from);
            totals.put(from, amt);
        } else {
            totals.put(from, totals.get(from) + amt);
        }
    }

    static void deleteTransaction(Transactions t) throws SQLException {
        Shortcuts.singleQuery("DELETE FROM transactions WHERE id=" + t.id);
        totals.put(t.borrower, totals.get(t.borrower) - t.amount);
    }

    static void getTransactions() throws SQLException {
        ResultSet r = Shortcuts.selectSet("SELECT * FROM transactions WHERE to=" + Shortcuts.username);
        while (r.next()) {
            transactionsArray.addElement(new Transactions(r.getInt("id"), r.getString("from"), r.getDouble("amt"), r.getString("desc")));
            transtotal += r.getDouble("amt");
            if (!totals.containsKey(r.getString("from")))
                totals.put(r.getString("from"), totals.get(r.getString("from") + r.getDouble("amt")));
        }
    }

    //may just go ahead and delete these
    static void getBorrowers() throws SQLException {
        ResultSet r = Shortcuts.selectSet("SELECT * FROM borrowers WHERE giver=" + Shortcuts.inSingleQuotes(Shortcuts.username));
        while (r.next()) {
            totals.put(r.getString("mooch"), 0.0);
        }
    }

    static void addBorrower(String name) throws SQLException {
        boolean isUnique = true;

        if (!totals.containsKey(name)) {
            Shortcuts.singleQuery("INSERT INTO borrowers VALUES (" + Shortcuts.inSingleQuotes(Shortcuts.username) + "," + Shortcuts.inSingleQuotes(name) + ")");
            totals.put(name, 0.0);
        }
    }
}
