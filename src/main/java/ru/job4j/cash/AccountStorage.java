package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        accounts.put(account.id(), account);
        return accounts.containsValue(account);
    }

    public synchronized boolean update(Account account) {
        accounts.put(account.id(), account);
        return accounts.containsValue(account);
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        Optional<Account> rsl = Optional.empty();
        if (accounts.get(id) != null) {
            rsl = Optional.of(accounts.get(id));
        }
        return rsl;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        Account from = accounts.get(fromId);
        Account to = accounts.get(toId);
        boolean rsl = false;
        if (from != null && to != null && from.amount() >= amount) {
            accounts.put(from.id(), new Account(from.id(), from.amount() - amount));
            accounts.put(to.id(), new Account(to.id(), to.amount() + amount));
            rsl = true;
        }
        return rsl;
    }
}
