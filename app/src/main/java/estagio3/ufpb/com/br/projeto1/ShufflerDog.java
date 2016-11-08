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

public class ShufflerDog extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private ImageView imageQuestion;
    private LinearLayout drop;
    private ImageButton menubt;
    private String [ ] palavras;
    private int nivel;
    private MyOnDragListener myOnDragListener;
    private MyOnLongClickListener myOnLongClickListener;
    private ImageView checkButon;

    private TextView l1;
    private TextView l2;
    private TextView l3;
    private TextView l4;
    private TextView l5;
    private TextView l6;
    private TextView l7;
    private TextView l8;
    private TextView l9;
    private TextView l0;

    public ShufflerDog() {
        this.nivel = 0;
        this.palavras = new String[]{"cachorro","gato"};
        this.myOnDragListener = new MyOnDragListener();
        this.myOnLongClickListener = new MyOnLongClickListener();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuffler_game_mode);

        this.menubt = (ImageButton) findViewById(R.id.menuButton);

        this.imageQuestion = (ImageView) findViewById(R.id.imageQuestion);

        this.checkButon = (ImageView) findViewById(R.id.checkButton);

        this.l1 = (TextView) findViewById(R.id.letra1);
        this.l2 = (TextView) findViewById(R.id.letra2);
        this.l3 = (TextView) findViewById(R.id.letra3);
        this.l4 = (TextView) findViewById(R.id.letra4);
        this.l5 = (TextView) findViewById(R.id.letra5);
        this.l6 = (TextView) findViewById(R.id.letra6);
        this.l7 = (TextView) findViewById(R.id.letra7);
        this.l8 = (TextView) findViewById(R.id.letra8);
        this.l9 = (TextView) findViewById(R.id.letra9);
        this.l0 = (TextView) findViewById(R.id.letra0);

        this.drop = (LinearLayout) findViewById(R.id.drop);

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

        this.drop.setOnDragListener(this.myOnDragListener);



        loadNiveis(this.nivel);
        setImage(this.nivel);




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
        TextView [] letras = new TextView[]{l1,l2,l3,l4,l5,l6,l7,l8,l9,l0};
        String aux = shuffle(palavras[pos]);
        for (int i= 0; i < aux.length(); i++){
            letras[i].setText(String.valueOf(aux.toCharArray()[i]));
            letras[i].setVisibility(View.VISIBLE);
            letras[i].setOnLongClickListener(this.myOnLongClickListener);
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
}
