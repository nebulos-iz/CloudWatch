package edu.brown.cs.ajlin.hackatbrown2014;

import matlabcontrol.*;

public class Main {

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	new Cloudwatcher().begin();
            }
        });
	}

}

