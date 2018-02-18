package br.com.candalo.recipes.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.candalo.recipes.R;
import br.com.candalo.recipes.domain.RecipeStep;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecipeStepFragment extends Fragment {

    private Unbinder unbinder;
    private RecipeStep step;
    @BindView(R.id.tv_step_short_description)
    TextView stepShortDescriptionTextView;
    @BindView(R.id.tv_step_description)
    TextView stepDescriptionTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_step, container, false);
        injectDependencies(view);

        return view;
    }

    private void injectDependencies(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setStep(RecipeStep step) {
        this.step = step;

        stepShortDescriptionTextView.setText(step.getShortDescription());
        stepDescriptionTextView.setText(step.getDescription());
    }
}
