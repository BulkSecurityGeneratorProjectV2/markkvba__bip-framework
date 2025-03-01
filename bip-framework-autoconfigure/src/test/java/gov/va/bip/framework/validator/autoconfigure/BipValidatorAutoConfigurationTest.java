/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.va.bip.framework.validator.autoconfigure;

import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import gov.va.bip.framework.validator.autoconfigure.BipValidatorAutoConfiguration;

/**
 *
 * @author akulkarni
 */
public class BipValidatorAutoConfigurationTest {
    
    private AnnotationConfigWebApplicationContext context;

    @After
    public void close() {
        if (this.context != null) {
            this.context.close();
        }
    }

   @Test
    public void testWebConfiguration() throws Exception {
        context = new AnnotationConfigWebApplicationContext();
        context.register(BipValidatorAutoConfiguration.class);
        context.refresh();
        assertNotNull(context);
        assertNotNull(this.context.getBean(BipValidatorAutoConfiguration.class));

    }
    
}