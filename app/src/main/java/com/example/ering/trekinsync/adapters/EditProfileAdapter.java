package com.example.ering.trekinsync.adapters;

import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.databinders.AddRowIconTitleCellDataBinder;
import com.example.ering.trekinsync.databinders.BaseDataBinder;
import com.example.ering.trekinsync.databinders.EditPhoneNumberRowBinder;
import com.example.ering.trekinsync.databinders.LabelDescriptionRowBinder;
import com.example.ering.trekinsync.databinders.PhoneNumberRowBinder;
import com.example.ering.trekinsync.databinders.ProfileHeaderRowBinder;
import com.example.ering.trekinsync.databinders.SectionDividerTitleRowBinder;
import com.example.ering.trekinsync.models.EmergencyContact;
import com.example.ering.trekinsync.presenters.EditProfilePresenter;

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
        listItems.add(new LabelDescriptionRowBinder("Birthday",
                presenter.getFormattedUserBirthday(),
                R.drawable.ic_date,
                presenter.createBirthdayDropDownListener()));
        listItems.add(new LabelDescriptionRowBinder("Citizenship",
                presenter.getUserCitizenship(),
                R.drawable.ic_keyboard_arrow_down,
                presenter.createCitizenshipDropDownRowListener()));

        //Health info
        listItems.add(new SectionDividerTitleRowBinder(presenter.getHealthSectionTitle()));
        listItems.add(new LabelDescriptionRowBinder("Blood Type",
                presenter.getUserBloodType(),
                R.drawable.ic_keyboard_arrow_down,
                presenter.createBloodTypeDropDownListener()));

        //Emergency Contact Info
        listItems.add(new SectionDividerTitleRowBinder(presenter.getEmergencyContactSectionTitle()));
        int pos = 0;
        for (EmergencyContact contact : presenter.getEmergencyContacts()) {
            if (contact != null) {
                listItems.add(new EditPhoneNumberRowBinder(contact.clone(), pos, presenter.getEmergencyContactListener()));
                pos++;
            }
        }
        if (pos < presenter.getMaxEmergencyContacts()) {
            listItems.add(new AddRowIconTitleCellDataBinder("Add Emergency Contact", presenter.getAddEmergencyContactListener()));
        }

        //Insurance Info
        listItems.add(new SectionDividerTitleRowBinder(presenter.getInsuranceSectionTitle()));
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
