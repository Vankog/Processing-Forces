/* Processing in IntelliJ: http://proto.ink/2016/06/03/processing-in-intellij-combining-proper-java-with-processing/
 */
import processing.core.PApplet;
import processing.core.PVector;


public class Forces extends PApplet {
    private Ball[] balls = new Ball[10];
    private Ball attractor;

    public static void main(String[] args) {
        PApplet.main("Forces", args);
    }

    public void settings(){
        //size(displayWidth, displayHeight);
        fullScreen();
    }

    public void setup() {
        for (int i = 0; i < balls.length; i++) {
            balls[i] = new Ball(this);
        }
        // attractor = new Ball();
        // attractor.loc = new PVector(width/2, height/2);
        // attractor.speed = new PVector (0,0);
        // attractor.update();
    }

    public void draw() {
        background(255);
        //PVector mouseForce = new PVector(mouseX, mouseY).sub(ball.getLocation()).setMag(0.1);
        //PVector gravity = new PVector(0, 0.3);
        //PVector wind = new PVector(0.2, 0);


        //attractor.applyForce(attractor.getGravity(attractor));
        //attractor.update();
        //attractor.draw();

        for (Ball ball1 : balls) {
            for (Ball ball2 : balls) {
                if (ball1 != ball2) {
                    //gravity has the same effect on objects no matter what the mass is.
                    //ball.applyForce(PVector.mult(gravity, ball.getMass()));
                    //wind blows
                    //ball.applyForce(wind);
                    //if (mousePressed)
                    ball1.applyForce(ball1.getGravity(ball2));
                }
            }
            //update the object data
            ball1.update();
            //draw the object
            ball1.draw();
        }
    }

    /* Friction on surfaces
	 * Force(friction) = -1 * µ *NormalForce.mag() * speed.normalize()
	 * Friction works in the opposite direction of the speed
	 */
    static PVector getFriction(PVector speed) {
        // µ = Friction coefficient
        final float frictionCoeff = 0.1f;
        // Magnitude of Normal Force (perpendicular to ground)
        final float normalMag = 1;

        PVector friction = speed.copy();

        //Force(friction) = -1 * µ *NormalForce.mag() * speed.normalize()
        friction.normalize();
        friction.mult(-1 * frictionCoeff * normalMag);
        return friction;
    }

    /* Drag (air resistance etc.)
	 * Force(drag) = -1/2 * ro * speed.mag()² * A * C(d) * speed.normalize()
	 * ro = density, A = surface area, C(d) = drag coefficient
	 */
    static PVector getDrag(PVector speed) {
        // density
        final float density = 1;
        // durface area
        final float area = 1;
        // C(d) = drag coefficient
        final float dragCoeff = 0.01f;

        PVector drag = speed.copy();

        // Force(drag) = -1/2 * ro * speed.mag()² * A * C(d) * speed.normalize();
        drag.normalize();
        drag.mult(-0.5f * density * speed.magSq() * area * dragCoeff);
        return drag;
    }
}
