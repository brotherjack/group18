package com.amphibian.tankwars;

public class moveTank {

	private boolean environment[][];

	//////////////////////////////////////////////////////////////////////////////////////////////
	//Project Name   : Tank Wars                                                                //
	//Author(s)      : Edward Jezisek                                                           //
	//Purpose        : The purpose is to create an environment that the tank can move around in.//
	//Version Number : Version 0.0                                                              //
	//Unit Testing	 : No                                                                       //
	//Date           : 2/12/2013                                                                //
	//////////////////////////////////////////////////////////////////////////////////////////////
	//Enhancements	 : First Created Version                                                    //
	//Version x      : No previous Version.                                                     //
	//Date           : None                                                                     //
	//////////////////////////////////////////////////////////////////////////////////////////////
	void setEnvironment(boolean environment[][], int width, int height)
	{
		this.environment=new boolean[width][height];
		for(int i=0; i<width; i++)
		{
			for(int j=0; j<height; j++)
			{
				this.environment[width][height]=environment[width][height];
			}
		}
	}
	
}
