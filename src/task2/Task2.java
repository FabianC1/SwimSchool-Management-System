package task2;

import java.util.List;
import java.util.Scanner;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Collections;
import java.util.Map;
import java.util.LinkedHashMap;

// Abstract class for qualification
abstract class Qualification {

    // Method to award qualification to a student
    public static void awardQualification(SwimStudent student, Instructor instructor, Qualification qualification) {
        student.assignQualification(instructor, qualification);
    }
}

// Concrete class for distance swim qualification
class DistanceSwim extends Qualification {

    private int distance;

    public DistanceSwim(int distance) {
        this.distance = distance;
    }

    // Getter method for distance
    public int getDistance() {
        return distance;
    }
}

// Concrete class for personal survival qualification
class PersonalSurvival extends Qualification {

    private String level;

    public PersonalSurvival(String level) {
        this.level = level;
    }

    // Getter method for level
    public String getLevel() {
        return level;
    }
}

// Class representing a swim lesson
class SwimLesson {

    private String day;
    private String start_time;
    private String level;
    private Instructor instructor;
    private List<SwimStudent> students;

    public SwimLesson(String day, String start_time, String level, Instructor instructor) {
        this.day = day;
        this.start_time = start_time;
        this.level = level;
        this.instructor = instructor;
        this.students = new ArrayList<>();
    }

    // Methods to add/remove students from the lesson
    public void addStudent(SwimStudent student) {
        students.add(student);
    }

    public void removeStudent(SwimStudent student) {
        students.remove(student);
    }

    public String getDayTime() {
        // Format the day and time consistently, e.g., "Monday 17:00"
        return day.trim() + " " + start_time.trim();
    }

    // Method to get the time of the swim lesson
    public String getTime() {
        return start_time;
    }

    // Method to check if there are available slots in the swim lesson
    public boolean getAvailableSlots() {
        // Assuming the max number of students is 10 for demonstration
        return students.size() < 10;
    }

