package edu.brown.cs.ajlin.hackatbrown2014;

import matlabcontrol.*;

public class MatLabInterface {
	MatlabProxyFactoryOptions options;
MatlabProxyFactory factory;
MatlabProxy proxy;
	public MatLabInterface()  throws MatlabConnectionException, MatlabInvocationException{
		options = new MatlabProxyFactoryOptions.Builder()
							.setUsePreviouslyControlledSession(true)
							.build();
		factory = new MatlabProxyFactory(options);
		proxy = factory.getProxy();
	}
	
	public void processImage()  throws MatlabConnectionException, MatlabInvocationException{
		proxy.eval("doStuff(2)");
	}
	
}
