package br.com.todeolho.todeolho;

import android.app.Dialog;
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

/**
 * Created by gustavomagalhaes on 4/13/16.
 */
public class MarkerFragmentDialog extends DialogFragment implements View.OnClickListener{


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.info_marker, null);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme);

        v.findViewById(R.id.btnAvalie).setOnClickListener(this);
        v.findViewById(R.id.btnFiscalize).setOnClickListener(this);
        v.findViewById(R.id.btnFechar).setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAvalie) {
            Toast.makeText(getActivity(), "Avaliar", Toast.LENGTH_LONG).show();
        } else if (v.getId() == R.id.btnFiscalize) {
            Toast.makeText(getActivity(), "Fiscalizar", Toast.LENGTH_LONG).show();

            FiscalizarFragment fiscalizar = new FiscalizarFragment();
            getFragmentManager().beginTransaction().add(fiscalizar, "teste").commit();

            dismiss();
        } else if (v.getId() == R.id.btnFechar){
            Toast.makeText(getActivity(), "Fechar", Toast.LENGTH_LONG).show();
        }
    }
}
