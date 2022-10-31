package com.example.flowershop;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class FlowershopDAO {

    private String dbPath = "jdbc:sqlite:./flowershop.db";

    public void FlowershopDAO() {
        final String createFlowerTable = "CREATE TABLE IF NOT EXISTS flowers(" +
                "flowerID       INTEGER PRIMARY KEY," +
                "name           TEXT," +
                "description    TEXT," +
                "purchasePrice  FLOAT," +
                "sellPrice      FLOAT," +
                "amount         INTEGER);";

        final String createBouquetTable = "CREATE TABLE IF NOT EXISTS bouquets(" +
                "bouquetID      INTEGER PRIMARY KEY," +
                "name           TEXT," +
                "description    TEXT," +
                "sellPrice      FLOAT," +
                "amount         INTEGER);";

        // TODO: herausfinden wie wir die anzahl der blumen aus denen ein Straus besteht speichern
        // Nicht atomar: wir speichern in eine Spalte die anzahl der Blumen, trennen mit Komma und lesen dann aus
        // Atomar: wir erstellen eine neue Tabelle wo die Anzahl der Blumen in extra Spalten gespeichert wird
        //         zu der dazugehörenden ID eines Straus

        // bessere idee gefunden. Sobald eine neue blume dazukommt erstellen wir eine neu Spalte bei den sträusen
        // da steht bei default 0, aber wenn wir diesem Strauß blumen zuweisen, ändert sich auch die zahl.
        // wenn wir eine Blume aus flowers löschen löscht sich auch die Spalte bei bouquets... oder gleich der ganze
        // straus, weiß noch nicht was mehr sinn macht <-- können wir eigentlich auch in einer extra tabelle lösen

        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            statement.execute(createFlowerTable);
            statement.execute(createBouquetTable);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbPath);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public Flower getFlower(int id) {
        String SQL = "SELECT * FROM flowers WHERE flowerID=?";

        String name = null;
        String description = null;
        Float purchasePrice = null;
        Float sellPrice = null;
        int amount = 0;

        try (Connection connection = connect()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, 1); // hier flowerID benutzen

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                name = resultSet.getString(2);
                description = resultSet.getString(3);
                purchasePrice = resultSet.getFloat(4);
                sellPrice = resultSet.getFloat(5);
                amount = resultSet.getInt(6);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new Flower(id, name, description, purchasePrice, sellPrice, amount);
    }

    public List<Flower> queryAllFlowers() {
        String SQL = "SELECT * FROM flowers;";

        List<Flower> flowerList = new LinkedList<>();

        try (Connection connection = connect()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String description = resultSet.getString(3);
                Float purchasePrice = resultSet.getFloat(4);
                Float sellPrice = resultSet.getFloat(5);
                int amount = resultSet.getInt(6);
                flowerList.add(new Flower(id, name, description, purchasePrice, sellPrice, amount));
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return flowerList;
    }

    public void insertFlower(Flower flower) {
        String SQL = "INSERT INTO flowers (name, description, purchasePrice, sellPrice, amount) VALUES (?, ?, ?, ?, ?);";

        try (Connection connection = connect()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, flower.getName());
            preparedStatement.setString(2, flower.getDescription());
            preparedStatement.setFloat(3, flower.getPurchasePrice());
            preparedStatement.setFloat(4, flower.getSellPrice());
            preparedStatement.setInt(5, flower.getAmount());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateFlower(int amount, int id) {
        String SQL = "UPDATE flowers SET amount=? WHERE flowerID=?";

        try (Connection connection = connect()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, id); // hier amount vom dialog nehmen
            preparedStatement.setInt(2, amount); // hier id eingeben von param flower
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteFlower() {
        String SQL = "DELETE FROM flowers WHERE flowerID=?;";

        try (Connection connection = connect()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, 1); // hier ID von flower nehmen
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //   Bouquet
    public void getBouquet() {
        String SQL = "";
    }


    //
    public List<Bouquet> queryAllBouquets() {
        String SQL = "SELECT * FROM bouquets;";

        List<Bouquet> bouquetList = new LinkedList<>();


        try (Connection connection = connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String description = resultSet.getString(3);
                Float sellPrice = resultSet.getFloat(4);
                int amount = resultSet.getInt(5);
                bouquetList.add(new Bouquet(id, name, description, sellPrice, amount));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bouquetList;
    }

    public void insertBouquet() {
        String SQL = "INSERT INTO bouquets (name, description, sellPrice, amount) VALUES (?, ?, ?, ?);";

        try (Connection connection = connect()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, "roseBouquet");
            preparedStatement.setString(2, "very nice");
            preparedStatement.setFloat(3, 1.33f);
            preparedStatement.setInt(4, 2);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        //todo hier eine schleife welche die blumennamen und deren anzahl ausliest und diese in der Tabelle updated

        // todo hier updateflower aufrufen um die anzahl zu berechnen

    }

    // diese Funktion wird immer dann aufgerufen wenn es weniger oder Mehr Blumen werden, oder wenn ein neuer
    // Strauß erstellt wird. Sie berechnet die Anzahl der Sträuße aus
    public void updateBouquet() {
        String SQL = "";
    }

    public void deleteBouquet() {
        String SQL = "";
    }

    public void clearFlowerTable() {
        String SQL = "DELETE FROM flowers;";

        try (Connection connection = connect();){
            Statement statement = connection.createStatement();
            statement.execute(SQL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void clearBouquetTable() {
        String SQL = "DELETE FROM bouquets;";

        try (Connection connection = connect();){
            Statement statement = connection.createStatement();
            statement.execute(SQL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addFlowerColumnToBouquetTable(String columnName) {

        String alterPart1 = "ALTER TABLE bouquets ADD ";
        String alterPart2 = columnName + " INTEGER;";
        String SQL = alterPart1 + alterPart2;

        try (Connection connection = connect()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void deleteFlowerColumnFromBouquetTable(String columnName) {
        String part1 = "ALTER TABLE bouquets DROP COLUMN ";
        String part2 = ";";
        String SQL = part1 + columnName + part2;

        try (Connection connection = connect()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<String> getFlowerNames() {
        String SQL = "SELECT name FROM flowers;";

        List<String> flowerList = new LinkedList<>();

        try (Connection connection = connect()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString(1);
                flowerList.add(name);
            }
            System.out.println(flowerList);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return flowerList;
    }
}
