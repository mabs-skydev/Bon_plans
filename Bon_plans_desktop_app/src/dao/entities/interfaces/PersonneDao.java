/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao.entities.interfaces;

import java.util.List;
import java.util.Map;

/**
 *
 * @author touir
 */
public interface PersonneDao {
    public List<Map<String,Object>> selectAll();
}