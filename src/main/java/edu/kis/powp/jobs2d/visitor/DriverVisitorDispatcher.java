package edu.kis.powp.jobs2d.visitor;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.drivers.*;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Dispatcher pattern implementation for driver visitors.
 * Eliminates instanceof checks
 */
public class DriverVisitorDispatcher {
    
    /**
     * Dispatch table mapping driver classes to visitor method calls.
     */
    private static final Map<Class<?>, BiConsumer<DriverVisitor, Job2dDriver>> DISPATCH_TABLE = new HashMap<>();
    
    static {
        DISPATCH_TABLE.put(LoggerDriver.class, 
            (visitor, driver) -> visitor.visit((LoggerDriver) driver));
        
        DISPATCH_TABLE.put(AnimatedDriverDecorator.class, 
            (visitor, driver) -> visitor.visit((AnimatedDriverDecorator) driver));
        
        DISPATCH_TABLE.put(LineDriverAdapter.class, 
            (visitor, driver) -> visitor.visit((LineDriverAdapter) driver));
        
        DISPATCH_TABLE.put(DriverComposite.class, 
            (visitor, driver) -> visitor.visit((DriverComposite) driver));
    }
    
    /**
     * Dispatches visitor to appropriate visit method based on driver type.
     * 
     * @param visitor the visitor to dispatch
     * @param driver the driver to visit
     * @throws IllegalArgumentException if driver type not registered
     */
    public static void dispatch(DriverVisitor visitor, Job2dDriver driver) {
        BiConsumer<DriverVisitor, Job2dDriver> dispatcher = DISPATCH_TABLE.get(driver.getClass());
        if (dispatcher != null) {
            dispatcher.accept(visitor, driver);
        } else {
            throw new IllegalArgumentException("Unknown driver type: " + driver.getClass().getName());
        }
    }
}