package com.example.stage; // Replace with your package

public class Technician {

    private int id; // Or a unique identifier (String if using UUIDs)
    private String name;
    private String specialty; // Or other relevant fields (e.g., contact info, certifications)

    // Constructor
    public Technician(int id, String name, String specialty) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
    }

    // Getters and setters (for accessing and modifying the fields)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    // Optional: Override toString() for easy debugging/display
    @Override
    public String toString() {
        return "Technician{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", specialty='" + specialty + '\'' +
                '}';
    }
}