    // Other getters
    public String getDay() {
        return day;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getLevel() {
        return level;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public List<SwimStudent> getStudents() {
        return students;
    }

}

// Class representing a swim student
class SwimStudent {

    private String name;
    private String level;
    private Optional<SwimLesson> lesson;
    private SwimLesson previousLesson;

    public SwimStudent(String name, String level) {
        this.name = name;
        this.level = level;
        this.lesson = Optional.empty();
    }

    public String getName() {
        return name;
    }

    public String getLevel() {
        return level;
    }

    public void assignLesson(SwimLesson lesson) {
        this.lesson = Optional.of(lesson);
    }

    public void setPreviousLesson(SwimLesson previousLesson) {
        this.previousLesson = previousLesson;
    }

    public Optional<SwimLesson> getPreviousLesson() {
        return Optional.ofNullable(previousLesson);
    }

    public Optional<SwimLesson> getLesson() {
        return lesson;
    }

    // Method to assign a qualification to the student
    public void assignQualification(Instructor instructor, Qualification qualification) {
        System.out.println("Awarding qualification to student: " + name);
        System.out.println("Instructor: " + instructor.getName());
        System.out.println("Qualification: " + qualification.getClass().getSimpleName());
        // Logic to record the qualification for the student
    }

    public void setLevel(String level) {
        this.level = level;
    }
    // Method to get the day and time of the swim lesson

    public String getDayTime() {
        if (lesson.isPresent()) {
            return lesson.get().getDayTime();
        }
        return "No lesson assigned";
    }

    // Method to get the time of the swim lesson
    public String getTime() {
        if (lesson.isPresent()) {
            return lesson.get().getTime();
        }
        return "No lesson assigned";
    }

// Method to check if there are available slots in the swim lesson
    public boolean getAvailableSlots() {
        if (lesson.isPresent()) {
            return lesson.get().getAvailableSlots(); // Return the boolean value directly
        }
        return false;
    }
}

// Class representing the waiting list
class WaitingList {

    private List<SwimStudent> students;

    public WaitingList() {
        this.students = new ArrayList<>();
    }

    // Methods to add/remove students from the waiting list
    public void addStudent(SwimStudent student) {
        students.add(student);
    }

    public void removeStudent(SwimStudent student) {
        students.remove(student);
    }

    // Method to check if a student is on the waiting list
    public boolean contains(SwimStudent student) {
        return students.contains(student);
    }

    // Method to get the list of students
    public List<SwimStudent> getStudents() {
        return students;
    }
}

// Class representing an instructor
class Instructor {

    private String name;

    public Instructor(String name) {
        this.name = name;
    }

    // Getter method for name
    public String getName() {
        return name;
    }
}

// Main class to run the program
public class Task2 {

    private static final int MAX_STUDENTS_PER_LESSON = 3; //

    public static void viewSwimStudentInformation(List<SwimStudent> swimStudents, WaitingList waitingList) {
        Scanner scanner = new Scanner(System.in);
        boolean continueChecking = true;

        do {
            System.out.println("Select a swim student:");
            for (int i = 0; i < swimStudents.size(); i++) {
                SwimStudent student = swimStudents.get(i);
                System.out.println((i + 1) + ". " + student.getName() + " - " + student.getLevel());
            }

            System.out.print("Enter the number of the student: ");
            int studentChoice;
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume invalid input
            }
            studentChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            if (studentChoice >= 1 && studentChoice <= swimStudents.size()) {
                SwimStudent selectedStudent = swimStudents.get(studentChoice - 1);
                System.out.println("Name: " + selectedStudent.getName());
                System.out.println("Level: " + selectedStudent.getLevel());

// Check if the student has a lesson assigned
                if (selectedStudent.getLesson().isPresent()) {
                    SwimLesson lesson = selectedStudent.getLesson().get();
                    System.out.println("Day and Time: " + lesson.getDay() + " " + lesson.getStart_time() + " - " + (lesson.getAvailableSlots() ? "Available" : "Full"));
                    System.out.println("Instructor: " + lesson.getInstructor().getName());
                } else if (waitingList.contains(selectedStudent)) {
                    System.out.println("Status: Waiting List");
                } else {
                    // Check if the student was moved from the waiting list to a lesson
                    boolean movedFromWaitingList = selectedStudent.getPreviousLesson().isPresent();
                    if (movedFromWaitingList) {
                        SwimLesson previousLesson = selectedStudent.getPreviousLesson().get();
                        System.out.println("This student has been moved from the waiting list to a lesson at "
                                + previousLesson.getDay() + " " + previousLesson.getStart_time()
                                + " with instructor " + previousLesson.getInstructor().getName() + ".");
                    } else {
                        System.out.println("This student has been moved from the waiting list to a lesson.");
                    }
                }

                System.out.println("------------------------------------------------------\n"); // Separator line

                String choice;
                do {
                    System.out.println("Do you want to check another student? (yes/no)");
                    choice = scanner.nextLine();
                    if (choice.equalsIgnoreCase("no")) {
                        continueChecking = false;
                    } else if (!choice.equalsIgnoreCase("yes")) {
                        System.out.println("Invalid answer. Please enter 'yes' or 'no'.");
                    }
                } while (!choice.equalsIgnoreCase("yes") && !choice.equalsIgnoreCase("no"));
            } else {
                System.out.println("Invalid choice.");
            }
        } while (continueChecking);

        System.out.println("------------------------------------------------------"); // Separator line
        System.out.println("Returning to the main menu...");
        System.out.println("------------------------------------------------------\n"); // Separator line
    }

    public static void viewSwimLessonDetails(List<SwimLesson> swimLessons) {
        Scanner scanner = new Scanner(System.in);
        boolean continueChecking = true;

        System.out.println("------------------------------------------------------"); // Separator line
        System.out.println("Select a swim lesson:");
        System.out.println("------------------------------------------------------"); // Separator line

        // Sort swim lessons by instructor name
        swimLessons.sort(Comparator.comparing(s -> s.getInstructor().getName()));

        do {
            String previousInstructor = "";

            // Iterate over sorted swim lessons
            int lessonNumber = 1;
            for (SwimLesson lesson : swimLessons) {
                String currentInstructor = lesson.getInstructor().getName();
                if (!currentInstructor.equals(previousInstructor)) {
                    if (!previousInstructor.isEmpty()) {
                        System.out.println("------------------------------------------------------"); // Separator line
                    }
                    System.out.println(currentInstructor + "'s classes:");
                    previousInstructor = currentInstructor;
                }
                System.out.println(lessonNumber++ + ". " + lesson.getDay() + ", " + lesson.getStart_time()
                        + ", Level: " + lesson.getLevel() + ", Instructor: " + lesson.getInstructor().getName());
            }

            System.out.println("------------------------------------------------------"); // Separator line
            System.out.print("Enter the number of the lesson: ");
            int lessonChoice;
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume invalid input
            }
            lessonChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            if (lessonChoice >= 1 && lessonChoice <= swimLessons.size()) {
                SwimLesson selectedLesson = swimLessons.get(lessonChoice - 1);
                List<SwimStudent> students = selectedLesson.getStudents();

                System.out.println("Instructor: " + selectedLesson.getInstructor().getName());
                System.out.println("Students:");

                if (students.isEmpty()) {
                    System.out.println("No students assigned to this lesson.");
                } else {
                    for (SwimStudent student : students) {
                        System.out.println("- " + student.getName());
                    }
                }

                int availableSpaces = MAX_STUDENTS_PER_LESSON - students.size();
                if (availableSpaces == 0) {
                    System.out.println("This lesson is currently full.");
                } else {
                    System.out.println("Available spaces: " + availableSpaces);
                }

                System.out.println("------------------------------------------------------\n"); // Separator line

                String choice;
                do {
                    System.out.println("Do you want to view another lesson? (yes/no)");
                    choice = scanner.nextLine();
                    if (choice.equalsIgnoreCase("no")) {
                        continueChecking = false;
                    } else if (!choice.equalsIgnoreCase("yes")) {
                        System.out.println("Invalid answer. Please enter 'yes' or 'no'.");
                    }
                } while (!choice.equalsIgnoreCase("yes") && !choice.equalsIgnoreCase("no"));
            } else {
                System.out.println("Invalid choice.");
            }
        } while (continueChecking);

        System.out.println("------------------------------------------------------"); // Separator line
        System.out.println("Returning to the main menu...");
        System.out.println("------------------------------------------------------\n"); // Separator line
    }

