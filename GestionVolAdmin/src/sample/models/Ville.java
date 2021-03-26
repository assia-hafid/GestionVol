package sample.models;

import java.sql.*;
import java.util.ArrayList;

public class Ville {

    private Integer codeVille;
    private String nomVille;

    public Integer getCodeVille() {
        return codeVille;
    }

    public void setCodeVille(Integer codeVille) {
        this.codeVille = codeVille;
    }

    public String getNomVille() {
        return nomVille;
    }

    public void setNomVille(String nomVille) {
        this.nomVille = nomVille;
    }
    @Override
    public String toString() {
        return nomVille;
    }

    public static ArrayList<Ville> getVilles(){
//      DBConnection dbConnection = new DBConnection();
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from ville");
            ArrayList<Ville> tableauVilles = new ArrayList<>();
            while (resultSet.next()){
                Ville v = new Ville();
                int codeVille = resultSet.getInt("code_ville");
                String nomVille = resultSet.getString("nom_ville");
                v.setCodeVille(codeVille);
                v.setNomVille(nomVille);
                tableauVilles.add(v);
            }
            resultSet.close();
            return tableauVilles;

        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void insertVille(Ville ville){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into ville(nom_ville) values(?)");
            preparedStatement.setString(1,ville.getNomVille());
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("insert into ville(nomVille) values(' "+ville.getNomVille()+" ')");
            preparedStatement.executeUpdate();
            System.out.println("Ville inserée avec succés !");
        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }

    public static void updateVille(Ville ville){
        Connection connection=DBConnection.getInstance().getConnection();
        try{
            PreparedStatement preparedStatement=connection.prepareStatement("update ville set nom_ville=? where code_ville=?");

            preparedStatement.setString(1,ville.getNomVille());
            preparedStatement.setInt(2,ville.getCodeVille());

            if(preparedStatement.executeUpdate()==1){
                System.out.println("la ville est modifiée avec sucées");
            }
            else {
                System.out.println("ville introuvable !!");
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }


    }



    public static Ville getVilleByID(int codeVille){
        Connection connection=DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from ville where code_ville=?");
            preparedStatement.setInt(1, codeVille);
            ResultSet resultSet = preparedStatement.executeQuery();

            Ville ville = new Ville();
            if (resultSet.next()) {
                ville.setNomVille(resultSet.getString("nom_ville"));
                ville.setCodeVille(resultSet.getInt("code_ville"));
                resultSet.close();
                return ville;
            } else {
                return null;
            }
        }

        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }



    public static void deleteVille(Ville ville){

        Connection connection=DBConnection.getInstance().getConnection();

        try {
            PreparedStatement preparedStatement =connection.prepareStatement("select num_vol from vol where code_ville_depart=? or code_ville_arrivee=?");
            preparedStatement.setInt(1,ville.getCodeVille());
            preparedStatement.setInt(2,ville.getCodeVille());
            ResultSet resultSet=preparedStatement.executeQuery();

            while(resultSet.next()){
                preparedStatement=connection.prepareStatement("delete from reservation where num_vol=?");
                preparedStatement.setInt(1,resultSet.getInt("num_vol"));
                int rowNumber=preparedStatement.executeUpdate();

                if(rowNumber>0){
                    System.out.println("le nombre de réservations supprimés sont : " +rowNumber);
                }

            }

            preparedStatement = connection.prepareStatement("delete from vol where code_ville_depart=? or code_ville_arrivee=?");
            preparedStatement.setInt(1, ville.getCodeVille());
            preparedStatement.setInt(2,ville.getCodeVille());
            int rowNumber1=preparedStatement.executeUpdate();

            if(rowNumber1>0){
                System.out.println("le nombre de vols supprimés est : " +rowNumber1);
            }

            preparedStatement = connection.prepareStatement("delete from ville where code_ville=?");
            preparedStatement.setInt(1, ville.getCodeVille());
            int deletedRow =preparedStatement.executeUpdate();

            if(deletedRow>0){
                System.out.println("ville supprimée avec succés");
            }
            else
                System.out.println("pas de lignes sur la table ville");


            System.out.println("la ligne est suprimée avec succés");
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }







}
