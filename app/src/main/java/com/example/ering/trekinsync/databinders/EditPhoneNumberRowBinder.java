package com.example.ering.trekinsync.databinders;

import android.support.annotation.NonNull;
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
        editPhoneNumberView.setPhoneNumber(contactModel.getPhoneNumber());
    }

    private AdapterView.OnItemSelectedListener relationListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            contactModel.setName(UserSingletonUtils.getInstance().getPhoneRelationCode(position));
            //notify listener of model change
            listener.onInputReceived(new EmergencyNumberDataListenerModel(contactModel, contactPosition));
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
            listener.onInputReceived(new EmergencyNumberDataListenerModel(contactModel, contactPosition));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            //spinner dismisses
        }
    };
}
