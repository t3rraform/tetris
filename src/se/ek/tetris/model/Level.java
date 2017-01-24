package se.ek.tetris.model;

/**
 * @author Eric Karlsson
 */
public final class Level {

	public static double baseInterval = 15;
	private static double maxLockTime = 70;
	private int score = 0;
	private int level = 1;

	public void addScore() {

		score++;
		if (score % 10 == 0) {
			levelUp();
		}
	}

	private void levelUp() {
		level++;
		baseInterval *= 0.8;
		maxLockTime *= 0.8;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public static double getBaseInterval() {
		return baseInterval;
	}

	public static void setBaseInterval(double baseInterval) {
		Level.baseInterval = baseInterval;
	}

	public static double getMaxLockTime() {
		return maxLockTime;
	}

	public static void setMaxLockTime(double maxLockTime) {
		Level.maxLockTime = maxLockTime;
	}

}
