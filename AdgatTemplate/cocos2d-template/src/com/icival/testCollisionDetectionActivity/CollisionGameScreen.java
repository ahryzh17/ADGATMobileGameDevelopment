package com.icival.testCollisionDetectionActivity;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import com.icival.mobilegamedevelopment.GameScreen;

public class CollisionGameScreen extends GameScreen
{
	/** Properties **********************************************************************************/
	
	/** Constructor *********************************************************************************/
	public CollisionGameScreen()
	{		
		// create ui layer
		m_uiLayer = new CollisionUILayer();
		m_uiLayer.setGameScreen(this);
		this.addChild(m_uiLayer, 20);
		
		// create game layer
		m_gameLayer = new CollisionGameLayer();
		m_gameLayer.setGameScreen(this);
		this.addChild(m_gameLayer, 10);
	}
	
	/** Methods *************************************************************************************/
}
