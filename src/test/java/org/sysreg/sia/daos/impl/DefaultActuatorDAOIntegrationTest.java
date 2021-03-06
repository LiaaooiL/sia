package org.sysreg.sia.daos.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.sysreg.sia.model.*;
import org.sysreg.sia.daos.*;
import org.sysreg.sia.model.actuator.Actuator;
import org.sysreg.sia.model.actuator.BasicActuator;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by jose on 08/02/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class DefaultActuatorDAOIntegrationTest {
    @Autowired
    private ActuatorDAO actuatorDao;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TownDAO townDAO;

    @Autowired
    private FieldDAO fieldDAO;

    @Autowired
    private ParcelDAO parcelDAO;

    @Autowired
    private EnclosureDAO enclosureDAO;

    @Autowired
    private BoardDAO boardDAO;

    @Value("${test.siarest.host}")
    String siarestHost;

    @Value("${test.siarest.port}")
    String siarestPort;
    
    @Value("${test.siarest.username}")
    String siarestUsername;
    
    @Value("${test.siarest.password}")
    String siarestPassword;
    
    @Test
    public void testCreation() {
        ArrayList<Actuator> actuators = new ArrayList<Actuator>();

        //Actuator
        actuators.add(new Actuator("1"));

        //Basic Actuator
        actuators.add(new BasicActuator("2"));

        for (Actuator i : actuators)
            System.out.println(i.toString());
    }

    @Test
    public void testInsertSelect() {
        //Prepare requiered fields
        User defaultUser = userDAO.findByUsername("sia");
        //Fields
        Field f1 = new Field();
        f1.setName("Field #1");
        //Parcel
        Parcel p1 = new Parcel();
        p1.setParcel(1);
        p1.setPolygon(1);
        p1.setAggregate(1);
        p1.setZone(1);
        p1.setTown(townDAO.findByName("Picassent"));
        p1.setArea(25F);
        Coordinates cP1 = new Coordinates();
        cP1.setX(36D);
        cP1.setY(45D);
        cP1.setDatum("DATUM1");
        cP1.setSpindle(29);
        p1.setCoordinates(cP1);
        //Enclosure
        Enclosure e1 = new Enclosure();
        e1.setEnclosure(1);
        e1.setCoordinates(cP1);
        e1.setArea(25F);
        e1.setIrrigationCoef(100);
        e1.setSlope(0F);
        //Server
        Server s1 = new Server(siarestHost, Integer.parseInt(siarestPort), siarestUsername, siarestPassword);
        //Board
        Board b1 = new Board(2222, "USB", "/dev/cu.usbmodem1411", "Test board");
        //Set relations
        e1.setParcel(p1);
        p1.setField(f1);
        f1.setUser(defaultUser);
        s1.setEnclosure(e1);
        b1.setServer(s1);

        fieldDAO.persist(f1);
        parcelDAO.persist(p1);
        enclosureDAO.persist(e1);
        boardDAO.persist(b1);


        ArrayList<Actuator> actuators = new ArrayList<Actuator>();
        Actuator actuator;

        //Actuator
        actuator = new Actuator("1");
        actuator.setDescription("Test actuator #1");
        actuator.setEnabled(true);
        actuator.setBoard(b1);
        actuators.add(actuator);

        //Basic Actuator
        actuator = new BasicActuator("2");
        actuator.setDescription("Test actuator #2");
        actuator.setEnabled(false);
        actuator.setBoard(b1);
        actuators.add(actuator);

        for (Actuator i : actuators)
            actuatorDao.persist(i);

        //SELECT
        actuator = actuatorDao.findById("1");
        assertEquals(actuator, actuators.get(0));
        assertEquals(e1.getArea(), actuators.get(0).getBoard().getServer().getEnclosure().getArea(), 0.002);

        actuator = actuatorDao.findById("2");
        assertEquals(actuator, actuators.get(1));
        assertEquals(e1.getParcel().getTown().getId(), actuators.get(1).getBoard().getServer().getEnclosure().getParcel().getTown().getId());
    }
}
