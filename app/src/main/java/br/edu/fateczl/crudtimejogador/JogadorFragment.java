package br.edu.fateczl.crudtimejogador;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import br.edu.fateczl.crudtimejogador.controller.JogadorController;
import br.edu.fateczl.crudtimejogador.controller.TimeController;
import br.edu.fateczl.crudtimejogador.model.Jogador;
import br.edu.fateczl.crudtimejogador.model.Time;
import br.edu.fateczl.crudtimejogador.persistence.JogadorDao;
import br.edu.fateczl.crudtimejogador.persistence.TimeDao;

public class JogadorFragment extends Fragment {

    /*
     *@author: Kelvin Santos Guimarães
     */

    private View view;

    private EditText etIdJogador, etNomeJogador, etDataNascJogador, etAlturaJogador, etPesoJogador;
    private Spinner spTimeJogador;
    private Button btnBuscarJogador, btnListarJogador, btnInserirJogador, btnExcluirJogador, btnModificarJogador;
    private TextView tvListarJogador;

    private TimeController tCont;
    private JogadorController jCont;

    private List<Time> times;

    public JogadorFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_jogador, container, false);

        etIdJogador = view.findViewById(R.id.etIdJogador);
        etNomeJogador = view.findViewById(R.id.etNomeJogador);
        etDataNascJogador = view.findViewById(R.id.etDataNascJogador);
        etAlturaJogador = view.findViewById(R.id.etAlturaJogador);
        etPesoJogador = view.findViewById(R.id.etPesoJogador);
        spTimeJogador = view.findViewById(R.id.spTimeJogador);
        btnBuscarJogador = view.findViewById(R.id.btnBuscarJogador);
        btnListarJogador = view.findViewById(R.id.btnListarJogador);
        btnInserirJogador = view.findViewById(R.id.btnInserirJogador);
        btnExcluirJogador = view.findViewById(R.id.btnExcluirJogador);
        btnModificarJogador = view.findViewById(R.id.btnModificarJogador);
        tvListarJogador = view.findViewById(R.id.tvListarJogador);
        tvListarJogador.setMovementMethod(new ScrollingMovementMethod());

        tCont = new TimeController(new TimeDao(view.getContext()));
        jCont = new JogadorController(new JogadorDao(view.getContext()));
        preencheSpinner();


        btnInserirJogador.setOnClickListener(op -> acaoInserir());
        btnModificarJogador.setOnClickListener(op -> acaoModificar());
        btnExcluirJogador.setOnClickListener(op -> acaoExcluir());
        btnBuscarJogador.setOnClickListener(op -> acaoBuscar());
        btnListarJogador.setOnClickListener(op -> acaoListar());

        return view;
    }

    private void acaoInserir() {
        int spPos = spTimeJogador.getSelectedItemPosition();
        if (spPos > 0) {
            Jogador jogador = montaJogador();
            try {
                jCont.inserir(jogador);
                Toast.makeText(view.getContext(), "Jogador inserido com sucesso", Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
            limpaCampos();
        } else {
            Toast.makeText(view.getContext(), "Selecione um Time", Toast.LENGTH_LONG).show();
        }
    }
    private void acaoModificar() {
        int spPos = spTimeJogador.getSelectedItemPosition();
        if (spPos > 0) {
            Jogador jogador = montaJogador();
            try {
                jCont.modificar(jogador);
                Toast.makeText(view.getContext(), "Jogador atualizada com sucesso", Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
            limpaCampos();
        } else {
            Toast.makeText(view.getContext(), "Selecione um Time", Toast.LENGTH_LONG).show();
        }

    }

    private void acaoExcluir() {
        Jogador jogador = montaJogador();
        try {
            jCont.deletar(jogador);
            Toast.makeText(view.getContext(), "Jogador excluído com sucesso", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();
    }

    private void acaoBuscar() {
        Jogador jogador = montaJogador();
        try {
            times = tCont.listar();
            jogador = jCont.buscar(jogador);

            if (jogador.getNome() != null) {
                preencheCampos(jogador);
            } else {
                Toast.makeText(view.getContext(), "Jogador não encontrado", Toast.LENGTH_LONG).show();
                limpaCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoListar() {
        try {
            List<Jogador> jogadores = jCont.listar();
            StringBuffer buffer = new StringBuffer();
            for (Jogador j: jogadores) {
                buffer.append(j.toString() + "\n");
            }
            tvListarJogador.setText(buffer.toString());
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void preencheSpinner() {
        Time t0 = new Time();
        t0.setCodigo(0);
        t0.setNome("Selecione um time");
        t0.setCidade("");

        try {
            times = tCont.listar();
            times.add(0, t0);

            ArrayAdapter ad = new ArrayAdapter(view.getContext(),
                    android.R.layout.simple_spinner_item,
                    times);
            ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spTimeJogador.setAdapter(ad);

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    private Jogador montaJogador() {
        Jogador j = new Jogador();
        j.setId(Integer.parseInt(etIdJogador.getText().toString()));
        j.setNome(etNomeJogador.getText().toString());
        j.setDataNascimento(LocalDate.parse(etDataNascJogador.getText().toString()));
        j.setAltura(Float.parseFloat(etAlturaJogador.getText().toString()));
        j.setPeso(Float.parseFloat(etPesoJogador.getText().toString()));
        j.setTime((Time) spTimeJogador.getSelectedItem());

        return j;
    }

    private void limpaCampos() {
        etIdJogador.setText("");
        etNomeJogador.setText("");
        etDataNascJogador.setText("");
        etPesoJogador.setText("");
        etAlturaJogador.setText("");
        spTimeJogador.setSelection(0);
    }

    private void preencheCampos(Jogador j) {
        etIdJogador.setText(String.valueOf(j.getId()));
        etNomeJogador.setText(j.getNome());
        etDataNascJogador.setText(j.getDataNascimento().toString());
        etPesoJogador.setText(String.valueOf(j.getPeso()));
        etAlturaJogador.setText(String.valueOf(j.getAltura()));

        int cont = 1;
        for (Time t: times) {
            if (t.getCodigo() == j.getTime().getCodigo()) {
                spTimeJogador.setSelection(cont);
            } else {
                cont++;
            }
        }
        if (cont > times.size()) {
            spTimeJogador.setSelection(0);
        }
    }
}