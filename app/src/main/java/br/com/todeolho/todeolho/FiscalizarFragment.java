package br.com.todeolho.todeolho;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import br.com.todeolho.todeolho.adapter.PerguntasAdapter;
import br.com.todeolho.todeolho.component.CustomTextView;
import br.com.todeolho.todeolho.model.Questionario;
import br.com.todeolho.todeolho.model.Resposta;

/**
 * Created by gustavomagalhaes on 4/13/16.
 */
public class FiscalizarFragment extends Fragment {

    public static final String ANSWER_INTENT_RECEIVER = "br.com.todeolho.ANSWER";
    private RecyclerView mRecyclerView;
    private PerguntasAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Questionario questionario = null;

    private boolean isConfirmar = false;

    private View mainView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_fiscalizar, container, false);

        CustomTextView lblConstrucao = (CustomTextView) mainView.findViewById(R.id.lblNomeConstrucao);
        lblConstrucao.setText(getArguments().getString("lblTituloConstrucao"));

        mRecyclerView = (RecyclerView) mainView.findViewById(R.id.lstPergunta);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


        try {
            Gson gson = new Gson();
            InputStream inputStream = getActivity().getAssets().open("questionario.json");
            Reader reader = new InputStreamReader(inputStream);
            questionario = gson.fromJson(reader, Questionario.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

//        FloatingActionButton actionButton = (FloatingActionButton)v.findViewById(R.id.btnResp);

//        actionButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Resposta resposta = mAdapter.getRespostaSelecionada();
//
//                InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
//
//                if (resposta.idProxPergunta >= 0){
//                    mAdapter.adicionarValor(questionario.perguntas.get(resposta.idProxPergunta));
//                }else{
//
//                    if (isConfirmar){
//                        AdicionaFotoComentarioFragment comentarioFragment = new AdicionaFotoComentarioFragment();
//                        getFragmentManager().beginTransaction().add(R.id.lytRootPesquisar, comentarioFragment)
//                                .addToBackStack(null)
//                                .commit();
//                    }else {
//                        ((FloatingActionButton) v).setImageResource(R.drawable.ic_menu_camera);
//                        mAdapter.finalizarPerguntas();
//                        isConfirmar = true;
//                    }
//                }
//            }
//
//        });

        // specify an adapter (see also next example)
        mAdapter = new PerguntasAdapter(questionario.perguntas.get(0), getContext());
        mRecyclerView.setAdapter(mAdapter);

        this.getContext().registerReceiver(answerReceiver, new IntentFilter(ANSWER_INTENT_RECEIVER));

        return mainView;
    }

    BroadcastReceiver answerReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Resposta resposta = mAdapter.getRespostaSelecionada();

            InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

            if (resposta.idProxPergunta >= 0){
                mAdapter.adicionarValor(questionario.perguntas.get(resposta.idProxPergunta));
            }else{

                if (isConfirmar){
                    AdicionaFotoComentarioFragment comentarioFragment = new AdicionaFotoComentarioFragment();
                    getFragmentManager().beginTransaction().add(R.id.lytRootPesquisar, comentarioFragment)
                            .addToBackStack(null)
                            .commit();
                }else {
//x                    ((FloatingActionButton) mainView).setImageResource(R.drawable.ic_menu_camera);
                    mAdapter.finalizarPerguntas();
                    isConfirmar = true;
                }
            }
        }
    };

}
