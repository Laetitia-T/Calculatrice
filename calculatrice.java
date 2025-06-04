package calculatrice;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;





public class Calculatrice extends JFrame implements ActionListener {

    private JTextField txtEquation = new JTextField(); // Champ pour l'Ã©quation
    private JTextField txtInput = new JTextField();    // Champ pour le rÃ©sultat
    private JButton[] numberButtons = new JButton[10];
    private JButton btAdd = new JButton("+");
    private JButton btSub = new JButton("-");
    private JButton btMul = new JButton("x");
    private JButton btDiv = new JButton("/");
    private JButton btEqual = new JButton("=");
    private JButton btClear = new JButton("C");
    private JButton btComma = new JButton(",");
    private JButton btCE = new JButton("CE");
    private JButton btDel = new JButton("Supprimer");
    private JButton btPercent = new JButton("%");
    private JButton btSqrt = new JButton("√");

    private String currentOperation = "";
    private double result = 0;
    private boolean isNewInput = true;
    private boolean allowNegative = false;
    private boolean errorState = false;

    public Calculatrice() {
        this.setTitle("Calculatrice");
        this.setBounds(100, 100, 400, 600);
this.getContentPane().setBackground(Color.darkGray);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);

      
        this.txtEquation.setBounds(20, 20, 340, 30);
        this.txtEquation.setEditable(false);
        this.txtEquation.setBackground(Color.LIGHT_GRAY);
        this.txtEquation.setHorizontalAlignment(JTextField.RIGHT);
        this.add(this.txtEquation);

        this.txtInput.setBounds(20, 60, 340, 50);
        this.txtInput.setEditable(false);
        this.txtInput.setBackground(Color.WHITE);
        this.txtInput.setHorizontalAlignment(JTextField.RIGHT);
        this.add(this.txtInput);


        this.btPercent.setBounds(20, 130, 60, 50);
        this.btCE.setBounds(90, 130, 60, 50);
        this.btClear.setBounds(160, 130, 60, 50);
        this.btDel.setBounds(230, 130, 130, 50);
        this.add(this.btPercent);
        this.add(this.btCE);
        this.add(this.btClear);
        this.add(this.btDel);

       
        int x = 20, y = 190;
        for (int i = 1; i <= 9; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setBounds(x, y, 60, 50);
            numberButtons[i].addActionListener(this);
            this.add(numberButtons[i]);
            x += 70;
            if (i % 3 == 0) {
                x = 20;
                y += 60;
            }
        }

     
        numberButtons[0] = new JButton(String.valueOf("0"));
        numberButtons[0].addActionListener(this);
        
        
        this.btSqrt.setBounds(20, 370, 60, 50);
        numberButtons[0].setBounds(90, 370, 60, 50);
        this.btComma.setBounds(160, 370, 60, 50);
        this.btAdd.setBounds(230, 370, 130, 50);
        this.btDiv.setBounds(230, 250, 130, 50);
        this.btMul.setBounds(230, 310, 130, 50);
        this.btSub.setBounds(230, 190, 130, 50);
        this.btEqual.setBounds(20, 430, 340, 50);

    
        this.add(numberButtons[0]);
        this.add(this.btSqrt);
        this.add(this.btComma);
        this.add(this.btAdd);
        this.add(this.btSub);
        this.add(this.btMul);
        this.add(this.btDiv);
        this.add(this.btEqual);


        this.btAdd.addActionListener(this);
        this.btSub.addActionListener(this);
        this.btMul.addActionListener(this);
        this.btDiv.addActionListener(this);
        this.btEqual.addActionListener(this);
        this.btClear.addActionListener(this);
        this.btComma.addActionListener(this);
        this.btCE.addActionListener(this);
        this.btDel.addActionListener(this);
        this.btPercent.addActionListener(this);
        this.btSqrt.addActionListener(this);

        this.setVisible(true);
    }
    
    
    
    
    
    
    ///////////////////////////////////////////////////////
    
    
    
    

    public static void main(String[] args) {
        new Calculatrice();
    }
    
    

    
    
