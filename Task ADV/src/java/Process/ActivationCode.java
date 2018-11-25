/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Process;

import Beans.Registeration;
import Util.NewHibernateUtil;
import java.util.List;
import java.util.Random;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Pop Shady
 */
public class ActivationCode {

    // Assign Random activation Code
    public static int AssignRandom() {

        Random ran = new Random();

        int randomCode = ran.nextInt(200000);

        return randomCode;

    }

    // check activation Code
    public List<Registeration> checkActivationCod(int Activation_Code) {

        Session se = NewHibernateUtil.getSessionFactory().openSession();

        //opject contain much data id,fname,lnaem ,....
        List<Registeration> users = null;//=new ArrayList<Registeration>();

        Transaction trans = null;
        try {

            System.err.println(">>>>>>" + Activation_Code);

            trans = se.beginTransaction();

            Criteria cri = se.createCriteria(Registeration.class);
            System.err.println(">>>>>>" + Activation_Code);
            users = cri.add(Restrictions.eq("activation_Code", Activation_Code)).list();

            // Query q=se.createQuery("from registeration where UserName = u ");
            // users=q.list();
            // System.err.println(">>>>>>"+Activation_Code);
            //users=se.createQuery("from Register where UserName ='" +userName+"'and Password ='" +  Password + "'").list();
            //System.out.println("dooooooon"+Activation_Code);
        } catch (RuntimeException ex) {

            if (trans != null) {

                trans.rollback();
            }
            //  ex.printStackTrace();
        } finally {

            trans.commit();
            se.close();

        }

        return users;
    }
}
