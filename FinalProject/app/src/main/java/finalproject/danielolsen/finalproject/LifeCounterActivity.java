package finalproject.danielolsen.finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class LifeCounterActivity extends AppCompatActivity {

    Button add1Btn;
    Button sub1Btn;
    Button reset;
    EditText lifeTotal;
    int counter = 40;
    Button add5Btn;
    Button sub5Btn;
    Button resetCommander;
    Button incCommander;
    int commanderCounter = 0;
    Button resetExp;
    Button incExp;
    int expCounter = 0;
    Button resetP;
    Button decP;
    Button incP;
    int pCounter = 0;
    EditText expCounters;
    EditText pCounters;
    EditText commanderCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.life_counter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pCounters = (EditText) findViewById(R.id.p_counters);
        pCounters.setText(Integer.toString(pCounter));
        resetP = (Button) findViewById(R.id.resetPCounters);
        decP = (Button) findViewById(R.id.decPCounters);
        incP = (Button) findViewById(R.id.incPCounters);
        add1Btn = (Button)findViewById(R.id.addButton);
        sub1Btn = (Button)findViewById(R.id.subtractButton);
        reset = (Button)findViewById(R.id.resetButton);
        add5Btn = (Button)findViewById(R.id.add5button);
        sub5Btn = (Button) findViewById(R.id.minus5button);
        resetCommander = (Button) findViewById(R.id.resetCommanderCost);
        incCommander = (Button) findViewById(R.id.incCommanderCost);
        lifeTotal = (EditText)findViewById(R.id.editText);
        commanderCost = (EditText) findViewById(R.id.commander_cost);
        lifeTotal.setText(Integer.toString(counter));
        commanderCost.setText(Integer.toString(commanderCounter));
        resetExp = (Button) findViewById(R.id.resetExpCounters);
        incExp = (Button) findViewById(R.id.incExpCounters);
        expCounters = (EditText) findViewById(R.id.e_counters);
        expCounters.setText(Integer.toString(expCounter));

        resetP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pCounter = 0;
                pCounters.setText(Integer.toString(pCounter));
            }
        });

        decP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pCounter == 0)
                {
                    return;
                }
                pCounter--;
                pCounters.setText(Integer.toString(pCounter));
            }
        });

        incP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pCounter++;
                pCounters.setText(Integer.toString(pCounter));
            }
        });

        resetExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expCounter = 0;
                expCounters.setText(Integer.toString(expCounter));
            }
        });

        incExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expCounter++;
                expCounters.setText(Integer.toString(expCounter));
            }
        });

        add1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                lifeTotal.setText(Integer.toString(counter));
            }
        });
        sub1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter--;
                lifeTotal.setText(Integer.toString(counter));
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 40;
                lifeTotal.setText(Integer.toString(counter));
            }
        });
        add5Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = counter+5;
                lifeTotal.setText(Integer.toString(counter));
            }
        });
        sub5Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = counter-5;
                lifeTotal.setText(Integer.toString(counter));
            }
        });
        incCommander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commanderCounter++;
                commanderCost.setText(Integer.toString(commanderCounter));
            }
        });
        resetCommander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commanderCounter = 0;
                commanderCost.setText(Integer.toString(commanderCounter));
            }
        });

    }

}
