package com.example.ering.trekinsync.adapters;

import android.content.Context;

import com.example.ering.trekinsync.databinders.BaseDataBinder;
import com.example.ering.trekinsync.databinders.InsuranceRowBinder;
import com.example.ering.trekinsync.databinders.LabelDescriptionRowBinder;
import com.example.ering.trekinsync.databinders.PhoneNumberRowBinder;
import com.example.ering.trekinsync.databinders.SectionDividerTitleRowBinder;
import com.example.ering.trekinsync.models.EmergencyContact;
import com.example.ering.trekinsync.models.InsuranceCompany;
import com.example.ering.trekinsync.models.PolicyInfo;
import com.example.ering.trekinsync.presenters.ProfilePresenter;

import java.util.ArrayList;
import java.util.List;

public class ProfileAdapter extends BaseAdapter {

    private final Context ctx;
    private final ProfilePresenter presenter;
    private final ArrayList<BaseDataBinder> listItems;

    /**
     * Create adapter for displaying profile details
     * @param context
     * @param profilePresenter
     */
    public ProfileAdapter(Context context, ProfilePresenter profilePresenter) {
        ctx = context;
        presenter = profilePresenter;
        listItems = new ArrayList<>();
    }

    /**
     * populates data in the profile adapter.
     */
    public void buildRows() {
        listItems.clear();
        //General Info
        listItems.add(new SectionDividerTitleRowBinder(presenter.getGeneralSectionTitle()));
        //TODO: create profile picture upload cell?
        listItems.add(new LabelDescriptionRowBinder(presenter.getFullNameLabel(),presenter.getFullName()));
        //TODO: create birth date and age cell where they are side by side
        listItems.add(new LabelDescriptionRowBinder(presenter.getBirthDateLabel(), presenter.getBirthDate()));
        listItems.add(new LabelDescriptionRowBinder(presenter.getAgeLabel(), presenter.getAge()));
        listItems.add(new LabelDescriptionRowBinder(presenter.getCitizenshipLabel(), presenter.getCitizenship()));

        //Health info
        listItems.add(new SectionDividerTitleRowBinder(presenter.getHealthSectionTitle()));
        //TODO: create allergy cell with severity indicator
        listItems.add(new LabelDescriptionRowBinder(presenter.getBloodTypeLabel(), presenter.getBloodType()));
        listItems.add(new LabelDescriptionRowBinder(presenter.getAllergiesLabel(), presenter.getAllergies()));
        listItems.add(new LabelDescriptionRowBinder(presenter.getMedicineLabel(), presenter.getMedicine()));

        //Emergency Contact Info
        if (presenter.getEmergencyContacts().length > 0) {
            listItems.add(new SectionDividerTitleRowBinder(presenter.getEmergencyContactSectionTitle()));
        }
        for (EmergencyContact contact:presenter.getEmergencyContacts()) {
            listItems.add(new PhoneNumberRowBinder(contact.getName(), contact.getPhoneNumber(), contact.getPhoneNumberType()));
        }

        //Insurance Info
        if (presenter.getInsuranceCompanies().length > 0) {
            listItems.add(new SectionDividerTitleRowBinder(presenter.getInsuranceSectionTitle()));
        }
        for (InsuranceCompany company:presenter.getInsuranceCompanies()) {
            ArrayList<String> labelsList = new ArrayList<>();
            ArrayList<String> numbersList = new ArrayList<>();
            for(PolicyInfo info:company.getPolicyInfo()) {
                labelsList.add(info.getName());
                numbersList.add(info.getNumber());
            }
            listItems.add(new InsuranceRowBinder(company.getName(), company.getPhoneNumber(), presenter.getCallFirstLabel(), company.isCallFirst(), labelsList, numbersList));
        }
    }

    @Override
    public List<BaseDataBinder> getItems() {
        return listItems;
    }
}
