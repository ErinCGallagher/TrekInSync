package com.example.ering.trekinsync.adapters;

import android.content.Context;

import com.example.ering.trekinsync.databinders.BaseDataBinder;
import com.example.ering.trekinsync.databinders.InsuranceRowBinder;
import com.example.ering.trekinsync.databinders.LabelDescriptionRowBinder;
import com.example.ering.trekinsync.databinders.PhoneNumberRowBinder;
import com.example.ering.trekinsync.databinders.SectionDividerTitleRowBinder;
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
        listItems.add(new LabelDescriptionRowBinder(presenter.getBirthDateLabel(), presenter.getBirthDate()));
        listItems.add(new LabelDescriptionRowBinder(presenter.getAgeLabel(), presenter.getAge()));
        listItems.add(new LabelDescriptionRowBinder(presenter.getCitizenshipLabel(), presenter.getCitizenship()));

        //Health info
        listItems.add(new SectionDividerTitleRowBinder(presenter.getHealthSectionTitle()));
        //TODO: create allergy cell with severity indicator
        listItems.add(new LabelDescriptionRowBinder(presenter.getBirthDateLabel(), presenter.getBloodType()));
        listItems.add(new LabelDescriptionRowBinder(presenter.getAllergiesLabel(), presenter.getAllergies()));
        listItems.add(new LabelDescriptionRowBinder(presenter.getMedicineLabel(), presenter.getMedicine()));

        //Emergency Contact Info
        listItems.add(new SectionDividerTitleRowBinder(presenter.getEmergencyContactSectionTitle()));
        //TODO: have presenter return number of contact models
        listItems.add(new PhoneNumberRowBinder("Mother", "416-747-3625", "Cell"));
        listItems.add(new PhoneNumberRowBinder("Father", "416-888-4836", "Work"));

        //Insurance Info
        listItems.add(new SectionDividerTitleRowBinder(presenter.getInsuranceSectionTitle()));
        //TODO: have presenter return number of insurance company models
        ArrayList<String> labelsList = new ArrayList<>();
        labelsList.add("Cert #");
        labelsList.add("policy #");
        ArrayList<String> numbersList = new ArrayList<>();
        numbersList.add("12312");
        numbersList.add("23584");
        listItems.add(new InsuranceRowBinder("Manulife", "123-321-1234", "Call First", true, labelsList, numbersList));
    }

    @Override
    public List<BaseDataBinder> getItems() {
        return listItems;
    }
}
