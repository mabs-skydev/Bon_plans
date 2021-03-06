/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package artan.services;

import artan.dbconnections.DataSource;
import artan.entities.Plan;
import artan.entities.Statistique;
import artan.interfaces.IStatistique;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import tn.esprit.bonplans.entity.Categorie;
import tn.esprit.bonplans.service.ICategorie;
import tn.esprit.bonplans.service.implementation.CategorieImpl;

/**
 *
 * @author Mohamed Ali
 */
public class StatistiqueServices implements IStatistique {

    Connection connection = null;

    public StatistiqueServices() {        
        connection = DataSource.getInstance().getConnection();        
    }    

    @Override
    public ArrayList<Statistique> meilleursVentesDuMois(int mois) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Statistique> meilleursVentesDuJours(Date jour) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Statistique> meilleurVenteParCategorie(int idCategorie) {
        //SELECT plan_reservation.idPlan, nb FROM `plan_reservation`, plan WHERE plan_reservation.idPlan=plan.idPlan AND plan.idCategorie = 11 ORDER BY nb DESC LIMIT 10
        ArrayList<Statistique> statistiques = new ArrayList<>();
        String requete = "SELECT plan_reservation.idPlan, nb FROM `plan_reservation`, plan WHERE plan_reservation.idPlan=plan.idPlan AND plan.idCategorie = " + idCategorie + " ORDER BY nb DESC LIMIT 10";        
        
        try {
            Statement statement=connection.createStatement();
            
            ResultSet resultSet =statement.executeQuery(requete);
            
            while(resultSet.next()){
                statistiques.add(new Statistique(resultSet.getInt("idPlan"), resultSet.getInt("nb")));
            }
            
            System.out.println("Requete select effectuée");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return statistiques;
    }
    
    @Override
    public ArrayList<Statistique> pireVenteParCategorie(int idCategorie) {
        ArrayList<Statistique> statistiques = new ArrayList<>();
        String requete = "SELECT plan_reservation.idPlan, nb FROM `plan_reservation`, plan WHERE plan_reservation.idPlan=plan.idPlan AND plan.idCategorie = " + idCategorie + " ORDER BY nb ASC LIMIT 10";        
        
        try {
            Statement statement=connection.createStatement();
            
            ResultSet resultSet =statement.executeQuery(requete);
            
            while(resultSet.next()){
                statistiques.add(new Statistique(resultSet.getInt("idPlan"), resultSet.getInt("nb")));
            }
            
            System.out.println("Requete select effectuée");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return statistiques;
    }

    @Override
    public ArrayList<Statistique> meilleurVenteParPersonne(int idPersonne) {
        //SELECT plan_reservation.idPlan, nb FROM `plan_reservation`, plan WHERE plan_reservation.idPlan=plan.idPlan AND plan.idAnnonceur = 34 ORDER BY nb DESC LIMIT 10
        ArrayList<Statistique> statistiques = new ArrayList<>();
        String requete = "SELECT plan_reservation.idPlan, nb FROM `plan_reservation`, plan WHERE plan_reservation.idPlan=plan.idPlan AND plan.idAnnonceur = " + idPersonne + " ORDER BY nb DESC LIMIT 10";        
        
        try {
            Statement statement=connection.createStatement();
            
            ResultSet resultSet =statement.executeQuery(requete);
            
            while(resultSet.next()){
                statistiques.add(new Statistique(resultSet.getInt("idPlan"), resultSet.getInt("nb")));
            }
            
            System.out.println("Requete select effectuée");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return statistiques;
    }

    @Override
    public ArrayList<Statistique> meilleurDixVentes() {
        
        ArrayList<Statistique> statistiques = new ArrayList<>();
        String requete = "SELECT * FROM `plan_reservation` ORDER BY `nb` DESC LIMIT 10";        
        
        try {
            Statement statement=connection.createStatement();
            
            ResultSet resultSet =statement.executeQuery(requete);
            
            while(resultSet.next()){
                statistiques.add(new Statistique(resultSet.getInt("idPlan"), resultSet.getInt("nb")));
            }
            
            System.out.println("Requete select effectuée");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return statistiques;
    }

    @Override
    public ArrayList<Statistique> pireDixVentes() {
        
        ArrayList<Statistique> statistiques = new ArrayList<>();
        String requete = "SELECT * FROM `plan_reservation` ORDER BY `nb` ASC LIMIT 10";        
        
        try {
            Statement statement=connection.createStatement();
            
            ResultSet resultSet =statement.executeQuery(requete);
            
            while(resultSet.next()){
                statistiques.add(new Statistique(resultSet.getInt("idPlan"), resultSet.getInt("nb")));
            }
            
            System.out.println("Requete select effectuée");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return statistiques;
    }

    @Override
    public HashMap<Integer, ArrayList<Statistique>> meilleurDixVentesParCategorie() {
        StatistiqueServices ss = new StatistiqueServices();
        HashMap<Integer, ArrayList<Statistique>> hashMap = new HashMap<>();
        ICategorie ic = new CategorieImpl();
        ArrayList<Categorie> categories = (ArrayList<Categorie>) ic.selectAll();
        
        for(Categorie c : categories) {
            hashMap.put(c.getIdCategorie(), ss.meilleurVenteParCategorie(c.getIdCategorie()));
        }
        
        return hashMap;
    }

    @Override
    public HashMap<Integer, ArrayList<Statistique>> pireDixVentesParCategorie() {
        StatistiqueServices ss = new StatistiqueServices();
        HashMap<Integer, ArrayList<Statistique>> hashMap = new HashMap<>();
        ICategorie ic = new CategorieImpl();
        ArrayList<Categorie> categories = (ArrayList<Categorie>) ic.selectAll();
        
        for(Categorie c : categories) {
            hashMap.put(c.getIdCategorie(), ss.pireVenteParCategorie(c.getIdCategorie()));
        }
        
        return hashMap;
    }

    @Override
    public ArrayList<Statistique> meilleurDixVentesParPersonne() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Statistique> pireDixVentesParPersonne() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Plan planlePlusCommenter() {
        String requete = "SELECT idPlan, COUNT(*) FROM `commentaire` group by idPlan ORDER BY COUNT(*) DESC LIMIT 1";
        
        PlanServices ps = new PlanServices();
        
        try {
            Statement statement=connection.createStatement();
            
            ResultSet resultSet =statement.executeQuery(requete);
            
            resultSet.next();
            
            System.out.println("Requete select effectuée");
            
            return ps.rechercheParID(resultSet.getInt(1));
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Plan planLeMoinsCommenter() {
        String requete = "SELECT idPlan, COUNT(*) FROM `commentaire` group by idPlan ORDER BY COUNT(*) ASC LIMIT 1";
        
        PlanServices ps = new PlanServices();
        
        try {
            Statement statement=connection.createStatement();
            
            ResultSet resultSet =statement.executeQuery(requete);
            
            resultSet.next();
            
            System.out.println("Requete select effectuée");
            
            return ps.rechercheParID(resultSet.getInt(1));
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Plan planLePlusAimer() {
        
        String requete = "SELECT annonce, COUNT(id) nb FROM avis WHERE avis.avi = 1 GROUP BY avis.annonce ORDER BY nb DESC LIMIT 1";
        
        PlanServices ps = new PlanServices();
        
        try {
            Statement statement=connection.createStatement();
            
            ResultSet resultSet =statement.executeQuery(requete);
            
            resultSet.next();
            
            System.out.println("Requete select effectuée");
            
            return ps.rechercheParID(resultSet.getInt(1));
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Plan planLePlusDetester() {
        
        String requete = "SELECT annonce, COUNT(id) nb FROM avis WHERE avis.avi = 2 GROUP BY avis.annonce ORDER BY nb DESC LIMIT 1";
        
        PlanServices ps = new PlanServices();
        
        try {
            Statement statement=connection.createStatement();
            
            ResultSet resultSet =statement.executeQuery(requete);
            
            resultSet.next();
            
            System.out.println("Requete select effectuée");
            
            return ps.rechercheParID(resultSet.getInt(1));
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
        
    }

    @Override
    public int nombreDesJaimes(Plan plan) {
        String requete = "SELECT count(*) FROM `avis` WHERE avi = 1 AND avis.annonce = " + plan.getIdPlan();    
        
        try {
            Statement statement=connection.createStatement();
            
            ResultSet resultSet =statement.executeQuery(requete);
            
            resultSet.next();
            
            System.out.println("Requete select effectuée");
            
            return resultSet.getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return 0;
    }

    @Override
    public int nombresDesAbominer(Plan plan) {
        
        String requete = "SELECT count(*) FROM `avis` WHERE avi = 2 AND avis.annonce = " + plan.getIdPlan();    
        try {
            Statement statement=connection.createStatement();
            
            ResultSet resultSet =statement.executeQuery(requete);
            
            resultSet.next();
            
            System.out.println("Requete select effectuée");
            
            return resultSet.getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return 0;
        
    }

    @Override
    public int nombreDesPlansPourJour(LocalDate date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int nombreDesPlansParMois(int mois) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double moyenneDesPlansParJour(int mois) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
