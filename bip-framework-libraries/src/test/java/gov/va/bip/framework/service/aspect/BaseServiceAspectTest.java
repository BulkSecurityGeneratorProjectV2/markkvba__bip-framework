package gov.va.bip.framework.service.aspect;

import org.junit.Test;

import gov.va.bip.framework.service.aspect.BaseServiceAspect;

public class BaseServiceAspectTest {

    @Test
    public void testStandardServiceMethod(){
        BaseServiceAspect.publicStandardServiceMethod();
        //does nothing
    }
    
    @Test
    public void testRestControllereMethod(){
        BaseServiceAspect.restController();;
        //does nothing
    }

    @Test
    public void testServiceImplMethod(){
        BaseServiceAspect.serviceImpl();
        //does nothing
    }
}
