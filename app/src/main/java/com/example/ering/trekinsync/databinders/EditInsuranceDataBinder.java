package com.example.ering.trekinsync.databinders;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.ering.trekinsync.customviews.EditInsuranceView;
import com.example.ering.trekinsync.interfaces.DataInputListener;
import com.example.ering.trekinsync.models.InsuranceCompany;
import com.example.ering.trekinsync.models.InsuranceListenerModel;
import com.example.ering.trekinsync.models.PolicyInfo;
import com.example.ering.trekinsync.utils.UserSingletonUtils;
import com.example.ering.trekinsync.viewholders.EditInsuranceViewHolder;

import java.util.ArrayList;

public class EditInsuranceDataBinder extends BaseDataBinder<EditInsuranceViewHolder> {
    private InsuranceCompany companyModel;
    private String checkBoxLabel;
    private int companyPosition;
    private DataInputListener<InsuranceListenerModel> listener;

    /**
     * creates a view holder for an Edit Insurance cell view
     */
    public EditInsuranceDataBinder(InsuranceCompany companyModel, String checkBoxLabel, int companyPosition,
                                   DataInputListener<InsuranceListenerModel> listener ) {
        this.companyModel = companyModel;
        this.checkBoxLabel = checkBoxLabel;
        this.companyPosition = companyPosition;
        this.listener = listener;
    }

    @Override
    public EditInsuranceViewHolder createViewHolder(ViewGroup parent) {
        View view = getView(EditInsuranceViewHolder.getLayoutId(), parent);
        return new EditInsuranceViewHolder(view);
    }

    @Override
    public void bindViewHolder(EditInsuranceViewHolder holder) {
        final EditInsuranceView editInsuranceView = holder.editInsuranceView;
        editInsuranceView.setLabel(companyModel.getName());
        editInsuranceView.setPhoneNumber(companyModel.getPhoneNumber(), null);
        editInsuranceView.setCheckboxLabel(checkBoxLabel);
        editInsuranceView.setCheckboxState(companyModel.isCallFirst());

//        PolicyInfo firstPolicy = policyInfo.get(0);
//        editInsuranceView.setDetailsLabel1(firstPolicy != null ? firstPolicy.getName() : "");
//        editInsuranceView.setDetail1Listener(firstPolicy != null ? firstPolicy.getNumber() : "", null);
//
//        PolicyInfo secondPolicy = policyInfo.get(1);
//        editInsuranceView.setDetailsLabel1(secondPolicy != null ? secondPolicy.getName() : "");
//        editInsuranceView.setDetail1Listener(secondPolicy != null ? secondPolicy.getNumber() : "", null);

    }

//    private AdapterView.OnItemSelectedListener policyNameListener = new AdapterView.OnItemSelectedListener() {
//        @Override
//        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//            contactModel.setName(UserSingletonUtils.getInstance().getPhoneRelationCode(position));
//            //notify listener of model change
//            listener.onInputReceived(new InsuranceListenerModel(contactModel, contactPosition, false));
//        }
//
//        @Override
//        public void onNothingSelected(AdapterView<?> parent) {
//            //spinner dismisses
//        }
//    };
}