    // Method to view instructor schedule
    public static void viewInstructorSchedule(List<Instructor> instructors, List<SwimLesson> swimLessons) {
        Scanner scanner = new Scanner(System.in);
        boolean viewAnotherInstructor = true;

        do {
            // Sort the list of instructors by name
            Collections.sort(instructors, Comparator.comparing(Instructor::getName));

            // Display list of instructors
            System.out.println("Select an instructor:");
            for (int i = 0; i < instructors.size(); i++) {
                System.out.println((i + 1) + ". " + instructors.get(i).getName());
            }

            // Prompt user to select an instructor
            System.out.print("Enter the number of the instructor: ");
            int instructorChoice;
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume invalid input
            }
            instructorChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            if (instructorChoice >= 1 && instructorChoice <= instructors.size()) {
                Instructor selectedInstructor = instructors.get(instructorChoice - 1);

                // Display allocated classes for the selected instructor
                System.out.println("Instructor Schedule for " + selectedInstructor.getName() + ":");
                boolean instructorHasLessons = false;
                boolean anyDayHasLessons = false;

                // Loop through each day
                for (String day : Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")) {
                    System.out.println("--------"); // Separator between days
                    System.out.println(day + ":");
                    boolean dayHasLessons = false;

                    // Display lessons for the selected instructor on the current day
                    for (SwimLesson lesson : swimLessons) {
                        if (lesson.getInstructor().equals(selectedInstructor) && lesson.getDay().equalsIgnoreCase(day)) {
                            if (!lesson.getStudents().isEmpty()) {
                                System.out.println("Time: " + lesson.getStart_time() + ", Level: " + lesson.getLevel());

                                List<SwimStudent> students = lesson.getStudents();
                                System.out.println("Students:");
                                for (SwimStudent student : students) {
                                    System.out.println("- " + student.getName());
                                }
                            } else {
                                System.out.println("No students enrolled in this lesson.");
                            }

                            dayHasLessons = true;
                            anyDayHasLessons = true;
                        }
                    }

                    if (!dayHasLessons) {
                        System.out.println("No lessons scheduled for this day.");
                    }
                    System.out.println(); // Blank line for separation

                    if (dayHasLessons) {
                        instructorHasLessons = true;
                    }
                }

                if (!instructorHasLessons) {
                    System.out.println("No lessons scheduled for any day.");
                    System.out.println(); // Blank line for separation
                }
            } else {
                System.out.println("Invalid choice.");
            }

            // Prompt user if they want to view another instructor's schedule with input validation
            String continueChoice;
            do {
                System.out.print("Do you want to view another instructor's schedule? (yes/no): ");
                continueChoice = scanner.nextLine().trim().toLowerCase();
                if (!continueChoice.equals("yes") && !continueChoice.equals("no")) {
                    System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                }
            } while (!continueChoice.equals("yes") && !continueChoice.equals("no"));

            viewAnotherInstructor = continueChoice.equals("yes");
        } while (viewAnotherInstructor);

