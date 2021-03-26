package sample.models;

import java.sql.*;
import java.util.ArrayList;

public class Client {

    private Integer cinClient;
    private String nomClient;
    private String prenomClient;
    private String adresseClient;
    private String username;
    private String password;


    public Integer getCinClient() {
        return cinClient;
    }

    public void setCinClient(Integer cinClient) {
        this.cinClient = cinClient;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getPrenomClient() {
        return prenomClient;
    }

    public void setPrenomClient(String prenomClient) {
        this.prenomClient = prenomClient;
    }

    public String getAdresseClient() {
        return adresseClient;
    }

    public void setAdresseClient(String adresseClient) {
        this.adresseClient = adresseClient;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "Client{" +
                "cinClient=" + cinClient +
                ", nomClient='" + nomClient + '\'' +
                ", prenomClient='" + prenomClient + '\'' +
                ", adresseClient='" + adresseClient + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }



    public static Client login(String username,String password){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement prepared = connection.prepareStatement("select * from client where username=? and password=?");
            prepared.setString(1,username);
            prepared.setString(2,password);
            ResultSet resultSet = prepared.executeQuery();
            if(resultSet.next()){
                Client client = new Client();
                client.setCinClient(resultSet.getInt("cin_client"));
                client.setAdresseClient(resultSet.getString("adresse_client"));
                client.setNomClient(resultSet.getString("nom_client"));
                client.setPrenomClient(resultSet.getString("prenom_client"));
                client.setUsername(username);
                client.setPassword(password);
                System.out.println("login successful");
                return client;
            }
            System.out.println("Login failed");
            return null;
        }catch (SQLException e){
            System.out.println("DB Problem, login failed");
            e.printStackTrace();
            return null;
        }
    }


    public static ArrayList<Client> getClients() {
//       DBConnection dbConnection = new DBConnection();
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from client");
            ArrayList<Client> tableauclients = new ArrayList<>();
            while (resultSet.next()) {
                Client c = new Client();
                int cinClient = resultSet.getInt("cin_client");
                String nomClient = resultSet.getString("nom_client");
                String prenomClient = resultSet.getString("prenom_client");
                String adresseClient = resultSet.getString("adresse_client");
                String username = resultSet.getString("username");
                String password=resultSet.getString("password");
                c.setCinClient(cinClient);
                c.setNomClient(nomClient);
                c.setPrenomClient(prenomClient);
                c.setAdresseClient(adresseClient);
                c.setUsername(username);
                c.setPassword(password);
                tableauclients.add(c);

            }
            resultSet.close();
            return tableauclients;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void insererClient(Client client) {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into client(nom_client,prenom_client,adresse_client,username,password) values(?,?,?,?,?)");
            preparedStatement.setString(1, client.getNomClient());
            preparedStatement.setString(2, client.getPrenomClient());
            preparedStatement.setString(3, client.getAdresseClient());
            preparedStatement.setString(4,client.getUsername());
            preparedStatement.setString(5,client.getPassword());
            preparedStatement.executeUpdate();
            System.out.println("Client inserée avec succés !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void updateClient(Client client) {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update client set nom_client=?,prenom_client=?,adresse_client=?,username=?,password=? where cin_client=?");
            preparedStatement.setString(1, client.getNomClient());
            preparedStatement.setString(2, client.getPrenomClient());
            preparedStatement.setString(3, client.getAdresseClient());
            preparedStatement.setString(4,client.getUsername());
            preparedStatement.setString(5,client.getPassword());
            preparedStatement.setInt(6, client.getCinClient());


            if (preparedStatement.executeUpdate() == 1) {
                System.out.println("Client est modifié avec succés");

            } else
                System.out.println("Client introuvable");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static Client getClientByID(int cinClient){

        Connection connection=DBConnection.getInstance().getConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("select * from client where cin_client=?");
            preparedStatement.setInt(1,cinClient);
            ResultSet resultSet=preparedStatement.executeQuery();

            Client client =new Client();

            if(resultSet.next()) {
                client.setCinClient(resultSet.getInt("cin_client"));
                client.setNomClient(resultSet.getString("nom_client"));
                client.setPrenomClient(resultSet.getString("prenom_client"));
                client.setAdresseClient(resultSet.getString("adresse_client"));
                client.setUsername(resultSet.getString("username"));
                client.setUsername(resultSet.getString("password"));
                resultSet.close();
                return client;
            }
            else{
                return null;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }



    public static void deleteClient(Client client){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from reservation where num_vol=? ");
            preparedStatement.setInt(1,client.getCinClient());
            int rowNumber1=preparedStatement.executeUpdate();

            if(rowNumber1>0){
                System.out.println("le nombre de réservations supprimées est : " +rowNumber1);
            }

            preparedStatement = connection.prepareStatement("delete from client where cin_client=?");
            preparedStatement.setInt(1, client.getCinClient());
            int deletedRow =preparedStatement.executeUpdate();

            if(deletedRow>0){
                System.out.println("client supprimé avec succés");
            }
            else
                System.out.println("pas de lignes sur la table client");

        }
        catch (SQLException e){
            e.printStackTrace();
        }



    }



}
