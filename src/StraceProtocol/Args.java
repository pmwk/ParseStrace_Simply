package src.StraceProtocol;

import java.util.ArrayList;
import java.util.HashMap;

public class Args {

    private String original;
    private char chars[];
    private ArrayList<String> parts = new ArrayList<>();
    private StringBuilder part = new StringBuilder();

    private HashMap<Character, Character> borders_map = new HashMap<>();
    private char border = '\0';
    private int depth = 0;

    private int index = -1;

    public Args(String original) {
        this.original = original;
        chars = original.toCharArray();

        borders_map.put('{', '}');
        borders_map.put('[', ']');
        borders_map.put('(', ')');
        borders_map.put('"', '"');
    }

    public boolean next() {
        index++;
        if (index < chars.length) {
            return true;
        } else {
            return false;
        }
    }

    public void reset () {
        parts = new ArrayList<>();
        part = new StringBuilder();
        index = -1;
    }

    public char getNext() {
        return chars[index];
    }

    public void calculate() {
        reset(); //сбрасываем все прошлые данные

        while (next()) {
            char current_char = getNext();

            Script script = getScript();
            switch (script) {
                case Continue:
                    _continue();
                    break;
                case EndPart:
                    _endPart();
                    break;
                case BorderStart:
                    _borderStart();
                    break;
                case BorderEnd:
                    _borderEnd();
                    break;
            }
        }
        _endPart();
    }

    private void _continue() {
        part.append(getNext());
    }

    private void _endPart() {
        parts.add(part.toString());
        part = new StringBuilder();
        if (index + 1 < chars.length) {
            index++;
        } //если это не последний аргумент, то следующий символ пробел - здесь мы его и пропускаем
    }

    private void _borderStart () {
        if (border == '\0') {
            border = getNext();
            depth = 1;
            _continue();
        } else {
            if (border == '"') {

                if (index + 1 < chars.length && chars[index+1] == ',') {
                    _borderEnd();
                } else if (index + 3 < chars.length && chars[index+1] == '.' && chars[index+2] == '.' &&  chars[index+3] == '.') {
                    _borderEnd();
                } else {
                    depth++;
                    _continue();
                }

                /*if (index + 1 < chars.length && chars[index+1] == ',') {
                    _borderEnd();
                } else {
                    depth++;
                    _continue();
                }*/
            } else {
                depth++;
                _continue();
            }
        }

    }

    private void _borderEnd() {
        depth--;
        if (depth == 0) {
            border = '\0';
        }
        _continue();
    }

    private Script getScript() {
        char current = getNext();
        if (border == '\0') {
            if (borders_map.containsKey(current)) {
                return Script.BorderStart;
            } else if (current == ',') {
                return Script.EndPart;
            }
        } else {
            if (current == border) {
                return Script.BorderStart;
            }
            if (borders_map.containsValue(current)) {
                if (borders_map.get(border) == current) {
                    return Script.BorderEnd;
                }
            }
        }
        return Script.Continue;
    } //проверяет, требуется ли текущему символу некая обработка

    public ArrayList<String> getArgs() {
        return  parts;
    }

    @Override
    public String toString() {
        return original;
    }
}

enum Script {
    Continue, EndPart, BorderStart, BorderEnd
}
