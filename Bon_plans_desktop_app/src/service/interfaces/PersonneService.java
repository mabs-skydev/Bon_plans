/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service.interfaces;

import java.util.List;
import service.entities.classes.Personne;

/**
 *
 * @author touir
 */
public interface PersonneService {
    public List<Personne> selectAll();
}