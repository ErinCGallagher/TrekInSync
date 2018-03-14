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
    private ArrayList<PolicyInfo> policies;

    /**
     * creates a view holder for an Edit Insurance cell view
     */
    public EditInsuranceDataBinder(InsuranceCompany companyModel, String checkBoxLabel, int companyPosition,
                                   DataInputListener<InsuranceListenerModel> listener ) {
        this.companyModel = companyModel;
        this.checkBoxLabel = checkBoxLabel;
        this.companyPosition = companyPosition;
        this.listener = listener;
        this.policies = companyModel.getPolicyInfo();
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
        editInsuranceView.setPhoneNumber(companyModel.getPhoneNumber(), phoneNumberListener);
        editInsuranceView.setCheckboxLabel(checkBoxLabel);
        editInsuranceView.setCheckboxState(companyModel.isCallFirst());
        editInsuranceView.setDeleteOnClickListener(deleteClickListener);

        PolicyInfo firstPolicy = policies.get(0); //Always 2 policy fields
        editInsuranceView.setDetailsLabel1(firstPolicy != null ? firstPolicy.getName() : "", policyName1Listener);
        editInsuranceView.setDetail1Listener(firstPolicy != null ? firstPolicy.getNumber() : "", policyNumber1Listener);

        PolicyInfo secondPolicy = policies.get(1); //Always 2 policy fields
        editInsuranceView.setDetailsLabel2(secondPolicy != null ? secondPolicy.getName() : "", policyName2Listener);
        editInsuranceView.setDetail2Listener(secondPolicy != null ? secondPolicy.getNumber() : "", policyNumber2Listener);

    }

    private DataInputListener<String> phoneNumberListener = new DataInputListener<String>() {
        @Override
        public void onInputReceived(String value) {
            companyModel.setPhoneNumber(value);
            //notify listener of model change
            listener.onInputReceived(new InsuranceListenerModel(companyModel, companyPosition, false));
        }
    };

    private AdapterView.OnItemSelectedListener policyName1Listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            policies.get(0).setName(UserSingletonUtils.getInstance().getInsurancePolicyCode(position));
            //notify listener of model change
            listener.onInputReceived(new InsuranceListenerModel(companyModel, companyPosition, false));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            //spinner dismisses
        }
    };

    private AdapterView.OnItemSelectedListener policyName2Listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            policies.get(1).setName(UserSingletonUtils.getInstance().getInsurancePolicyCode(position));
            //notify listener of model change
            listener.onInputReceived(new InsuranceListenerModel(companyModel, companyPosition, false));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            //spinner dismisses
        }
    };

    private DataInputListener<String> policyNumber1Listener = new DataInputListener<String>() {
        @Override
        public void onInputReceived(String value) {
            policies.get(0).setNumber(value);
            //notify listener of model change
            listener.onInputReceived(new InsuranceListenerModel(companyModel, companyPosition, false));
        }
    };

    private DataInputListener<String> policyNumber2Listener = new DataInputListener<String>() {
        @Override
        public void onInputReceived(String value) {
            policies.get(1).setNumber(value);
            //notify listener of model change
            listener.onInputReceived(new InsuranceListenerModel(companyModel, companyPosition, false));
        }
    };


    private View.OnClickListener deleteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            listener.onInputReceived(new InsuranceListenerModel(companyModel, companyPosition, true));
        }
    };
}
