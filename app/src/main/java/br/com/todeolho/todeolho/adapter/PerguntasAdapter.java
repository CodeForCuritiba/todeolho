package br.com.todeolho.todeolho.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

import br.com.todeolho.todeolho.R;
import br.com.todeolho.todeolho.component.CustomTextView;
import br.com.todeolho.todeolho.model.Pergunta;
import br.com.todeolho.todeolho.model.Resposta;

/**
 * Created by gustavomagalhaes on 4/13/16.
 */
public class PerguntasAdapter extends RecyclerView.Adapter<PerguntasAdapter.ViewHolder> {

    private final Context context;
    private ArrayList<Pergunta> perguntas = new ArrayList<Pergunta>();

    private RadioButton selected = null;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout layoutPergunta;
        public ViewHolder(LinearLayout v) {
            super(v);
            layoutPergunta = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PerguntasAdapter(Pergunta pergunta, Context context) {
        this.context = context;
        perguntas.add(pergunta);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PerguntasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_pergunta, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder((LinearLayout) v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        holder.mTextView.setText(perguntas[position]);

        Pergunta pergunta = perguntas.get(position);

        CustomTextView customTextView = (CustomTextView) holder.layoutPergunta.findViewById(R.id.lblPergunta);
        customTextView.setText(pergunta.pergunta);

        RadioGroup group = (RadioGroup) holder.layoutPergunta.findViewById(R.id.rdogrpResp);

        if (group.getChildCount() > 0){
            return;
        }

        for (Resposta resposta:
             pergunta.respostas) {
            RadioButton rda = new RadioButton(context);
            rda.setTag(resposta);
            rda.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            rda.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            rda.setText(resposta.resposta);

            rda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selected = (RadioButton) v;
                }
            });

            group.addView(rda);
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return perguntas.size();
    }

    public Resposta getRespostaSelecionada(){
        return (Resposta) selected.getTag();
    }

    public void adicionarValor(Pergunta pergunta){
        perguntas.add(pergunta);
    }
}
