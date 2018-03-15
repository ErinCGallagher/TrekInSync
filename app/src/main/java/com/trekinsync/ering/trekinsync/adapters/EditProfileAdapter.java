package com.trekinsync.ering.trekinsync.adapters;

import com.trekinsync.ering.trekinsync.R;
import com.trekinsync.ering.trekinsync.databinders.AddRowIconTitleCellDataBinder;
import com.trekinsync.ering.trekinsync.databinders.BaseDataBinder;
import com.trekinsync.ering.trekinsync.databinders.EditInsuranceDataBinder;
import com.trekinsync.ering.trekinsync.databinders.EditPhoneNumberRowBinder;
import com.trekinsync.ering.trekinsync.databinders.LabelDescriptionRowBinder;
import com.trekinsync.ering.trekinsync.databinders.OpenTextWithLabelCellDataBinder;
import com.trekinsync.ering.trekinsync.databinders.ProfileHeaderRowBinder;
import com.trekinsync.ering.trekinsync.databinders.SectionDividerTitleRowBinder;
import com.trekinsync.ering.trekinsync.models.EmergencyContact;
import com.trekinsync.ering.trekinsync.models.InsuranceCompany;
import com.trekinsync.ering.trekinsync.presenters.EditProfilePresenter;

import java.util.ArrayList;
import java.util.List;

public class EditProfileAdapter extends BaseAdapter {

    private final EditProfilePresenter presenter;
    private final ArrayList<BaseDataBinder> listItems;

    /**
     * Create an adapter to display travel contact cells
     * @param profilePresenter
     */
    public EditProfileAdapter(EditProfilePresenter profilePresenter) {
        presenter = profilePresenter;
        listItems = new ArrayList<>();
    }

    /**
     * populates data in the edit profile adapter.
     */
    public void buildRows() {
        listItems.clear();
        //TODO: indicate updates happening on changes, no save necessary
        listItems.add(new ProfileHeaderRowBinder(presenter.getFullName(), presenter.getHeaderColor(),
                presenter.getProfileIcon(), presenter.getFullNameEditListener()));
        listItems.add(new LabelDescriptionRowBinder(presenter.getBirthDateLabel(),
                presenter.getFormattedUserBirthday(),
                R.drawable.ic_date,
                presenter.createBirthdayDropDownListener()));
        listItems.add(new LabelDescriptionRowBinder(presenter.getCitizenshipLabel(),
                presenter.getUserCitizenship(),
                R.drawable.ic_keyboard_arrow_down,
                presenter.createCitizenshipDropDownRowListener()));

        //Health info
        listItems.add(new SectionDividerTitleRowBinder(presenter.getHealthSectionTitle()));
        listItems.add(new LabelDescriptionRowBinder(presenter.getBloodTypeLabel(),
                presenter.getUserBloodType(),
                R.drawable.ic_keyboard_arrow_down,
                presenter.createBloodTypeDropDownListener()));
        listItems.add(new OpenTextWithLabelCellDataBinder(presenter.getAllergiesLabel(),
                presenter.getAllergies(),
                presenter.createAllergiesDataListener()));
        listItems.add(new OpenTextWithLabelCellDataBinder(presenter.getMedicationLabel(),
                presenter.getMedications(),
                presenter.createMedicationDataListener()));

        //Emergency Contact Info
        listItems.add(new SectionDividerTitleRowBinder(presenter.getEmergencyContactSectionTitle()));
        int contactPos = 0;
        for (EmergencyContact contact : presenter.getEmergencyContacts()) {
            if (contact != null) {
                listItems.add(new EditPhoneNumberRowBinder(contact.clone(), contactPos,
                        presenter.getEmergencyContactListener()));
                contactPos++;
            }
        }
        if (contactPos < presenter.getMaxEmergencyContacts()) {
            listItems.add(new AddRowIconTitleCellDataBinder(presenter.getAddNumberButtonLabel(),
                    presenter.getAddEmergencyContactListener()));
        }

        //Insurance Info
        listItems.add(new SectionDividerTitleRowBinder(presenter.getInsuranceSectionTitle()));
        int insurancePos = 0;
        for (InsuranceCompany company: presenter.getInsuranceCompanies()) {
            if (company != null) {
                listItems.add(new EditInsuranceDataBinder(company.clone(), insurancePos,
                        presenter.getInsuranceCompanyListener()));
                insurancePos++;
            }
        }
        if (insurancePos < presenter.getMaxInsuranceCompanies()) {
            listItems.add(new AddRowIconTitleCellDataBinder(presenter.getAddInsuranceCompanyButtonLabel(),
                    presenter.getAddInsuranceCompanyListener()));
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
