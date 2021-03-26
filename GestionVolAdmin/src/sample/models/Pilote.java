package sample.models;

import java.sql.*;
import java.util.ArrayList;

public class Pilote {

    private Integer matricule;
    private String nomPilote;
    private String prenomPilote;
    private String adressePilote;

    public Integer getMatricule() {
        return matricule;
    }

    public void setMatricule(Integer matricule) {
        this.matricule = matricule;
    }

    public String getNomPilote() {
        return nomPilote;
    }

    public void setNomPilote(String nomPilote) {
        this.nomPilote = nomPilote;
    }

    public String getPrenomPilote() {
        return prenomPilote;
    }

    public void setPrenomPilote(String prenomPilote) {
        this.prenomPilote = prenomPilote;
    }

    public String getAdressePilote() {
        return adressePilote;
    }

    public void setAdressePilote(String adressePilote) {
        this.adressePilote = adressePilote;
    }

    @Override
    public String toString() {
        return nomPilote+" "+prenomPilote;
    }

    public static ArrayList<Pilote> getPilotes(){
//        DBConnection dbConnection = new DBConnection();
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet =statement.executeQuery("select * from pilote");
            ArrayList<Pilote> tableauPilotes =new ArrayList<>();
            while(resultSet.next()){
                Pilote p = new Pilote();
                int matricule = resultSet.getInt("matricule");
                String nomPilote = resultSet.getString("nom_pilote");
                String prenomPilote = resultSet.getString("prenom_pilote");
                String adressePilote = resultSet.getString("adresse_pilote");
                p.setMatricule(matricule);
                p.setNomPilote(nomPilote);
                p.setPrenomPilote(prenomPilote);
                p.setAdressePilote(adressePilote);
                tableauPilotes.add(p);
            }
            resultSet.close();
            return tableauPilotes;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void insererPilote(Pilote pilote){
        Connection connection=DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into pilote(nom_pilote,prenom_pilote,adresse_pilote) values(?,?,?)");
            preparedStatement.setString(1,pilote.getNomPilote());
            preparedStatement.setString(2,pilote.getPrenomPilote());
            preparedStatement.setString(3,pilote.getAdressePilote());
            preparedStatement.executeUpdate();
            System.out.println("Pilote inserée avec succés !");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }


    public static void updatePilote(Pilote pilote){
        Connection connection=DBConnection.getInstance().getConnection();
        try{
            PreparedStatement preparedStatement=connection.prepareStatement("update pilote set nom_pilote=?,prenom_pilote=?,adresse_pilote=? where matricule=?");
            preparedStatement.setString(1,pilote.getNomPilote());
            preparedStatement.setString(2,pilote.getPrenomPilote());
            preparedStatement.setString(3,pilote.getAdressePilote());
            preparedStatement.setInt(4,pilote.getMatricule());

            if(preparedStatement.executeUpdate()==1){
                System.out.println("Pilote est modifié avec succés");

            }
            else
            System.out.println("Pilote introuvable");

        }
        catch (SQLException e){
            e.printStackTrace();
        }


    }



    public static Pilote getPiloteByID(int matricule){

        Connection connection=DBConnection.getInstance().getConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("select * from pilote where matricule=?");
            preparedStatement.setInt(1,matricule);
            ResultSet resultSet=preparedStatement.executeQuery();

            Pilote pilote=new Pilote();

            if(resultSet.next()) {
                pilote.setMatricule(resultSet.getInt("matricule"));
                pilote.setNomPilote(resultSet.getString("nom_pilote"));
                pilote.setPrenomPilote(resultSet.getString("prenom_pilote"));
                pilote.setAdressePilote(resultSet.getString("adresse_pilote"));
                resultSet.close();
                return pilote;
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





    public static void deletePilote(Pilote pilote){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from vol where matricule=? ");
            preparedStatement.setInt(1,pilote.getMatricule());
            int rowNumber1=preparedStatement.executeUpdate();

            if(rowNumber1>0){
                System.out.println("le nombre de vols supprimés est : " +rowNumber1);
            }

            preparedStatement = connection.prepareStatement("delete from pilote where matricule=?");
            preparedStatement.setInt(1, pilote.getMatricule());
            int deletedRow =preparedStatement.executeUpdate();

            if(deletedRow>0){
                System.out.println("Pilote supprimé avec succés");
            }
            else
                System.out.println("pas de lignes sur la table pilote");

        }
        catch (SQLException e){
            e.printStackTrace();
        }






    }







}
