package br.com.todeolho.todeolho;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gustavomagalhaes on 4/15/16.
 */
public class RootPesquisarFragment extends Fragment{


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_root_pesquisar, container, false);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.lytRootPesquisar, new PesquisarFragment()).commit();

        return rootView;
    }
}
