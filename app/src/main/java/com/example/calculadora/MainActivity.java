package com.example.calculadora;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fathzer.soft.javaluator.DoubleEvaluator;



public class MainActivity extends AppCompatActivity {
    private TextView tvRes; // mostrar el resultat
    private StringBuilder expressio = new StringBuilder(); // ex: "33+5+15")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvRes = findViewById(R.id.tvRes);
        Button btn0 = findViewById(R.id.btn0);
        Button btn1 = findViewById(R.id.btn1);
        Button btn2 = findViewById(R.id.btn2);
        Button btn3 = findViewById(R.id.btn3);
        Button btn4 = findViewById(R.id.btn4);
        Button btn5 = findViewById(R.id.btn5);
        Button btn6 = findViewById(R.id.btn6);
        Button btn7 = findViewById(R.id.btn7);
        Button btn8 = findViewById(R.id.btn8);
        Button btn9 = findViewById(R.id.btn9);

        Button btnSum = findViewById(R.id.btnSum);
        Button btnRes = findViewById(R.id.btnRestar);
        Button btnMult = findViewById(R.id.btnMultiplicar);
        Button btnDiv = findViewById(R.id.btnDiv);
        Button btnPerc = findViewById(R.id.btnPorcentaje);

        Button btnClear = findViewById(R.id.btnClear);
        Button btnEquals = findViewById(R.id.buttonEqual);
        Button btnParIzq = findViewById(R.id.btnParentesiIzq);
        Button btnParDer = findViewById(R.id.btnParentesiDer);
        Button btnComa = findViewById(R.id.btnComa);

        // Listeners
        btn0.setOnClickListener(v -> afegirNum("0"));
        btn1.setOnClickListener(v -> afegirNum("1"));
        btn2.setOnClickListener(v -> afegirNum("2"));
        btn3.setOnClickListener(v -> afegirNum("3"));
        btn4.setOnClickListener(v -> afegirNum("4"));
        btn5.setOnClickListener(v -> afegirNum("5"));
        btn6.setOnClickListener(v -> afegirNum("6"));
        btn7.setOnClickListener(v -> afegirNum("7"));
        btn8.setOnClickListener(v -> afegirNum("8"));
        btn9.setOnClickListener(v -> afegirNum("9"));

        btnSum.setOnClickListener(v -> operacio("+"));
        btnRes.setOnClickListener(v -> operacio("-"));
        btnMult.setOnClickListener(v -> operacio("*"));
        btnDiv.setOnClickListener(v -> operacio("/"));
        btnPerc.setOnClickListener(v -> operacio("%"));
        btnParIzq.setOnClickListener(v -> afegirNum("("));
        btnParDer.setOnClickListener(v -> afegirNum(")"));
        btnComa.setOnClickListener(v -> afegirNum("."));

        btnEquals.setOnClickListener(v -> evaluar());

        btnClear.setOnClickListener(v -> clear());

        actualitzar();
    }

    private void afegirNum(String c) {
        expressio.append(c);
        tvRes.setText(expressio.toString());
        actualitzar();
    }

    private void operacio(String operacio) {
        // Permetre '+' només si encara no n'hi ha (evita "33++-5")
        // codi ...

        if (expressio.length() > 0){
            char ultim = expressio.charAt(expressio.length() -1);

            if ("+-*/.%".indexOf(ultim) != -1) {
                //substituir pel nou simbol
                expressio.setCharAt(expressio.length() -1, operacio.charAt(0));
            }else {expressio.append((operacio));}
        }else {
            // Si està buida, només permet nombres negatius
            if (operacio.equals("-")) {
                expressio.append(operacio);
            }
        }

        tvRes.setText(expressio.toString());
        actualitzar();
    }

    private void evaluar() {
        // https://mvnrepository.com/artifact/com.fathzer/javaluator
        // https://github.com/fathzer/javaluator  3.0.6
        // "(2^3-1)*sin(pi/4)/ln(pi^2)" = 2.1619718020347976

        // evaluem
        DoubleEvaluator evaluator = new DoubleEvaluator();

        try {
            Double result = evaluator.evaluate(expressio.toString());

            // Mostrem resultat
            tvRes.setText(result.toString());

            expressio.setLength(0);
            expressio.append(result);
        } catch (IllegalArgumentException e) {
            // Error argument no valid
            tvRes.setText("Error: expressió invàlida");
            expressio.setLength(0);
        } catch (ArithmeticException e) {
            // Error al dividir entre 0
            tvRes.setText("Error: operació no vàlida");
            expressio.setLength(0);
        } catch (Exception e) {
            // Altres errors
            tvRes.setText("");
            expressio.setLength(0);
        }
        // mostrem resultat
        // try/catch


    }

    private void actualitzar() {
        tvRes.setText(expressio.toString());
    }

    private void clear(){
        expressio.setLength(0);
        tvRes.setText("");

    }
}