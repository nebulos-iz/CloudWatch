package edu.brown.cs.ajlin.hackatbrown2014;

import matlabcontrol.*;

public class Main {

	public static void main(String[] args) throws MatlabConnectionException, MatlabInvocationException
	{
	    //Create a proxy, which we will use to control MATLAB
		MatlabProxyFactoryOptions options = new MatlabProxyFactoryOptions.Builder()
													.setUsePreviouslyControlledSession(true)
													.build();
	    MatlabProxyFactory factory = new MatlabProxyFactory(options);
	    MatlabProxy proxy = factory.getProxy();

	    //evaluates the function doStuff
	    proxy.eval("doStuff(2)");

	    //Disconnect the proxy from MATLAB
	    proxy.disconnect();
	}
}

