package com.coveo;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import com.sun.management.UnixOperatingSystemMXBean;

public class Main
{
    // Fake url for connection timeout simulation (works with the real url too if no connection can be made)
    private static final String URL = "jdbc:redshift://coveo.com:5439/database";
    private static final String USER = "user";
    private static final String PASSWORD = "password";

    public static void main(String[] args) throws Exception
    {
        // Dynamically load driver at runtime.
        Class.forName("com.amazon.redshift.jdbc41.Driver");

        Properties properties = new Properties();

        properties.setProperty("user", USER);
        properties.setProperty("password", PASSWORD);
        properties.setProperty("loginTimeout", "1"); // for faster timeouts

        for (int i = 0;; i++) {
            System.out.println("Connecting...");

            Connection connection = null;

            try {
                connection = DriverManager.getConnection(URL, properties);
            } catch (Exception e) {
                System.out.println("Failed : " + e.getMessage());
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }

            if (i % 10 == 0) {
                OperatingSystemMXBean os = ManagementFactory.getOperatingSystemMXBean();

                if (os instanceof UnixOperatingSystemMXBean) {
                    System.out.println("Number of open file descriptor: "
                            + ((UnixOperatingSystemMXBean) os).getOpenFileDescriptorCount());
                }
            }
        }
    }
}