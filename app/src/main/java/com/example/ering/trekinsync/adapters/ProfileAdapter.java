package com.example.ering.trekinsync.adapters;

import com.example.ering.trekinsync.databinders.BaseDataBinder;
import com.example.ering.trekinsync.databinders.DualLabelDescriptionRowBinder;
import com.example.ering.trekinsync.databinders.InsuranceRowBinder;
import com.example.ering.trekinsync.databinders.LabelDescriptionRowBinder;
import com.example.ering.trekinsync.databinders.PhoneNumberRowBinder;
import com.example.ering.trekinsync.databinders.ProfileHeaderRowBinder;
import com.example.ering.trekinsync.databinders.SectionDividerTitleRowBinder;
import com.example.ering.trekinsync.models.EmergencyContact;
import com.example.ering.trekinsync.models.InsuranceCompany;
import com.example.ering.trekinsync.models.PolicyInfo;
import com.example.ering.trekinsync.presenters.ProfilePresenter;
import com.example.ering.trekinsync.utils.UserSingletonUtils;

import java.util.ArrayList;
import java.util.List;

public class ProfileAdapter extends BaseAdapter {

    private final ProfilePresenter presenter;
    private final ArrayList<BaseDataBinder> listItems;

    /**
     * Create adapter for displaying profile details
     * @param profilePresenter
     */
    public ProfileAdapter(ProfilePresenter profilePresenter) {
        presenter = profilePresenter;
        listItems = new ArrayList<>();
    }

    /**
     * populates data in the profile adapter.
     */
    public void buildRows() {
        listItems.clear();
        listItems.add(new ProfileHeaderRowBinder(presenter.getFullName(), presenter.getHeaderColor(), presenter.getProfileIcon()));
        listItems.add(new DualLabelDescriptionRowBinder(presenter.getBirthDateLabel(), presenter.getBirthDate(),
                presenter.getAgeLabel(), presenter.getAge()));
        listItems.add(new LabelDescriptionRowBinder(presenter.getCitizenshipLabel(), presenter.getCitizenship()));

        //Health info
        listItems.add(new SectionDividerTitleRowBinder(presenter.getHealthSectionTitle()));
        //TODO: create allergy cell with severity indicator
        listItems.add(new LabelDescriptionRowBinder(presenter.getBloodTypeLabel(), presenter.getBloodType()));

        //Allergies
        if (presenter.getAllergies() != null && !presenter.getAllergies().isEmpty()) {
            listItems.add(new LabelDescriptionRowBinder(presenter.getAllergiesLabel(), presenter.getAllergies()));
        }
        
        //Medication
        if (presenter.getMedicine() != null & !presenter.getMedicine().isEmpty()) {
            listItems.add(new LabelDescriptionRowBinder(presenter.getMedicineLabel(), presenter.getMedicine()));
        }

        //Emergency Contact Info
        if (presenter.getEmergencyContacts() != null && presenter.getEmergencyContacts().length > 0) {
            listItems.add(new SectionDividerTitleRowBinder(presenter.getEmergencyContactSectionTitle()));

            for (EmergencyContact contact : presenter.getEmergencyContacts()) {
                if (contact != null) {
                    String name = UserSingletonUtils.getInstance().getFormattedPhoneRelation(contact.getName());
                    String type = UserSingletonUtils.getInstance().getFormattedPhoneType(contact.getPhoneNumberType());
                    listItems.add(new PhoneNumberRowBinder(name, contact.getPhoneNumber(), type));
                }
            }
        }

        //Insurance Info
        if (presenter.getInsuranceCompanies() != null && presenter.getInsuranceCompanies().length > 0) {
            listItems.add(new SectionDividerTitleRowBinder(presenter.getInsuranceSectionTitle()));

            for (InsuranceCompany company : presenter.getInsuranceCompanies()) {
                ArrayList<String> labelsList = new ArrayList<>();
                ArrayList<String> numbersList = new ArrayList<>();
                for (PolicyInfo info : company.getPolicyInfo()) {
                    labelsList.add(info.getName());
                    numbersList.add(info.getNumber());
                }
                listItems.add(new InsuranceRowBinder(company.getName(), company.getPhoneNumber(), labelsList, numbersList));
            }
        }
    }

    public void reloadData() {
        buildRows();
        notifyDataSetChanged();
    }

    @Override
    public List<BaseDataBinder> getItems() {
        return listItems;
    }
}
