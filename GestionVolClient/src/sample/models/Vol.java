package sample.models;

import java.sql.*;
import java.util.ArrayList;

public class Vol {

    private Integer numVol;
    private Date dateDepart;
    private Date dateArrivee;
    private Time heureDepart;
    private Time heureArrivee;
    private Integer matricule;
    private Integer codeVilleDepart;
    private Integer codeVilleArrivee;
    private Pilote pilote;
    private Ville villeDepart;
    private Ville villeArrivee;

    public Integer getNumVol() {
        return numVol;
    }

    public void setNumVol(Integer numVol) {
        this.numVol = numVol;
    }

    public Date getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(Date dateDepart) {
        this.dateDepart = dateDepart;
    }

    public Date getDateArrivee() {
        return dateArrivee;
    }

    public void setDateArrivee(Date dateArrivee) {
        this.dateArrivee = dateArrivee;
    }

    public Time getHeureDepart() {
        return heureDepart;
    }

    public void setHeureDepart(Time heureDepart) {
        this.heureDepart = heureDepart;
    }

    public Time getHeureArrivee() {
        return heureArrivee;
    }

    public void setHeureArrivee(Time heureArrivee) {
        this.heureArrivee = heureArrivee;
    }

    public Integer getMatricule() {
        return matricule;
    }

    public void setMatricule(Integer matricule) {
        this.matricule = matricule;
    }

    public Integer getCodeVilleDepart() {
        return codeVilleDepart;
    }

    public void setCodeVilleDepart(Integer codeVilleDepart) {
        this.codeVilleDepart = codeVilleDepart;
    }

    public Integer getCodeVilleArrivee() {
        return codeVilleArrivee;
    }

    public void setCodeVilleArrivee(Integer codeVilleArrivee) {
        this.codeVilleArrivee = codeVilleArrivee;
    }

    public Pilote getPilote() {
        return pilote;
    }

    public void setPilote(Pilote pilote) {
        this.pilote = pilote;
    }

    public Ville getVilleDepart() {
        return villeDepart;
    }

    public void setVilleDepart(Ville villeDepart) {
        this.villeDepart = villeDepart;
    }

    public Ville getVilleArrivee() {
        return villeArrivee;
    }

    public void setVilleArrivee(Ville villeArrivee) {
        this.villeArrivee = villeArrivee;
    }

    @Override
    public String toString() {
        return "Vol{" +
                "numVol=" + numVol +
                ", dateDepart=" + dateDepart +
                ", dateArrivee=" + dateArrivee +
                ", heureDepart=" + heureDepart +
                ", heureArrivee=" + heureArrivee +
                ", Ville Depart=" + villeDepart.getNomVille()+
                ", Ville Arrivee=" + villeArrivee.getNomVille() +
                ", nom pilote=" + pilote.getNomPilote() +
                ", prenom pilote=" + pilote.getPrenomPilote() +
                '}';
    }
    public boolean equals(Object obj) {
        return ((Vol) obj).getNumVol().equals(numVol);
    }

