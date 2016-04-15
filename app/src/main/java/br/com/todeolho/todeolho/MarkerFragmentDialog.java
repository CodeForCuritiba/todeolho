package br.com.todeolho.todeolho;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import br.com.todeolho.todeolho.databinding.InfoMarkerBinding;
import br.com.todeolho.todeolho.model.MapMarker;

/**
 * Created by gustavomagalhaes on 4/13/16.
 */
public class MarkerFragmentDialog extends DialogFragment implements View.OnClickListener{


    private MapMarker marker;

    public MarkerFragmentDialog() {
        super();
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.marker = (MapMarker) args.getSerializable("marker");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        InfoMarkerBinding binding = DataBindingUtil.inflate(inflater, R.layout.info_marker, container, false);
        binding.setMarker(marker);

        View v = binding.getRoot();
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme);

        v.findViewById(R.id.btnAvalie).setOnClickListener(this);
        v.findViewById(R.id.btnFiscalize).setOnClickListener(this);
        v.findViewById(R.id.btnFechar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MarkerFragmentDialog.this.dismiss();
            }
        });
        return v;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAvalie) {
            Toast.makeText(getActivity(), "Avaliar", Toast.LENGTH_LONG).show();
        } else if (v.getId() == R.id.btnFiscalize) {

            FiscalizarFragment fiscalizar = new FiscalizarFragment();
            getFragmentManager().beginTransaction().add(R.id.lytRootPesquisar, fiscalizar)
                    .addToBackStack(null)
                    .commit();

            dismiss();
        } else if (v.getId() == R.id.btnFechar){
            Toast.makeText(getActivity(), "Fechar", Toast.LENGTH_LONG).show();
        }
    }
}
