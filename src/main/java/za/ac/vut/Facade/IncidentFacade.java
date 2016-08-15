/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.vut.Facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import za.ac.vut.Entity.Incident;

/**
 *
 * @author 2015127
 */
@Stateless
public class IncidentFacade extends AbstractFacade<Incident>
{

    @PersistenceContext(unitName = "ac.za.vut_ResidenceMaintenance_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public IncidentFacade()
    {
        super(Incident.class);
    }
    
}
