package sample.models;

import java.sql.*;
import java.util.ArrayList;

public class Reservation {

    private int cinClient;
    private int numVol;
    private Date dateReservation ;
    private Client client;
    private Vol vol;

    public int getCinClient() {
        return cinClient;
    }

    public void setCinClient(int cinClient) {
        this.cinClient = cinClient;
    }

    public int getNumVol() {
        return numVol;
    }

    public void setNumVol(int numVol) {
        this.numVol = numVol;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Vol getVol() {
        return vol;
    }

    public void setVol(Vol vol) {
        this.vol = vol;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                ", nom Client=" + client.getNomClient()+
                ", prenom Client=" + client.getPrenomClient()+
                ", num vol=" + numVol +
                ", dateReservation=" + dateReservation +
                ", ville départ=" +vol.getVilleDepart().getNomVille()+
                ", ville Arrivée=" +vol.getVilleArrivee().getNomVille()+
                ", date départ=" +vol.getDateDepart()+
                ", heure départ=" +vol.getHeureDepart()+
                ", date arrivée=" +vol.getDateArrivee()+
                ", heure arrivée=" +vol.getHeureDepart()+
                '}';
    }

    public static ArrayList<Reservation> getReservations(){
//        DBConnection dbConnection = new DBConnection();
        Connection connection = DBConnection.getInstance().getConnection();
        ArrayList<Reservation> tableauReservations=new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from reservation");
            while (resultSet.next()){
                Reservation r=new Reservation();
                int cinClient=resultSet.getInt("cin_client");

                Statement statement4=connection.createStatement();
                String querry4="select nom_client,prenom_client from client where cin_client="+cinClient;
                ResultSet resultSet4=statement4.executeQuery(querry4);
                Client client =new Client();
                if(resultSet4.next()){
                    String nomClient=resultSet4.getString("nom_client");
                    String prenomClient=resultSet4.getString("prenom_client");
                    client.setNomClient(nomClient);
                    client.setPrenomClient(prenomClient);
                }


                int numVol=resultSet.getInt("num_vol");

                Vol vol1 =Vol.getVolByID(numVol);

                Date dateReservation=resultSet.getDate("date_reservation");
                r.setCinClient(cinClient);
                r.setNumVol(numVol);
                r.setDateReservation(dateReservation);
                r.setClient(client);
                r.setVol(vol1);
                tableauReservations.add(r);
            }
            resultSet.close();
            return tableauReservations;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }



    public static void insererReservation(Reservation reservation){
        Connection connection=DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into reservation(cin_client,num_vol,date_reservation) values(?,?,?)");
            preparedStatement.setInt(1,reservation.getCinClient());
            preparedStatement.setInt(2,reservation.getNumVol());
            preparedStatement.setDate(3,reservation.getDateReservation());

            preparedStatement.executeUpdate();
            System.out.println("Reservation inserée avec succés !");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }


    public static void updateReservation(Reservation reservation){
        Connection connection=DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update  reservation set date_reservation=? where cin_client=? and num_vol=?");
            preparedStatement.setDate(1,reservation.getDateReservation());
            preparedStatement.setInt(2,reservation.getCinClient());
            preparedStatement.setInt(3,reservation.getNumVol());

            if(preparedStatement.executeUpdate()==1) {
                System.out.println("Reservation modifié avec succés !");
            }
            else
                System.out.println("reservation introuvable");
        }

        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public static Reservation getReservationByID(int numVol,int cinClient){
        Connection connection=DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from reservation where num_vol=? and cin_client=?");
            preparedStatement.setInt(1,numVol);
            preparedStatement.setInt(2,cinClient);
            ResultSet resultSet=preparedStatement.executeQuery();
            Reservation reservation=new Reservation();
            if(resultSet.next()){
                reservation.setNumVol(resultSet.getInt("num_vol"));
                reservation.setCinClient(resultSet.getInt("cin_client"));
                reservation.setDateReservation(resultSet.getDate("date_reservation"));
                resultSet.close();
                return reservation;
            }
            else {
                return null;
            }

        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }



    public static void deleteReservation(Reservation reservation){
        Connection connection = DBConnection.getInstance().getConnection();
        try {

            PreparedStatement preparedStatement = connection.prepareStatement("delete from reservation where cin_client=? and num_vol=?");
            preparedStatement.setInt(1, reservation.getCinClient());
            preparedStatement.setInt(2,reservation.getNumVol());
            int deletedRow =preparedStatement.executeUpdate();

            if(deletedRow>0){
                System.out.println("Réservation supprimée avec succés");
            }
            else
                System.out.println("pas de lignes sur la table réservation");

        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }






}
