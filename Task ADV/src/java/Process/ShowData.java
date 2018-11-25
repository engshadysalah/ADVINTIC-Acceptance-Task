/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Process;

import Beans.Registeration;
import Util.NewHibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Pop Shady
 */
public class ShowData {

    public List GetData(String userName, String Password) {

        Session se = NewHibernateUtil.getSessionFactory().openSession();

        List<Registeration> users = new ArrayList<Registeration>();

        Transaction trans = null;
        try {

            trans = se.beginTransaction();

            Criteria cri = se.createCriteria(Registeration.class);

            Criterion a = Restrictions.eq("userName", userName);

            Criterion b = Restrictions.eq("password", Password);

            LogicalExpression and = Restrictions.and(a, b);

            cri.add(and);

            users = cri.list();

            //1
            //  Criteria cri=se.createCriteria(Registeration.class);
            // users=cri.add(Restrictions.eq("userName", userName )).list();
            //2
            //users=se.createQuery("from registeration where userName ='" +userName+"'and password ='" +  Password + "'").list();
            //3
            //Query q=se.createQuery("from registeration where userName ='" +userName+"'and password ='" +  Password + "'");
            // users=q.list();
          //  System.out.println("dooooooon" + userName + "passsssssss" + Password);

        } catch (RuntimeException ex) {

            if (trans != null) {

                //  setResult("notfound.xhtml");
                //  System.out.println("DDDDDon 1"+FName+PAss +getResult());
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
