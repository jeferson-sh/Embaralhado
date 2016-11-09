package estagio3.ufpb.com.br.projeto1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ShufflerGameMode extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private ImageButton menubt;
    private ImageButton soundbt;

    private ImageView imageQuestion;


    private LinearLayout drop1;
    private LinearLayout drop2;
    private LinearLayout drop3;
    private LinearLayout drop4;
    private LinearLayout drop5;
    private LinearLayout drop6;
    private LinearLayout drop7;
    private LinearLayout drop8;
    private LinearLayout drop9;
    private LinearLayout drop0;

    private ImageView drag1;
    private ImageView drag2;
    private ImageView drag3;
    private ImageView drag4;
    private ImageView drag5;
    private ImageView drag6;
    private ImageView drag7;
    private ImageView drag8;
    private ImageView drag9;
    private ImageView drag0;

    private MyOnDragListener myOnDragListener = new MyOnDragListener();;
    private MyOnLongClickListener myOnLongClickListener = new MyOnLongClickListener();;

    private ImageButton checkButon;
    private ImageButton backbt;
    private ImageButton nextbt;

    private String [ ] palavras = new String[]{"CACHORRO","GATO"};;
    private int nivel = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuffler_game_mode);

        this.menubt = (ImageButton) findViewById(R.id.menuButton);

        this.imageQuestion = (ImageView) findViewById(R.id.imageQuestion);

        this.checkButon = (ImageButton) findViewById(R.id.checkButton);

        this.nextbt = (ImageButton) findViewById(R.id.nextButton);

        this.drag1 = (ImageView) findViewById(R.id.drag1);
        this.drag2 = (ImageView) findViewById(R.id.drag2);
        this.drag3 = (ImageView) findViewById(R.id.drag3);
        this.drag4 = (ImageView) findViewById(R.id.drag4);
        this.drag5 = (ImageView) findViewById(R.id.drag5);
        this.drag6 = (ImageView) findViewById(R.id.drag6);
        this.drag7 = (ImageView) findViewById(R.id.drag7);
        this.drag8 = (ImageView) findViewById(R.id.drag8);
        this.drag9 = (ImageView) findViewById(R.id.drag9);
        this.drag0 = (ImageView) findViewById(R.id.drag0);

        this.drop1 = (LinearLayout) findViewById(R.id.drop1);
        this.drop2 = (LinearLayout) findViewById(R.id.drop2);
        this.drop3 = (LinearLayout) findViewById(R.id.drop3);
        this.drop4 = (LinearLayout) findViewById(R.id.drop4);
        this.drop5 = (LinearLayout) findViewById(R.id.drop5);
        this.drop6 = (LinearLayout) findViewById(R.id.drop6);
        this.drop7 = (LinearLayout) findViewById(R.id.drop7);
        this.drop8 = (LinearLayout) findViewById(R.id.drop8);
        this.drop9 = (LinearLayout) findViewById(R.id.drop9);
        this.drop0 = (LinearLayout) findViewById(R.id.drop0);


        this.drag1.setOnLongClickListener(new MyOnLongClickListener());
        this.drag2.setOnLongClickListener(new MyOnLongClickListener());
        this.drag3.setOnLongClickListener(new MyOnLongClickListener());
        this.drag4.setOnLongClickListener(new MyOnLongClickListener());
        this.drag5.setOnLongClickListener(new MyOnLongClickListener());
        this.drag6.setOnLongClickListener(new MyOnLongClickListener());
        this.drag7.setOnLongClickListener(new MyOnLongClickListener());
        this.drag8.setOnLongClickListener(new MyOnLongClickListener());
        this.drag9.setOnLongClickListener(new MyOnLongClickListener());
        this.drag0.setOnLongClickListener(new MyOnLongClickListener());

        this.menubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);

            }
        });

        this.checkButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerifyWord(palavras[nivel]);
            }
        });

        this.drop1.setOnDragListener(this.myOnDragListener);
        this.drop2.setOnDragListener(this.myOnDragListener);
        this.drop3.setOnDragListener(this.myOnDragListener);
        this.drop4.setOnDragListener(this.myOnDragListener);
        this.drop5.setOnDragListener(this.myOnDragListener);
        this.drop6.setOnDragListener(this.myOnDragListener);
        this.drop7.setOnDragListener(this.myOnDragListener);
        this.drop8.setOnDragListener(this.myOnDragListener);
        this.drop9.setOnDragListener(this.myOnDragListener);
        this.drop0.setOnDragListener(this.myOnDragListener);



        loadNiveis(this.nivel);
        setImage(this.nivel);




    }



    private void setImage(int i) {
        switch (i){
            case 0:
                this.imageQuestion.setImageResource(R.drawable.bulldog);
                break;
            case 1:
                this.imageQuestion.setImageResource(R.drawable.cat);

        }
    }

    public void loadNiveis(int pos){
        ImageView [] letras = new ImageView[]{drag1,drag2,drag3,drag4,drag5,drag6,drag7,drag8,drag9,drag0};
        LinearLayout [] drops = new LinearLayout[]{drop1,drop2,drop3,drop4,drop5,drop6,drop7,drop8,drop9,drop0};
        char aux [] = shuffle(palavras[pos]).toCharArray();
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

    public static String shuffle(String s) {
        List<String> letters = Arrays.asList(s.split(""));
        Collections.shuffle(letters);
        StringBuilder t = new StringBuilder(s.length());
        for (String k : letters) {
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
