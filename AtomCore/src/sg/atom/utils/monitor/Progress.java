package sg.atom.utils.monitor;

/**
 * start -> end로 진행하는 진행상태값.
 * addProgress()를 호출할 경우, 바로 값이 적용되지 않고 update()를 호출함에 따라
 * 처음에는 빨리, 목표치에 도달할 때는 천천히 증가한다.
 * 그리고, 목표치가 end값과 같아지면, maxSpeed로 진행한다.
 * 
 * @author mulova
 */
public class Progress implements IProgress {
	private static final float TOLERANCE = 0.0001f;
	private float speed = 0.2f;
	private float start = 0f; // 시작값
	private float end = 1f;   // 중간값
	private float current; // update에서 진행된 값. progress 이상 올라가지 않는다.
	private float progress = 0f; // start->end
	private float maxSpeed = 1f;
	
	private String activity;
	private float goal = 1f;   // 목표값


	public Progress() {
	}

	@Override
	public void addProgress(final float toAdd) {
		addProgress(toAdd, null);
	}

	@Override
	public void addProgress(final float rate, final String activity) {
		this.progress += rate;
		this.activity = activity;
		if (progress > end) {
			progress = end;
		}
	}
	
	public boolean isDone() {
		return current >= end;
	}
	
	/**
	 * 실제 progress와 현재 display값의 차이
	 * @return
	 * @author mulova
	 */
	public float getDelta() {
		return progress-current;
	}
	
	/** 
	 * 현재 보여지고 있는 진행 상태. 'progress' 까지 올라갈 수 있다.
	 */
	public float getCurrent() {
		return current;
	}
	
	/**
	 * @return 목표 percentage. 일반적으로 1
	 */
	public float getEnd() {
		return end;
	}

	/**
	 * update로 올라갈 수 있는 최대 값. 즉 addProgress(), setProgress()에 의해 결정된 값.
	 * end == 1일 경우에만 setProgress() == getProgress() 이다. 
	 * @return
	 */
	@Override
	public float getProgress() {
		return progress;
	}

	@Override
	public float increment() {
		return 0;
	}

	@Override
	public float increment(final int steps) {
		return 0;
	}

	@Override
	public float increment(final int steps, final String activity) {
		return 0;
	}

	@Override
	public float increment(final String activity) {
		return 0;
	}

	public void reset() {
		this.progress = start;
		this.current = start;
	}

	/**
	 * progress가 max이 되었을 때의 실제 값 여러개의 progress가 모여 하나의 progress가 될 때 사용한다.
	 * 예를 들면 0.3, 0.3, 0.4 의 세개의 progress로 이루어 졌다고
	 * 할때, setLimit(0.3f) setProgress(0.5f) => getProgress() = 0.15f setProgress(1f) => getProgress() = 0.3f 가 되고, 이후
	 * setLimit(0.6f) setProgress(0.5f) => getProgress() = 0.15f setProgress(1f) => getProgress() = 0.3f 가 되고,
	 * 
	 * @param max
	 *            progress의 최대값. 0~1사이의 값을 가지며, setProgress(1) 일때 실제로 max값이 된다.
	 */
	public void setEnd(float max) {
		if (max < 0)
			return;
		if (max < current)
			max = current;
		start = current;
		end = max;
	}
	
	public void setRange(float start, float end) {
		this.start = start;
		this.end = end;
		reset();
	}

	/**
	 * update없이 값을 바로 적용한다.
	 * @param value
	 */
	public void setNow(final float value) {
		this.current = value;
		this.progress = value;
	}

	/**
	 * 설정한 값이 바로 적용되지 않는다.
	 * 현재의 progress보다 작은 값일 경우 무시된다. 
	 */
	@Override
	public void setProgress(final float rate) {
		setProgress(rate, null);
	}

	/**
	 * progress가 1이 되면 max까지 진행한다.
	 * @see com.jmex.game.state.load.Loader#setProgress(float, java.lang.String)
	 */
	@Override
	public void setProgress(float rate, final String activity) {
		rate = (end - start) * rate + start;
		if (rate > progress)
			progress = rate;
	}

	/**
	 * 증가하는 속도를 설정한다.
	 * 
	 * @param speed
	 */
	public void setSpeed(final float speed) {
		this.speed = speed;
		if (maxSpeed < speed) {
			maxSpeed = speed;
		}
	}
	
	/**
	 * delta값이 크면 더 빨리 증가하도록 한다.
	 */
	public void update(final float time) {
		if (current >= progress) {
			return;
		}

		if (progress >= goal) {
			// 최종 단계까지 도달했다면 빠르게 진행한다.
			current += (goal-start)* time * maxSpeed;
		} else {
			float delta = getDelta() * speed;
			current += delta * time;
		}

		if (end - current <= TOLERANCE) {
			current = end;
			progress = end;
		}
	}


	/**
	 * @param maxSpeed 1초동안 증가할 수 있는 최대값
	 * @author mulova
	 */
	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

}