        System.out.println("------------------------------------------------------"); // Separator line
        System.out.println("Returning to the main menu...");
        System.out.println("------------------------------------------------------\n"); // Separator line
    }

    public static void addNewSwimStudent(List<SwimLesson> swimLessons, List<SwimStudent> swimStudents, WaitingList waitingList) {
        Scanner scanner = new Scanner(System.in);
        boolean continueAdding = true;

        do {
            System.out.print("Enter the name of the new student: ");
            String studentName = scanner.nextLine().trim();

            // Validate if the student name contains only letters
            if (!studentName.matches("[a-zA-Z]+")) {
                System.out.println("Invalid input. Student name should contain only letters.");
                continue;
            }

            // Display available classes for novice level, starting from Monday
            System.out.println("Weekly Schedule of Classes for Novice Level:");
            for (String day : new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"}) {
                for (SwimLesson lesson : swimLessons) {
                    if (lesson.getLevel().equalsIgnoreCase("novice") && lesson.getDay().equalsIgnoreCase(day)) {
                        int availableSpaces = MAX_STUDENTS_PER_LESSON - lesson.getStudents().size();
                        String availability = availableSpaces > 0 ? "Available spaces: " + availableSpaces : "Full";
                        System.out.println("- " + lesson.getDay() + ", " + lesson.getStart_time() + " - " + availability);
                    }
                }
            }

            // Prompt user to select a session or add to waiting list
            System.out.print("Do you want to allocate this student to a session or add to waiting list? (session/waiting): ");
            String allocationChoice = scanner.nextLine().trim().toLowerCase();

            if (allocationChoice.equals("session")) {
                System.out.print("Enter the day of the session: ");
                String sessionDay = scanner.nextLine().trim();

                System.out.print("Enter the start time of the session: ");
                String sessionTime = scanner.nextLine().trim();

                // Find the selected lesson
                SwimLesson selectedLesson = null;
                for (SwimLesson lesson : swimLessons) {
                    if (lesson.getDay().equalsIgnoreCase(sessionDay) && lesson.getStart_time().equalsIgnoreCase(sessionTime) && lesson.getLevel().equalsIgnoreCase("novice")) {
                        selectedLesson = lesson;
                        break;
                    }
                }

                if (selectedLesson != null) {
                    if (selectedLesson.getStudents().size() < MAX_STUDENTS_PER_LESSON) {
                        SwimStudent newStudent = new SwimStudent(studentName, "novice");
                        selectedLesson.addStudent(newStudent);
                        newStudent.assignLesson(selectedLesson);
                        swimStudents.add(newStudent); // Add the new student to the list
                        System.out.println("Student " + studentName + " has been successfully allocated to the session on " + sessionDay + " at " + sessionTime + ".");
                    } else {
                        System.out.println("Sorry, the selected session is full. Adding " + studentName + " to the waiting list.");
                        waitingList.addStudent(new SwimStudent(studentName, "novice"));
                    }
                } else {
                    System.out.println("Invalid session. Please make sure to enter the correct day and start time.");
                }
            } else if (allocationChoice.equals("waiting")) {
                waitingList.addStudent(new SwimStudent(studentName, "novice"));
                System.out.println("Student " + studentName + " has been added to the waiting list.");
            } else {
                System.out.println("Invalid choice. Please enter 'session' or 'waiting'.");
            }

            // Prompt user if they want to add another student
            String addAnother;
            do {
                System.out.print("Do you want to add another student? (yes/no): ");
                addAnother = scanner.nextLine().trim().toLowerCase();
                if (!addAnother.equals("yes") && !addAnother.equals("no")) {
                    System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                }
            } while (!addAnother.equals("yes") && !addAnother.equals("no"));

            continueAdding = addAnother.equals("yes");
        } while (continueAdding);

        System.out.println("------------------------------------------------------"); // Separator line
        System.out.println("Returning to the main menu...");
        System.out.println("------------------------------------------------------\n"); // Separator line
    }

    public static void awardSwimQualification(List<Instructor> instructors, List<SwimStudent> swimStudents, WaitingList waitingList) {
        Scanner scanner = new Scanner(System.in);
        boolean continueAwarding = true;

        do {
            // Display list of instructors sorted alphabetically by name
            System.out.println("Select an instructor:");
            List<Instructor> sortedInstructors = new ArrayList<>(instructors);
            sortedInstructors.sort(Comparator.comparing(Instructor::getName));
            for (int i = 0; i < sortedInstructors.size(); i++) {
                System.out.println((i + 1) + ". " + sortedInstructors.get(i).getName());
            }

            // Prompt user to select an instructor
            System.out.print("Enter the number of the instructor: ");
            int instructorChoice;
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume invalid input
            }
            instructorChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            if (instructorChoice >= 1 && instructorChoice <= sortedInstructors.size()) {
                Instructor selectedInstructor = sortedInstructors.get(instructorChoice - 1);

                // Display list of swim students sorted alphabetically by name
                System.out.println("Select a swim student:");
                List<SwimStudent> sortedStudents = new ArrayList<>(swimStudents);
                sortedStudents.sort(Comparator.comparing(SwimStudent::getName));
                for (int i = 0; i < sortedStudents.size(); i++) {
                    SwimStudent student = sortedStudents.get(i);
                    System.out.println((i + 1) + ". " + student.getName() + " (" + student.getLevel() + ")");
                }

                // Prompt user to select a swim student
                System.out.print("Enter the number of the student: ");
                int studentChoice;
                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next(); // Consume invalid input
                }
                studentChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                if (studentChoice >= 1 && studentChoice <= sortedStudents.size()) {
                    SwimStudent selectedStudent = sortedStudents.get(studentChoice - 1);

                    if (selectedStudent.getLevel().equalsIgnoreCase("advanced")) {
                        System.out.println("Select the type of qualification:");
                        System.out.println("1. Distance Swim Qualification");
                        System.out.println("2. Personal Survival Qualification");
                        System.out.print("Enter your choice: ");
                        int qualificationChoice;
                        while (!scanner.hasNextInt()) {
                            System.out.println("Invalid input. Please enter a number.");
                            scanner.next(); // Consume invalid input
                        }
                        qualificationChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character

                        if (qualificationChoice == 1) {
                            System.out.println("Select a distance swim qualification:");
                            System.out.println("1. 50 metres");
                            System.out.println("2. 100 metres");
                            System.out.println("3. 200 metres");
                            System.out.println("4. 400 metres");
                            System.out.println("5. 800 metres");
                            System.out.println("6. 1500 metres");
                            System.out.print("Enter your choice: ");
                            int distanceChoice;
                            while (!scanner.hasNextInt()) {
                                System.out.println("Invalid input. Please enter a number.");
                                scanner.next(); // Consume invalid input
                            }
                            distanceChoice = scanner.nextInt();
                            scanner.nextLine(); // Consume newline character

                            switch (distanceChoice) {
                                case 1:
                                    DistanceSwim swimQualification50m = new DistanceSwim(50);
                                    Qualification.awardQualification(selectedStudent, selectedInstructor, swimQualification50m);
                                    checkAndPromoteStudent(selectedStudent, swimQualification50m);
                                    break;
                                case 2:
                                    DistanceSwim swimQualification100m = new DistanceSwim(100);
                                    Qualification.awardQualification(selectedStudent, selectedInstructor, swimQualification100m);
                                    checkAndPromoteStudent(selectedStudent, swimQualification100m);
                                    break;
                                case 3:
                                    DistanceSwim swimQualification200m = new DistanceSwim(200);
                                    Qualification.awardQualification(selectedStudent, selectedInstructor, swimQualification200m);
                                    checkAndPromoteStudent(selectedStudent, swimQualification200m);
                                    break;
                                case 4:
                                    DistanceSwim swimQualification400m = new DistanceSwim(400);
                                    Qualification.awardQualification(selectedStudent, selectedInstructor, swimQualification400m);
                                    checkAndPromoteStudent(selectedStudent, swimQualification400m);
                                    break;
                                case 5:
                                    DistanceSwim swimQualification800m = new DistanceSwim(800);
                                    Qualification.awardQualification(selectedStudent, selectedInstructor, swimQualification800m);
                                    checkAndPromoteStudent(selectedStudent, swimQualification800m);
                                    break;
                                case 6:
                                    DistanceSwim swimQualification1500m = new DistanceSwim(1500);
                                    Qualification.awardQualification(selectedStudent, selectedInstructor, swimQualification1500m);
                                    checkAndPromoteStudent(selectedStudent, swimQualification1500m);
                                    break;
                                default:
                                    System.out.println("Invalid choice.");
                            }

                        } else if (qualificationChoice == 2) {
                            System.out.println("Select a personal survival qualification:");
                            System.out.println("1. Basic Survival");
                            System.out.println("2. Intermediate Survival");
                            System.out.println("3. Advanced Survival");
                            System.out.print("Enter your choice: ");
                            int survivalChoice;
                            while (!scanner.hasNextInt()) {
                                System.out.println("Invalid input. Please enter a number.");
                                scanner.next(); // Consume invalid input
                            }
                            survivalChoice = scanner.nextInt();
                            scanner.nextLine(); // Consume newline character

                            switch (survivalChoice) {
                                case 1:
                                    PersonalSurvival basicSurvival = new PersonalSurvival("Basic");
                                    Qualification.awardQualification(selectedStudent, selectedInstructor, basicSurvival);
                                    break;
                                case 2:
                                    PersonalSurvival intermediateSurvival = new PersonalSurvival("Intermediate");
                                    Qualification.awardQualification(selectedStudent, selectedInstructor, intermediateSurvival);
                                    break;
                                case 3:
                                    PersonalSurvival advancedSurvival = new PersonalSurvival("Advanced");
                                    Qualification.awardQualification(selectedStudent, selectedInstructor, advancedSurvival);
                                    break;
                                default:
                                    System.out.println("Invalid choice.");
                            }

                        } else {
                            System.out.println("Invalid choice.");
                        }
                    } else {
                        System.out.println("Select a distance swim qualification:");
                        System.out.println("1. 20 metres");
                        System.out.println("2. 50 metres");
                        System.out.println("3. 100 metres");
                        System.out.println("4. 200 metres");
                        System.out.println("5. 400 metres");
                        System.out.print("Enter your choice: ");
                        int distanceChoice;
                        while (!scanner.hasNextInt()) {
                            System.out.println("Invalid input. Please enter a number.");
                            scanner.next(); // Consume invalid input
                        }
                        distanceChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character

                        switch (distanceChoice) {
                            case 1:
                                DistanceSwim swimQualification20m = new DistanceSwim(20);
                                Qualification.awardQualification(selectedStudent, selectedInstructor, swimQualification20m);
                                checkAndPromoteStudent(selectedStudent, swimQualification20m);
                                break;
                            case 2:
                                DistanceSwim swimQualification50m = new DistanceSwim(50);
                                Qualification.awardQualification(selectedStudent, selectedInstructor, swimQualification50m);
                                checkAndPromoteStudent(selectedStudent, swimQualification50m);
                                break;
                            case 3:
                                DistanceSwim swimQualification100m = new DistanceSwim(100);
                                Qualification.awardQualification(selectedStudent, selectedInstructor, swimQualification100m);
                                checkAndPromoteStudent(selectedStudent, swimQualification100m);
                                break;
                            case 4:
                                DistanceSwim swimQualification200m = new DistanceSwim(200);
                                Qualification.awardQualification(selectedStudent, selectedInstructor, swimQualification200m);
                                checkAndPromoteStudent(selectedStudent, swimQualification200m);
                                break;
                            case 5:
                                DistanceSwim swimQualification400m = new DistanceSwim(400);
                                Qualification.awardQualification(selectedStudent, selectedInstructor, swimQualification400m);
                                checkAndPromoteStudent(selectedStudent, swimQualification400m);
                                break;
                            default:
                                System.out.println("Invalid choice.");
                        }
                    }

                    System.out.println("Qualification awarded successfully.");

                } else {
                    System.out.println("Invalid choice.");
                }
            } else {
                System.out.println("Invalid choice.");
            }

            // Prompt user if they want to award another qualification
            String awardAnother;
            do {
                System.out.print("Do you want to award another qualification? (yes/no): ");
                awardAnother = scanner.nextLine().trim().toLowerCase();
                if (!awardAnother.equals("yes") && !awardAnother.equals("no")) {
                    System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                }
            } while (!awardAnother.equals("yes") && !awardAnother.equals("no"));

            continueAwarding = awardAnother.equals("yes");
        } while (continueAwarding);

        System.out.println("------------------------------------------------------"); // Separator line
        System.out.println("Returning to the main menu...");
        System.out.println("------------------------------------------------------\n"); // Separator line
    }

