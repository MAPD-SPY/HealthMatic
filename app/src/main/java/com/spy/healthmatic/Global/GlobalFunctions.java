package com.spy.healthmatic.Global;


import com.spy.healthmatic.POJO.Doctor;
import com.spy.healthmatic.POJO.Nurse;
import com.spy.healthmatic.models.Patient;

import java.util.ArrayList;

/**
 * Created by yatin on 28/10/16.
 */

public class GlobalFunctions {

    public static ArrayList<Patient> getDummyPatients(int maxCount){
        ArrayList<Patient> patients = new ArrayList<>();
        for (int i=0; i<maxCount; i++){
            if(i%2==0) {
                patients.add(new Patient("Patient " + i, "male", 100 + i + "", "Normal", 9929192, "Brain", new Doctor(), new ArrayList<Doctor>(), new ArrayList<Nurse>()));
            }else{
                patients.add(new Patient("Patient " + i, "female", 100 + i + "", "Critical", 9929192, "Heart", new Doctor(), new ArrayList<Doctor>(), new ArrayList<Nurse>()));
            }
        }
        return patients;
    }

    public static ArrayList<Doctor> getDummyDoctors(int maxCount){
        ArrayList<Doctor> doctors = new ArrayList<>();
        for (int i=0; i<maxCount; i++){
            if(i%2==0) {
                doctors.add(new Doctor("Doctor "+i, "male", "Heart"));
            }else{
                doctors.add(new Doctor("Doctor "+i, "female", "Brain"));
            }
        }
        return doctors;
    }

    public static ArrayList<Nurse> getDummyNurses(int maxCount){
        ArrayList<Nurse> nurses = new ArrayList<>();
        for (int i=0; i<maxCount; i++){
            if(i%2==0) {
                nurses.add(new Nurse("Nurse "+i, "male", i));
            }else{
                nurses.add(new Nurse("Nurse "+i, "female", i));
            }
        }
        return nurses;
    }

}
