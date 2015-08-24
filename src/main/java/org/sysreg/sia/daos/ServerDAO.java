package org.sysreg.sia.daos;

import org.sysreg.sia.model.Server;

/**
 * Created by joseant on 22/07/15.
 */
public interface ServerDAO {

    void persist(Server server);

    Server findById(String id);
}