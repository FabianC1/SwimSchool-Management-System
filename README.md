# SwimSchool-Management-System

A Java-based swimming school management system designed to handle swim lessons, student enrollment, instructor schedules, qualifications, and waiting lists.

## Features

- Manage swim lessons with instructors, days, times, and student levels
- Assign students to lessons with capacity limits and waiting lists
- Track swim student qualifications (e.g., distance swim, personal survival)
- View detailed student information, swim lesson details, and instructor schedules
- Interactive console interface for managing and viewing data

## Technologies

- Java (no external dependencies)
- Console-based user interaction via `Scanner`

## How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/FabianC1/SwimSchool-Management-System.git
   ```
2. Navigate into the project directory:
   ```
   cd SwimSchool-Management-System/src
   ```
3. Compile the Java files:
   ```
   javac task2/*.java
   ```
4. Run the main class:]
   ```
   java task2.Task2
   ```
## Project Structure
Qualification (abstract class) and its subclasses DistanceSwim and PersonalSurvival

SwimLesson — represents lessons with day, time, instructor, and enrolled students

SwimStudent — student details, lesson assignment, and qualification

Instructor — instructor info

WaitingList — students waiting for a lesson slot

Task2 — main class that provides console-based user interaction

## Author 
Fabian Galasel

## Notes
This project demonstrates object-oriented design principles and Java programming skills by modeling a real-world swimming school management system. 
It provides functionality to manage student enrollments, lesson schedules, instructor allocations, and qualification tracking through a simple text-based interface. 
The system also incorporates data validation and user input handling to ensure robust interaction.
