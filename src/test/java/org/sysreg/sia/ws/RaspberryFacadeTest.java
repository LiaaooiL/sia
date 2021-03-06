package org.sysreg.sia.ws;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.sysreg.sia.dtos.BoardDTO;
import org.sysreg.sia.facades.ServerFacade;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by joseant on 20/07/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
public class RaspberryFacadeTest {
    @Autowired
    MockHttpServletRequest request;

    @Autowired
    ServerFacade raspberryFacade;

    @Value("${test.siarest.host}")
    String siarestHost;

    @Value("${test.siarest.port}")
    String siarestPort;
    
    @Value("${test.siarest.username}")
    String siarestUsername;

    @Value("${test.siarest.password}")
    String siarestPassword;

    @Before
    public void setUp() {
        raspberryFacade.initConnection(siarestHost, Integer.parseInt(siarestPort),
                siarestUsername, siarestPassword);
    }

    @Test
    public void testGetBoards() {
        List<BoardDTO> boards = raspberryFacade.getBoards();
        assertNotNull(boards);
        assertTrue(boards.size() > 0);
    }
}
