import java.util.*;

// Interface for appointment management
interface Appointmentable {
    void bookAppointment(String patientId, String doctorName) throws Exception;
    void viewAppointments();
    void postponeAppointment(String patientId) throws Exception;
    void cancelAppointment(String patientId) throws Exception;
}

// Base class Person
class Person {
    protected String name;
    protected int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void viewRecord() {
        System.out.println("Viewing general record.");
    }
}

// Doctor class
class Doctor extends Person implements Appointmentable {
    private String specialization;
    private String username;
    private String password;
    private List<String> appointments = new ArrayList<>();
    private Map<String, String> patientStatus = new HashMap<>();

    public Doctor(String name, int age, String specialization, String username, String password) {
        super(name, age);
        this.specialization = specialization;
        this.username = username;
        this.password = password;
    }

    public String getSpecialization() {
        return specialization;
    }

    public List<String> getAppointments() {
        return appointments;
    }

    public boolean login(String user, String pass) {
        return username.equals(user) && password.equals(pass);
    }

    @Override
    public void bookAppointment(String patientId, String doctorName) throws Exception {
        if (appointments.contains(patientId)) {
            throw new Exception("Duplicate appointment detected for patient ID: " + patientId);
        }
        appointments.add(patientId);
        patientStatus.put(patientId, "Safe");
        System.out.println("Appointment booked for patient ID: " + patientId + " with Dr. " + doctorName);
    }

    @Override
    public void viewAppointments() {
        System.out.println("\nAppointments for Dr. " + name + " (" + specialization + "):");
        if (appointments.isEmpty()) {
            System.out.println("No appointments available.");
        } else {
            for (String id : appointments) {
                System.out.println("- Patient ID: " + id + ", Status: " + patientStatus.get(id));
            }
        }
    }

    public void changePatientStatus(String patientId, String status) {
        if (patientStatus.containsKey(patientId)) {
            patientStatus.put(patientId, status);
            System.out.println("Patient ID: " + patientId + " status updated to: " + status);
        } else {
            System.out.println("Invalid patient ID or no appointment booked.");
        }
    }

    @Override
    public void postponeAppointment(String patientId) throws Exception {
        if (appointments.contains(patientId)) {
            System.out.println("Appointment postponed for patient ID: " + patientId);
        } else {
            throw new Exception("Appointment not found.");
        }
    }

    @Override
    public void cancelAppointment(String patientId) throws Exception {
        if (appointments.remove(patientId)) {
            patientStatus.remove(patientId);
            System.out.println("Appointment canceled for patient ID: " + patientId);
        } else {
            throw new Exception("Appointment not found.");
        }
    }
}

// Patient class
class Patient extends Person {
    private String patientId;
    private List<String> medicalHistory = new ArrayList<>();
    private double dueBill;

    public Patient(String name, int age, String patientId, double dueBill) {
        super(name, age);
        this.patientId = patientId;
        this.dueBill = dueBill;
    }

    public String getPatientId() {
        return patientId;
    }

    public void addMedicalRecord(String record) {
        medicalHistory.add(record);
    }

    public void viewBill() {
        System.out.println("\nDue Bill: Rs." + (int) dueBill);
    }

    public void makePayment(double amount) {
        if (amount <= dueBill) {
            dueBill -= amount;
            System.out.println("Payment of Rs." + (int) amount + " made. Remaining bill: " + (int) dueBill);
        } else {
            System.out.println("Invalid amount.");
        }
    }

    @Override
    public void viewRecord() {
        System.out.println("\nPatient: " + name + ", ID: " + patientId + ", Age: " + age);
        System.out.println("Medical History:");
        for (String record : medicalHistory) {
            System.out.println("- " + record);
        }
    }

    public void viewAppointments(List<Doctor> doctors) {
        System.out.println("\nYour Appointments:");
        boolean hasAppointments = false;

        for (Doctor doctor : doctors) {
            if (doctor.getAppointments().contains(patientId)) {
                hasAppointments = true;
                System.out.println("- Dr. " + doctor.name + " (" + doctor.getSpecialization() + ")");
            }
        }

        if (!hasAppointments) {
            System.out.println("No appointments found.");
        }
    }

