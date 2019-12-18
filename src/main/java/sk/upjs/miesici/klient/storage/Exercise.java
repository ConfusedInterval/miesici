package sk.upjs.miesici.klient.storage;

public class Exercise {

	private Long id;
	private Long trainingId;
	private Long typeOfExerciseId;
	private double weight;
	private int reps;
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTrainingId() {
		return trainingId;
	}

	public void setTrainingId(Long trainingId) {
		this.trainingId = trainingId;
	}

	public Long getTypeOfExerciseId() {
		return typeOfExerciseId;
	}

	public void setTypeOfExerciseId(Long typeOfExerciseId) {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Exercise [trainingId=" + trainingId + ", typeOfExerciseId=" + typeOfExerciseId + ", weight=" + weight
				+ ", reps=" + reps + "]";
	}

}
