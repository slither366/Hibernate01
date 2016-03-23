package mapeos;

import hibernatenormal.Contacto;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ContactosDAO {

    private Session session;
    private Transaction tx;

    private void iniciaOperacion() throws HibernateException {
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
    }

    private void manejaExcepcion(HibernateException he) throws HibernateException {
        tx.rollback();
        throw new HibernateException("Ocurrió un error en la capa de acceso a datos", he);
    }

    public long guardaContacto(Contacto contacto) {
        long id = 0;
        
        try{
            iniciaOperacion();
            id = (Long)session.save(contacto);
            tx.commit();
        }catch(HibernateException he){
            manejaExcepcion(he);
            throw he;
        }finally{
            session.close();
        }
        return id;
    }
}
