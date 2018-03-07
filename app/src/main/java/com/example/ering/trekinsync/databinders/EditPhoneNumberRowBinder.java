package com.example.ering.trekinsync.databinders;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.ering.trekinsync.customviews.EditPhoneNumberView;
import com.example.ering.trekinsync.interfaces.DataInputListener;
import com.example.ering.trekinsync.models.EmergencyNumberDataListenerModel;
import com.example.ering.trekinsync.models.EmergencyContact;
import com.example.ering.trekinsync.utils.UserSingletonUtils;
import com.example.ering.trekinsync.viewholders.EditPhoneNumberViewHolder;

public class EditPhoneNumberRowBinder extends BaseDataBinder<EditPhoneNumberViewHolder> {
    private EmergencyContact contactModel;
    private int contactPosition;
    private DataInputListener<EmergencyNumberDataListenerModel> listener;

    /**
     * creates a view holder for edit phone number cell with phone number type and relationship.
     */
    public EditPhoneNumberRowBinder(@NonNull EmergencyContact contactModel, int contactPosition, DataInputListener<EmergencyNumberDataListenerModel> listener) {
        this.contactModel = contactModel;
        this.contactPosition = contactPosition;
        this.listener = listener;
        //TODO: implement delete listener
    }

    @Override
    public EditPhoneNumberViewHolder createViewHolder(ViewGroup parent) {
        View view = getView(EditPhoneNumberViewHolder.getLayoutId(), parent);
        return new EditPhoneNumberViewHolder(view);
    }

    @Override
    public void bindViewHolder(EditPhoneNumberViewHolder holder) {
        final EditPhoneNumberView editPhoneNumberView = holder.editPhoneNumberView;
        editPhoneNumberView.setRelationshipAndListener(contactModel.getName(), relationListener);
        editPhoneNumberView.setPhoneTypeAndListener(contactModel.getPhoneNumberType(), phoneTypeListener);
        editPhoneNumberView.setPhoneNumberAndListener(contactModel.getPhoneNumber(), phoneNumberInputListener);
        editPhoneNumberView.setDeleteOnClickListener(deleteClickListener);
    }

    private AdapterView.OnItemSelectedListener relationListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            contactModel.setName(UserSingletonUtils.getInstance().getPhoneRelationCode(position));
            //notify listener of model change
            listener.onInputReceived(new EmergencyNumberDataListenerModel(contactModel, contactPosition, false));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            //spinner dismisses
        }
    };

    private AdapterView.OnItemSelectedListener phoneTypeListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            contactModel.setPhoneNumberType(UserSingletonUtils.getInstance().getPhoneTypeCode(position));
            //notify listener of model change
            listener.onInputReceived(new EmergencyNumberDataListenerModel(contactModel, contactPosition, false));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            //spinner dismisses
        }
    };

    private DataInputListener<String> phoneNumberInputListener = new DataInputListener<String>() {
        @Override
        public void onInputReceived(String value) {
            contactModel.setPhoneNumber(value);
            //notify listener of model change
            listener.onInputReceived(new EmergencyNumberDataListenerModel(contactModel, contactPosition, false));
        }
    };


    private View.OnClickListener deleteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            listener.onInputReceived(new EmergencyNumberDataListenerModel(contactModel, contactPosition, true));
        }
    };
}
