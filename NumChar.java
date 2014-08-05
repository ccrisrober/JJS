package others;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Cristian
 */
public class NumChar {

    @Override
    public String toString() {
        return "NumChar{" + "pos=" + pos + ", asociatedChar=" + asociatedChar + '}';
    }

    protected int pos;
    protected char asociatedChar;

    public NumChar(String n, String c) {
        try {
            pos = Integer.parseInt(n);
            asociatedChar = c.charAt(0);
        } catch (ClassCastException cce) {
            pos = -1;
            asociatedChar = '#';
        }
    }
    
    public NumChar(int n, char c) {
        this.asociatedChar = c;
        this.pos = n;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + this.pos;
        hash = 71 * hash + this.asociatedChar;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        boolean exit = true;
        if (obj == null) {
            exit = false;
        }
        else if (getClass() != obj.getClass()) {
            exit = false;
        } else {
            final NumChar other = (NumChar) obj;
            if (this.pos != other.pos) {
                exit = false;
            }
            if (this.asociatedChar != other.asociatedChar) {
                exit = false;
            }
        }
        return exit;
    }
    
    
    
    public NumChar(String randomChar) {
        if (randomChar == null || randomChar.isEmpty()) {
            pos = 1;
            asociatedChar = 'a';
            return;
        }
        // Extraigo el caracter asociado a la posicion size(user)^2 % size(randomChar);
        int sizeRandomChar = randomChar.length();
        int sizeUser = randomChar.length();
        sizeUser *= sizeUser;       // Aquí tengo sizeUser^2
        pos = sizeUser % sizeRandomChar;

        // Genero el char asociado
        asociatedChar = randomChar.charAt(pos);
    }

    public int getPos() {
        return pos;
    }

    public char getChar() {
        return asociatedChar;
    }
}
