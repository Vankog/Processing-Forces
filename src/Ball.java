
import processing.core.PVector;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Created by danie on 15.12.2016.
 */
public class Ball {
	private Forces sketch;
	private PVector loc;
	private PVector speed;
	private PVector acc;
	private float mass;
	private float size;

	// assumes the current class is called MyLogger
	private final Logger log = Logger.getLogger(Ball.class.getName());

	Ball(Forces sketch) {
		this.sketch = sketch;
		log.setLevel(Level.OFF);
		loc = new PVector(sketch.random(sketch.width), sketch.random(sketch.height));
		speed = new PVector(sketch.random(-3, 3), sketch.random(-3, 3));
		acc = new PVector(0, 0);
		size = sketch.random(10, 100);
		//mass-size relation with arbitrary numbers
		mass = size / 34;
	}

	void draw() {
		sketch.stroke(0);
		sketch.strokeWeight(2);
		sketch.fill(150);
		sketch.ellipse(loc.x, loc.y, size, size);
	}

	void update() {
		//update speed with accumulated forces
		speed.add(acc);
		//update location
		loc.add(speed);
		//reset acceleration
		acc = new PVector(0, 0);
		//check edge cases
		edge();
	}

	//F=m×a -> a=F/m
	void applyForce(PVector force) {
		acc.add(PVector.div(force, mass));
	}

	//Check if object boundaries are outside the canvas limits.
	//If so, reverse direction of speed and set object back onto canvas boundary.
	private void edge() {
		log.log(Level.INFO, "enter edge, loc.x={0} loc.y={1} ", new Object[]{loc.x + (size / 2), loc.y + (size / 2)});

		if (loc.x + (size / 2) > sketch.width && speed.x > 0) {
			log.log(Level.INFO, "x>width");
			loc.x = sketch.width - (size / 2);
			speed.x *= -1;
		} else if (loc.x - (size / 2) < 0 && speed.x < 0) {
			log.log(Level.INFO, "x<0");
			loc.x = 0 + (size / 2);
			speed.x *= -1;
		}
		if (loc.y + (size / 2) > sketch.height && speed.y > 0) {
			log.log(Level.INFO, "y>height");
			loc.y = sketch.height - (size / 2);
			speed.y *= -1;
		} else if (loc.y - (size / 2) < 0 && speed.y < 0) {
			log.log(Level.INFO, "y<0");
			loc.y = 0 + (size / 2);
			speed.y *= -1;
		}
	}

	/* Gravitation
	 * F(gravity) = ((g * m1 * m2) / distance²) * direction
	 */
	PVector getGravity(Ball object) {
		// direction and distance²
		PVector dir = PVector.sub(object.getLocation(), loc);
		final float distanceSq = sketch.constrain(dir.magSq(), 2, 300); //contraints are needed to keep simulation concise.
		dir.normalize();
		// g force
		final float g = 2;

		PVector gravity = dir.mult((g * mass * object.getMass()) / distanceSq);
		return gravity;
	}

	PVector getLocation() {
		return loc;
	}

	PVector getSpeed() {
		return speed;
	}

	float getMass() {
		return mass;
	}
}