///////////////////////////////////////////////////////
    
    
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (errorState) {
            clearAll();
        }

        for (int i = 0; i < 10; i++) {
            if (e.getSource() == numberButtons[i]) {
                if (isNewInput) {
                    txtInput.setText(allowNegative ? "-" : "");
                    isNewInput = false;
                }
                txtInput.setText(txtInput.getText() + i);
            }
        }

        if (e.getSource() == btComma) {
            if (isNewInput) {
                txtInput.setText("0,");
                isNewInput = false;
            } else if (!txtInput.getText().contains(",")) {
                txtInput.setText(txtInput.getText() + ",");
            }
        } else if (e.getSource() == btAdd) {
            performOperation("+");
        } else if (e.getSource() == btSub) {
            if (isNewInput) {
                allowNegative = true;
                txtInput.setText("-");
                isNewInput = false;
            } else {
                performOperation("-");
            }
        } else if (e.getSource() == btMul) {
            performOperation("x");
        } else if (e.getSource() == btDiv) {
            performOperation("/");
        } else if (e.getSource() == btEqual) {
            calculateResult();
        } else if (e.getSource() == btClear) {
            clearAll();
        } else if (e.getSource() == btCE) {
    txtInput.setText("");
        } else if (e.getSource() == btDel) {
            if (!txtInput.getText().isEmpty()) {
                txtInput.setText(txtInput.getText().substring(0, txtInput.getText().length() - 1));
            }
    } else if (e.getSource() == btPercent) {
            try {
                if (!txtInput.getText().isEmpty()) {
                    double value = Double.parseDouble(txtInput.getText().replace(",", "."));
                    txtInput.setText(String.valueOf(value / 100).replace(".", ","));
                }
            } catch (NumberFormatException ex) {
                setErrorState();
            }
        } else if (e.getSource() == btSqrt) {
            try {
                if (!txtInput.getText().isEmpty()) {
                    double value = Double.parseDouble(txtInput.getText().replace(",", "."));
                    if (value >= 0) {
                        txtInput.setText(String.valueOf(Math.sqrt(value)).replace(".", ","));
                    } else {
                        setErrorState();
                    }
                }
            } catch (NumberFormatException ex) {
                setErrorState();
            }
        }
    }
    
    
    
///////////////////////////////////////////////////////
    
    
    
    private void performOperation(String operation) {
        try {
            if (!txtInput.getText().isEmpty()) {
                double input = Double.parseDouble(txtInput.getText().replace(",", "."));
                if (currentOperation.isEmpty()) {
                    result = input;
                } else {
                    calculateResult();
                    result = Double.parseDouble(txtInput.getText().replace(",", "."));
                }
                currentOperation = operation;
                txtEquation.setText(result + " " + currentOperation);
                isNewInput = true;
                allowNegative = false;
            }
        } catch (NumberFormatException ex) {
            setErrorState();
        }
    }
    
    
    
    
///////////////////////////////////////////////////////
    
    
    
    

    private void calculateResult() {
        try {
            if (!txtInput.getText().isEmpty() && !currentOperation.isEmpty()) {
                double input = Double.parseDouble(txtInput.getText().replace(",", "."));
                switch (currentOperation) {
                    case "+":
                        result += input;
                        break;
                    case "-":
                        result -= input;
                        break;
                    case "x":
                        result *= input;
                        break;
                    case "/":
                        if (input != 0) {
                            result /= input;
                        } else {
                            txtInput.setText("Erreur : Division par 0");
                            currentOperation = "";
                            errorState = true;
                            return;
                        }
                        break;
                }
                txtInput.setText(String.valueOf(result).replace(".", ","));
                txtEquation.setText("");
                currentOperation = "";
                isNewInput = true;
            }
        } catch (NumberFormatException ex) {
            setErrorState();
        }
    }
    
    
///////////////////////////////////////////////////////
    
    

    private void clearAll() {
        txtInput.setText("");
        txtEquation.setText("");
        result = 0;
        currentOperation = "";
        isNewInput = true;
        allowNegative = false;
        errorState = false;
    }
    
    
    
    
///////////////////////////////////////////////////////

    private void setErrorState() {
        txtInput.setText("Erreur");
        errorState = true;
    }
}