    public static Vol getVolByID(int numVol){

        Connection connection = DBConnection.getInstance().getConnection();
        try{

            Statement statement=connection.createStatement();
            ResultSet resultSet =statement.executeQuery("select * from vol where num_vol="+numVol);
            Vol vol =new Vol();

            resultSet.next();

                Date dateDepart = resultSet.getDate("date_depart");
                Date dateArrivee = resultSet.getDate("date_arrivee");
                Time heureDepart = resultSet.getTime("heure_depart");
                Time heureArrivee = resultSet.getTime("heure_arrivee");


            int matricule = resultSet.getInt("matricule");
            Statement statement1=connection.createStatement();
            String querry1 = "select nom_pilote,prenom_pilote from pilote where matricule="+matricule;
            ResultSet resultSet1 = statement1.executeQuery(querry1);
            Pilote pilote=new Pilote();
            if(resultSet1.next()) {
                String nomPilote = resultSet1.getString("nom_pilote");
                String prenomPilote = resultSet1.getString("prenom_pilote");
                pilote.setNomPilote(nomPilote);
                pilote.setPrenomPilote(prenomPilote);
            }
            int codeVilleDepart=resultSet.getInt("code_ville_depart");
            Statement statement2=connection.createStatement();
            String querry2="select nom_ville from ville where code_ville="+codeVilleDepart;
            ResultSet resultSet2 = statement2.executeQuery(querry2);
            Ville villeD=new Ville();
            if(resultSet2.next()) {
                String nomVilleDepart = resultSet2.getString("nom_ville");
                villeD.setNomVille(nomVilleDepart);
            }
            int codeVilleArrivee=resultSet.getInt("code_ville_arrivee");
            Statement statement3=connection.createStatement();
            String querry3="select nom_ville from ville where code_ville="+codeVilleArrivee;
            ResultSet resultSet3 = statement3.executeQuery(querry3);
            Ville villeA=new Ville();
            if(resultSet3.next()) {
                String nomVilleArrivee = resultSet3.getString("nom_ville");
                villeA.setNomVille(nomVilleArrivee);
            }
            vol.setCodeVilleArrivee(codeVilleArrivee);
            vol.setCodeVilleDepart(codeVilleDepart);
            vol.setMatricule(matricule);
            vol.setHeureDepart(heureDepart);
            vol.setHeureArrivee(heureArrivee);
            vol.setDateArrivee(dateArrivee);
            vol.setDateDepart(dateDepart);
            vol.setNumVol(numVol);
            vol.setPilote(pilote);
            vol.setVilleDepart(villeD);
            vol.setVilleArrivee(villeA);
            resultSet.close();
            return vol;
        }

        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



    public static ArrayList<Vol> getVols(){

//      DBConnection dbConnection = new DBConnection();
        Connection connection = DBConnection.getInstance().getConnection();
        ArrayList<Vol> tableauVols=new ArrayList<>();
        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from vol");
            while(resultSet.next()){
                Vol v = new Vol();
                int numVol=resultSet.getInt("num_vol");
                Date dateDepart=resultSet.getDate("date_depart");
                Date dateArrivee=resultSet.getDate("date_arrivee");
                Time heureDepart=resultSet.getTime("heure_depart");
                Time heureArrivee=resultSet.getTime("heure_arrivee");
                int matricule=resultSet.getInt("matricule");

                Statement statement1=connection.createStatement();
                String querry1 = "select * from pilote where matricule="+matricule;
                ResultSet resultSet1 = statement1.executeQuery(querry1);
                Pilote pilote=new Pilote();
                if(resultSet1.next()) {
                    String nomPilote = resultSet1.getString("nom_pilote");
                    String prenomPilote = resultSet1.getString("prenom_pilote");
                    String adressePilote= resultSet1.getString("adresse_pilote");
                    pilote.setMatricule(matricule);
                    pilote.setNomPilote(nomPilote);
                    pilote.setPrenomPilote(prenomPilote);
                    pilote.setAdressePilote(adressePilote);
                }

                int codeVilleDepart=resultSet.getInt("code_ville_depart");

                Statement statement2=connection.createStatement();
                String querry2="select * from ville where code_ville="+codeVilleDepart;
                ResultSet resultSet2 = statement2.executeQuery(querry2);
                Ville villeD=new Ville();
                if(resultSet2.next()) {

                    String nomVilleDepart = resultSet2.getString("nom_ville");
                    villeD.setNomVille(nomVilleDepart);
                    villeD.setCodeVille(codeVilleDepart);
                }

                int codeVilleArrivee=resultSet.getInt("code_ville_arrivee");

                Statement statement3=connection.createStatement();
                String querry3="select * from ville where code_ville="+codeVilleArrivee;
                ResultSet resultSet3 = statement3.executeQuery(querry3);
                Ville villeA=new Ville();
                if(resultSet3.next()) {
                    String nomVilleArrivee = resultSet3.getString("nom_ville");
                    villeA.setNomVille(nomVilleArrivee);
                    villeA.setCodeVille(codeVilleArrivee);
                }



                v.setCodeVilleArrivee(codeVilleArrivee);
                v.setCodeVilleDepart(codeVilleDepart);
                v.setMatricule(matricule);
                v.setHeureDepart(heureDepart);
                v.setHeureArrivee(heureArrivee);
                v.setDateArrivee(dateArrivee);
                v.setDateDepart(dateDepart);
                v.setNumVol(numVol);
                v.setPilote(pilote);
                v.setVilleDepart(villeD);
                v.setVilleArrivee(villeA);

                tableauVols.add(v);
            }
            resultSet.close();
            return tableauVols;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void insererVol(Vol vol){
        Connection connection= DBConnection.getInstance().getConnection();
        try {
//            Statement statement=connection.createStatement();
//            ResultSet resultSet =statement.executeQuery("insert into vol(date_depart,date_arrivee,heure_depart,heure_arrivee,matricule,code_ville_depart,code_vile_arrivee) values(?,?,?,?,?,?,?)");
            PreparedStatement preparedStatement = connection.prepareStatement("insert into vol(date_depart,date_arrivee,heure_depart,heure_arrivee,matricule,code_ville_depart,code_ville_arrivee) values(?,?,?,?,?,?,?)");

            preparedStatement.setDate(1,vol.getDateDepart());
            preparedStatement.setDate(2,vol.getDateArrivee());
            preparedStatement.setTime(3,vol.getHeureDepart());
            preparedStatement.setTime(4,vol.getHeureArrivee());
            preparedStatement.setInt(5,vol.getMatricule());
            preparedStatement.setInt(6,vol.getCodeVilleDepart());
            preparedStatement.setInt(7,vol.getCodeVilleArrivee());

            preparedStatement.executeUpdate();
            System.out.println("Pilote inserée avec succés !");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }




    public static void updateVol(Vol vol) {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update vol set date_depart=?,date_arrivee=?,heure_depart=?,heure_arrivee=?,matricule=?,code_ville_depart=?,code_ville_arrivee=? where num_vol=?" );
            preparedStatement.setDate(1,vol.getDateDepart());
            preparedStatement.setDate(2,vol.getDateArrivee());
            preparedStatement.setTime(3,vol.getHeureDepart());
            preparedStatement.setTime(4,vol.getHeureArrivee());
            preparedStatement.setInt(5,vol.getMatricule());
            preparedStatement.setInt(6,vol.getCodeVilleDepart());
            preparedStatement.setInt(7,vol.getCodeVilleArrivee());
            preparedStatement.setInt(8,vol.getNumVol());

            if (preparedStatement.executeUpdate() == 1) {
                System.out.println("Vol est modifié avec succés");

            } else
                System.out.println("Vol introuvable");

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    public static Vol getRowByID(int numVol){
        Connection connection= DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from vol where num_vol=?");
            preparedStatement.setInt(1,numVol);
            ResultSet resultSet=preparedStatement.executeQuery();
            Vol vol=new Vol();
            if(resultSet.next()){
                vol.setNumVol(resultSet.getInt("num_vol"));
                vol.setDateDepart(resultSet.getDate("date_depart"));
                vol.setDateArrivee(resultSet.getDate("date_arrivee"));
                vol.setHeureDepart(resultSet.getTime("heure_depart"));
                vol.setHeureArrivee(resultSet.getTime("heure_arrivee"));
                vol.setMatricule(resultSet.getInt("matricule"));
                vol.setCodeVilleDepart(resultSet.getInt("code_ville_depart"));
                vol.setCodeVilleArrivee(resultSet.getInt("code_ville_arrivee"));
                resultSet.close();
                return vol;
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




    public static void deleteVol(Vol vol){
        Connection connection = DBConnection.getInstance().getConnection();
        try {

            PreparedStatement preparedStatement = connection.prepareStatement("delete from vol where num_vol=?");
            preparedStatement.setInt(1, vol.getNumVol());
            int deletedRow =preparedStatement.executeUpdate();

            if(deletedRow>0){
                System.out.println("vol supprimée avec succés");
            }
            else
                System.out.println("pas de lignes sur la table vol");

        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }
}

