/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Process;

import Beans.Registeration;
import Util.NewHibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Pop Shady
 */
public class InsertInformation {
    
    
    
     
    Transaction trans = null;

    public void Inseart(Registeration registeration) {

        Session se = NewHibernateUtil.getSessionFactory().openSession();

        try {

            trans = se.beginTransaction();
            
            
            se.save(registeration);
            se.getTransaction().commit();

        } catch (RuntimeException ex) {
            
            if(trans != null){
            
                trans.rollback();
            }
            ex.printStackTrace();
        }
        
        finally {
                se.flush();
                se.close();
    
                }
    }

}
