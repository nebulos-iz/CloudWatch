package edu.brown.cs.ajlin.hackatbrown2014;

import matlabcontrol.*;

public class MatlabInterface {

	private MatlabProxy proxy_;

	public MatlabInterface() throws MatlabConnectionException {
		MatlabProxyFactoryOptions options = new MatlabProxyFactoryOptions.Builder()
							.setUsePreviouslyControlledSession(true)
							.build();
		MatlabProxyFactory factory = new MatlabProxyFactory(options);
		proxy_ = factory.getProxy();
	}
	
	public String cloudwatch(String cloudPath) throws MatlabInvocationException {
		proxy_.eval("cd matlab"); 
		proxy_.eval("j_cloudwatch_ret = cloudWatch('" + cloudPath + "')");
		Object retval = proxy_.getVariable("j_cloudwatch_ret");
		String outfile = (String) retval;
		return outfile;
	}
	
}