// Helper method to check and promote student level based on the awarded qualification
    private static void checkAndPromoteStudent(SwimStudent student, Qualification qualification) {
        if (student.getLevel().equalsIgnoreCase("novice") && qualification instanceof DistanceSwim) {
            DistanceSwim swimQualification = (DistanceSwim) qualification;
            int distance = swimQualification.getDistance();
            if (distance == 20) {
                student.setLevel("improver");
                System.out.println(student.getName() + " has been promoted to improver level.");
            }
        } else if (student.getLevel().equalsIgnoreCase("improver") && qualification instanceof DistanceSwim) {
            DistanceSwim swimQualification = (DistanceSwim) qualification;
            int distance = swimQualification.getDistance();
            if (distance == 400) {
                student.setLevel("advanced");
                System.out.println(student.getName() + " has been promoted to advanced level.");
            }
        }
    }

    public static void moveSwimStudentFromWaitingList(WaitingList waitingList, List<SwimLesson> swimLessons) {
        Scanner scanner = new Scanner(System.in);

        // Display swim students in the waiting list
        List<SwimStudent> waitingStudents = waitingList.getStudents();
        if (waitingStudents.isEmpty()) {
            System.out.println("There are no swim students in the waiting list.");
            return;
        }

        // Organize waiting students by level
        Map<String, List<SwimStudent>> organizedWaitingStudents = new LinkedHashMap<>();
        for (SwimStudent student : waitingStudents) {
            String level = student.getLevel();
            organizedWaitingStudents.putIfAbsent(level, new ArrayList<>());
            organizedWaitingStudents.get(level).add(student);
        }

        // Display waiting students organized by level
        System.out.println("Swim students in the waiting list:");
        int studentCount = 1;
        for (Map.Entry<String, List<SwimStudent>> entry : organizedWaitingStudents.entrySet()) {
            String level = entry.getKey();
            List<SwimStudent> studentsInLevel = entry.getValue();
            for (SwimStudent student : studentsInLevel) {
                System.out.println(studentCount + ". " + student.getName() + " - " + level);
                studentCount++;
            }
        }

        // Prompt user to select a swim student from the waiting list
        SwimStudent selectedStudent = selectStudentFromWaitingList(waitingStudents, scanner);
        if (selectedStudent == null) {
            System.out.println("------------------------------------------------------"); // Separator line
            System.out.println("Invalid choice. Returning to the main menu.");
            System.out.println("------------------------------------------------------\n"); // Separator line
            return;
        }

        // Display weekly schedule of classes for the selected student's level
        displayWeeklySchedule(selectedStudent, swimLessons);

        // Prompt user to select a session to transfer the swim student
        SwimLesson selectedLesson = selectSessionToTransfer(selectedStudent, swimLessons, scanner);
        if (selectedLesson == null) {
            System.out.println("\n\n------------------------------------------------------"); // Separator line
            System.out.println("Invalid session choice. Returning to the main menu.");
            System.out.println("------------------------------------------------------\n"); // Separator line
            return;
        }

        // Transfer the swim student to the selected session
        transferStudentToSession(selectedStudent, selectedLesson);

        // Remove the selected student from the waiting list
        waitingList.removeStudent(selectedStudent);
    }

    private static SwimStudent selectStudentFromWaitingList(List<SwimStudent> waitingStudents, Scanner scanner) {
        int studentChoice;
        do {
            System.out.print("Enter the number of the swim student to move from the waiting list: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume invalid input
            }
            studentChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            if (studentChoice < 1 || studentChoice > waitingStudents.size()) {
                System.out.println("Invalid choice. Please enter a number within the range.");
            }
        } while (studentChoice < 1 || studentChoice > waitingStudents.size());

        return waitingStudents.get(studentChoice - 1);
    }

    private static void displayWeeklySchedule(SwimStudent student, List<SwimLesson> swimLessons) {
        System.out.println("Weekly schedule of classes for " + student.getName() + " (" + student.getLevel() + "):");
        for (SwimLesson lesson : swimLessons) {
            if (lesson.getLevel().equalsIgnoreCase(student.getLevel())) {
                int availableSpaces = MAX_STUDENTS_PER_LESSON - lesson.getStudents().size();
                String availability = availableSpaces > 0 ? "Available (" + availableSpaces + " spaces)" : "Full";
                System.out.println(lesson.getDay() + " " + lesson.getTime() + " - " + availability);
            }
        }
    }

    private static SwimLesson selectSessionToTransfer(SwimStudent student, List<SwimLesson> swimLessons, Scanner scanner) {
        System.out.print("Enter the day of the session to transfer to (e.g., Monday): ");
        String dayChoice = scanner.nextLine().trim().toLowerCase();

        System.out.print("Enter the time of the session to transfer to (e.g., 18:00): ");
        String timeChoice = scanner.nextLine().trim();

        String sessionChoice = dayChoice + " " + timeChoice;

        for (SwimLesson lesson : swimLessons) {
            if (lesson.getLevel().equalsIgnoreCase(student.getLevel()) && lesson.getDayTime().trim().toLowerCase().equalsIgnoreCase(sessionChoice)) {
                return lesson;
            }
        }
        return null;
    }

    private static void transferStudentToSession(SwimStudent student, SwimLesson lesson) {
        int availableSpaces = MAX_STUDENTS_PER_LESSON - lesson.getStudents().size();
        if (availableSpaces > 0) {
            lesson.addStudent(student);
            System.out.println(student.getName() + " has been successfully moved to the session at " + lesson.getDayTime());
        } else {
            System.out.println(student.getName() + " was not transferred to " + lesson.getDay() + " " + lesson.getTime() + ". Session full.");
        }
    }

    public static void main(String[] args) {
        // Dummy data initialization
        Instructor instructor1 = new Instructor("John Doe");
        Instructor instructor2 = new Instructor("Jane Smith");
        Instructor instructor3 = new Instructor("Michael Johnson");

        SwimStudent student1 = new SwimStudent("Alice", "novice");
        SwimStudent student2 = new SwimStudent("Bob", "improver");
        SwimStudent student3 = new SwimStudent("Charlie", "advanced");
        SwimStudent student4 = new SwimStudent("David", "novice");
        SwimStudent student5 = new SwimStudent("Eva", "novice");
        SwimStudent student6 = new SwimStudent("Frank", "novice");
        SwimStudent student7 = new SwimStudent("George", "novice");
        SwimStudent student8 = new SwimStudent("Hannah", "advanced");
        SwimStudent student9 = new SwimStudent("Ian", "improver");
        SwimStudent student10 = new SwimStudent("Julia", "novice");

        // Assigning students to some initial lessons
        SwimLesson lesson1 = new SwimLesson("Monday", "17:00", "novice", instructor1);
        lesson1.addStudent(student1);
        student1.assignLesson(lesson1);

        SwimLesson lesson2 = new SwimLesson("Wednesday", "17:30", "improver", instructor1);
        lesson2.addStudent(student2);
        student2.assignLesson(lesson2);

        SwimLesson lesson3 = new SwimLesson("Friday", "18:00", "advanced", instructor2);
        lesson3.addStudent(student3);
        student3.assignLesson(lesson3);

        // Adding students to the waiting list
        WaitingList waitingList = new WaitingList();
        waitingList.addStudent(student7);
        waitingList.addStudent(student8);
        waitingList.addStudent(student9);

        // Additional lessons with varying numbers of students enrolled
        SwimLesson lesson4 = new SwimLesson("Monday", "18:00", "novice", instructor2);
        lesson4.addStudent(student4);
        lesson4.addStudent(student5);
        lesson4.addStudent(student10);
        student4.assignLesson(lesson4);
        student5.assignLesson(lesson4);
        student10.assignLesson(lesson4);

        SwimLesson lesson5 = new SwimLesson("Wednesday", "17:30", "novice", instructor2);
        lesson5.addStudent(student6);
        student6.assignLesson(lesson5);

        SwimLesson lesson6 = new SwimLesson("Friday", "18:00", "novice", instructor1);

        SwimLesson lesson7 = new SwimLesson("Tuesday", "15:00", "novice", instructor2);
        SwimLesson lesson8 = new SwimLesson("Thursday", "16:00", "improver", instructor1);
        SwimLesson lesson9 = new SwimLesson("Saturday", "14:00", "advanced", instructor2);

        // Additional lessons for the third instructor
        SwimLesson lesson10 = new SwimLesson("Monday", "16:00", "novice", instructor3);
        lesson10.addStudent(student1);
        student1.assignLesson(lesson10);

        SwimLesson lesson11 = new SwimLesson("Tuesday", "17:30", "improver", instructor3);
        lesson11.addStudent(student2);
        lesson11.addStudent(student3);
        student2.assignLesson(lesson11);
        student3.assignLesson(lesson11);

        SwimLesson lesson12 = new SwimLesson("Wednesday", "18:00", "advanced", instructor3);
        lesson12.addStudent(student4);
        lesson12.addStudent(student8);
        student4.assignLesson(lesson12);
        student8.assignLesson(lesson12);

// List of SwimStudents
        List<SwimStudent> swimStudents = new ArrayList<>(Arrays.asList(student1, student2, student3, student4, student5, student6, student7,
                student8, student9, student10));

        // List of instructors
        List<Instructor> instructors = Arrays.asList(instructor1, instructor2, instructor3);
        // Combine all swim lessons into a single list
        List<SwimLesson> swimLessons = Arrays.asList(lesson1, lesson2, lesson3, lesson4, lesson5, lesson6, lesson7, lesson8,
                lesson9, lesson10, lesson11, lesson12);

        // Implement the console-based user interface to facilitate the listed use cases
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Swim School Management System");
            System.out.println("1. View swim student information");
            System.out.println("2. View swim lesson details");
            System.out.println("3. View instructor schedule");
            System.out.println("4. Add new swim student");
            System.out.println("5. Award swim qualification");
            System.out.println("6. Move swim student from waiting list");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            // Input validation for menu choice
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a numeric value.");
                scanner.next(); // consume invalid input
            }
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewSwimStudentInformation(swimStudents, waitingList);
                    break;
                case 2:
                    viewSwimLessonDetails(Arrays.asList(lesson1, lesson2, lesson3, lesson4, lesson5, lesson6, lesson7, lesson8, lesson9, lesson10, lesson11, lesson12));
                    break;
                case 3:
                    viewInstructorSchedule(instructors, swimLessons);
                    break;
                case 4:
                    addNewSwimStudent(swimLessons, swimStudents, waitingList);
                    break;
                case 5:
                    awardSwimQualification(instructors, swimStudents, waitingList);
                    break;
                case 6:
                    moveSwimStudentFromWaitingList(waitingList, swimLessons);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 0);
        scanner.close();
    }
}
