package com.z.loa.manager;

import com.badlogic.gdx.ai.steer.*;
import com.badlogic.gdx.ai.utils.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.*;

public class ActorSteerable implements Steerable<Vector2> {
	private final Actor actor;
	private final Vector2 position;
	private final Vector2 linearVelocity;

	private float orientation = 180;
	private float boundingRadius = 48f;
	private boolean tagged = false;
	private float maxLinearSpeed = 64f;
	private float maxLinearAcceleration = 32f;
	private float maxAngularSpeed = 90f;
	private float maxAngularAcceleration = 45f;

	public ActorSteerable(Actor actor) {
		this.actor = actor;
		position = new Vector2();
		linearVelocity = new Vector2();
	}

	@Override
	public Vector2 angleToVector(Vector2 out_vector, float angle) {
		if (out_vector == null) {
			out_vector = new Vector2();
		}
		out_vector.set(MathUtils.cos(angle), MathUtils.sin(angle));
		return out_vector;
	}

	@Override
	public float getOrientation() {
		return orientation;
	}

	@Override
	public Vector2 getPosition() {
		position.set(actor.getX(), actor.getY());
		return position;
	}

	@Override
	public Location<Vector2> newLocation() {
		return null;
	}

	@Override
	public void setOrientation(float orientation) {
		this.orientation = orientation;
	}

	@Override
	public float vectorToAngle(Vector2 vector) {
		return MathUtils.atan2(vector.y, vector.x);
	}

	@Override
	public float getAngularVelocity() {
		return 0;
	}

	@Override
	public float getBoundingRadius() {
		return boundingRadius;
	}

	public void setBoundingRadius(float bounding_radius) {
		boundingRadius = bounding_radius;
	}

	@Override
	public Vector2 getLinearVelocity() {
		return linearVelocity;
	}

	@Override
	public boolean isTagged() {
		return tagged;
	}

	@Override
	public void setTagged(boolean tagged) {
		this.tagged = tagged;
	}

	@Override
	public float getMaxAngularAcceleration() {
		return maxAngularAcceleration;
	}

	@Override
	public float getMaxAngularSpeed() {
		return maxAngularSpeed;
	}

	@Override
	public float getMaxLinearAcceleration() {
		return maxLinearAcceleration;
	}

	@Override
	public float getMaxLinearSpeed() {
		return maxLinearSpeed;
	}

	@Override
	public float getZeroLinearSpeedThreshold() {
		// TODO: Implement this method
		return 0;
	}

	@Override
	public void setMaxAngularAcceleration(float max_angular_acceleration) {
		this.maxAngularAcceleration = max_angular_acceleration;
	}

	@Override
	public void setMaxAngularSpeed(float max_angular_speed) {
		this.maxAngularSpeed = max_angular_speed;
	}

	@Override
	public void setMaxLinearAcceleration(float max_linear_acceleration) {
		this.maxLinearAcceleration = max_linear_acceleration;
	}

	@Override
	public void setMaxLinearSpeed(float max_linear_speed) {
		this.maxLinearSpeed = max_linear_speed;
	}

	@Override
	public void setZeroLinearSpeedThreshold(float p) {
		// TODO: Implement this method
	}

}

