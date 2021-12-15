package activeRecord;

import java.sql.SQLException;
import java.util.List;

/**
 * cette classe a juste pour objectif de verifier les noms des methodes
 */
public class CompilationMain {

    public static void main(String[] args) throws SQLException, SerieAbsenteException {
        ///////////////// test serie
        DBConnection.setNomDB("testSerie");


        //creation de la table serie
        Serie.createTable();


        //constructeur
        Serie p;
        p=new Serie("DocteurQui", "SF");
        p.save();
        p=new Serie("Guimauve", "Fantasy");
        p.save();
        p=new Serie("Farscape", "SF");
        System.out.println("**** table cree et tuples ajoutes ***");


        //recherche
        System.out.println("**** tuples present - findall ****");
        List<Serie> liste=Serie.findAll();
        for (Serie plits:liste)
            System.out.println(plits);

        //ajout
        System.out.println("**** ajout de DocteurMaison - save ****");
        p=new Serie("DocteurMaison", "Medical");
        p.save();

        //recuperation
        System.out.println("**** recuperation Guimauve - findbyid 2 ****");
        Serie temp=Serie.findById(2);
        System.out.println(temp);

        //suppression
        System.out.println("**** suppression Guimauve - delete ****");
        temp.delete();
        List<Serie> liste2=Serie.findAll();
        for (Serie plits:liste2)
            System.out.println(plits);


        //recherche
        System.out.println("**** recherche DocteurMaison - findbyname ****");
        temp.delete();
        List<Serie> liste3=Serie.findByName("DocteurMaison");
        for (Serie plits:liste3)
            System.out.println(plits);


        //modification
        Serie s2=liste3.get(0);
        s2.setNom("Docteur Maison");
        s2.setGenre("Science medicale");
        s2.save();
        System.out.println("**** test modification  - save *** ");
        List<Serie> liste4=Serie.findAll();
        for (Serie plits:liste4)
            System.out.println(plits);

        //getter
        s2.getId();
        s2.getNom();
        s2.getGenre();





        ///////////////// test personnage

        //create table
        Personnage.createTable();

        Personnage f=new Personnage("Docteur Maison", s2);
        //sauvegarde
        f.save();

        //finByID
        System.out.println("**** Personnage finbyid **** ");
        Personnage p2=Personnage.findById(1);
        System.out.println(p2);
        System.out.println(p2.getId());
        System.out.println(p2.getNom());
        Serie s3=p2.getSerie();
        System.out.println(s3);

        //setter
        p2.setNom("Docteur Immeuble");
        p2.save();

        //sauve personnage modifie
        System.out.println("**** personnage modifie **** ");
        Personnage p3=Personnage.findById(1);
        System.out.println(p3);
        System.out.println(p3.getId());
        System.out.println(p3.getNom());
        Serie s5=p3.getSerie();
        System.out.println(s5);


        //supprime table prsonnage
        Personnage.deleteTable();
        //suppression de la table serie
        Serie.deleteTable();



    }

}