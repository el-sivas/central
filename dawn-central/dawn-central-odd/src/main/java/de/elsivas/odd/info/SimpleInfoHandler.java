package de.elsivas.odd.info;

import de.elsivas.basic.ioc.DawnContextInitializer;

public class SimpleInfoHandler {	

	public static void main(String[] args) {

		DawnContextInitializer.init("context.xml");

		DawnContextInitializer.close();
	}
}
