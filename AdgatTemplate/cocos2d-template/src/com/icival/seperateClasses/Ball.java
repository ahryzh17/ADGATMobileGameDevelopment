package com.icival.seperateClasses;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import com.icival.mobilegamedevelopment.Constants;

public class Ball extends CCNode
{
	/** Properties **********************************************************************************/
	// public
	public float g_radius;
	public CGPoint g_prevTouch;
	public CGPoint g_direction;
	
	// private
	private CCSprite m_skin;
	private CGSize m_screenSize;
	
	/** Constructor *********************************************************************************/
	public Ball(String p_fileName)
	{	
		m_screenSize = CCDirector.sharedDirector().displaySize();
		
		m_skin = CCSprite.sprite(p_fileName);
		m_skin.setAnchorPoint(CGPoint.ccp(0.5f, 0.5f));
		m_skin.setPosition(CGPoint.ccp(0.0f, 0.0f));
		this.addChild(m_skin);
		
		g_radius = m_skin.getContentSize().width/2;
		g_direction = CGPoint.zero();
	}
	
	/** Methods *************************************************************************************/
	// public
	public void update(float p_deltaTime)
	{
		// comment this for gravity
		// dont check for update if the g_direction is zero
		//if( CGPoint.equalToPoint(g_direction, CGPoint.zero()) )
		//{
		//	return;
		//}
		
		// update gravity on the velocity vector of your object
		g_direction.y -= Constants.GRAVITY/8 * p_deltaTime;
		
		// compute for new position
		CGPoint newPosition = this.getPosition();
				newPosition.x += g_direction.x * p_deltaTime;
				newPosition.y += g_direction.y * p_deltaTime;
			
		// check for boundery
		this.limitBorders(newPosition);
				
		// increment ball's position
		this.setPosition(newPosition);
	}
	
	public CGRect getBoundingBox()
	{
		CGPoint origin = CGPoint.ccp(-g_radius, -g_radius);
				origin = this.convertToWorldSpace(origin.x, origin.y);
		CGPoint size = CGPoint.ccp(g_radius, g_radius);
				size = this.convertToWorldSpace(size.x, size.y);
		return CGRect.make(origin.x, origin.y, size.x, size.y);
	}
	
	public void computeDirection(CGPoint p_lastTouch)
	{
		g_direction = CGPoint.ccpSub(p_lastTouch, g_prevTouch);
	}
	
	public boolean ballCollidedToOtherBall(Ball p_ball)
	{
		float totalRadius = (this.g_radius + p_ball.g_radius);
		if( totalRadius > CGPoint.ccpDistance(this.getPosition(), p_ball.getPosition()) )
		{
			CGPoint pushBack = CGPoint.ccpSub(this.getPosition(), p_ball.getPosition());
					pushBack = CGPoint.ccpNormalize(pushBack);
					pushBack = CGPoint.ccpMult(pushBack, totalRadius * 1.01f);
					pushBack = CGPoint.ccpAdd(pushBack, p_ball.getPosition());
			this.setPosition(pushBack);
			g_prevTouch = CGPoint.zero();
			g_direction = CGPoint.zero();
			p_ball.g_prevTouch = CGPoint.zero();
			p_ball.g_direction = CGPoint.zero();
			return true;
		}
		return false;
	}
	
	// private
	private void limitBorders(CGPoint p_newPosition)
	{
		// limit left x
		if( p_newPosition.x < (0 + g_radius) )
		{
			p_newPosition.x = (0 + g_radius);
			g_prevTouch = CGPoint.zero();
			g_direction.x = 0.0f;
		}
		
		// limit right x
		if( p_newPosition.x > (m_screenSize.width - g_radius) )
		{
			p_newPosition.x = (m_screenSize.width - g_radius);
			g_prevTouch = CGPoint.zero();
			g_direction.x = 0.0f;
		}
		
		// limit top y
		if( p_newPosition.y < (0 + g_radius) )
		{
			p_newPosition.y = (0 + g_radius);
			g_prevTouch = CGPoint.zero();
			g_direction.y = 0.0f;
		}
		
		// limit bottom y
		if( p_newPosition.y > (m_screenSize.height - g_radius) )
		{
			p_newPosition.y = (m_screenSize.height - g_radius);
			g_prevTouch = CGPoint.zero();
			g_direction.y = 0.0f;
		}
	}
}
