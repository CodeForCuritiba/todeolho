package br.com.todeolho.todeolho;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by gustavomagalhaes on 4/11/16.
 */
public class AmigosFragment extends Fragment{


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_amigos, container, false);

//        Button btn = (Button) rootView.findViewById(R.id.btnTeste);
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MarkerFragmentDialog dialog = new MarkerFragmentDialog();
//                dialog.show(getActivity().getSupportFragmentManager(), "teste");
//            }
//        });

        return rootView;
    }

}
