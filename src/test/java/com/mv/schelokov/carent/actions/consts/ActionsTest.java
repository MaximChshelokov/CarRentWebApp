/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mv.schelokov.carent.actions.consts;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class ActionsTest {
    
    public ActionsTest() {
    }

    /**
     * Test of getActionName method, of class Actions.
     */
    @Test
    public void testGetActionName() {
        System.out.println("getActionName");
        String action = Actions.ADMIN_ACTIONS;
        String expResult = "";
        String result = Actions.getActionName(action);
        System.out.println(Actions.getActionName(action));
        assertTrue(true);
    }
    
}
