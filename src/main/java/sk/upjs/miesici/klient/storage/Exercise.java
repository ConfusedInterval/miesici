package sk.upjs.miesici.klient.storage;

public class Exercise {
	
	private long trainingId;
	private long typeOfExerciseId;
	private double weight;
	private int reps;
	
	public long getTrainingId() {
		return trainingId;
	}
	public void setTrainingId(long trainingId) {
		this.trainingId = trainingId;
	}
	public long getTypeOfExerciseId() {
		return typeOfExerciseId;
	}
	public void setTypeOfExerciseId(long typeOfExerciseId) {
		this.typeOfExerciseId = typeOfExerciseId;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public int getReps() {
		return reps;
	}
	public void setReps(int reps) {
		this.reps = reps;
	}
	@Override
	public String toString() {
		return "Exercise [trainingId=" + trainingId + ", typeOfExerciseId=" + typeOfExerciseId + ", weight=" + weight
				+ ", reps=" + reps + "]";
	}
	
	
	
}
