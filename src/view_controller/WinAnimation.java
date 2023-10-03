package view_controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * Plays a fireworks animation
 */
public class WinAnimation extends Canvas {
	
	private Image spritesheet;
	private GraphicsContext g2;
	private Timeline timeline;
	private boolean darkMode;
	
	public WinAnimation(boolean darkMode) {
		this.setWidth(256);
		this.setHeight(256);
		this.darkMode = darkMode;
		
		if (darkMode) {
			// this.setStyle("-fx-background-color: #515151");
		} else {
			// this.setStyle("-fx-background-color: white");
		}
		
		spritesheet = new Image("file:Firework.png");
		g2 = this.getGraphicsContext2D();
		
		timeline = new Timeline(new KeyFrame(Duration.millis(33), new AnimateStarter(darkMode)));
		timeline.setCycleCount(Animation.INDEFINITE);
	}
	
	/**
	 * Plays the animation.
	 */
	public void animate() {
		timeline.play();
	}
	
	
	private class AnimateStarter implements EventHandler<ActionEvent> {
		private int tic = 0;
		double sx = 0, sy = 0, sw = 256, sh = 256, dx = 0, dy = 0, dw = 256, dh = 256;
		
		public AnimateStarter(boolean darkMode) {
			if (darkMode) {
				g2.setFill(Color.web("#515151"));
			} else {
				g2.setFill(Color.WHITE);
			}
			
		}
		
		@Override
		public void handle(ActionEvent arg0) {
			tic++;
			g2.fillRect(0, 0, 256, 256);
			g2.drawImage(spritesheet, sx, sy, sw, sh, dx, dy, dw, dh);
			
			sx += 256;
			
			if (sx == 1536) {
				sx = 0;
				sy += 256;
			} 
			
			if (sy == 1280) {
				sy = 0;
			}
		}
		
	}
}
