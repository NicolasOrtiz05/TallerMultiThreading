package com.example;

public class BankAccountExample {
    private static double balance = 1000.00; // Saldo inicial de la cuenta

    public static synchronized void transaction(double amount) {
        balance += amount;
        System.out.println(Thread.currentThread().getName() +
                " realizó una transacción de: " + amount + ". Saldo actual: " + balance);
    }

    public static void main(String[] args) {

        // Creamos varias transacciones (positivas para depósitos, negativas para
        // retiros)
        Runnable depositTask = () -> transaction(200.00); // Deposita $200
        Runnable withdrawalTask = () -> transaction(-150.00); // Retira $150

        // Creamos un conjunto de hilos para realizar las transacciones
        Thread t1 = new Thread(depositTask, "Deposito 1");
        Thread t2 = new Thread(withdrawalTask, "Retiro 1");
        Thread t3 = new Thread(depositTask, "Deposito 2");
        Thread t4 = new Thread(withdrawalTask, "Retiro 2");

        // Iniciamos los hilos
        t1.start();
        t2.start();
        t3.start();
        t4.start();

        // Esperamos a que todos los hilos terminen
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Imprimimos el saldo final
        System.out.println("Saldo final de la cuenta: " + balance);
    }
}
