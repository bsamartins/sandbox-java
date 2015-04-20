package org.bmartins.sandbox.javaagent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FindUsageTransformer implements ClassFileTransformer {
	
	private static final Logger LOG = LoggerFactory.getLogger(FindUsageTransformer.class);
	
	Class clazz = null;
	
	@Override
    public byte[] transform(
    		ClassLoader loader,
    		String className,
    		Class<?> classBeingRedefined,  
    		ProtectionDomain protectionDomain,
            byte[] classfileBuffer)    throws IllegalClassFormatException {		
        if(className.startsWith("org/bmartins/sandbox/springaspectj")){
        	LOG.debug("Transform: {}", className);
       		doClass(className, classBeingRedefined, classfileBuffer);
        }
        return classfileBuffer;
    }
    
    private byte[] doClass(String name, Class clazz, byte[] b) {
    	ClassPool pool = ClassPool.getDefault();
        CtClass cl = null;
        CtMethod currentMethod = null;     
        try {
        	cl = pool.makeClass(new java.io.ByteArrayInputStream(b));        	
        	for(CtMethod method: cl.getMethods()) {
        		currentMethod = method;
        		if(!method.isEmpty() 
        				&& !Modifier.isNative(method.getModifiers())) {
                	LOG.debug(method.getLongName());
                	// here you have lot of options t	o explore        		
                	method.insertBefore("System.out.println(Thread.currentThread().getStackTrace()[0].getClassName()+ Thread.currentThread().getStackTrace()[0].getMethodName());");
        		} else {
        			LOG.debug("Skipping: {}", method.getLongName());
        		}
        	}
        	b = cl.toBytecode();
        } catch (Exception e) {
        	if(currentMethod == null) {
        		LOG.error("Could not instrument {}: {}", name, e.getMessage());
        	} else {
        		LOG.error("Could not instrument {}, method {}: {}", name, currentMethod.getSignature(), e.getMessage());
        	}        	
        } finally {
        	if (cl != null) {
        		cl.detach();
        	}
        }
        return b;
    }
}