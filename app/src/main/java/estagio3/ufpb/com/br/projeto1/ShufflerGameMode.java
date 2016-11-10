package estagio3.ufpb.com.br.projeto1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ShufflerGameMode extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private ImageButton menubt;
    private ImageButton soundbt;

    private ImageView imageQuestion;


    private LinearLayout drop0;
    private LinearLayout drop1;
    private LinearLayout drop2;
    private LinearLayout drop3;
    private LinearLayout drop4;
    private LinearLayout drop5;
    private LinearLayout drop6;
    private LinearLayout drop7;
    private LinearLayout drop8;
    private LinearLayout drop9;

    private LinearLayout dragContainer;

    private ImageView letra0;
    private ImageView letra1;
    private ImageView letra2;
    private ImageView letra3;
    private ImageView letra4;
    private ImageView letra5;
    private ImageView letra6;
    private ImageView letra7;
    private ImageView letra8;
    private ImageView letra9;

    private MyOnDragListener myOnDragListener;;
    private MyOnLongClickListener myOnLongClickListener;

    private ImageButton checkButon;
    private ImageButton backbt;
    private ImageButton nextbt;

    private Bundle palavra;
    private int nivel;
    private int nivelAleatorio;

    private List<String> palavras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuffler_game_mode);

        myOnDragListener = new MyOnDragListener();
        myOnLongClickListener = new MyOnLongClickListener();
        palavras = new ArrayList<String>();
        palavra = new Bundle();
        nivel = 0;
        nivelAleatorio = (int) (Math.random() * palavras.size());


        addPalavra("CACHORRO",R.drawable.bulldog);
        addPalavra("GATO",R.drawable.cat);

        this.menubt = (ImageButton) findViewById(R.id.menuButton);

        this.imageQuestion = (ImageView) findViewById(R.id.imageQuestion);

        this.checkButon = (ImageButton) findViewById(R.id.checkButton);

        this.nextbt = (ImageButton) findViewById(R.id.nextButton);

        this.letra0 = (ImageView) findViewById(R.id.letra0);
        this.letra1 = (ImageView) findViewById(R.id.letra1);
        this.letra2 = (ImageView) findViewById(R.id.letra2);
        this.letra3 = (ImageView) findViewById(R.id.letra3);
        this.letra4 = (ImageView) findViewById(R.id.letra4);
        this.letra5 = (ImageView) findViewById(R.id.letra5);
        this.letra6 = (ImageView) findViewById(R.id.letra6);
        this.letra7 = (ImageView) findViewById(R.id.letra7);
        this.letra8 = (ImageView) findViewById(R.id.letra8);
        this.letra9 = (ImageView) findViewById(R.id.letra9);

        this.drop0 = (LinearLayout) findViewById(R.id.drop0);
        this.drop1 = (LinearLayout) findViewById(R.id.drop1);
        this.drop2 = (LinearLayout) findViewById(R.id.drop2);
        this.drop3 = (LinearLayout) findViewById(R.id.drop3);
        this.drop4 = (LinearLayout) findViewById(R.id.drop4);
        this.drop5 = (LinearLayout) findViewById(R.id.drop5);
        this.drop6 = (LinearLayout) findViewById(R.id.drop6);
        this.drop7 = (LinearLayout) findViewById(R.id.drop7);
        this.drop8 = (LinearLayout) findViewById(R.id.drop8);
        this.drop9 = (LinearLayout) findViewById(R.id.drop9);

        this.dragContainer = (LinearLayout)findViewById(R.id.drag);

        this.letra0.setOnLongClickListener(myOnLongClickListener);
        this.letra1.setOnLongClickListener(myOnLongClickListener);
        this.letra2.setOnLongClickListener(myOnLongClickListener);
        this.letra3.setOnLongClickListener(myOnLongClickListener);
        this.letra4.setOnLongClickListener(myOnLongClickListener);
        this.letra5.setOnLongClickListener(myOnLongClickListener);
        this.letra6.setOnLongClickListener(myOnLongClickListener);
        this.letra7.setOnLongClickListener(myOnLongClickListener);
        this.letra8.setOnLongClickListener(myOnLongClickListener);
        this.letra9.setOnLongClickListener(myOnLongClickListener);

        this.drop0.setOnDragListener(this.myOnDragListener);
        this.drop1.setOnDragListener(this.myOnDragListener);
        this.drop2.setOnDragListener(this.myOnDragListener);
        this.drop3.setOnDragListener(this.myOnDragListener);
        this.drop4.setOnDragListener(this.myOnDragListener);
        this.drop5.setOnDragListener(this.myOnDragListener);
        this.drop6.setOnDragListener(this.myOnDragListener);
        this.drop7.setOnDragListener(this.myOnDragListener);
        this.drop8.setOnDragListener(this.myOnDragListener);
        this.drop9.setOnDragListener(this.myOnDragListener);

        this.menubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);

            }
        });

        this.checkButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerifyWord(palavras.get(nivelAleatorio));
            }
        });

        this.nextbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                nivelAleatorio = (int) (Math.random() * palavras.size());
                loadNivel(nivelAleatorio);
                setImage(palavras.get(nivelAleatorio));
                setNivel();
            }
        });


        loadNivel(nivelAleatorio);
        setImage(palavras.get(nivelAleatorio));
    }

    public void addPalavra(String palavra, int imagem){
        this.palavra.putInt(palavra,imagem);
        palavras.add(palavra);
        setNivel();
    }

    private void setNivel() {
        if(nivel < 50) {
            this.nivel++;
        }else{
            Toast.makeText(this, "Memoria Cheia", Toast.LENGTH_LONG);
        }
    }

    private void reset(){
        ImageView [] letras = new ImageView[]{letra0, letra1, letra2, letra3, letra4, letra5, letra6, letra7, letra8, letra9};
        LinearLayout [] drops = new LinearLayout[]{drop0, drop1, drop2, drop3, drop4, drop5, drop6, drop7, drop8, drop9};
        for(int i = 0; i < letras.length;i++){
            ViewGroup dropLayout = (ViewGroup) letras[i].getParent();
            dropLayout.removeView(letras[i]);
            dragContainer.addView(letras[i]);
            letras[i].setVisibility(View.GONE);
            drops[i].setVisibility(View.GONE);

        }
    }

    private void setImage(String i) {
        this.imageQuestion.setImageResource(palavra.getInt(i));
    }

    public void loadNivel(int pos){
        ImageView [] letras = new ImageView[]{letra0, letra1, letra2, letra3, letra4, letra5, letra6, letra7, letra8, letra9};
        LinearLayout [] drops = new LinearLayout[]{drop0, drop1, drop2, drop3, drop4, drop5, drop6, drop7, drop8, drop9};
        char aux [] = shuffle(palavras.get(pos)).toCharArray();
        for (int i= 0; i < aux.length; i++){
            char letra = aux[i];
            switch (letra){
                case 'A':
                    letras[i].setImageResource(R.drawable.a);
                    letras[i].setTag("A");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case 'B':
                    letras[i].setImageResource(R.drawable.b);
                    letras[i].setTag("B");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case 'C':
                    letras[i].setImageResource(R.drawable.c);
                    letras[i].setTag("C");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case 'D':
                    letras[i].setImageResource(R.drawable.d);
                    letras[i].setTag("D");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case 'E':
                    letras[i].setImageResource(R.drawable.e);
                    letras[i].setTag("E");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[1].setVisibility(View.VISIBLE);
                    continue;

                case 'F':
                    letras[i].setImageResource(R.drawable.f);
                    letras[i].setTag("F");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case 'G':
                    letras[i].setImageResource(R.drawable.g);
                    letras[i].setTag("G");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case 'H':
                    letras[i].setImageResource(R.drawable.h);
                    letras[i].setTag("H");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case 'I':
                    letras[i].setImageResource(R.drawable.i);
                    letras[i].setTag("I");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case 'J':
                    letras[i].setImageResource(R.drawable.j);
                    letras[i].setTag("J");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case 'K':
                    letras[i].setImageResource(R.drawable.k);
                    letras[i].setTag("K");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case 'L':
                    letras[i].setImageResource(R.drawable.l);
                    letras[i].setTag("L");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case 'M':
                    letras[i].setImageResource(R.drawable.m);
                    letras[i].setTag("M");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case 'N':
                    letras[i].setImageResource(R.drawable.n);
                    letras[i].setTag("N");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case 'O':
                    letras[i].setImageResource(R.drawable.o);
                    letras[i].setTag("O");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case 'P':
                    letras[i].setImageResource(R.drawable.p);
                    letras[i].setTag("P");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case 'Q':
                    letras[i].setImageResource(R.drawable.q);
                    letras[i].setTag("Q");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case 'R':
                    letras[i].setImageResource(R.drawable.r);
                    letras[i].setTag("R");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case 'S':
                    letras[i].setImageResource(R.drawable.s);
                    letras[i].setTag("S");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case 'T':
                    letras[i].setImageResource(R.drawable.t);
                    letras[i].setTag("T");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case 'U':
                    letras[i].setImageResource(R.drawable.u);
                    letras[i].setTag("U");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case 'V':
                    letras[i].setImageResource(R.drawable.v);
                    letras[i].setTag("V");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case 'W':
                    letras[i].setImageResource(R.drawable.w);
                    letras[i].setTag("W");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case 'X':
                    letras[i].setImageResource(R.drawable.x);
                    letras[i].setTag("X");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case 'Y':
                    letras[i].setImageResource(R.drawable.y);
                    letras[i].setTag("Y");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case 'Z':
                    letras[i].setImageResource(R.drawable.z);
                    letras[i].setTag("Z");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case 'Ç':
                    letras[i].setImageResource(R.drawable.cc);
                    letras[i].setTag("Ç");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;
            }
        }

    }

    public String shuffle(String s) {
        List<String> letras = Arrays.asList(s.split(""));
        Collections.shuffle(letras);
        StringBuilder t = new StringBuilder(s.length());
        for (String k : letras) {
            t.append(k);
        }
        return t.toString();
    }

    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.main_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.op1:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }

    private void VerifyWord(String s) {
        if (s.equalsIgnoreCase(myOnDragListener.getPalavra())){
            Toast toast = Toast.makeText(this, "Acertou!", Toast.LENGTH_LONG);
            toast.show();
        }else {
            Toast toast = Toast.makeText(this, "Errou!", Toast.LENGTH_LONG);
            toast.show();
        }

    }
}
