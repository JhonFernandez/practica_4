package edu.pucmm.controllers;

import edu.pucmm.models.Tag;
import edu.pucmm.models.User;
import edu.pucmm.models.Valoracion;
import edu.pucmm.services.GestionDb;

/**
 * Created by Jhon on 16/6/2017.
 */
public class ValoracionDao extends GestionDb<Valoracion,Integer> {
    private static ValoracionDao instance;

    private ValoracionDao() {
        super(Valoracion.class);
    }

    public static ValoracionDao getInstance(){
        if (instance ==null){
            instance = new ValoracionDao();
        }
        return instance;
    }


}
