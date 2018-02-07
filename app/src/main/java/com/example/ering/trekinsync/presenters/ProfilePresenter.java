package com.example.ering.trekinsync.presenters;

import com.example.ering.trekinsync.models.EmergencyContact;
import com.example.ering.trekinsync.models.InsuranceCompany;
import com.example.ering.trekinsync.models.PolicyInfo;
import com.example.ering.trekinsync.models.User;

public class ProfilePresenter {
    private User user;
    /**
     * Create Profile Presenter for Business logic
     */
    public ProfilePresenter () {
        //take in view interface
        user = createTestData();
    }

    //Section Title Data
    public String getGeneralSectionTitle() {
        return "GENERAL INFO";
    }

    public String getHealthSectionTitle() {
        return "HEALTH INFO";
    }

    public String getEmergencyContactSectionTitle() {
        return "EMERGENCY CONTACT INFO";
    }

    public String getInsuranceSectionTitle() {
        return "INSURANCE INFO";
    }

    //General Info Section
    public String getActionBarTitle() {
        return "Personal profile";
    }

    public String getFullNameLabel() {
        return "Full Name";
    }

    public String getFullName() {
        return user.getName();
    }

    public String getBirthDateLabel() {
        return "Birth Date";
    }

    public String getBirthDate() {
        return user.getBirthDate();
    }

    public String getAgeLabel() {
        return "Age";
    }

    public String getAge() {
        return Integer.toString(user.getAge());
    }

    public String getCitizenshipLabel() {
        return "Citizenship";
    }

    public String getCitizenship() {
        return user.getCitizenship();
    }

    //Health Section
    public String getBloodTypeLabel() {
        return "Blood Type";
    }

    public String getBloodType() {
        return user.getBloodType();
    }

    public String getAllergiesLabel() {
        return "Allergies";
    }

    public String getAllergies() {
        //TODO: use model to retrieve list, or could potentially be large string
        return user.getAllergies();
    }

    public String getMedicineLabel() {
        return "Medicine";
    }

    public String getMedicine() {
        return user.getMedicine();
    }

    //Emergency Contact section
    public EmergencyContact[] getEmergencyContacts() {
        return user.getEmergencyContacts();
    }

    //Insurance Company Section
    public String getCallFirstLabel() {
        return "Call First";
    }

    public InsuranceCompany[] getInsuranceCompanies() {
        return user.getInsuranceInfo();
    }

    private User createTestData() {
        PolicyInfo policyInfo = new PolicyInfo("policy #", "12345");
        PolicyInfo policyInfo2 = new PolicyInfo("cert #", "098");
        PolicyInfo[] policyInfoArray = new PolicyInfo[2];
        policyInfoArray[0] = policyInfo;
        policyInfoArray[1] = policyInfo2;

        InsuranceCompany insuranceCompany = new InsuranceCompany("Manulife", "416-098-4663", true, policyInfoArray);
        InsuranceCompany[] insuranceCompanyArray = new InsuranceCompany[1];
        insuranceCompanyArray[0] = insuranceCompany;

        EmergencyContact emergencyContact = new EmergencyContact("Mother", "416-747-3625", "Cell");
        EmergencyContact emergencyContact2 = new EmergencyContact("Father", "416-888-9865", "Work");
        EmergencyContact[] emergencyContactArray = new EmergencyContact[2];
        emergencyContactArray[0] = emergencyContact;
        emergencyContactArray[1] = emergencyContact2;

        User user = new User("Erin Gallagher",
                "March 24, 1994",
                23,
                "Canadian",
                "O Negative",
                "Scented Creme, Heat, Maple Trees",
                "none",
                emergencyContactArray,
                insuranceCompanyArray);
        return user;
    }
}