    public void bookAppointment(Doctor doctor) {
        try {
            doctor.bookAppointment(patientId, doctor.name);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void postponeAppointment(Doctor doctor) {
        try {
            doctor.postponeAppointment(patientId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void cancelAppointment(Doctor doctor) {
        try {
            doctor.cancelAppointment(patientId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

// Main class
public class HospitalManagementSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Creating 5 doctors
        List<Doctor> doctors = Arrays.asList(
                new Doctor("Mark", 45, "Cardiologist", "Mark", "pass1"),
                new Doctor("John", 50, "Neurologist", "John", "pass2"),
                new Doctor("Tony", 38, "Orthopedic", "Tony", "pass3"),
                new Doctor("David", 42, "Dermatologist", "david123", "pass4"),
                new Doctor("Eva", 35, "Pediatrician", "eva123", "pass5")
        );

        // Creating 15 patients
        List<Patient> patients = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            patients.add(new Patient("Patient" + i, 20 + i, "P100" + i, 10000 * i));
            patients.get(i - 1).addMedicalRecord("Visit " + i + " - Routine Checkup");
        }

        while (true) {
            System.out.println("\nHospital Management System");
            System.out.println("1. Doctor Login");
            System.out.println("2. Patient Login");
            System.out.println("3. Exit");
            System.out.print("Choose: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Username: ");
                    String user = sc.nextLine();
                    System.out.print("Enter Password: ");
                    String pass = sc.nextLine();

                    Doctor currentDoctor = null;

                    for (Doctor doc : doctors) {
                        if (doc.login(user, pass)) {
                            currentDoctor = doc;
                            break;
                        }
                    }

                    if (currentDoctor != null) {
                        System.out.println("\nWelcome Dr. " + currentDoctor.name);
                        while (true) {
                            System.out.println("\n1. View Appointments");
                            System.out.println("2. Change Patient Status");
                            System.out.println("3. Postpone Appointment");
                            System.out.println("4. Cancel Appointment");
                            System.out.println("5. Logout");
                            System.out.print("Choose: ");

                            int docChoice = sc.nextInt();
                            sc.nextLine();

                            if (docChoice == 5) break;

                            switch (docChoice) {
                                case 1:
                                    currentDoctor.viewAppointments();
                                    break;
                                case 2:
                                    System.out.print("Enter Patient ID: ");
                                    String statusId = sc.nextLine();
                                    System.out.print("Enter Status (Safe / Emergency): ");
                                    String status = sc.nextLine();
                                    currentDoctor.changePatientStatus(statusId, status);
                                    break;
                                case 3:
                                    System.out.print("Enter Patient ID to postpone: ");
                                    String postponeId = sc.nextLine();
                                    try {
                                        currentDoctor.postponeAppointment(postponeId);
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                    break;
                                case 4:
                                    System.out.print("Enter Patient ID to cancel: ");
                                    String cancelId = sc.nextLine();
                                    try {
                                        currentDoctor.cancelAppointment(cancelId);
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                    break;
                                default:
                                    System.out.println("Invalid choice.");
                            }
                        }
                    } else {
                        System.out.println("Invalid credentials.");
                    }
                    break;

                case 2:
                    System.out.print("Enter Patient ID: ");
                    String patientId = sc.nextLine();

                    Patient currentPatient = null;

                    for (Patient pat : patients) {
                        if (pat.getPatientId().equals(patientId)) {
                            currentPatient = pat;
                            break;
                        }
                    }

                    if (currentPatient != null) {
                        System.out.println("\nWelcome " + currentPatient.name);
                        while (true) {
                            System.out.println("\n1. Book Appointment");
                            System.out.println("2. View Appointments");
                            System.out.println("3. Postpone Appointment");
                            System.out.println("4. Cancel Appointment");
                            System.out.println("5. View Bill");
                            System.out.println("6. Make Payment");
                            System.out.println("7. View Health Records");
                            System.out.println("8. Logout");
                            System.out.print("Choose: ");

                            int patChoice = sc.nextInt();
                            sc.nextLine();

                            if (patChoice == 8) break;

                            switch (patChoice) {
                                case 1:
                                    System.out.println("\nAvailable Doctors:");
                                    for (int i = 0; i < doctors.size(); i++) {
                                        System.out.println((i + 1) + ". Dr. " + doctors.get(i).name + " (" + doctors.get(i).getSpecialization() + ")");
                                    }
                                    System.out.print("Choose Doctor by ID (1-5): ");
                                    int docId = sc.nextInt();
                                    sc.nextLine();
                                    currentPatient.bookAppointment(doctors.get(docId - 1));
                                    break;
                                case 2:
                                    currentPatient.viewAppointments(doctors);
                                    break;
                                case 3:
                                    System.out.println("\nAvailable Doctors:");
                                    for (int i = 0; i < doctors.size(); i++) {
                                        System.out.println((i + 1) + ". Dr. " + doctors.get(i).name + " (" + doctors.get(i).getSpecialization() + ")");
                                    }
                                    System.out.print("Choose Doctor by ID (1-5): ");
                                    docId = sc.nextInt();
                                    sc.nextLine();
                                    currentPatient.postponeAppointment(doctors.get(docId - 1));
                                    break;
                                case 4:
                                    System.out.println("\nAvailable Doctors:");
                                    for (int i = 0; i < doctors.size(); i++) {
                                        System.out.println((i + 1) + ". Dr. " + doctors.get(i).name + " (" + doctors.get(i).getSpecialization() + ")");
                                    }
                                    System.out.print("Choose Doctor by ID (1-5): ");
                                    docId = sc.nextInt();
                                    sc.nextLine();
                                    currentPatient.cancelAppointment(doctors.get(docId - 1));
                                    break;
                                case 5:
                                    currentPatient.viewBill();
                                    break;
                                case 6:
                                    System.out.print("Enter Payment Amount: ");
                                    double amount = sc.nextDouble();
                                    sc.nextLine();
                                    currentPatient.makePayment(amount);
                                    break;
                                case 7:
                                    currentPatient.viewRecord();
                                    break;
                                default:
                                    System.out.println("Invalid choice.");
                            }
                        }
                    } else {
                        System.out.println("Invalid Patient ID.");
                    }
                    break;

                case 3:
                    System.out.println("Exiting...");
                    sc.close();
                    System.exit(0);
            }
        }
    }
}