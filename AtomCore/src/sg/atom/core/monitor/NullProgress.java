package sg.atom.core.monitor;

public class NullProgress implements IProgress {

    @Override
    public float getProgress() {
        return 0;
    }

    @Override
    public void addProgress(float rate) {
    }

    @Override
    public void addProgress(float rate, String activity) {
    }

    @Override
    public void setProgress(float progress) {
    }

    @Override
    public void setProgress(float progress, String activity) {
    }

    @Override
    public float increment() {
        return 0;
    }

    @Override
    public float increment(int steps) {
        return 0;
    }

    @Override
    public float increment(String activity) {
        return 0;
    }

    @Override
    public float increment(int steps, String activity) {
        return 0;
    }
}
